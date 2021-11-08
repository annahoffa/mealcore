package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.user.additionalData.UserAllergicReactionEntity;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserAllergicReactionRepository extends JpaRepository<UserAllergicReactionEntity, Long> {
    Optional<UserAllergicReactionEntity> findByUserIdAndDate(Long userId, Date date);
    void deleteAllByUserIdAndDate(Long userId, Date date);
}
