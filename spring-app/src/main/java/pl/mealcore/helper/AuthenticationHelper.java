package pl.mealcore.helper;

import lombok.experimental.UtilityClass;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.mealcore.model.user.basicData.UserRole;

import java.util.Optional;

import static java.util.Objects.nonNull;

@UtilityClass
public class AuthenticationHelper {
    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean userLogin = Optional.ofNullable(auth)
                .map(Authentication::getName)
                .isPresent();
        return !(auth instanceof AnonymousAuthenticationToken) && userLogin;
    }

    public static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken)
                && auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(UserRole.ADMIN_ROLE.toString()::equals);
    }

    public static String getLoggedUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return nonNull(auth) ? auth.getName() : null;
    }
}
