package pl.mealcore.helper;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import pl.mealcore.dto.account.User;
import pl.mealcore.model.user.basicData.UserRole;

import static java.util.Objects.nonNull;

@UtilityClass
public class AuthenticationHelper {
    public static boolean isAuthenticated(Authentication auth, User user) {
        return !(auth instanceof AnonymousAuthenticationToken) && nonNull(user);
    }

    public static boolean isAuthenticated(Authentication auth, String userLogin) {
        return !(auth instanceof AnonymousAuthenticationToken) && nonNull(userLogin);
    }

    public static boolean isAdmin(Authentication auth) {
        return !(auth instanceof AnonymousAuthenticationToken)
                && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(UserRole.ADMIN_ROLE.toString()::equals);
    }
}
