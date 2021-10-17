package pl.mealcore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalRequirementsResponse {
    private Double kcal;
    private Double proteins;
    private Double carbohydrates;
    private Double fat;
    private Double fiber;
    private boolean success;

    public NutritionalRequirementsResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
