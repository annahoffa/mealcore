package pl.mealcore.dto.response;

import lombok.Data;
import pl.mealcore.dto.product.BasicNutrients;
import pl.mealcore.dto.product.Product;

import java.util.List;

@Data
public class UserProductsResponse {
    private List<Product> products;
    private double kcal;
    private double proteins;
    private double carbohydrates;
    private double fat;
    private double fiber;
    private boolean success;

    public UserProductsResponse(List<Product> products, BasicNutrients nutrients) {
        this.products = products;
        kcal = nutrients.getKcal();
        proteins = nutrients.getProteins();
        carbohydrates = nutrients.getCarbohydrates();
        fat = nutrients.getFat();
        fiber = nutrients.getFiber();
    }

    public UserProductsResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
