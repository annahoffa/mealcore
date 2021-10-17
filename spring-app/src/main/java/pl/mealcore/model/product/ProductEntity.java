package pl.mealcore.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "products_1")
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends BaseEntity {

    private String code;
    private String name;
    private String brands;
    private String countries_tags;
    private String serving_quantity;
    private String serving_size;
    private String emb_codes_tags;
    private String quantity;
    private List<ImageEntity> images;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @Column(name = "product_name")
    public String getName() {
        return name;
    }

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    public List<ImageEntity> getImages() {
        return images;
    }
}
