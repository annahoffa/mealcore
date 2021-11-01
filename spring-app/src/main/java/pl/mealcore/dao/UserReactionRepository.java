package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.model.user.additionalData.UserReactionEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserReactionRepository extends JpaRepository<UserReactionEntity, Long> {
    List<UserReactionEntity> findAllByUserIdAndDate(Long userId, Date date);

    Optional<UserReactionEntity> findByUserIdAndDateAndCategory(Long userId, Date date, ProductCategory category);
}
