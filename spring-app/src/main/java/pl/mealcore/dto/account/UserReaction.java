package pl.mealcore.dto.account;

import lombok.*;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.user.additionalData.UserReactionEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserReaction extends BaseDto<UserReactionEntity> {
    private ProductCategory category;
    private Integer value;
    private Date date;
    private User user;

    public UserReaction(UserReactionEntity entity) {
        super(entity);
        this.category = entity.getCategory();
        this.value = entity.getValue();
        this.date = entity.getDate();
        this.user = createDto(User::new, entity.getUser());
    }

    @Override
    public UserReactionEntity toEntity() {
        UserReactionEntity entity = new UserReactionEntity();
        entity.setId(id);
        entity.setCategory(category);
        entity.setValue(value);
        entity.setDate(date);
        entity.setUser(createEntityReference(UserEntity.class, user));
        return entity;
    }
}
