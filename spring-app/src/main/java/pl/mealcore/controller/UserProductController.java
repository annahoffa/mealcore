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
import pl.mealcore.dto.response.BasicResponse;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.service.UserProductService;
import pl.mealcore.service.UserService;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static pl.mealcore.helper.AuthenticationHelper.isAuthenticated;

@Slf4j
@RestApiController(path = "user")
@RequiredArgsConstructor
public class UserProductController {
    private final UserService userService;
    private final UserProductService userProductService;

    @ResponseBody
    @PostMapping("/addProduct")
    ResponseEntity<Object> addProduct(@RequestParam(name = "productId") Long productId,
                                      @RequestParam(name = "quantity", required = false, defaultValue = "100") Integer quantity,
                                      @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        if (quantity < 1 || quantity > 15000) {
            log.info("FAILED addProduct, invalid quantity: '{}'", quantity);
            return new ResponseEntity<>(response.withMessage("Ilość musi być liczbą z zakresu od 1 do 15000"), HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            userProductService.addUserProduct(user, productId, quantity, DateHelper.parse(date));
            log.info("SUCCESSFUL add product '{}' to user '{}' ", productId, user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED addProduct, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @PutMapping("/editProduct")
    ResponseEntity<Object> editProduct(@RequestParam(name = "productId") Long productId,
                                      @RequestParam(name = "quantity") Integer quantity,
                                      @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        if (quantity < 1 || quantity > 15000) {
            log.info("FAILED editProduct, invalid quantity: '{}'", quantity);
            return new ResponseEntity<>(response.withMessage("Ilość musi być liczbą z zakresu od 1 do 15000"), HttpStatus.BAD_REQUEST);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            if(userProductService.editUserProduct(user, productId, quantity, DateHelper.parse(date))) {
                log.info("SUCCESSFUL edit product '{}' for user '{}' and date '{}'", productId, user.getLogin(), date);
                return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
            } else {
                log.info("FAILED product '{}' not found for user '{}' and date '{}'", productId, user.getLogin(), date);
                return new ResponseEntity<>(response.withSuccess(false), HttpStatus.NOT_FOUND);
            }
        } else {
            log.info("FAILED editProduct, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    //    ----Delete endpoints----
    @ResponseBody
    @DeleteMapping("/removeProduct")
    ResponseEntity<Object> removeProduct(@RequestParam(name = "productId") Long productId,
                                         @RequestParam(name = "date", required = false) String date) {
        BasicResponse response = new BasicResponse().withSuccess(false);
        if (nonNull(date) && isNull(DateHelper.parse(date)))
            return new ResponseEntity<>(response.withMessage("Nieprawidłowy format daty"), HttpStatus.BAD_REQUEST);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            userProductService.deleteUserProduct(user, productId, DateHelper.parse(date));
            log.info("SUCCESSFUL deleted product '{}' from user '{}' ", productId, user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED addProduct, no user in session");
            return new ResponseEntity<>(response.withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getUserProducts")
    ResponseEntity<Object> getUserProducts(@RequestParam(name = "date", required = false) String date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        if (isAuthenticated(auth, user)) {
            UserProductsResponse response = userProductService.getProductsWithNutrientsForUser(user, DateHelper.parse(date));
            log.info("SUCCESSFUL get '{}' products", user.getLogin());
            return new ResponseEntity<>(response.withSuccess(true), HttpStatus.OK);
        } else {
            log.info("FAILED getUserProducts, no user in session");
            return new ResponseEntity<>(new BasicResponse().withSuccess(false).withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

}
