package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.account.UserExerciseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserExerciseRepository extends JpaRepository<UserExerciseEntity, Long> {
    List<UserExerciseEntity> findAllByUserIdAndDate(Long userId, Date date);

    Optional<UserExerciseEntity> findByUserIdAndSportIdAndDate(Long userId, Long sportId, Date date);
}
