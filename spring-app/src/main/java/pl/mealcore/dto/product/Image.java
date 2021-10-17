package pl.mealcore.dto.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.product.ImageEntity;
import pl.mealcore.model.product.ImageType;
import pl.mealcore.model.product.ProductEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class Image extends BaseDto<ImageEntity> {

    private Long productId;
    private String url;
    private ImageType type;

    public Image(ImageEntity entity) {
        super(entity);
        productId = getEntityId(entity.getProduct());
        url = entity.getUrl();
        type = entity.getType();
    }

    @Override
    public ImageEntity toEntity() {
        ImageEntity entity = new ImageEntity();
        entity.setId(id);
        entity.setProduct(createEntityReference(ProductEntity.class, productId));
        entity.setUrl(url);
        entity.setType(type);
        return entity;
    }
}
