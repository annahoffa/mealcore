package pl.mealcore.model.converter;

import pl.mealcore.model.product.ImageType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.isNull;

@Converter
public class ImageTypeConverter implements AttributeConverter<ImageType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ImageType attribute) {
        if (isNull(attribute))
            return null;
        return attribute.getCode();
    }

    @Override
    public ImageType convertToEntityAttribute(Integer code) {
        if (isNull(code))
            return null;
        return ImageType.fromCode(code);
    }
}
