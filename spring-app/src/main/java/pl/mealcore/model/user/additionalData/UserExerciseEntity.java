package pl.mealcore.model.user.additionalData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.sport.SportEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users_exercises_12")
@EqualsAndHashCode(callSuper = true)
public class UserExerciseEntity extends BaseEntity {

    private UserEntity user;
    private SportEntity sport;
    private Date date;
    private Double duration;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    @ManyToOne
    public SportEntity getSport() {
        return sport;
    }
}
