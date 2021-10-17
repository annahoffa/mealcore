package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.product.AdditionEntity;

import java.util.Optional;

@Repository
public interface AdditivesRepository extends JpaRepository<AdditionEntity, Long> {
    Optional<AdditionEntity> findFirstByName(String name);
}
