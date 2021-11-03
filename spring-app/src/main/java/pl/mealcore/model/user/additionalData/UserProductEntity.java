package pl.mealcore.model.user.additionalData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.converter.ProductCategoryConverter;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users_products_10")
@EqualsAndHashCode(callSuper = true)
public class UserProductEntity extends BaseEntity {

    private UserEntity user;
    private ProductEntity product;
    private Date date;
    private Integer quantity;
    private ProductCategory category;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }

    @ManyToOne
    public ProductEntity getProduct() {
        return product;
    }

    @Convert(converter = ProductCategoryConverter.class)
    public ProductCategory getCategory() {
        return category;
    }
}
