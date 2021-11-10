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
import pl.mealcore.dto.allergicReaction.AllergicReaction;
import pl.mealcore.dto.allergicReaction.AllergySymptomsList;
import pl.mealcore.dto.response.BasicResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.service.UserAllergicReactionService;
import pl.mealcore.service.UserService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.helper.AuthenticationHelper.isAuthenticated;

@Slf4j
@RestApiController(path = "user")
@RequiredArgsConstructor
public class UserAllergicReactionController {
    private final UserService userService;
    private final UserAllergicReactionService userAllergicReactionService;

    @ResponseBody
    @PutMapping("/editAllergySymptoms")
    ResponseEntity<Void> editAllergicReaction(@RequestBody AllergySymptomsList allergySymptomsList) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            userAllergicReactionService.updateAllergySymptoms(allergySymptomsList.getSymptomIds(), allergySymptomsList.getDate(), user);
            log.info("SUCCESSFUL add AllergicSymptoms to user '{}' ", user.getLogin());
            return ResponseEntity.ok().build();
        } else {
            log.info("FAILED addAllergicReaction, no user in session");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ResponseBody
    @GetMapping("/getUserAllergicReaction")
    ResponseEntity<Object> getUserAllergicReaction(@RequestParam(name = "date", required = false) String date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            log.info("SUCCESSFUL get '{}' AllergicReaction", user.getLogin());
            List<AllergicReaction> allergicReactions = userAllergicReactionService.getAllergicReactionsForUser(user, DateHelper.parse(date));
            if (isNull(allergicReactions))
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(allergicReactions, HttpStatus.OK);
        } else {
            log.info("FAILED getUserAllergicReaction, no user in session");
            return new ResponseEntity<>(new BasicResponse().withSuccess(false).withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

}
