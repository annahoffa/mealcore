package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserReaction;
import pl.mealcore.model.product.ProductCategory;

import java.util.Date;
import java.util.List;

public interface UserReactionService {

    boolean addUserReaction(@NonNull User user, @NonNull ProductCategory category, @NonNull Integer value, Date date);

    boolean editUserReaction(@NonNull User user, @NonNull ProductCategory category, @NonNull Integer value, Date date);

    List<UserReaction> getReactions(@NonNull User user, Date date);
}
