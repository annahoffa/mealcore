package pl.mealcore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mealcore.annotations.RestApiController;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.product.wrapper.ProductPL;
import pl.mealcore.service.ProductService;
import pl.mealcore.service.UserService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RestApiController(path = "products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @ResponseBody
    @GetMapping("/suggestions/{name}")
    ResponseEntity<Object> suggestions(@PathVariable String name, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        if (name.length() >= 2) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
            List<Product> suggestions = productService.getSuggestionsByName(user, name, page);
            if (suggestions.isEmpty()) {
                log.info("FAILED products suggestions, no product was found for query: '{}'", name);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            log.info("SUCCESS products suggestions, query: '{}'", name);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        }
        log.info("FAILED products suggestions, query is to short: '{}'", name);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @GetMapping("/findById")
    ResponseEntity<Object> findById(@RequestParam(name = "productId") Long productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByLogin(nonNull(auth) ? auth.getName() : null);
        Product product = productService.getProduct(user, productId);
        if (isNull(product)) {
            log.info("FAILED find product by id, product with id: '{}' not found", productId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        log.info("SUCCESS find product by id: '{}'", productId);
        return new ResponseEntity<>(new ProductPL(product), HttpStatus.OK);
    }
}
