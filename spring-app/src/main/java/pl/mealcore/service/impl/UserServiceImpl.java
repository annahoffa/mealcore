package pl.mealcore.service.impl;

import javassist.tools.web.BadHttpRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.AllergenRepository;
import pl.mealcore.dao.UserProductRepository;
import pl.mealcore.dao.UserRepository;
import pl.mealcore.dto.account.Allergen;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserProduct;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.request.UserDataRequest;
import pl.mealcore.error.*;
import pl.mealcore.model.account.AccountType;
import pl.mealcore.model.account.AllergenEntity;
import pl.mealcore.service.UserService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.dto.account.User.*;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AllergenRepository allergenRepository;
    private final UserProductRepository userProductRepository;

    //    PUBLIC
    @Override
    public User registerNewUser(User user) throws UserAlreadyExistException, UserInvalidCredentialsException, UserInvalidPersonalDataException {
        if (!validateCredentials(user))
            throw new UserInvalidCredentialsException();
        if (!validatePersonalData(user))
            throw new UserInvalidPersonalDataException();
        if (userExists(user.getLogin()))
            throw new UserAlreadyExistException(user.getLogin());
        user.setType(AccountType.NORMAL);
        return save(user, true);
    }

    @Override
    public void changeUserPassword(User user, String oldPassword, String newPassword) throws UserInvalidCredentialsException, UserWrongPasswordException, BadSessionException {
        if (isNull(user))
            throw new BadSessionException("Nie znaleziono zalogowanego użytkownika.");
        if (!isPasswordValidForUser(oldPassword, user))
            throw new UserWrongPasswordException("Wprowadzono złe hasło.");
        if (!validatePassword(newPassword))
            throw new UserInvalidCredentialsException("Nowe hasło musi składać się od 8 do 100 znaków.");
        user.setPassword(newPassword);
        save(user, true);
    }

    @Override
    public void changeUserLogin(User user, String newLogin, String password) throws BadSessionException, UserWrongPasswordException, UserInvalidCredentialsException, UserAlreadyExistException {
        if (userExists(newLogin))
            throw new UserAlreadyExistException("Nazwa: '" + newLogin + "' jest juz zajęta.");
        if (isNull(user))
            throw new BadSessionException("Nie znaleziono zalogowanego użytkownika.");
        if (!isPasswordValidForUser(password, user))
            throw new UserWrongPasswordException("Wprowadzono złe hasło.");
        if (!validateLogin(newLogin))
            throw new UserInvalidCredentialsException("Nowy login musi składać się od 4 do 50 znaków.");
        user.setLogin(newLogin);
        save(user, false);
    }

    @Override
    @Transactional
    public User save(User user, boolean isRawPassword) {
        if (isRawPassword)
            user.hashPassword();
        User saved = new User(userRepository.save(user.toEntity()));
        saveAllergens(user.getAllergens(), saved);
        return saved;
    }


    private void saveAllergens(List<String> allergensFromUser, User saved) {
        List<AllergenEntity> allergensFromDb = allergenRepository.findAllByUserId(saved.getId());
        List<String> allergensToSave = allergensFromUser.stream()
                .filter(a -> !allergensFromDb.stream()
                        .map(AllergenEntity::getAllergen)
                        .collect(Collectors.toList())
                        .contains(a))
                .collect(Collectors.toList());
        for (String allergen : allergensToSave) {
            allergenRepository.save(Allergen.builder()
                    .allergen(allergen)
                    .userId(saved.getId())
                    .build()
                    .toEntity());
        }
        //delete allergens
        allergensFromDb.stream()
                .filter(a -> !allergensFromUser.contains(a.getAllergen()))
                .forEach(allergenRepository::delete);
        saved.getAllergens().addAll(allergensFromUser);
    }

    @Override
    public User getByLogin(String login) {
        if (isNull(login))
            return null;
        User user = userRepository.findByLogin(login)
                .map(User::new)
                .orElse(null);
        if (isNull(user))
            return null;
        user.setAllergens(allergenRepository.findAllByUserId(user.getId()).stream()
                .map(AllergenEntity::getAllergen)
                .collect(Collectors.toList()));
        return user;
    }

    @Override
    public boolean isPasswordValidForUser(String password, User user) {
        return User.PASSWORD_ENCODER.matches(password, user.getPassword());
    }

    @Override
    @Transactional
    public void changeUserData(User user, UserDataRequest request) throws BadHttpRequest {
        if (isNull(user) || isNull(request) || !validatePersonalData(user))
            throw new BadHttpRequest();
        user.setAge(request.getAge());
        user.setAllergens(request.getAllergens().stream()
                .map(String::trim)
                .collect(Collectors.toList()));
        user.setGender(request.getGender());
        user.setActivityLevel(request.getActivityLevel());
        user.setHeight(request.getHeight());
        user.setWeight(request.getWeight());
        save(user, false);
    }

    @Override
    public void addUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity) {
        if (userProductRepository.findByUserIdAndProductId(user.getId(), productId).isEmpty()) {
            UserProduct toAdd = new UserProduct(user, new Product(productId), new Date(), quantity);
            userProductRepository.save(toAdd.toEntity());
        }
    }

    @Override
    public void deleteUserProduct(User user, Long productId) {
        userProductRepository.findByUserIdAndProductId(user.getId(), productId)
                .ifPresent(userProductRepository::delete);
    }

    //  PRIVS
    private boolean validateCredentials(User user) {
        return validateLogin(user.getLogin()) && validatePassword(user.getPassword());
    }

    private boolean validatePersonalData(User user) {
        if (nonNull(user.getAge()) && user.getAge() < 1)
            return false;
        if (nonNull(user.getHeight()) && user.getHeight() < 1)
            return false;
        if (nonNull(user.getWeight()) && user.getWeight() < 1)
            return false;
        if (nonNull(user.getAllergens()) && user.getAllergens().stream().anyMatch(allergen -> allergen.length() > MAX_ALLERGEN_LENGTH))
            return false;
        return true;
    }

    private boolean validatePassword(String password) {
        return StringUtils.length(password) >= MIN_PASSWORD_LENGTH && StringUtils.length(password) <= MAX_PASSWORD_LENGTH;
    }

    private boolean validateLogin(String login) {
        return StringUtils.length(login) >= MIN_LOGIN_LENGTH && StringUtils.length(login) <= MAX_LOGIN_LENGTH;
    }

    private boolean userExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }
}
