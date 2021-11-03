package pl.mealcore.model.user.additionalData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "allergens_8")
@EqualsAndHashCode(callSuper = true)
public class UserAllergenEntity extends BaseEntity {

    private String allergen;
    private UserEntity user;

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
}
