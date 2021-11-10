package pl.mealcore.model.user.basicData;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static pl.mealcore.model.user.basicData.UserRole.ADMIN_ROLE;
import static pl.mealcore.model.user.basicData.UserRole.BASIC_USER_ROLE;


@Getter
@AllArgsConstructor
public enum AccountType {
    NORMAL(0, new String[]{BASIC_USER_ROLE.toString()}),
//    PREMIUM(1, new String[]{BASIC_USER_ROLE.toString(), PREMIUM_USER_ROLE.toString()}),
    ADMIN(5, new String[]{BASIC_USER_ROLE.toString(), ADMIN_ROLE.toString()});

    private final Integer code;
    private final String[] roles;

    public static AccountType fromCode(Integer code) {
        return Arrays.stream(values())
                .filter(t -> t.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

}
