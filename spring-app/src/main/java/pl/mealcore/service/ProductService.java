package pl.mealcore.service;

import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.model.product.ProductEntity;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Product> getSuggestionsByName(User user, String text, int page);

    Product getProduct(User user, Long id);

    Product createBaseProduct(ProductEntity entity);

    void completeProduct(User user, Product product);

    UserProductsResponse getProductsWithNutrientsForUser(User user, Date parse);

    boolean addProduct(Product product);
}
