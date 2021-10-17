package pl.mealcore.dto.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.account.AccountType;
import pl.mealcore.model.account.ActivityLevel;
import pl.mealcore.model.account.Gender;
import pl.mealcore.model.account.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static pl.mealcore.model.account.AccountType.NORMAL;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDto<UserEntity> {
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    public static final int MAX_ALLERGEN_LENGTH = 100;
    public static final int MAX_PASSWORD_LENGTH = 100;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_LOGIN_LENGTH = 50;
    public static final int MIN_LOGIN_LENGTH = 4;

    private String login;
    private String password;
    private AccountType type;
    private Gender gender;
    private Integer age;
    private Double weight;
    private Double height;
    private ActivityLevel activityLevel;
    private List<String> allergens;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(UserEntity entity) {
        super(entity);
        login = entity.getLogin();
        password = entity.getPassword();
        type = entity.getAccount_type();
        gender = entity.getSex();
        age = entity.getAge();
        weight = entity.getWeight();
        height = entity.getHeight();
        activityLevel = entity.getExercise_type();
        allergens = new ArrayList<>();
    }

    @Override
    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(id);
        entity.setLogin(login);
        entity.setPassword(password);
        entity.setAccount_type(type);
        entity.setSex(gender);
        entity.setAge(age);
        entity.setWeight(weight);
        entity.setHeight(height);
        entity.setExercise_type(activityLevel);
        return entity;
    }

    public org.springframework.security.core.userdetails.User toUserDetails() {
        return new org.springframework.security.core.userdetails.User(
                this.login,
                this.password,
                AuthorityUtils.createAuthorityList(Optional.ofNullable(this.type)
                        .map(AccountType::getRoles)
                        .orElse(NORMAL.getRoles())));
    }

    public void hashPassword() {
        this.password = PASSWORD_ENCODER.encode(this.password);
    }
}