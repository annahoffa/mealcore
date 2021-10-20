package pl.mealcore.service;

import javassist.tools.web.BadHttpRequest;
import lombok.NonNull;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.request.UserDataRequest;
import pl.mealcore.error.*;

import java.util.Date;

public interface UserService {
    User save(User user, boolean hashPassword);

    User registerNewUser(User user) throws UserAlreadyExistException, UserInvalidCredentialsException, UserInvalidPersonalDataException;

    User getByLogin(String login);

    boolean isPasswordValidForUser(String password, User user);

    void changeUserPassword(User user, String oldPassword, String newPassword) throws UserInvalidCredentialsException, UserWrongPasswordException, BadSessionException;

    void changeUserLogin(User user, String newLogin, String password) throws BadSessionException, UserWrongPasswordException, UserInvalidCredentialsException, UserAlreadyExistException;

    void changeUserData(User user, UserDataRequest request) throws BadHttpRequest;

    void addUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date);

    void deleteUserProduct(@NonNull User user, @NonNull  Long productId, Date date);
}
