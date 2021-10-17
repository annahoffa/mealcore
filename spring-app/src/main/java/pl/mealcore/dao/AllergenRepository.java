package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.account.AllergenEntity;

import java.util.List;

@Repository
public interface AllergenRepository extends JpaRepository<AllergenEntity, Long> {
    List<AllergenEntity> findAllByUserId(Long userId);
}
