package pl.mealcore.dto.account;

import lombok.*;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.user.additionalData.UserNoteEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserNote extends BaseDto<UserNoteEntity> {

    private User user;
    private Date date;
    private String text;

    public UserNote(UserNoteEntity entity) {
        super(entity);
        this.user = createDto(User::new, entity.getUser());
        this.date = entity.getDate();
        this.text = entity.getText();
    }

    @Override
    public UserNoteEntity toEntity() {
        UserNoteEntity entity = new UserNoteEntity();
        entity.setId(id);
        entity.setUser(createEntityReference(UserEntity.class, user));
        entity.setDate(date);
        entity.setText(text);
        return entity;
    }
}
