package pl.mealcore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mealcore.dao.AdditivesRepository;
import pl.mealcore.dto.product.Addition;
import pl.mealcore.service.AdditionService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AdditionServiceImpl implements AdditionService {
    public static final String ADDITIVES_SEPARATOR = ",";
    private static final String NO_INFORMATION_MESSAGE = "Brak informacji o dodatku";

    private final AdditivesRepository additivesRepository;

    @Override
    public List<Addition> extractAdditivesFromString(String additivesString) {
        List<Addition> result = new ArrayList<>();
        if (nonNull(additivesString))
            for (String name : additivesString.split(ADDITIVES_SEPARATOR))
                result.add(additivesRepository.findFirstByName(name)
                        .map(Addition::new)
                        .orElse(new Addition(name, NO_INFORMATION_MESSAGE)));
        return result;
    }
}
