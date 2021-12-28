package pl.mealcore.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticNutrients {

    private String date;
    private double kcal;
    private double proteins;
    private double carbohydrates;
    private double fat;
    private double fiber;
}
