package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.ReactionValue;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.model.product.ProductCategory;

import java.util.Date;

public interface UserProductService {

    void addUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category);

    boolean editUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category);

    void deleteUserProduct(@NonNull User user, @NonNull Long productId, Date date);

    UserProductsResponse getProblematicProductsForUser(User user);

    ReactionValue getReactionForProduct(User user, Long productId);

    void checkWarningsAndReactions(User user, Product product);
}
