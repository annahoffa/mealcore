package pl.mealcore.model.product;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ingredients_3")
@EqualsAndHashCode(callSuper = true)
public class IngredientsEntity extends BaseEntity {

    private Long productId;
    private String ingredients_text;
    private String allergens;
    private String traces_tags;
    private String additives_tags;
    private String ingredients_from_palm_oil_tags;
    private String ingredients_that_may_be_from_palm_oil_tags;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
