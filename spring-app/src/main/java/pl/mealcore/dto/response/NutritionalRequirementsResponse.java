package pl.mealcore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NutritionalRequirementsResponse extends BasicResponse {
    private Double kcal;
    private Double proteins;
    private Double carbohydrates;
    private Double fat;
    private Double fiber;

    @Override
    public NutritionalRequirementsResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
