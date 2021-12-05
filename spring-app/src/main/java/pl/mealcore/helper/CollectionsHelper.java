package pl.mealcore.helper;

import lombok.experimental.UtilityClass;
import pl.mealcore.dto.BaseDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@UtilityClass
public class CollectionsHelper {
    public static <T extends BaseDto> Predicate<T> distinctById() {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(t.getId(), Boolean.TRUE) == null;
    }
}
