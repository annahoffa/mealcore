package pl.mealcore.service;

import pl.mealcore.dto.product.Addition;

import java.util.List;

public interface AdditionService {
    List<Addition> extractAdditivesFromString(String additivesString);
}
