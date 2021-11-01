package pl.mealcore.model.converter;

import pl.mealcore.model.user.basicData.AccountType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.isNull;

@Converter
public class UserTypeConverter implements AttributeConverter<AccountType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountType attribute) {
        if (isNull(attribute))
            return null;
        return attribute.getCode();
    }

    @Override
    public AccountType convertToEntityAttribute(Integer code) {
        if (isNull(code))
            return null;
        return AccountType.fromCode(code);
    }
}
