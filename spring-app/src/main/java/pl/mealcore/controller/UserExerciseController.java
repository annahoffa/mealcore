package pl.mealcore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mealcore.annotations.RestApiController;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.response.BasicResponse;
import pl.mealcore.dto.response.UserExercisesResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.service.UserExerciseService;
import pl.mealcore.service.UserService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.helper.AuthenticationHelper.getLoggedUserLogin;
import static pl.mealcore.helper.AuthenticationHelper.isAuthenticated;

@Slf4j
@RestApiController(path = "user")
@RequiredArgsConstructor
public class UserExerciseController {
    private final UserService userService;
    private final UserExerciseService userExerciseService;

    @ResponseBody
    @PostMapping("/addExercise")
    ResponseEntity<BasicResponse> addExercise(@RequestParam(name = "sportId") Long sportId,
                                              @RequestParam(name = "duration", required = false, defaultValue = "1") Double duration,
                                              @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        if (duration < 0 || duration > 24) {
            log.info("FAILED addExercise, invalid duration: '{}'", duration);
            return new ResponseEntity<>(response.withMessage("Długość ćwiczenia musi być z zakresu (0, 24)"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            userExerciseService.addUserExercise(user, sportId, duration, DateHelper.parse(date));
            log.info("SUCCESSFUL add exercise '{}' to user '{}' ", sportId, user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED addExercise, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @PutMapping("/editExercise")
    ResponseEntity<BasicResponse> editExercise(@RequestParam(name = "sportId") Long sportId,
                                               @RequestParam(name = "duration") Double duration,
                                               @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        if (duration < 0 || duration > 24) {
            log.info("FAILED editExercise, invalid duration: '{}'", duration);
            return new ResponseEntity<>(response.withMessage("Długość ćwiczenia musi być z zakresu (0, 24)"), HttpStatus.BAD_REQUEST);
        }
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            if (userExerciseService.editUserExercise(user, sportId, duration, DateHelper.parse(date))) {
                log.info("SUCCESSFUL edit exercise '{}' for user '{}' and date '{}'", sportId, user.getLogin(), date);
                return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
            } else {
                log.info("FAILED exercise '{}' not found for user '{}' and date '{}'", sportId, user.getLogin(), date);
                return new ResponseEntity<>(response.withSuccess(false), HttpStatus.NOT_FOUND);
            }
        } else {
            log.info("FAILED editExercise, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    //    ----Delete endpoints----
    @ResponseBody
    @DeleteMapping("/removeExercise")
    ResponseEntity<BasicResponse> removeExercise(@RequestParam(name = "sportId") Long sportId,
                                                 @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            userExerciseService.deleteUserExercise(user, sportId, DateHelper.parse(date));
            log.info("SUCCESSFUL deleted exercise '{}' from user '{}' ", sportId, user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED addExercise, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getUserExercises")
    ResponseEntity<UserExercisesResponse> getUserExercises(@RequestParam(name = "date", required = false) String date) {
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            UserExercisesResponse response = userExerciseService.getExercisesForUser(user, DateHelper.parse(date));
            log.info("SUCCESSFUL get '{}' exercise", user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED getUserExercises, no user in session");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
