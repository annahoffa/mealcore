package pl.mealcore.service;

import javassist.tools.web.BadHttpRequest;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.request.UserDataRequest;
import pl.mealcore.error.*;

public interface UserService {
    User save(User user, boolean hashPassword);

    User registerNewUser(User user) throws UserAlreadyExistException, UserInvalidCredentialsException, UserInvalidPersonalDataException;

    User getByLogin(String login);

    boolean isPasswordValidForUser(String password, User user);

    void changeUserPassword(User user, String oldPassword, String newPassword) throws UserInvalidCredentialsException, UserWrongPasswordException, BadSessionException;

    void changeUserLogin(User user, String newLogin, String password) throws BadSessionException, UserWrongPasswordException, UserInvalidCredentialsException, UserAlreadyExistException;

    void changeUserData(User user, UserDataRequest request) throws BadHttpRequest;
}
