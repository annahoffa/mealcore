package pl.mealcore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.mealcore.annotations.RestApiController;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserReaction;
import pl.mealcore.dto.response.BasicResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.service.UserReactionService;
import pl.mealcore.service.UserService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.helper.AuthenticationHelper.isAuthenticated;

@Slf4j
@RestApiController(path = "user")
@RequiredArgsConstructor
public class UserReactionController {
    private final UserService userService;
    private final UserReactionService userReactionService;

    @ResponseBody
    @PostMapping("/addReaction")
    ResponseEntity<BasicResponse> addReaction(@RequestParam(name = "category") ProductCategory category,
                                              @RequestParam(name = "value") Integer value,
                                              @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        if (value < 1 || value > 5)
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy zakres wartości"), HttpStatus.BAD_REQUEST);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            if (userReactionService.addUserReaction(user, category, value, DateHelper.parse(date))) {
                log.info("SUCCESSFUL add Reaction for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
            } else {
                log.info("FAILED add Reaction for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withMessage("Notatka dla dnia " + DateHelper.parse(date) + " już istnieje")
                        .withSuccess(false), HttpStatus.CONFLICT);
            }
        } else {
            log.info("FAILED addExercise, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @PutMapping("/editReaction")
    ResponseEntity<BasicResponse> editReaction(@RequestParam(name = "category") ProductCategory category,
                                               @RequestParam(name = "value") Integer value,
                                               @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        if (value < 1 || value > 5)
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy zakres wartości"), HttpStatus.BAD_REQUEST);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            if (userReactionService.editUserReaction(user, category, value, DateHelper.parse(date))) {
                log.info("SUCCESSFUL edit Reaction for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
            } else {
                log.info("FAILED edit Reaction for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withMessage("Nie znaleziono reakcji " + DateHelper.parse(date))
                        .withSuccess(false), HttpStatus.NOT_FOUND);
            }
        } else {
            log.info("FAILED addExercise, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getReactions")
    ResponseEntity<List<UserReaction>> getReaction(@RequestParam(name = "date", required = false) String date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            List<UserReaction> reactions = userReactionService.getReactions(user, DateHelper.parse(date));
            if (reactions.isEmpty()) {
                log.info("SUCCESSFUL get Reaction '{}'", user.getLogin());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("SUCCESSFUL get Reaction '{}'", user.getLogin());
                return new ResponseEntity<>(reactions, HttpStatus.OK);
            }
        } else {
            log.info("FAILED getReaction, no user in session");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
