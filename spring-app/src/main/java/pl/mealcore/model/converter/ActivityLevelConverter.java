package pl.mealcore.model.converter;

import pl.mealcore.model.account.ActivityLevel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.isNull;

@Converter
public class ActivityLevelConverter implements AttributeConverter<ActivityLevel, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ActivityLevel attribute) {
        if (isNull(attribute))
            return null;
        return attribute.getCode();
    }

    @Override
    public ActivityLevel convertToEntityAttribute(Integer code) {
        if (isNull(code))
            return null;
        return ActivityLevel.fromCode(code);
    }
}
