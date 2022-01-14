package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.user.additionalData.UserAllergicReactionEntity;

import java.util.Date;
import java.util.List;

@Repository
public interface UserAllergicReactionRepository extends JpaRepository<UserAllergicReactionEntity, Long> {
    List<UserAllergicReactionEntity> findAllByUserIdAndDate(Long userId, Date date);

    List<UserAllergicReactionEntity> findAllByUserId(Long userId);

    long deleteAllByUserIdAndDate(Long userId, Date date);
}
