package pl.mealcore.handler;

import org.springframework.stereotype.Component;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.response.NutritionalRequirementsResponse;
import pl.mealcore.model.user.basicData.Gender;

import static java.util.Objects.isNull;

@Component
public class CalculatorNutritionalRequirementsHandler {

    public NutritionalRequirementsResponse calculate(User user) {
        if (isNull(user.getGender()) || isNull(user.getActivityLevel()) || isNull(user.getAge()) || isNull(user.getWeight()) || isNull(user.getHeight()))
            return new NutritionalRequirementsResponse().withSuccess(false);
        Gender gender = user.getGender();
        Double kcal = calculateBMR(user);
        Double scale = kcal / gender.getKcal();
        return NutritionalRequirementsResponse.builder()
                .kcal(kcal)
                .proteins(gender.getProteins() * scale)
                .carbohydrates(gender.getCarbohydrates() * scale)
                .fat(gender.getFat() * scale)
                .fiber(gender.getFiber() * scale)
                .success(true)
                .build();
    }

    public Double calculateBMR(User user) {
        return switch (user.getGender()) {
            case MALE -> (66 + (13.7 * user.getWeight()) + (5 * user.getHeight()) - (6.8 * user.getAge())) * user.getActivityLevel().getBmrMultiplier();
            case FEMALE -> (655 + (9.6 * user.getWeight()) + (1.8 * user.getHeight()) - (4.7 * user.getAge())) * user.getActivityLevel().getBmrMultiplier();
        };
    }
}
