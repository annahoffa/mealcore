package pl.mealcore.service;

import pl.mealcore.dto.account.User;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.product.ProductSortType;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.dto.statistic.StatisticNutrients;
import pl.mealcore.model.product.ProductEntity;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Product> getSuggestionsByName(User user, String text);

    List<Product> applyFilters(List<Product> suggestions, Integer kcalFrom, Integer kcalTo, String makeQuery);

    List<Product> sort(List<Product> suggestions, ProductSortType sortType, boolean reverseSort);

    Product getProduct(User user, Long id);

    List<StatisticNutrients> getStatisticsForUser(User user, Date fromDate, Date toDate);

    UserProductsResponse getProductsWithNutrientsForUser(User user, Date parse);

    Product createBaseProduct(ProductEntity entity, User user);

    boolean addProduct(Product product, User user);

    List<Product> getUnapprovedProducts();

    void approveProduct(Long productId);

    void dismissProduct(Long productId);
}
