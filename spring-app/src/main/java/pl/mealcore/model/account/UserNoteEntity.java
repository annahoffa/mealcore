package pl.mealcore.model.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "USERS_NOTES_13")
@EqualsAndHashCode(callSuper = true)
public class UserNoteEntity extends BaseEntity {

    private UserEntity user;
    private Date date;
    private String text;

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
