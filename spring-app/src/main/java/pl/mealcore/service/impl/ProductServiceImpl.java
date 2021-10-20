package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import pl.mealcore.dao.IngredientsRepository;
import pl.mealcore.dao.NutrientsRepository;
import pl.mealcore.dao.ProductRepository;
import pl.mealcore.dao.UserProductRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.BasicNutrients;
import pl.mealcore.dto.product.Ingredients;
import pl.mealcore.dto.product.Nutrients;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.account.UserProductEntity;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.service.AdditionService;
import pl.mealcore.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final int PAGE_SIZE = 25;
    private final ProductRepository productRepository;
    private final UserProductRepository userProductRepository;
    private final NutrientsRepository nutrientsRepository;
    private final IngredientsRepository ingredientsRepository;
    private final AdditionService additionService;

    @Override
    public List<Product> getSuggestionsByName(User user, String text, int page) {
        List<Product> suggestions = productRepository.findAllByNameIgnoreCase(text).stream()
                .map(this::createBaseProduct)
                .collect(Collectors.toList());
        productRepository.findAllByNameStartsWith(text).stream()
                .map(this::createBaseProduct)
                .forEach(suggestions::add);
        productRepository.findAllByNameContains(" " + text + " ").stream()
                .map(this::createBaseProduct)
                .forEach(suggestions::add);
        productRepository.findAllByNameContains(text).stream()
                .map(this::createBaseProduct)
                .forEach(suggestions::add);
        return suggestions.stream()
                .distinct()
                .skip((long) PAGE_SIZE * page)
                .limit(PAGE_SIZE)
                .peek(product -> checkAllergensWarnings(user, product))
                .collect(Collectors.toList());
    }

    @Override
    public Product getProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .map(this::createBaseProduct)
                .orElse(null);
        completeProduct(user, product);
        return product;
    }


    @Override
    public UserProductsResponse getProductsWithNutrientsForUser(@NonNull User user, Date date) {
        List<Product> products = new ArrayList<>();
        BasicNutrients nutrients = new BasicNutrients();
        List<UserProductEntity> userProducts = userProductRepository.findAllByUserIdAndDate(user.getId(), DateHelper.getDateWithoutTime(date));
        for (UserProductEntity userProduct : userProducts) {
            Product product = createBaseProduct(userProduct.getProduct());
            completeProduct(user, product);
            product.setAddedQuantity(userProduct.getQuantity());
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
        return new UserProductsResponse(products, nutrients);
    }

    //    ----PRIVS----
    private void completeProduct(User user, Product product) {
        if (nonNull(product)) {
            product.setNutrients(nutrientsRepository.findByProductId(product.getId())
                    .map(Nutrients::new)
                    .orElse(null));
            checkAllergensWarnings(user, product);
        }
    }

    private void checkAllergensWarnings(User user, Product product) {
        if (isNull(product) || isNull(product.getIngredients()))
            return;
        List<String> allergens = Optional.ofNullable(user)
                .map(User::getAllergens)
                .orElse(Collections.emptyList());
        for (String allergen : allergens) {
            if (StringUtils.containsIgnoreCase(product.getIngredients().getIngredientsText(), allergen)) {
                product.setAllergenWarning(true);
                break;
            }
        }
    }

    private Product createBaseProduct(ProductEntity entity) {
        Product product = new Product(entity);
        product.setIngredients(ingredientsRepository.findByProductId(product.getId())
                .map(e -> new Ingredients(e, additionService.extractAdditivesFromString(e.getAdditives_tags())))
                .orElse(null));
        return product;
    }

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

        if (kcal > 0)
            nutrients.setEnergyKcal(String.valueOf(Math.floor(kcal * 100) / 100));
        if (carbohydrates > 0)
            nutrients.setCarbohydrates(String.valueOf(Math.floor(carbohydrates * 100) / 100));
        if (fat > 0)
            nutrients.setFat(String.valueOf(Math.floor(fat * 100) / 100));
        if (proteins > 0)
            nutrients.setProteins(String.valueOf(Math.floor(proteins * 100) / 100));
        if (fiber > 0)
            nutrients.setFiber(String.valueOf(Math.floor(fiber * 100) / 100));
    }
}
