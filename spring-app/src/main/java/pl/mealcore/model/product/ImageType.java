package pl.mealcore.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ImageType {
    MAIN(0),
    MAIN_SMALL(1),
    INGREDIENTS(2),
    INGREDIENTS_SMALL(3),
    NUTRITION(4),
    NUTRITION_SMALL(5);

    private final int code;

    public static ImageType fromCode(int code) {
        return Arrays.stream(values())
                .filter(i -> i.getCode() == code)
                .findFirst()
                .orElse(null);
    }
}
