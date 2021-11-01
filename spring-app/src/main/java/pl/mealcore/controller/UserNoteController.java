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
import pl.mealcore.dto.account.UserNote;
import pl.mealcore.dto.response.BasicResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.service.UserNoteService;
import pl.mealcore.service.UserService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.helper.AuthenticationHelper.isAuthenticated;

@Slf4j
@RestApiController(path = "user")
@RequiredArgsConstructor
public class UserNoteController {
    private final UserService userService;
    private final UserNoteService userNoteService;

    @ResponseBody
    @PostMapping("/addNote")
    ResponseEntity<Object> addNote(@RequestParam(name = "note") String note,
                                   @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            if (userNoteService.addUserNote(user, note, DateHelper.parse(date))) {
                log.info("SUCCESSFUL add note for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
            } else {
                log.info("FAILED add note for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withMessage("Notatka dla dnia " + DateHelper.parse(date) + " już istnieje")
                        .withSuccess(false), HttpStatus.CONFLICT);
            }
        } else {
            log.info("FAILED addExercise, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @PutMapping("/editNote")
    ResponseEntity<Object> editNote(@RequestParam(name = "note") String note,
                                    @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            if (userNoteService.editUserNote(user, note, DateHelper.parse(date))) {
                log.info("SUCCESSFUL edit note for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
            } else {
                log.info("FAILED edit note for user '{}' ", user.getLogin());
                return new ResponseEntity<>(response.withMessage("Nie znaleziono notatki z dnia " + DateHelper.parse(date))
                        .withSuccess(false), HttpStatus.NOT_FOUND);
            }
        } else {
            log.info("FAILED addExercise, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getNote")
    ResponseEntity<Object> getNote(@RequestParam(name = "date", required = false) String date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            UserNote note = userNoteService.getNote(user, DateHelper.parse(date));
            if (nonNull(note)) {
                log.info("SUCCESSFUL get note '{}'", user.getLogin());
                return new ResponseEntity<>(note, HttpStatus.OK);
            } else {
                log.info("SUCCESSFUL get note '{}'", user.getLogin());
                return new ResponseEntity<>(new BasicResponse().withMessage("Nie znaleziono notatki z dnia " + DateHelper.parse(date))
                        .withSuccess(false),
                        HttpStatus.NOT_FOUND);
            }
        } else {
            log.info("FAILED getNote, no user in session");
            return new ResponseEntity<>(new BasicResponse().withSuccess(false).withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

}
