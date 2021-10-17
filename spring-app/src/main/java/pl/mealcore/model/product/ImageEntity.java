package pl.mealcore.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.converter.ImageTypeConverter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "images_4")
@EqualsAndHashCode(callSuper = true)
public class ImageEntity extends BaseEntity {

    private ProductEntity product;
    private String url;
    private ImageType type;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @ManyToOne
    public ProductEntity getProduct(){
        return product;
    }

    @Convert(converter = ImageTypeConverter.class)
    public ImageType getType() {
        return type;
    }
}
