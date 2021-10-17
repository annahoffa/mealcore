package pl.mealcore.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE('m', 2500.0, 60.0, 340.0, 80.0, 25.0),
    FEMALE('k', 2000.0, 50.0, 270.0, 70.0, 25.0);
    private final Character code;
    private final Double kcal;
    private final Double proteins;
    private final Double carbohydrates;
    private final Double fat;
    private final Double fiber;

    public static Gender fromCode(Character code) {
        return Arrays.stream(values())
                .filter(g -> g.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
