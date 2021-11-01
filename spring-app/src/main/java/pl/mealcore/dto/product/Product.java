package pl.mealcore.dto.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.product.ImageEntity;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.product.ProductEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseDto<ProductEntity> {

    private String code;
    private String name;
    private String brands;
    private String countriesTags;
    private String servingQuantity;
    private String servingSize;
    private String embCodesTags;
    private String quantity;
    private Nutrients nutrients;
    private Ingredients ingredients;
    private List<Image> images = new ArrayList<>();

    private boolean allergenWarning = false;
    private boolean badReaction = false;
    private boolean goodReaction = false;
    private Integer addedQuantity;
    private ProductCategory category;

    public Product(ProductEntity entity) {
        super(entity);
        this.code = entity.getCode();
        this.name = entity.getName();
        this.brands = entity.getBrands();
        this.countriesTags = entity.getCountries_tags();
        this.servingQuantity = entity.getServing_quantity();
        this.servingSize = entity.getServing_size();
        this.embCodesTags = entity.getEmb_codes_tags();
        this.quantity = entity.getQuantity();
        this.images = entity.getImages().stream()
                .map(i -> createDto(Image::new, i))
                .collect(Collectors.toList());
    }

    public Product(Long id) {
        super(id);
    }

    @Override
    public ProductEntity toEntity() {
        ProductEntity entity = new ProductEntity();
        entity.setId(this.id);
        entity.setCode(this.code);
        entity.setName(this.name);
        entity.setBrands(this.brands);
        entity.setCountries_tags(this.countriesTags);
        entity.setServing_quantity(this.servingQuantity);
        entity.setServing_size(this.servingSize);
        entity.setEmb_codes_tags(this.embCodesTags);
        entity.setQuantity(this.quantity);
        entity.setImages(this.images.stream()
                .map(i -> createEntityReference(ImageEntity.class, i))
                .collect(Collectors.toList()));
        return entity;
    }
}
