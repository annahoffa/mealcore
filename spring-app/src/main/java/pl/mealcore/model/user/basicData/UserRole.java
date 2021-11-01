package pl.mealcore.model.user.basicData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    BASIC_USER_ROLE("BASIC_USER"),
    PREMIUM_USER_ROLE("PREMIUM_USER"),
    DIETICIAN_ROLE("DIETICIAN"),
    ADMIN_ROLE("ADMIN");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
