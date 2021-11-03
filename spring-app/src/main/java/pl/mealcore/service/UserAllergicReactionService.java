package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.allergicReaction.AllergicReaction;

import java.util.Date;

public interface UserAllergicReactionService {

    void addEditUserAllergicReaction(@NonNull User user, @NonNull Long allergicReactionId, Date date);

    void deleteUserAllergicReaction(@NonNull User user, Date date);

    AllergicReaction getAllergicReactionsForUser(@NonNull User user, Date date);
}
