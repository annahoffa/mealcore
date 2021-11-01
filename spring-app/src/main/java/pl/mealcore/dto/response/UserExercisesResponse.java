package pl.mealcore.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mealcore.dto.sport.Sport;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserExercisesResponse extends BasicResponse{
    private List<Sport> exercises;
    private double kcal;
    private String date;

    public UserExercisesResponse(List<Sport> exercises, String date) {
        this.exercises = exercises;
        this.kcal = exercises.stream()
                .mapToDouble(Sport::getCalculatedKcal)
                .sum();
        this.date = date;
    }

    @Override
    public UserExercisesResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
