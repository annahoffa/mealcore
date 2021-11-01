package pl.mealcore.model.user.additionalData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.converter.ProductCategoryConverter;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.user.basicData.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users_reactions_14")
@EqualsAndHashCode(callSuper = true)
public class UserReactionEntity extends BaseEntity {

    private ProductCategory category;
    private Integer value;
    private Date date;
    private UserEntity user;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Convert(converter = ProductCategoryConverter.class)
    public ProductCategory getCategory() {
        return category;
    }

    @ManyToOne
    public UserEntity getUser() {
        return user;
    }
}
