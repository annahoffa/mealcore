package pl.mealcore.dto.response;

import lombok.Data;
import pl.mealcore.dto.sport.Sport;

import java.util.List;

@Data
public class UserExercisesResponse {
    private List<Sport> exercises;
    private double kcal;
    private String date;
    private boolean success;

    public UserExercisesResponse(List<Sport> exercises, String date) {
        this.exercises = exercises;
        this.kcal = exercises.stream()
                .mapToDouble(Sport::getCalculatedKcal)
                .sum();
        this.date = date;
    }

    public UserExercisesResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
