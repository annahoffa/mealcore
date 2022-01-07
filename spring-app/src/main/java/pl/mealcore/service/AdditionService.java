package pl.mealcore.service;

import pl.mealcore.dto.product.Addition;
import pl.mealcore.model.product.ProductEntity;

import java.util.List;

public interface AdditionService {
    List<Addition> extractAdditives(ProductEntity product);

    List<Addition> getAll();
}
