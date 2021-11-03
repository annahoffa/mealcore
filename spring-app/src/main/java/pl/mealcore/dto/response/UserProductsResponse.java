package pl.mealcore.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mealcore.dto.product.BasicNutrients;
import pl.mealcore.dto.product.Product;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserProductsResponse extends BasicResponse {
    private List<Product> products;
    private double kcal;
    private double proteins;
    private double carbohydrates;
    private double fat;
    private double fiber;
    private String date;

    public UserProductsResponse(List<Product> products, BasicNutrients nutrients, String date) {
        this.products = products;
        this.kcal = nutrients.getKcal();
        this.proteins = nutrients.getProteins();
        this.carbohydrates = nutrients.getCarbohydrates();
        this.fat = nutrients.getFat();
        this.fiber = nutrients.getFiber();
        this.date = date;
    }
    public UserProductsResponse(List<Product> products) {
        this.products = products;
    }

    @Override
    public UserProductsResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
