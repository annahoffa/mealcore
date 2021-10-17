package pl.mealcore.dto.account;

import lombok.*;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.account.AllergenEntity;
import pl.mealcore.model.account.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Allergen extends BaseDto<AllergenEntity> {
    private String allergen;
    private Long userId;

    public Allergen(AllergenEntity entity) {
        super(entity);
        userId = getEntityId(entity.getUser());
        allergen = entity.getAllergen();
    }

    @Override
    public AllergenEntity toEntity() {
        AllergenEntity entity = new AllergenEntity();
        entity.setId(id);
        entity.setUser(createEntityReference(UserEntity.class, userId));
        entity.setAllergen(allergen);
        return entity;
    }
}
