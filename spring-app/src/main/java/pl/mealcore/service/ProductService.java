package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.response.UserProductsResponse;

import java.util.List;

public interface ProductService {
    List<Product> getSuggestionsByName(User user, String text, int page);

    Product getProduct(User user, Long id);

    UserProductsResponse getProductsWithNutrientsForUser(@NonNull User user);
}
