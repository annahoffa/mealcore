package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.product.IngredientsEntity;

import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<IngredientsEntity, Long> {
    Optional<IngredientsEntity> findByProductId(Long productId);
}
