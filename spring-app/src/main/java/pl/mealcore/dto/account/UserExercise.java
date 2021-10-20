package pl.mealcore.dto.account;

import lombok.*;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.dto.sport.Sport;
import pl.mealcore.model.account.UserEntity;
import pl.mealcore.model.account.UserExerciseEntity;
import pl.mealcore.model.sport.SportEntity;

import java.util.Date;

import static java.util.Objects.isNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserExercise extends BaseDto<UserExerciseEntity> {

    private User user;
    private Sport sport;
    private Date date;
    private Double duration;

    public UserExercise(UserExerciseEntity entity) {
        super(entity);
        this.user = createDto(User::new, entity.getUser());
        this.sport = createDto(Sport::new, entity.getSport());
        this.date = entity.getDate();
        this.duration = entity.getDuration();
    }

    @Override
    public UserExerciseEntity toEntity() {
        UserExerciseEntity entity = new UserExerciseEntity();
        entity.setId(id);
        entity.setUser(createEntityReference(UserEntity.class, user));
        entity.setSport(createEntityReference(SportEntity.class, sport));
        entity.setDate(date);
        entity.setDuration(duration);
        return entity;
    }

    public void addDuration(Double toAdd) {
        if (isNull(toAdd))
            return;
        duration = isNull(duration) ? toAdd : duration + toAdd;
    }
}
