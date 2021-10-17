package pl.mealcore.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum ActivityLevel {
    LACK_OF_ACTIVITY(0, 1.2),
    LITTLE_ACTIVITY(1, 1.375),
    MODERATE_ACTIVITY(2, 1.55),
    LOT_OF_ACTIVITY(3, 1.725),
    VERY_ACTIVE(4, 1.9);

    private final Integer code;
    private final double bmrMultiplier;

    public static ActivityLevel fromCode(Integer code) {
        return Arrays.stream(values())
                .filter(t -> t.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

}
