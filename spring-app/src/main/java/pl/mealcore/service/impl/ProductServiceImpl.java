package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.*;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.*;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.dto.statistic.StatisticNutrients;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.helper.NumberHelper;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.model.user.additionalData.UserProductEntity;
import pl.mealcore.service.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.dto.product.ProductSortType.ALPHABETIC;
import static pl.mealcore.helper.AuthenticationHelper.getLoggedUserLogin;
import static pl.mealcore.helper.AuthenticationHelper.isAdmin;
import static pl.mealcore.helper.CollectionsHelper.distinctEntitiesById;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

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
    public List<Product> getSuggestionsByName(String text) {
        List<ProductEntity> suggestions = productRepository.findAllByNameIgnoreCaseAndApprovedIsTrue(text);
        suggestions.addAll(productRepository.findAllByNameStartsWithAndApprovedIsTrue(text));
        suggestions.addAll(productRepository.findAllByNameContainsAndApprovedIsTrue(" " + text + " "));
        suggestions.addAll(productRepository.findAllByNameContainsAndApprovedIsTrue(text));
        return suggestions.stream()
                .filter(distinctEntitiesById())
                .map(this::createBaseProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> applyFilters(List<Product> suggestions, Integer kcalFrom, Integer kcalTo, String makeQuery) {
        return suggestions.stream()
                .filter(p -> {
                    if (isNull(kcalFrom) || isNull(p.getNutrients()))
                        return true;
                    return kcalFrom <= p.getNutrients().getKcal();
                })
                .filter(p -> {
                    if (isNull(kcalTo) || isNull(p.getNutrients()))
                        return true;
                    return kcalTo >= p.getNutrients().getKcal();
                })
                .filter(p -> isNull(makeQuery) || StringUtils.containsIgnoreCase(p.getBrands(), makeQuery))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> sort(List<Product> suggestions, ProductSortType sortType, boolean reverseSort) {
        if (nonNull(sortType)) {
            if (!ALPHABETIC.equals(sortType))
                suggestions = suggestions.stream()
                        .filter(p -> nonNull(p.getNutrients()))
                        .collect(Collectors.toList());
            Comparator<Product> comparator;
            double defaultValue = reverseSort ? Double.MIN_VALUE : Double.MAX_VALUE;
            switch (sortType) {
                case KCAL -> comparator = Comparator.comparing((Product p) -> p.getNutrients().getKcal(defaultValue));
                case FAT -> comparator = Comparator.comparing((Product p) -> NumberUtils.toDouble(p.getNutrients().getFat(), defaultValue));
                case PROTEINS -> comparator = Comparator.comparing((Product p) -> NumberUtils.toDouble(p.getNutrients().getProteins(), defaultValue));
                case CARBOHYDRATES -> comparator = Comparator.comparing((Product p) -> NumberUtils.toDouble(p.getNutrients().getCarbohydrates(), defaultValue));
                default -> comparator = Comparator.comparing(Product::getName);
            }
            suggestions.sort(reverseSort ? comparator.reversed() : comparator);
        }
        return suggestions;
    }

    @Override
    public Product getProduct(User user, Long id) {
        return productRepository.findByIdAndApprovedIsTrue(id)
                .map(this::createBaseProduct)
                .map(p -> userProductService.checkWarningsAndReactions(user, p))
                .orElse(null);
    }

    @Override
    public UserProductsResponse getProductsWithNutrientsForUser(@NonNull User user, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        List<Product> products = new ArrayList<>();
        BasicNutrients nutrients = new BasicNutrients();
        List<UserProductEntity> userProducts = userProductRepository.findAllByUserIdAndDate(user.getId(), date);
        for (UserProductEntity userProduct : userProducts) {
            Product product = createBaseProduct(userProduct.getProduct());
            userProductService.checkWarningsAndReactions(user, product);
            product.setAddedQuantity(userProduct.getQuantity());
            product.setCategory(userProduct.getCategory());
            products.add(product);
            Nutrients productNutrients = product.getNutrients();
            if (nonNull(productNutrients)) {
                double scale = userProduct.getQuantity() / 100d;
                updateNutrientsByScale(scale, productNutrients);
                nutrients.addKcal(productNutrients.getKcal());
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
    public List<StatisticNutrients> getStatisticsForUser(User user, Date fromDate, Date toDate) {
        ArrayList<StatisticNutrients> result = new ArrayList<>();
        Date date = fromDate;
        while (!date.after(toDate)) {
            StatisticNutrients item = new StatisticNutrients();
            UserProductsResponse productsForDay = getProductsWithNutrientsForUser(user, date);
            item.setKcal(productsForDay.getKcal());
            item.setCarbohydrates(productsForDay.getCarbohydrates());
            item.setFat(productsForDay.getFat());
            item.setProteins(productsForDay.getProteins());
            item.setFiber(productsForDay.getFiber());
            item.setDate(DateHelper.format(date));
            result.add(item);
            date = DateHelper.addDays(date, 1);
        }
        return result;
    }

    @Override
    public Product createBaseProduct(ProductEntity entity) {
        return new Product(entity, additionService.extractAdditives(entity));
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
                    .collect(Collectors.toList());
        else
            return Collections.emptyList();
    }

    @Override
    public void approveProduct(Long productId) {
        if (isAdmin()) {
            productRepository.findById(productId)
                    .ifPresent(productEntity -> {
                        Product product = new Product(productEntity, additionService.extractAdditives(productEntity));
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
        double kcal = nutrients.getKcal() * scale;
        double carbohydrates = NumberUtils.toDouble(nutrients.getCarbohydrates()) * scale;
        double fat = NumberUtils.toDouble(nutrients.getFat()) * scale;
        double proteins = NumberUtils.toDouble(nutrients.getProteins()) * scale;
        double fiber = NumberUtils.toDouble(nutrients.getFiber()) * scale;

        nutrients.setEnergyKcal(NumberHelper.format(kcal));
        nutrients.setCarbohydrates(NumberHelper.format(carbohydrates));
        nutrients.setFat(NumberHelper.format(fat));
        nutrients.setProteins(NumberHelper.format(proteins));
        nutrients.setFiber(NumberHelper.format(fiber));
    }
}
