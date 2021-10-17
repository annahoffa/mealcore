package pl.mealcore.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "addition_5")
@EqualsAndHashCode(callSuper = true)
public class AdditionEntity extends BaseEntity {

    private String name;
    private String description;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
