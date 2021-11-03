package pl.mealcore.dto.account;

import lombok.*;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.user.additionalData.UserAllergenEntity;
import pl.mealcore.model.user.basicData.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Allergen extends BaseDto<UserAllergenEntity> {
    private String allergen;
    private Long userId;

    public Allergen(UserAllergenEntity entity) {
        super(entity);
        userId = getEntityId(entity.getUser());
        allergen = entity.getAllergen();
    }

    @Override
    public UserAllergenEntity toEntity() {
        UserAllergenEntity entity = new UserAllergenEntity();
        entity.setId(id);
        entity.setUser(createEntityReference(UserEntity.class, userId));
        entity.setAllergen(allergen);
        return entity;
    }
}
