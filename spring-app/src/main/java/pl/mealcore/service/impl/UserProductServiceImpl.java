package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.UserProductRepository;
import pl.mealcore.dao.UserReactionRepository;
import pl.mealcore.dto.account.ReactionValue;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserProduct;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.user.additionalData.UserProductEntity;
import pl.mealcore.model.user.additionalData.UserReactionEntity;
import pl.mealcore.service.UserProductService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {
    private final UserProductRepository userProductRepository;
    private final UserReactionRepository userReactionRepository;

    @Override
    public void addUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserProduct toSave = userProductRepository.findByUserIdAndProductIdAndDate(user.getId(), productId, date)
                .map(UserProduct::new)
                .orElse(new UserProduct(user, new Product(productId), date, 0, isNull(category) ? ProductCategory.OTHER : category));
        toSave.addQuantity(quantity);
        userProductRepository.save(toSave.toEntity());
    }

    @Override
    public boolean editUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserProductEntity userProduct = userProductRepository.findByUserIdAndProductIdAndDate(user.getId(), productId, date).orElse(null);
        if (nonNull(userProduct)) {
            userProduct.setQuantity(quantity);
            userProduct.setCategory(isNull(category) ? ProductCategory.OTHER : category);
            userProductRepository.save(userProduct);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUserProduct(User user, Long productId, Date date) {
        userProductRepository.findByUserIdAndProductIdAndDate(user.getId(), productId, DateHelper.getDateWithoutTime(date, new Date()))
                .ifPresent(userProductRepository::delete);
    }

    @Override
    public UserProductsResponse getProblematicProductsForUser(User user) {
        return new UserProductsResponse(userProductRepository.findAllByUserId(user.getId()).stream()
                .map(UserProductEntity::getProduct)
                .distinct()
                .map(Product::new)
                .filter(product -> ReactionValue.BAD.equals(getReactionForProduct(user, product.getId())))
                .collect(Collectors.toList()));
    }

    @Override
    public ReactionValue getReactionForProduct(User user, Long productId) {
        int goodCount = 0;
        int badCount = 0;
        for (UserReactionEntity reaction : userReactionRepository.findAllByUserId(user.getId()))
            if (getProductsForReaction(reaction).stream().anyMatch(p -> p.getId().equals(productId))) {
                if (reaction.getValue() < 3)
                    badCount++;
                if (reaction.getValue() > 3)
                    goodCount++;
            }
        if (goodCount > badCount)
            return ReactionValue.GOOD;
        if (goodCount < badCount)
            return ReactionValue.BAD;
        return ReactionValue.NONE;
    }

    @Override
    public void checkWarningsAndReactions(User user, Product product) {
        if (isNull(product) || isNull(product.getIngredients()))
            return;
        if (nonNull(user)) {
            for (String allergen : user.getAllergens()) {
                if (StringUtils.containsIgnoreCase(product.getIngredients().getIngredientsText(), allergen)) {
                    product.setAllergenWarning(true);
                    break;
                }
            }
            ReactionValue reaction = getReactionForProduct(user, product.getId());
            if (ReactionValue.GOOD.equals(reaction))
                product.setGoodReaction(true);
            if (ReactionValue.BAD.equals(reaction))
                product.setBadReaction(true);
        }
    }

    private List<Product> getProductsForReaction(UserReactionEntity reaction) {
        return userProductRepository.findAllByUserIdAndDateAndCategory(reaction.getUser().getId(), reaction.getDate(),
                reaction.getCategory()).stream()
                .map(UserProductEntity::getProduct)
                .map(Product::new)
                .collect(Collectors.toList());
    }
}
