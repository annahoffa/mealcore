package pl.mealcore.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.product.IngredientsEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static pl.mealcore.service.impl.AdditionServiceImpl.ADDITIVES_SEPARATOR;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Ingredients extends BaseDto<IngredientsEntity> {

    private Long productId;
    private String ingredientsText;
    private String allergens;
    private String tracesTags;
    private List<Addition> additives;
    private String ingredientsFromPalmOilTags;
    private String ingredientsThatMayBeFromPalmOilTags;

    public Ingredients(IngredientsEntity entity) {
        super(entity);
        productId = entity.getProductId();
        if (nonNull(entity.getIngredients_text()))
            ingredientsText = entity.getIngredients_text().replace("_", "");
        allergens = entity.getAllergens();
        tracesTags = entity.getTraces_tags();
        additives = new ArrayList<>();
        ingredientsFromPalmOilTags = entity.getIngredients_from_palm_oil_tags();
        ingredientsThatMayBeFromPalmOilTags = entity.getIngredients_that_may_be_from_palm_oil_tags();
    }

    public Ingredients(IngredientsEntity entity, List<Addition> additives) {
        this(entity);
        this.additives = additives;
    }

    @Override
    public IngredientsEntity toEntity() {
        IngredientsEntity entity = new IngredientsEntity();
        entity.setId(id);
        entity.setProductId(productId);
        entity.setIngredients_text(ingredientsText);
        entity.setAllergens(allergens);
        entity.setTraces_tags(tracesTags);
        entity.setAdditives_tags(additives.stream()
                .map(Addition::getName)
                .collect(Collectors.joining(ADDITIVES_SEPARATOR)));
        entity.setIngredients_from_palm_oil_tags(ingredientsFromPalmOilTags);
        entity.setIngredients_that_may_be_from_palm_oil_tags(ingredientsThatMayBeFromPalmOilTags);
        return entity;
    }
}
