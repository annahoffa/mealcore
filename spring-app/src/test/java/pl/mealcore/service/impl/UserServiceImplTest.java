package pl.mealcore.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mealcore.MealCoreApplication;
import pl.mealcore.dao.AllergenRepository;
import pl.mealcore.dao.UserRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.error.UserAlreadyExistException;
import pl.mealcore.error.UserInvalidCredentialsException;
import pl.mealcore.error.UserInvalidPersonalDataException;
import pl.mealcore.error.UserWrongPasswordException;
import pl.mealcore.model.user.additionalData.UserAllergenEntity;
import pl.mealcore.model.user.basicData.AccountType;
import pl.mealcore.model.user.basicData.ActivityLevel;
import pl.mealcore.model.user.basicData.Gender;
import pl.mealcore.model.user.basicData.UserEntity;
import pl.mealcore.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MealCoreApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(
        locations = "classpath:application-test.properties")
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AllergenRepository allergenRepository;
    @Autowired
    private UserService userService;

    private final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        UserEntity user = new UserEntity();
        user.setLogin("test@test.pl");
        user.setPassword(PASSWORD_ENCODER.encode("testtest"));
        user.setAccount_type(AccountType.NORMAL);
        user.setSex(Gender.FEMALE);
        user.setAge(20);
        user.setWeight(50.5);
        user.setHeight(160d);
        user.setExercise_type(ActivityLevel.LITTLE_ACTIVITY);
        UserEntity saved = userRepository.save(user);
        UserAllergenEntity userAllergenEntity = new UserAllergenEntity();
        userAllergenEntity.setUser(saved);
        userAllergenEntity.setAllergen("mleko");
        allergenRepository.save(userAllergenEntity);

        UserEntity admin = new UserEntity();
        admin.setLogin("admin@test.pl");
        admin.setPassword(PASSWORD_ENCODER.encode("adminadmin"));
        admin.setAccount_type(AccountType.ADMIN);
        admin.setSex(Gender.MALE);
        admin.setAge(22);
        admin.setWeight(70.5);
        admin.setHeight(180d);
        admin.setExercise_type(ActivityLevel.VERY_ACTIVE);
        userRepository.save(admin);
    }

    @AfterEach
    public void cleanUp() {
        allergenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void when_login_exists_registerNewUser_should_throw_UserAlreadyExistException() {
        UserEntity user = new UserEntity();
        user.setLogin("test@test.pl");
        user.setPassword("testtest");
        user.setAccount_type(AccountType.NORMAL);
        user.setSex(Gender.FEMALE);
        user.setAge(20);
        user.setWeight(50.5);
        user.setHeight(160d);
        user.setExercise_type(ActivityLevel.LITTLE_ACTIVITY);

        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUser(new User(user)));
    }

    @Test
    void when_invalid_password_registerNewUser_should_throw_UserInvalidCredentialsException() {
        UserEntity user = new UserEntity();
        user.setLogin("test2@test.pl");
        user.setPassword("testt");
        user.setAccount_type(AccountType.NORMAL);
        user.setSex(Gender.FEMALE);
        user.setAge(20);
        user.setWeight(50.5);
        user.setHeight(160d);
        user.setExercise_type(ActivityLevel.LITTLE_ACTIVITY);

        assertThrows(UserInvalidCredentialsException.class, () -> userService.registerNewUser(new User(user)));
    }

    @Test
    void when_invalid_login_registerNewUser_should_throw_UserInvalidCredentialsException() {
        UserEntity user = new UserEntity();
        user.setLogin("tes");
        user.setPassword("testttest");
        user.setAccount_type(AccountType.NORMAL);
        user.setSex(Gender.FEMALE);
        user.setAge(20);
        user.setWeight(50.5);
        user.setHeight(160d);
        user.setExercise_type(ActivityLevel.LITTLE_ACTIVITY);

        assertThrows(UserInvalidCredentialsException.class, () -> userService.registerNewUser(new User(user)));
    }

    @Test
    void when_invalid_personalData_registerNewUser_should_throw_UserInvalidPersonalDataException() {
        UserEntity user = new UserEntity();
        user.setLogin("test2@test.pl");
        user.setPassword("testtest2");
        user.setAccount_type(AccountType.NORMAL);
        user.setAge(-5);

        assertThrows(UserInvalidPersonalDataException.class, () -> userService.registerNewUser(new User(user)));
    }

    @Test
    void when_valid_data_registerNewUser_should_save_user() {
        UserEntity user = new UserEntity();
        user.setLogin("test2@test.pl");
        user.setPassword("testtest2");
        user.setAccount_type(AccountType.NORMAL);
        user.setAge(50);

        assertDoesNotThrow(() -> userService.registerNewUser(new User(user)));

        User saved = userService.getByLogin("test2@test.pl");

        assertEquals("test2@test.pl", saved.getLogin());
        assertEquals(AccountType.NORMAL, saved.getType());
        assertEquals(50, saved.getAge());
    }

    @Test
    void when_invalid_password_changeUserPassword_should_throw_UserWrongPasswordException() {
        User user = userService.getByLogin("test@test.pl");

        assertThrows(UserWrongPasswordException.class, () -> userService.changeUserPassword(user, "ivalidPassword", "newPassword"));
    }

    @Test
    void when_invalid_newpassword_changeUserPassword_should_throw_UserInvalidCredentialsException() {
        User user = userService.getByLogin("test@test.pl");

        assertThrows(UserInvalidCredentialsException.class, () -> userService.changeUserPassword(user, "testtest", "aaa"));
    }

    @Test
    void when_login_exists_changeUserLogin_should_throw_UserAlreadyExistException() {
        User user = userService.getByLogin("test@test.pl");

        assertThrows(UserAlreadyExistException.class, () -> userService.changeUserLogin(user, "admin@test.pl", "testtest"));
    }

    @Test
    void when_login_invalid_changeUserLogin_should_throw_UserInvalidCredentialsException() {
        User user = userService.getByLogin("test@test.pl");

        assertThrows(UserInvalidCredentialsException.class, () -> userService.changeUserLogin(user, "a", "testtest"));
    }

    @Test
    void when_password_invalid_changeUserLogin_should_throw_UserWrongPasswordException() {
        User user = userService.getByLogin("test@test.pl");

        assertThrows(UserWrongPasswordException.class, () -> userService.changeUserLogin(user, "test2@test.pl", "as"));
    }

    @Test
    void when_login_valid_changeUserLogin_should_change_login() {
        User user = userService.getByLogin("test@test.pl");

        assertDoesNotThrow(() -> userService.changeUserLogin(user, "test123@test.pl", "testtest"));

        assertNull(userService.getByLogin("test@test.pl"));
        assertNotNull(userService.getByLogin("test123@test.pl"));
    }

}