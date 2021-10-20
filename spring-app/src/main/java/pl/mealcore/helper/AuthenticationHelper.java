package pl.mealcore.helper;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import pl.mealcore.dto.account.User;

import static java.util.Objects.nonNull;

@UtilityClass
public class AuthenticationHelper {
    public static boolean isAuthenticated(Authentication auth, User user) {
        return !(auth instanceof AnonymousAuthenticationToken) && nonNull(user);
    }

    public static boolean isAuthenticated(Authentication auth, String userLogin) {
        return !(auth instanceof AnonymousAuthenticationToken) && nonNull(userLogin);
    }
}
