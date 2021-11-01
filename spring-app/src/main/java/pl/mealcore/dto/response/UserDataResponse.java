package pl.mealcore.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mealcore.dto.account.User;
import pl.mealcore.model.account.AccountType;
import pl.mealcore.model.account.ActivityLevel;
import pl.mealcore.model.account.Gender;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDataResponse extends BasicResponse{
    private String login;
    private AccountType type;
    private Gender gender;
    private Integer age;
    private Double weight;
    private Double height;
    private ActivityLevel activityLevel;
    private List<String> allergens;

    public UserDataResponse(User user) {
        this.login = user.getLogin();
        this.type = user.getType();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.weight = user.getWeight();
        this.height = user.getHeight();
        this.activityLevel = user.getActivityLevel();
        this.allergens = user.getAllergens();
    }

    @Override
    public UserDataResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
