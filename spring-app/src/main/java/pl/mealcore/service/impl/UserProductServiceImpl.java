package pl.mealcore.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.UserProductRepository;
import pl.mealcore.dto.account.User;
import pl.mealcore.dto.account.UserProduct;
import pl.mealcore.dto.product.BasicNutrients;
import pl.mealcore.dto.product.Nutrients;
import pl.mealcore.dto.product.Product;
import pl.mealcore.dto.response.UserProductsResponse;
import pl.mealcore.helper.DateHelper;
import pl.mealcore.model.account.UserProductEntity;
import pl.mealcore.model.product.ProductCategory;
import pl.mealcore.service.ProductService;
import pl.mealcore.service.UserProductService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {
    private final UserProductRepository userProductRepository;
    private final ProductService productService;

    @Override
    public UserProductsResponse getProductsWithNutrientsForUser(@NonNull User user, Date date) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        List<Product> products = new ArrayList<>();
        BasicNutrients nutrients = new BasicNutrients();
        List<UserProductEntity> userProducts = userProductRepository.findAllByUserIdAndDate(user.getId(), date);
        for (UserProductEntity userProduct : userProducts) {
            Product product = productService.createBaseProduct(userProduct.getProduct());
            productService.completeProduct(user, product);
            product.setAddedQuantity(userProduct.getQuantity());
            product.setCategory(userProduct.getCategory());
            products.add(product);
            Nutrients productNutrients = product.getNutrients();
            if (nonNull(productNutrients)) {
                double scale = userProduct.getQuantity() / 100d;
                updateNutrientsByScale(scale, productNutrients);
                nutrients.addKcal(NumberUtils.toDouble(productNutrients.getEnergyKcal()));
                nutrients.addCarbohydrates(NumberUtils.toDouble(productNutrients.getCarbohydrates()));
                nutrients.addFat(NumberUtils.toDouble(productNutrients.getFat()));
                nutrients.addProteins(NumberUtils.toDouble(productNutrients.getProteins()));
                nutrients.addFiber(NumberUtils.toDouble(productNutrients.getFiber()));
            }
        }
        return new UserProductsResponse(products, nutrients, DateHelper.format(date));
    }

    @Override
    public void addUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserProduct toSave = userProductRepository.findByUserIdAndProductIdAndDate(user.getId(), productId, date)
                .map(UserProduct::new)
                .orElse(new UserProduct(user, new Product(productId), date, 0, isNull(category) ? ProductCategory.OTHER : category));
        toSave.addQuantity(quantity);
        userProductRepository.save(toSave.toEntity());
    }

    @Override
    public boolean editUserProduct(@NonNull User user, @NonNull Long productId, @NonNull Integer quantity, Date date, ProductCategory category) {
        date = DateHelper.getDateWithoutTime(date, new Date());
        UserProductEntity userProduct = userProductRepository.findByUserIdAndProductIdAndDate(user.getId(), productId, date).orElse(null);
        if (nonNull(userProduct)) {
            userProduct.setQuantity(quantity);
            userProduct.setCategory(isNull(category) ? ProductCategory.OTHER : category);
            userProductRepository.save(userProduct);
            return true;
        }
        return false;
    }

    @Override
    public void deleteUserProduct(User user, Long productId, Date date) {
        userProductRepository.findByUserIdAndProductIdAndDate(user.getId(), productId, DateHelper.getDateWithoutTime(date, new Date()))
                .ifPresent(userProductRepository::delete);
    }

    //  PRIVS
    private void updateNutrientsByScale(Double scale, Nutrients nutrients) {
        double kcal = 0;
        double carbohydrates = NumberUtils.toDouble(nutrients.getCarbohydrates()) * scale;
        double fat = NumberUtils.toDouble(nutrients.getFat()) * scale;
        double proteins = NumberUtils.toDouble(nutrients.getProteins()) * scale;
        double fiber = NumberUtils.toDouble(nutrients.getFiber()) * scale;

        if (nonNull(nutrients.getEnergyKcal()))
            kcal = NumberUtils.toDouble(nutrients.getEnergyKcal()) * scale;
        else if (nonNull(nutrients.getEnergyKj()))
            kcal = NumberUtils.toDouble(nutrients.getEnergyKj()) * 0.2390 * scale;
        else if (nonNull(nutrients.getEnergy()))
            kcal = NumberUtils.toDouble(nutrients.getEnergy()) * 0.2390 * scale;

        if (kcal > 0)
            nutrients.setEnergyKcal(String.valueOf(Math.floor(kcal * 100) / 100));
        if (carbohydrates > 0)
            nutrients.setCarbohydrates(String.valueOf(Math.floor(carbohydrates * 100) / 100));
        if (fat > 0)
            nutrients.setFat(String.valueOf(Math.floor(fat * 100) / 100));
        if (proteins > 0)
            nutrients.setProteins(String.valueOf(Math.floor(proteins * 100) / 100));
        if (fiber > 0)
            nutrients.setFiber(String.valueOf(Math.floor(fiber * 100) / 100));
    }
}
