package pl.mealcore.controller;

import io.swagger.annotations.ApiOperation;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.mealcore.annotations.RestApiController;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.request.UserDataRequest;
import pl.mealcore.dto.response.BasicResponse;
import pl.mealcore.dto.response.NutritionalRequirementsResponse;
import pl.mealcore.dto.response.UserDataResponse;
import pl.mealcore.error.*;
import pl.mealcore.handler.CalculatorNutritionalRequirementsHandler;
import pl.mealcore.service.UserProductService;
import pl.mealcore.service.UserService;

import java.util.Collection;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static pl.mealcore.helper.AuthenticationHelper.getLoggedUserLogin;
import static pl.mealcore.helper.AuthenticationHelper.isAuthenticated;

@Slf4j
@RestApiController(path = "user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserProductService userProductService;
    private final CalculatorNutritionalRequirementsHandler calculatorNutritionalRequirementsHandler;

    //    ----Create endpoints----
    @ResponseBody
    @PostMapping("/register")
    ResponseEntity<BasicResponse> register(@RequestBody User user) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        try {
            userService.registerNewUser(user);
            log.info("SUCCESSFUL registration, user: '{}' was created", user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true).withMessage("Pomy??lnie utworzono u??ytkownika!"), HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            log.info("FAILED registration, user with login: '{}' already exist", e.getMessage());
            return new ResponseEntity<>(response.withMessage("U??ytkownik o nazwie:" + e.getMessage() + " ju?? istnieje"), HttpStatus.CONFLICT);
        } catch (UserInvalidCredentialsException e) {
            log.info("FAILED registration, bad login or password.");
            return new ResponseEntity<>(response.withMessage("Podane has??o lub login nie spe??nia wymaga??."), HttpStatus.BAD_REQUEST);
        } catch (UserInvalidPersonalDataException e) {
            log.info("FAILED registration, bad personal data.");
            return new ResponseEntity<>(response.withMessage("Podane dane personalne nie spe??nia wymaga??."), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.info("FAILED registration, internal server error: ", e);
            return new ResponseEntity<>(response.withMessage("B????d wewn??trzny serwera."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    ----Get data endpoints----
    @ResponseBody
    @GetMapping("/getPersonalData")
    ResponseEntity<UserDataResponse> getPersonalData() {
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            log.info("SUCCESSFUL get personal data for user '{}'", user.getLogin());
            return new ResponseEntity<>(new UserDataResponse(user).withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED get personal data, no user in session");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getLogin")
    ResponseEntity<BasicResponse> getLogin() {
        BasicResponse response = new BasicResponse().withSuccess(false);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = Optional.ofNullable(auth)
                .map(Authentication::getName)
                .orElse(null);
        if (isAuthenticated()) {
            log.info("SUCCESSFUL get login, for user '{}'", login);
            return new ResponseEntity<>(response.withSuccess(true).withMessage(login), HttpStatus.OK);
        } else {
            log.info("FAILED get login, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego u??ytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getNutritionalRequirements")
    ResponseEntity<NutritionalRequirementsResponse> getNutritionalRequirements() {
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            NutritionalRequirementsResponse nutritionalRequirements = calculatorNutritionalRequirementsHandler.calculate(user);
            if (nutritionalRequirements.isSuccess()) {
                log.info("SUCCESSFUL get nutritional requirements, for user '{}'", user.getLogin());
                return new ResponseEntity<>(nutritionalRequirements, HttpStatus.OK);
            } else {
                log.info("FAILED get nutritional requirements, user some required data is empty for user: '{}'", user.getLogin());
                return new ResponseEntity<>(nutritionalRequirements, HttpStatus.BAD_REQUEST);
            }
        } else {
            log.info("FAILED get nutritional requirements, no user in session");
            return new ResponseEntity<>(new NutritionalRequirementsResponse().withSuccess(false), HttpStatus.UNAUTHORIZED);
        }
    }

    //    ----Edit data endpoints----
    @ResponseBody
    @PutMapping("/changePassword")
    ResponseEntity<BasicResponse> changePassword(@RequestParam(name = "oldPassword") String oldPassword,
                                                 @RequestParam(name = "newPassword") String newPassword) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
            userService.changeUserPassword(user, oldPassword, newPassword);
            Authentication newAuth = new UsernamePasswordAuthenticationToken(user.toUserDetails(), user.toUserDetails().getPassword(), user.toUserDetails().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            log.info("SUCCESSFUL password change");
            return new ResponseEntity<>(response.withSuccess(true).withMessage("Has??o zosta??o zmienione pomy??lnie."), HttpStatus.OK);
        } catch (BadSessionException e) {
            log.info("FAILED password change, no logged user in session");
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (UserWrongPasswordException e) {
            log.info("FAILED password change, wrong old password");
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (UserInvalidCredentialsException e) {
            log.info("FAILED password change, wrong new password");
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.info("FAILED password change, internal server error: ", e);
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PutMapping("/changeLogin")
    ResponseEntity<BasicResponse> changeLogin(@RequestParam(name = "newLogin") String newLogin,
                                              @RequestParam(name = "password") String password) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
            userService.changeUserLogin(user, newLogin, password);
            log.info("SUCCESSFUL login change form '{}' to '{}'", nonNull(auth) ? auth.getName() : null, newLogin);
            Authentication newAuth = new UsernamePasswordAuthenticationToken(user.toUserDetails(), user.toUserDetails().getPassword(), user.toUserDetails().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } catch (BadSessionException e) {
            log.info("FAILED login change to '{}', no logged user in session", newLogin);
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (UserWrongPasswordException e) {
            log.info("FAILED login change to '{}', wrong password", newLogin);
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (UserInvalidCredentialsException e) {
            log.info("FAILED login change to '{}', wrong login", newLogin);
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExistException e) {
            log.info("FAILED login change to '{}', user already exists", newLogin);
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            log.info("FAILED login change to '{}', internal server error: ", newLogin, e);
            return new ResponseEntity<>(response.withMessage(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @PutMapping("/changePersonalData")
    ResponseEntity<BasicResponse> changePersonalData(@RequestBody UserDataRequest userDataRequest) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
            if (isAuthenticated()) {
                userService.changeUserData(user, userDataRequest);
                log.info("SUCCESSFUL changed personal data, for user '{}'", nonNull(auth) ? auth.getName() : null);
                return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
            } else {
                log.info("FAILED to change personal data, no user in session");
                return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego u??ytkownika."), HttpStatus.UNAUTHORIZED);
            }
        } catch (BadHttpRequest e) {
            log.info("FAILED to change personal data, bad request");
            return new ResponseEntity<>(response.withMessage("B????dne zapytanie."), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Implemented by Spring Security
     */
    @ApiOperation(value = "Login", notes = "Login with the given credentials.")
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }

    @SuppressWarnings({"squid:S2160", "squid:S1104"})
    private class AuthResponse extends BasicResponse {
        public Collection<GrantedAuthority> authorities;
    }

    /**
     * Implemented by Spring Security
     */
    @ApiOperation(value = "Logout", notes = "Logout the current user.")
    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }
}
