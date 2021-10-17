package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.account.UserProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProductRepository extends JpaRepository<UserProductEntity, Long> {
    List<UserProductEntity> findAllByUserId(Long userId);

    Optional<UserProductEntity> findByUserIdAndProductId(Long userId, Long productId);
}
