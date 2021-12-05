package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.*;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.*;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.helper.NumberHelper;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.model.user.additionalData.UserProductEntity;
import pl.mealcore.service.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static pl.mealcore.helper.AuthenticationHelper.getLoggedUserLogin;
import static pl.mealcore.helper.AuthenticationHelper.isAdmin;
import static pl.mealcore.helper.CollectionsHelper.distinctById;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final int PAGE_SIZE = 25;
    private final ProductRepository productRepository;
    private final NutrientsRepository nutrientsRepository;
    private final IngredientsRepository ingredientsRepository;
    private final UserProductRepository userProductRepository;
    private final ImageRepository imageRepository;
    private final AdditionService additionService;
    private final UserProductService userProductService;
    private final UserService userService;
    private final UserExerciseService userExerciseService;

    @Override
    public List<Product> getSuggestionsByName(User user, String text, int page) {
        List<Product> suggestions = productRepository.findAllByNameIgnoreCaseAndApprovedIsTrue(text).stream()
                .map(this::createBaseProduct)
                .collect(Collectors.toList());
        productRepository.findAllByNameStartsWithAndApprovedIsTrue(text).stream()
                .map(this::createBaseProduct)
                .forEach(suggestions::add);
        productRepository.findAllByNameContainsAndApprovedIsTrue(" " + text + " ").stream()
                .map(this::createBaseProduct)
                .forEach(suggestions::add);
        productRepository.findAllByNameContainsAndApprovedIsTrue(text).stream()
                .map(this::createBaseProduct)
                .forEach(suggestions::add);
        return suggestions.stream()
                .filter(distinctById())
                .skip((long) PAGE_SIZE * page)
                .limit(PAGE_SIZE)
                .peek(product -> userProductService.checkWarningsAndReactions(user, product))
                .collect(Collectors.toList());
    }

    @Override
    public Product getProduct(User user, Long id) {
        Product product = productRepository.findByIdAndApprovedIsTrue(id)
                .map(this::createBaseProduct)
                .orElse(null);
        completeProduct(user, product);
        return product;
    }

    @Override
    public UserProductsResponse getProductsWithNutrientsForUser(@NonNull User user, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        List<Product> products = new ArrayList<>();
        BasicNutrients nutrients = new BasicNutrients();
        List<UserProductEntity> userProducts = userProductRepository.findAllByUserIdAndDate(user.getId(), date);
        for (UserProductEntity userProduct : userProducts) {
            Product product = createBaseProduct(userProduct.getProduct());
            completeProduct(user, product);
            product.setAddedQuantity(userProduct.getQuantity());
            product.setCategory(userProduct.getCategory());
            products.add(product);
            Nutrients productNutrients = product.getNutrients();
            if (nonNull(productNutrients)) {
                double scale = userProduct.getQuantity() / 100d;
                updateNutrientsByScale(scale, productNutrients);
                nutrients.addKcal(NumberUtils.toDouble(productNutrients.getEnergyKcal()));
                nutrients.addCarbohydrates(NumberUtils.toDouble(productNutrients.getCarbohydrates()));
                nutrients.addFat(NumberUtils.toDouble(productNutrients.getFat()));
                nutrients.addProteins(NumberUtils.toDouble(productNutrients.getProteins()));
                nutrients.addFiber(NumberUtils.toDouble(productNutrients.getFiber()));
            }
        }
        double burnedKcal = userExerciseService.getExercisesForUser(user, date).getKcal();
        nutrients.addKcal(-burnedKcal);
        return new UserProductsResponse(products, nutrients, DateHelper.format(date));
    }

    @Override
    public Product createBaseProduct(ProductEntity entity) {
        Product product = new Product(entity);
        product.setIngredients(ingredientsRepository.findByProductId(product.getId())
                .map(e -> new Ingredients(e, additionService.extractAdditivesFromString(e.getAdditives_tags())))
                .orElse(null));
        return product;
    }

    @Override
    public void completeProduct(User user, Product product) {
        if (nonNull(product)) {
            product.setNutrients(nutrientsRepository.findByProductId(product.getId())
                    .map(Nutrients::new)
                    .orElse(null));
            userProductService.checkWarningsAndReactions(user, product);
        }
    }

    @Override
    public boolean addProduct(Product product, User user) {
        try {
            if (nonNull(product) && nonNull(product.getName())) {
                product.setInsertDate(new Date());
                product.setInsertBy(user);
                product.setApproved(false);
                if (isAdmin()) {
                    product.setApproved(true);
                    product.setApprovedBy(user);
                    product.setApprovedDate(new Date());
                }
                Ingredients ingredients = product.getIngredients();
                Nutrients nutrients = product.getNutrients();
                List<Image> images = product.getImages();
                Long savedId = productRepository.save(product.toEntity()).getId();
                if (nonNull(ingredients)) {
                    ingredients.setProductId(savedId);
                    ingredientsRepository.save(ingredients.toEntity());
                }
                if (nonNull(nutrients)) {
                    nutrients.setProductId(savedId);
                    nutrientsRepository.save(nutrients.toEntity());
                }
                if (nonNull(images)) {
                    images.forEach(i -> i.setProductId(savedId));
                    imageRepository.saveAll(
                            images.stream()
                                    .map(Image::toEntity)
                                    .collect(Collectors.toList()));
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Adding product broke by: ", e);
            return false;
        }
    }

    @Override
    public List<Product> getUnapprovedProducts() {
        if (isAdmin())
            return productRepository.findAllByApprovedIsFalse().stream()
                    .map(this::createBaseProduct)
                    .peek(p -> completeProduct(null, p))
                    .collect(Collectors.toList());
        else
            return Collections.emptyList();
    }

    @Override
    public void approveProduct(Long productId) {
        if (isAdmin()) {
            productRepository.findById(productId)
                    .ifPresent(productEntity -> {
                        Product product = new Product(productEntity);
                        product.setApproved(true);
                        product.setApprovedBy(userService.getByLogin(getLoggedUserLogin()));
                        product.setApprovedDate(new Date());
                        productRepository.save(product.toEntity());
                    });
        }
    }

    @Override
    public void dismissProduct(Long productId) {
        if (isAdmin() && !productRepository.findById(productId)
                .map(ProductEntity::isApproved)
                .orElse(true))
            productRepository.deleteById(productId);
    }

    //  PRIVS
    private void updateNutrientsByScale(Double scale, Nutrients nutrients) {
        double kcal = 0;
        double carbohydrates = NumberUtils.toDouble(nutrients.getCarbohydrates()) * scale;
        double fat = NumberUtils.toDouble(nutrients.getFat()) * scale;
        double proteins = NumberUtils.toDouble(nutrients.getProteins()) * scale;
        double fiber = NumberUtils.toDouble(nutrients.getFiber()) * scale;

        if (nonNull(nutrients.getEnergyKcal()))
            kcal = NumberUtils.toDouble(nutrients.getEnergyKcal()) * scale;
        else if (nonNull(nutrients.getEnergyKj()))
            kcal = NumberUtils.toDouble(nutrients.getEnergyKj()) * 0.2390 * scale;
        else if (nonNull(nutrients.getEnergy()))
            kcal = NumberUtils.toDouble(nutrients.getEnergy()) * 0.2390 * scale;

        nutrients.setEnergyKcal(NumberHelper.format(kcal));
        nutrients.setCarbohydrates(NumberHelper.format(carbohydrates));
        nutrients.setFat(NumberHelper.format(fat));
        nutrients.setProteins(NumberHelper.format(proteins));
        nutrients.setFiber(NumberHelper.format(fiber));
    }
}
