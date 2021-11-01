package pl.mealcore.dto.account;

import lombok.*;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.dto.product.Product;
import pl.mealcore.model.account.UserEntity;
import pl.mealcore.model.account.UserProductEntity;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.product.ProductEntity;

import java.util.Date;

import static java.util.Objects.isNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserProduct extends BaseDto<UserProductEntity> {

    private User user;
    private Product product;
    private Date date;
    private Integer quantity;
    private ProductCategory category;

    public UserProduct(UserProductEntity entity) {
        super(entity);
        this.user = createDto(User::new, entity.getUser());
        this.product = createDto(Product::new, entity.getProduct());
        this.date = entity.getDate();
        this.quantity = entity.getQuantity();
        this.category = entity.getCategory();
    }

    @Override
    public UserProductEntity toEntity() {
        UserProductEntity entity = new UserProductEntity();
        entity.setId(id);
        entity.setUser(createEntityReference(UserEntity.class, user));
        entity.setProduct(createEntityReference(ProductEntity.class, product));
        entity.setDate(date);
        entity.setQuantity(quantity);
        entity.setCategory(category);
        return entity;
    }

    public void addQuantity(Integer toAdd) {
        if (isNull(toAdd))
            return;
        quantity = isNull(quantity) ? toAdd : quantity + toAdd;
    }
}
