package pl.mealcore.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import javax.persistence.*;
import java.util.Date;
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
    private boolean approved;
    private Date inserted_date;
    private UserEntity inserted_by;
    private Date approved_date;
    private UserEntity approved_by;

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

    @ManyToOne
    public UserEntity getInserted_by() {
        return inserted_by;
    }

    @ManyToOne
    public UserEntity getApproved_by() {
        return approved_by;
    }
}
