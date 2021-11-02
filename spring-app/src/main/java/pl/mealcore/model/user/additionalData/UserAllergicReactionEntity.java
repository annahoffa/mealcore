package pl.mealcore.model.user.additionalData;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;
import pl.mealcore.model.allergicReaction.AllergicReactionEntity;
import pl.mealcore.model.user.basicData.UserEntity;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users_allergic_reactions_16")
@EqualsAndHashCode(callSuper = true)
public class UserAllergicReactionEntity extends BaseEntity {

    private UserEntity user;
    private AllergicReactionEntity allergicReaction;
    private Date date;

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

    @ManyToOne
    public AllergicReactionEntity getAllergicReaction() {
        return allergicReaction;
    }
}
