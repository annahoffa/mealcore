package pl.mealcore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.mealcore.dto.account.User;
import pl.mealcore.service.UserService;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = service.getByLogin(login);
        if (isNull(user))
            throw new UsernameNotFoundException("No user found with username: " + login);
        return user.toUserDetails();
    }
}
