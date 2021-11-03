package pl.mealcore.dto.product.wrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mealcore.dto.product.Image;
import pl.mealcore.dto.product.Ingredients;
import pl.mealcore.dto.product.Product;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ProductPL {

    private Long id;
    private String code;
    private String name;
    private String brands;
    private String countriesTags;
    private String servingQuantity;
    private String servingSize;
    private String embCodesTags;
    private String quantity;
    private NutrientsPL nutrients;
    private Ingredients ingredients;
    private List<Image> images = new ArrayList<>();
    private boolean allergenWarning = false;
    private boolean badReaction = false;
    private boolean goodReaction = false;
    private Integer addedQuantity;

    public ProductPL(Product product) {
        id = product.getId();
        code = product.getCode();
        name = product.getName();
        brands = product.getBrands();
        countriesTags = product.getCountriesTags();
        servingQuantity = product.getServingQuantity();
        servingSize = product.getServingSize();
        embCodesTags = product.getEmbCodesTags();
        quantity = product.getQuantity();
        if (nonNull(product.getNutrients()))
            nutrients = new NutrientsPL(product.getNutrients());
        ingredients = product.getIngredients();
        images = product.getImages();
        allergenWarning = product.isAllergenWarning();
        badReaction = product.isBadReaction();
        goodReaction = product.isGoodReaction();
        addedQuantity = product.getAddedQuantity();
    }
}
