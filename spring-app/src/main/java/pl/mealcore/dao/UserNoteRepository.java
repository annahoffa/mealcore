package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.user.additionalData.UserNoteEntity;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserNoteRepository extends JpaRepository<UserNoteEntity, Long> {
    Optional<UserNoteEntity> findByUserIdAndDate(Long userId, Date date);
}
