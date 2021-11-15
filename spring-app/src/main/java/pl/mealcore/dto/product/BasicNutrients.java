package pl.mealcore.dto.product;

import lombok.Data;
import pl.mealcore.helper.NumberHelper;

@Data
public class BasicNutrients {
    private double kcal = 0.0;
    private double proteins = 0.0;
    private double carbohydrates = 0.0;
    private double fat = 0.0;
    private double fiber = 0.0;

    public void addKcal(double value){
        kcal = NumberHelper.round(kcal + value);
    }
    public void addProteins(double value){
        proteins = NumberHelper.round(proteins + value);
    }
    public void addCarbohydrates(double value){
        carbohydrates = NumberHelper.round(carbohydrates + value);
    }
    public void addFat(double value){
        fat = NumberHelper.round(fat + value);
    }
    public void addFiber(double value){
        fiber = NumberHelper.round(fiber + value);
    }
}
