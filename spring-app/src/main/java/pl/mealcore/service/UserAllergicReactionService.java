package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.allergicReaction.AllergicReaction;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public interface UserAllergicReactionService {

    List<AllergicReaction> getAllergicReactionsForUser(@NonNull User user, Date date);

    void updateAllergySymptoms(List<Long> symptomIds, Instant date, User user);
}
