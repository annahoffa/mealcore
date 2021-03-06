package pl.mealcore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import static pl.mealcore.helper.AuthenticationHelper.getLoggedUserLogin;
import static pl.mealcore.helper.AuthenticationHelper.isAuthenticated;

@Slf4j
@RestApiController(path = "user")
@RequiredArgsConstructor
public class UserReactionController {
    private final UserService userService;
    private final UserReactionService userReactionService;

    @ResponseBody
    @PostMapping("/addEditReaction")
    ResponseEntity<BasicResponse> addEditReaction(@RequestParam(name = "category") ProductCategory category,
                                                  @RequestParam(name = "value") Integer value,
                                                  @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        if (value < 1 || value > 5)
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy zakres wartości"), HttpStatus.BAD_REQUEST);
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            userReactionService.addEditUserReaction(user, category, value, DateHelper.parse(date));
            log.info("SUCCESSFUL add/edit Reaction for user '{}' ", user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED addEditReaction, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getReactions")
    ResponseEntity<List<UserReaction>> getReaction(@RequestParam(name = "date", required = false) String date) {
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            List<UserReaction> reactions = userReactionService.getReactions(user, DateHelper.parse(date));

            log.info("SUCCESSFUL get Reaction '{}'", user.getLogin());
            return new ResponseEntity<>(reactions, HttpStatus.OK);

        } else {
            log.info("FAILED getReaction, no user in session");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
