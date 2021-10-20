package pl.mealcore.model.sport;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sports_11")
@EqualsAndHashCode(callSuper = true)
public class SportEntity extends BaseEntity {

    private String name;
    private Integer kcal;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
