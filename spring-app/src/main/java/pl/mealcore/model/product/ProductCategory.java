package pl.mealcore.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    BREAKFAST(0), LUNCH(1), DINNER(2), SUPPER(3), SNACK(4), OTHER(9);

    private final int code;

    public static ProductCategory fromCode(int code) {
        return Arrays.stream(values())
                .filter(i -> i.getCode() == code)
                .findFirst()
                .orElse(null);
    }
}
