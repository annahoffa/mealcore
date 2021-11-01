package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.user.additionalData.UserAllergenEntity;

import java.util.List;

@Repository
public interface AllergenRepository extends JpaRepository<UserAllergenEntity, Long> {
    List<UserAllergenEntity> findAllByUserId(Long userId);
}
