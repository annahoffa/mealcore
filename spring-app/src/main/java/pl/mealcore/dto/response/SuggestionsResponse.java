package pl.mealcore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.mealcore.dto.product.Product;

import java.util.List;

@Data
@AllArgsConstructor
public class SuggestionsResponse {
    private List<Product> products;
    private int productCount;
}
