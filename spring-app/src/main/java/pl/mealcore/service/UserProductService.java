package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.response.UserProductsResponse;

import java.util.Date;

public interface UserProductService {

    void addUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date);

    boolean editUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date);

    void deleteUserProduct(@NonNull User user, @NonNull Long productId, Date date);

    UserProductsResponse getProductsWithNutrientsForUser(@NonNull User user, Date parse);
}
