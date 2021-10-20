package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.sport.SportEntity;

@Repository
public interface SportRepository extends JpaRepository<SportEntity, Long> {
}
