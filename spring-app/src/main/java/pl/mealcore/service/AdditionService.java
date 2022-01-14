package pl.mealcore.service;

import pl.mealcore.dto.product.Addition;
import pl.mealcore.model.product.ProductEntity;

import java.util.List;
import java.util.Map;

public interface AdditionService {
    List<Addition> extractAdditives(ProductEntity product, Map<String, Addition> additions);

    List<Addition> extractAdditives(ProductEntity product);

    Map<String, Addition> getAdditionsMap();

    List<Addition> getAll();
}
