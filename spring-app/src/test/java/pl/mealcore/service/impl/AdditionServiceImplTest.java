package pl.mealcore.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mealcore.MealCoreApplication;
import pl.mealcore.dao.AdditivesRepository;
import pl.mealcore.dto.product.Addition;
import pl.mealcore.model.product.AdditionEntity;
import pl.mealcore.model.product.IngredientsEntity;
import pl.mealcore.model.product.ProductEntity;
import pl.mealcore.service.AdditionService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MealCoreApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(
        locations = "classpath:application-test.properties")
class AdditionServiceImplTest {

    @Autowired
    private AdditivesRepository additivesRepository;
    @Autowired
    private AdditionService additionService;


    @BeforeEach
    public void setUp() {
        AdditionEntity e120 = new AdditionEntity();
        e120.setId(1L);
        e120.setName("E120");
        e120.setDescription("E120 DESC");
        AdditionEntity e125 = new AdditionEntity();
        e125.setId(2L);
        e125.setName("E125");
        e125.setDescription("E125 DESC");

        additivesRepository.save(e120);
        additivesRepository.save(e125);
    }

    @AfterEach
    public void cleanUp() {
        additivesRepository.deleteAll();
    }

    @Test
    void when_addition_not_found_extractAdditives_response_should_be_valid() {
        ProductEntity product = new ProductEntity();
        product.setIngredients(new IngredientsEntity());
        product.getIngredients().setAdditives_tags("E120,E121");

        List<Addition> additions = additionService.extractAdditives(product);

        assertEquals(2, additions.size());
        assertEquals(1L, additions.get(0).getId());
        assertEquals("E120", additions.get(0).getName());
        assertEquals("E120 DESC", additions.get(0).getDescription());
        assertNull(additions.get(1).getId());
        assertEquals("E121", additions.get(1).getName());
        assertEquals("Brak informacji o dodatku", additions.get(1).getDescription());
        throw new RuntimeException();
    }

    @Test
    void when_product_without_additives_tags_extractAdditives_return_empty_list() {
        ProductEntity product = new ProductEntity();

        List<Addition> additions = additionService.extractAdditives(product);

        assertNotNull(additions);
        assertTrue(additions.isEmpty());
    }

    @Test
    void when_getAll_should_return_valid_list() {
        List<Addition> additions = additionService.getAll();

        assertEquals(2, additions.size());
        assertEquals(1L, additions.get(0).getId());
        assertEquals("E120", additions.get(0).getName());
        assertEquals("E120 DESC", additions.get(0).getDescription());
        assertEquals(2L, additions.get(1).getId());
        assertEquals("E125", additions.get(1).getName());
        assertEquals("E125 DESC", additions.get(1).getDescription());
    }
}