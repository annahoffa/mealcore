package pl.mealcore.model.allergicReaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "allergic_reactions_15")
@EqualsAndHashCode(callSuper = true)
public class AllergicReactionEntity extends BaseEntity {

    private String name;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
