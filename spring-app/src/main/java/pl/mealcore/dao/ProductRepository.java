package pl.mealcore.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mealcore.model.product.ProductEntity;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findAllByNameContains(String name);

    List<ProductEntity> findAllByNameStartsWith(String name);

    List<ProductEntity> findAllByNameIgnoreCase(String name);
}
