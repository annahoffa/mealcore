package pl.mealcore.dto.account;

import lombok.*;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.dto.allergicReaction.AllergicReaction;
import pl.mealcore.model.allergicReaction.AllergicReactionEntity;
import pl.mealcore.model.user.additionalData.UserAllergicReactionEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserAllergicReaction extends BaseDto<UserAllergicReactionEntity> {

    private User user;
    private AllergicReaction allergicReaction;
    private Date date;

    public UserAllergicReaction(UserAllergicReactionEntity entity) {
        super(entity);
        this.user = createDto(User::new, entity.getUser());
        this.allergicReaction = createDto(AllergicReaction::new, entity.getAllergicReaction());
        this.date = entity.getDate();
    }

    @Override
    public UserAllergicReactionEntity toEntity() {
        UserAllergicReactionEntity entity = new UserAllergicReactionEntity();
        entity.setId(id);
        entity.setUser(createEntityReference(UserEntity.class, user));
        entity.setAllergicReaction(createEntityReference(AllergicReactionEntity.class, allergicReaction));
        entity.setDate(date);
        return entity;
    }
}
