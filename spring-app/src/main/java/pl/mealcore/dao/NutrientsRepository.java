package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.product.NutrientsEntity;

import java.util.Optional;

@Repository
public interface NutrientsRepository extends JpaRepository<NutrientsEntity, Long> {
    Optional<NutrientsEntity> findByProductId(Long productId);
}
