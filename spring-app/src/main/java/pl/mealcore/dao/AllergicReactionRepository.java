package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.allergicReaction.AllergicReactionEntity;

@Repository
public interface AllergicReactionRepository extends JpaRepository<AllergicReactionEntity, Long> {
}
