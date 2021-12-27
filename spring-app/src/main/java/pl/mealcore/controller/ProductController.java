package pl.mealcore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mealcore.annotations.RestApiController;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.Addition;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.product.ProductSortType;
import pl.mealcore.dto.product.wrapper.ProductPL;
import pl.mealcore.dto.response.BasicResponse;
import pl.mealcore.service.AdditionService;
import pl.mealcore.service.ProductService;
import pl.mealcore.service.UserService;

import java.util.List;

import static java.util.Objects.isNull;
import static pl.mealcore.helper.AuthenticationHelper.*;

@Slf4j
@RestApiController(path = "products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final AdditionService additionService;
    private final UserService userService;

    @ResponseBody
    @GetMapping("/suggestions")
    ResponseEntity<List<Product>> suggestions(@RequestParam String query,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                              @RequestParam(name = "kcalFrom", required = false) int kcalFrom,
                                              @RequestParam(name = "kcalTo", required = false) int kcalTo,
                                              @RequestParam(name = "make", required = false) String makeQuery,
                                              @RequestParam(name = "sortBy", required = false) ProductSortType sortType) {
        if (query.length() >= 2) {
            User user = userService.getByLogin(getLoggedUserLogin());
            List<Product> suggestions = productService.getSuggestionsByName(user, query, page);
            if (suggestions.isEmpty()) {
                log.info("FAILED products suggestions, no product was found for query: '{}'", query);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("SUCCESS products suggestions, query: '{}'", query);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        }
        log.info("FAILED products suggestions, query is to short: '{}'", query);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping("/findById")
    ResponseEntity<ProductPL> findById(@RequestParam(name = "productId") Long productId) {
        User user = userService.getByLogin(getLoggedUserLogin());
        Product product = productService.getProduct(user, productId);
        if (isNull(product)) {
            log.info("FAILED find product by id, product with id: '{}' not found", productId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("SUCCESS find product by id: '{}'", productId);
        return new ResponseEntity<>(new ProductPL(product), HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/add")
    ResponseEntity<BasicResponse> addProduct(@RequestBody Product product) {
        User user = userService.getByLogin(getLoggedUserLogin());
        if (isAuthenticated()) {
            if (productService.addProduct(product, user)) {
                log.info("SUCCESS addProduct");
                return new ResponseEntity<>(new BasicResponse().withSuccess(true).withMessage("Dodano produkt"), HttpStatus.CREATED);
            } else {
                log.info("FAILED addProduct, bad request");
                return new ResponseEntity<>(new BasicResponse().withSuccess(false).withMessage("Nie udało się dodać produktu."), HttpStatus.BAD_REQUEST);
            }
        } else {
            log.info("FAILED editProduct, no user in session");
            return new ResponseEntity<>(new BasicResponse().withSuccess(false).withMessage("Nie znaleziono zalogowanego użytkownika."), HttpStatus.UNAUTHORIZED);
        }
    }

    @ResponseBody
    @GetMapping("/getUserSuggestions")
    ResponseEntity<List<Product>> getUserSuggestions() {
        if (isAdmin()) {
            List<Product> products = productService.getUnapprovedProducts();
            log.info("SUCCESS addProduct");
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            log.info("FAILED getUserSuggestions, user in not admin");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @ResponseBody
    @GetMapping("/getAdditions")
    ResponseEntity<List<Addition>> getAdditions() {

        List<Addition> additions = additionService.getAll();
        log.info("SUCCESS getAdditions");
        return new ResponseEntity<>(additions, HttpStatus.OK);

    }

    @ResponseBody
    @PostMapping("/approve")
    ResponseEntity<Void> approve(@RequestParam(name = "productId") Long productId) {
        productService.approveProduct(productId);
        log.info("SUCCESS approved product: '{}'", productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/dismiss")
    ResponseEntity<Void> dismiss(@RequestParam(name = "productId") Long productId) {
        productService.dismissProduct(productId);
        log.info("SUCCESS dismissed product: '{}'", productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
