package pl.mealcore.model.converter;

import pl.mealcore.model.product.ProductCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.isNull;

@Converter
public class ProductCategoryConverter implements AttributeConverter<ProductCategory, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ProductCategory attribute) {
        if (isNull(attribute))
            return null;
        return attribute.getCode();
    }

    @Override
    public ProductCategory convertToEntityAttribute(Integer code) {
        if (isNull(code))
            return null;
        return ProductCategory.fromCode(code);
    }
}
