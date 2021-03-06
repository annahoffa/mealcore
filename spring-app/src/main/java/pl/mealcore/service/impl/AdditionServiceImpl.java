package pl.mealcore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mealcore.dao.AdditivesRepository;
import pl.mealcore.dto.product.Addition;
import pl.mealcore.model.product.IngredientsEntity;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.service.AdditionService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class AdditionServiceImpl implements AdditionService {
    public static final String ADDITIVES_SEPARATOR = ",";
    private static final String NO_INFORMATION_MESSAGE = "Brak informacji o dodatku";

    private final AdditivesRepository additivesRepository;

    @Override
    public List<Addition> extractAdditives(ProductEntity product, Map<String, Addition> additions) {
        String additivesString = Optional.ofNullable(product)
                .map(ProductEntity::getIngredients)
                .map(IngredientsEntity::getAdditives_tags)
                .orElse(null);
        List<Addition> result = new ArrayList<>();
        if (nonNull(additivesString))
            for (String name : additivesString.split(ADDITIVES_SEPARATOR))
                result.add(additions.getOrDefault(name, new Addition(name, NO_INFORMATION_MESSAGE)));
        return result;

    }

    @Override
    public List<Addition> extractAdditives(ProductEntity product) {
        Map<String, Addition> additionMap = getAdditionsMap();
        return extractAdditives(product, additionMap);
    }

    @Override
    public Map<String, Addition> getAdditionsMap() {
        Map<String, Addition> additionMap = new HashMap<>();
        for (Addition addition : getAll())
            additionMap.put(addition.getName(), addition);
        return additionMap;
    }

    @Override
    public List<Addition> getAll() {
        return additivesRepository.findAll().stream()
                .map(Addition::new)
                .collect(Collectors.toList());
    }
}
