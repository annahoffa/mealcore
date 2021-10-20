package pl.mealcore.service;

import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.response.UserExercisesResponse;

import java.util.Date;

public interface UserExerciseService {

    void addUserExercise(@NonNull User user, @NonNull Long sportId, @NonNull Double duration, Date date);

    boolean editUserExercise(@NonNull User user, @NonNull Long sportId, @NonNull Double duration, Date date);

    void deleteUserExercise(@NonNull User user, @NonNull Long sportId, Date date);

    UserExercisesResponse getExercisesForUser(@NonNull User user, Date date);
}
