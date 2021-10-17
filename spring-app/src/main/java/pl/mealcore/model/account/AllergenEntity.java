package pl.mealcore.model.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "allergens_8")
@EqualsAndHashCode(callSuper = true)
public class AllergenEntity extends BaseEntity {

    private String allergen;
    private UserEntity user;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @ManyToOne
    public UserEntity getUser(){
        return user;
    }
}
