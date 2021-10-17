package pl.mealcore.model.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.converter.ActivityLevelConverter;
import pl.mealcore.model.converter.GenderConverter;
import pl.mealcore.model.converter.UserTypeConverter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users_6")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    private String login;
    private String password;
    private AccountType account_type;
    private Gender sex;
    private Integer age;
    private Double weight;
    private Double height;
    private ActivityLevel exercise_type;
    private List<AllergenEntity> allergens;


    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Convert(converter = UserTypeConverter.class)
    public AccountType getAccount_type() {
        return account_type;
    }

    @Convert(converter = GenderConverter.class)
    public Gender getSex() {
        return sex;
    }

    @Convert(converter = ActivityLevelConverter.class)
    public ActivityLevel getExercise_type() {
        return exercise_type;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    public List<AllergenEntity> getAllergens() {
        return allergens;
    }
}
