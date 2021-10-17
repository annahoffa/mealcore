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
import pl.mealcore.model.account.UserProductEntity;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.service.AdditionService;
import pl.mealcore.service.ProductService;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
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
    public UserProductsResponse getProductsWithNutrientsForUser(@NonNull User user) {
        List<Product> products = new ArrayList<>();
        BasicNutrients nutrients = new BasicNutrients();
        List<UserProductEntity> userProducts = userProductRepository.findAllByUserId(user.getId());
        for (UserProductEntity userProduct : userProducts) {
            Product product = createBaseProduct(userProduct.getProduct());
            completeProduct(user, product);
            product.setAddedQuantity(userProduct.getQuantity());
            products.add(product);
            Nutrients productNutrients = product.getNutrients();
            if (nonNull(productNutrients)) {
                double scale = userProduct.getQuantity() / 100d;
                if (nonNull(productNutrients.getEnergyKcal()))
                    nutrients.addKcal(NumberUtils.toDouble(productNutrients.getEnergyKcal()) * scale);
                else if (nonNull(productNutrients.getEnergyKj())) {
                    productNutrients.setEnergyKcal(DecimalFormat.getNumberInstance().format(NumberUtils.toDouble(productNutrients.getEnergyKj()) * 0.2390));
                    nutrients.addKcal(NumberUtils.toDouble(productNutrients.getEnergyKj()) * scale * 0.2390);
                }
                else if (nonNull(productNutrients.getEnergy())) {
                    productNutrients.setEnergyKcal(DecimalFormat.getNumberInstance().format(NumberUtils.toDouble(productNutrients.getEnergy()) * 0.2390));
                    nutrients.addKcal(NumberUtils.toDouble(productNutrients.getEnergy()) * scale * 0.2390);
                }
                nutrients.addCarbohydrates(NumberUtils.toDouble(productNutrients.getCarbohydrates()) * scale);
                nutrients.addFat(NumberUtils.toDouble(productNutrients.getFat()) * scale);
                nutrients.addProteins(NumberUtils.toDouble(productNutrients.getProteins()) * scale);
                nutrients.addFiber(NumberUtils.toDouble(productNutrients.getFiber()) * scale);
            }
        }
        nutrients.setKcal(Math.floor(nutrients.getKcal() * 100) / 100);
        nutrients.setCarbohydrates(Math.floor(nutrients.getCarbohydrates() * 100) / 100);
        nutrients.setFat(Math.floor(nutrients.getFat() * 100) / 100);
        nutrients.setProteins(Math.floor(nutrients.getProteins() * 100) / 100);
        nutrients.setFiber(Math.floor(nutrients.getFiber() * 100) / 100);
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
}
