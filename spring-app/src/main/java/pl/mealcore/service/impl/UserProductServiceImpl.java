package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.UserAllergicReactionRepository;
import pl.mealcore.dao.UserProductRepository;
import pl.mealcore.dao.UserReactionRepository;
import pl.mealcore.dto.account.ReactionValue;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserProduct;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.user.additionalData.UserAllergicReactionEntity;
import pl.mealcore.model.user.additionalData.UserProductEntity;
import pl.mealcore.model.user.additionalData.UserReactionEntity;
import pl.mealcore.service.AdditionService;
import pl.mealcore.service.UserProductService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.helper.CollectionsHelper.distinctDtosById;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {
    private final UserProductRepository userProductRepository;
    private final UserReactionRepository userReactionRepository;
    private final UserAllergicReactionRepository userAllergicReactionRepository;
    private final AdditionService additionService;

    @Override
    public void addUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserProduct toSave = userProductRepository.findByUserIdAndProductIdAndDateAndCategory(user.getId(), productId, date, category)
                .map(entity -> new UserProduct(entity, additionService.extractAdditives(entity.getProduct())))
                .orElse(new UserProduct(user, new Product(productId), date, 0, isNull(category) ? ProductCategory.OTHER : category));
        toSave.addQuantity(quantity);
        userProductRepository.save(toSave.toEntity());
    }

    @Override
    public boolean editUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserProductEntity userProduct = userProductRepository.findByUserIdAndProductIdAndDateAndCategory(user.getId(), productId, date, category).orElse(null);
        if (nonNull(userProduct)) {
            userProduct.setQuantity(quantity);
            userProduct.setCategory(isNull(category) ? ProductCategory.OTHER : category);
            userProductRepository.save(userProduct);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUserProduct(User user, Long productId, ProductCategory category, Date date) {
        userProductRepository.findByUserIdAndProductIdAndDateAndCategory(user.getId(), productId, DateHelper.getDateWithoutTime(date, new Date()), category)
                .ifPresent(userProductRepository::delete);
    }

    @Override
    public UserProductsResponse getProblematicProductsForUser(User user) {
        return new UserProductsResponse(userProductRepository.findAllByUserId(user.getId()).stream()
                .map(UserProductEntity::getProduct)
                .map(product -> new Product(product, additionService.extractAdditives(product)))
                .filter(distinctDtosById())
                .filter(product -> ReactionValue.BAD.equals(getReactionForProduct(user, product.getId())))
                .collect(Collectors.toList()));
    }

    @SuppressWarnings("squid:S3518")
    @Override
    public ReactionValue getReactionForProduct(User user, Long productId) {
        double count = 0;
        double value = 0;
        for (UserReactionEntity reaction : userReactionRepository.findAllByUserId(user.getId()))
            if (getProductsForReaction(reaction).stream().anyMatch(p -> p.getId().equals(productId))) {
                value+=reaction.getValue();
                count+=1;
            }
        for (UserAllergicReactionEntity reaction : userAllergicReactionRepository.findAllByUserId(user.getId()))
            if (getProductsForReaction(reaction).stream().anyMatch(p -> p.getId().equals(productId))) {
                count += 1;
                value += 2;
            }

        if (count >= 5) {
            double score = value / count;
            if (score > 4)
                return ReactionValue.GOOD;
            if (score < 2)
                return ReactionValue.BAD;
        }
        return ReactionValue.NONE;
    }

    @Override
    public Product checkWarningsAndReactions(User user, Product product) {
        if (isNull(product) || isNull(user))
            return product;
        if (nonNull(product.getIngredients())) {
            for (String allergen : user.getAllergens()) {
                if (StringUtils.containsIgnoreCase(product.getIngredients().getIngredientsText(), allergen)) {
                    product.setAllergenWarning(true);
                    break;
                }
            }
        }
        ReactionValue reaction = getReactionForProduct(user, product.getId());
        if (ReactionValue.GOOD.equals(reaction))
            product.setGoodReaction(true);
        if (ReactionValue.BAD.equals(reaction))
            product.setBadReaction(true);
        return product;
    }

    private List<Product> getProductsForReaction(UserReactionEntity reaction) {
        return userProductRepository.findAllByUserIdAndDateAndCategory(reaction.getUser().getId(), reaction.getDate(),
                reaction.getCategory()).stream()
                .map(UserProductEntity::getProduct)
                .map(product -> new Product(product, additionService.extractAdditives(product)))
                .collect(Collectors.toList());
    }

    private List<Product> getProductsForReaction(UserAllergicReactionEntity reaction) {
        return userProductRepository.findAllByUserIdAndDate(reaction.getUser().getId(), reaction.getDate()).stream()
                .map(UserProductEntity::getProduct)
                .map(product -> new Product(product, additionService.extractAdditives(product)))
                .collect(Collectors.toList());
    }
}
