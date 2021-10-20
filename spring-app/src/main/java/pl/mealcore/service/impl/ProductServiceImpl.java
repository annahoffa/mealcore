package pl.mealcore.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.IngredientsRepository;
import pl.mealcore.dao.NutrientsRepository;
import pl.mealcore.dao.ProductRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.Ingredients;
import pl.mealcore.dto.product.Nutrients;
import pl.mealcore.dto.product.Product;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.service.AdditionService;
import pl.mealcore.service.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final int PAGE_SIZE = 25;
    private final ProductRepository productRepository;
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
            checkAllergensWarnings(user, product);
        }
    }

    //    ----PRIVS----
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
}
