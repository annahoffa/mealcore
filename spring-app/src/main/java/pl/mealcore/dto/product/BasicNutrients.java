package pl.mealcore.dto.product;

import lombok.Data;

@Data
public class BasicNutrients {
    private double kcal = 0.0;
    private double proteins = 0.0;
    private double carbohydrates = 0.0;
    private double fat = 0.0;
    private double fiber = 0.0;

    public void addKcal(double value){
        kcal += value;
    }
    public void addProteins(double value){
        proteins += value;
    }
    public void addCarbohydrates(double value){
        carbohydrates += value;
    }
    public void addFat(double value){
        fat += value;
    }
    public void addFiber(double value){
        fiber += value;
    }
}
