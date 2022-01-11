package pl.mealcore.helper;

import lombok.experimental.UtilityClass;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.BaseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@UtilityClass
public class CollectionsHelper {
    public static <T extends BaseDto> Predicate<T> distinctDtosById() {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(t.getId(), Boolean.TRUE) == null;
    }

    public static <T extends BaseEntity> Predicate<T> distinctEntitiesById() {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(t.getId(), Boolean.TRUE) == null;
    }
}
