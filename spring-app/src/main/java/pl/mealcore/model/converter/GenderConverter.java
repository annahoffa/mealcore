package pl.mealcore.model.converter;

import pl.mealcore.model.account.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.isNull;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Character> {

    @Override
    public Character convertToDatabaseColumn(Gender attribute) {
        if (isNull(attribute))
            return null;
        return attribute.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(Character code) {
        if (isNull(code))
            return null;
        return Gender.fromCode(code);
    }
}
