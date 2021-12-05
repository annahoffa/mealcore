package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.product.ProductEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByNameContainsAndApprovedIsTrue(String name);

    List<ProductEntity> findAllByNameStartsWithAndApprovedIsTrue(String name);

    List<ProductEntity> findAllByNameIgnoreCaseAndApprovedIsTrue(String name);

    List<ProductEntity> findAllByApprovedIsFalse();

    Optional<ProductEntity> findByIdAndApprovedIsTrue(Long id);

    Optional<ProductEntity> findById(Long id);
}
