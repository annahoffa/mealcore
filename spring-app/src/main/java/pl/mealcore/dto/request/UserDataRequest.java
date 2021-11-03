package pl.mealcore.dto.request;

import lombok.Data;
import pl.mealcore.model.user.basicData.ActivityLevel;
import pl.mealcore.model.user.basicData.Gender;

import java.util.List;

@Data
public class UserDataRequest {
    private Gender gender;
    private Integer age;
    private Double weight;
    private Double height;
    private ActivityLevel activityLevel;
    private List<String> allergens;
}
