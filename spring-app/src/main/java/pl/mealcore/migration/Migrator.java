package pl.mealcore.migration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.mealcore.dao.*;
import pl.mealcore.model.product.*;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class Migrator implements ApplicationListener<ApplicationReadyEvent> {
//    BEFORE MIGRATION REMOVE @GeneratedValue ANNOTATION

    private static final String SEPARATOR = "\\|-\\|";
    private static final String MIGRATION_MODE = "migration";

    private final ProductRepository productRepository;
    private final NutrientsRepository nutrientsRepository;
    private final IngredientsRepository ingredientsRepository;
    private final ImageRepository imageRepository;
    private final AdditivesRepository additivesRepository;

    List<ProductEntity> products = new ArrayList<>();
    List<NutrientsEntity> nutrients = new ArrayList<>();
    List<IngredientsEntity> ingredients = new ArrayList<>();
    List<ImageEntity> images = new ArrayList<>();
    List<AdditionEntity> additions = new ArrayList<>();

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if (isMigrationMode(event) && productRepository.count() == 0L) {
            log.info("START IN MIGRATION MODE");
            processFile("products_1.csv", this::addProduct);
            log.info("Processed {} products", products.size());
            processFile("nutrients_2.csv", this::addNutrients);
            log.info("Processed {} nutrients", nutrients.size());
            processFile("ingredients_3.csv", this::addIngredients);
            log.info("Processed {} ingredients", ingredients.size());
            processFile("images_4.csv", this::addImages);
            log.info("Processed {} images", images.size());
            processFile("addition_5.csv", this::addAddition);
            log.info("Processed {} additions", additions.size());
            log.info("SAVING...");
            saveAll();
            log.info("MIGRATION COMPLETED");
        }
    }

    private void saveAll() {
        productRepository.saveAll(products);
        productRepository.flush();
        log.info("Saved products");
        nutrientsRepository.saveAll(nutrients);
        nutrientsRepository.flush();
        log.info("Saved nutrients");
        ingredientsRepository.saveAll(ingredients);
        ingredientsRepository.flush();
        log.info("Saved ingredients");
        imageRepository.saveAll(images);
        imageRepository.flush();
        log.info("Saved images");
        additivesRepository.saveAll(additions);
        additivesRepository.flush();
        log.info("Saved additions");
    }

    private void processFile(String path, Consumer<? super String> addMethod) {
        try {
            File file = new File(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            reader.lines().forEach(addMethod);
            reader.close();
        } catch (Exception e) {
            log.error("Failed", e);
            System.exit(0);
        }
    }

    private void addProduct(String line) {
        String[] row = line.split(SEPARATOR, -1);
        ProductEntity entity = new ProductEntity();
        entity.setId(Long.valueOf(row[0]));
        entity.setCode(row[1]);
        entity.setName(row[2]);
        entity.setBrands(row[3]);
        entity.setCountries_tags(row[4]);
        entity.setServing_quantity(row[5]);
        entity.setServing_size(row[6]);
        entity.setEmb_codes_tags(row[7]);
        entity.setQuantity(row[8]);
        products.add(entity);
    }

    private void addIngredients(String line) {
        String[] row = line.split(SEPARATOR, -1);
        IngredientsEntity entity = new IngredientsEntity();
        entity.setId(Long.valueOf(row[0]));
        entity.setIngredients_text(row[1]);
        entity.setAllergens(row[2]);
        entity.setTraces_tags(row[3]);
        entity.setAdditives_tags(row[4]);
        entity.setIngredients_from_palm_oil_tags(row[5]);
        entity.setIngredients_that_may_be_from_palm_oil_tags(row[6]);
        entity.setProductId(Long.valueOf(row[7]));
        ingredients.add(entity);
    }

    private void addAddition(String line) {
        String[] row = line.split(SEPARATOR, -1);
        AdditionEntity entity = new AdditionEntity();
        entity.setId(Long.valueOf(row[0]));
        entity.setName(row[1]);
        entity.setDescription(row[2]);
        additions.add(entity);
    }

    private void addImages(String line) {
        String[] row = line.split(SEPARATOR, -1);
        ImageEntity entity = new ImageEntity();
        entity.setId(Long.valueOf(row[0]));
        entity.setUrl(row[1]);
        entity.setType(ImageType.fromCode(Integer.parseInt(row[2])));
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(Long.valueOf(row[3]));
        entity.setProduct(productEntity);
        images.add(entity);
    }

    private void addNutrients(String line) {
        String[] row = line.split(SEPARATOR, -1);
        NutrientsEntity entity = new NutrientsEntity();
        entity.setId(Long.valueOf(row[0]));
        entity.setEnergy_kj_100g(row[1]);
        entity.setEnergy_kcal_100g(row[2]);
        entity.setEnergy_100g(row[3]);
        entity.setEnergy_from_fat_100g(row[4]);
        entity.setFat_100g(row[5]);
        entity.setSaturated_fat_100g(row[6]);
        entity.setButyric_acid_100g(row[7]);
        entity.setCaproic_acid_100g(row[8]);
        entity.setCaprylic_acid_100g(row[9]);
        entity.setCapric_acid_100g(row[10]);
        entity.setLauric_acid_100g(row[11]);
        entity.setMyristic_acid_100g(row[12]);
        entity.setPalmitic_acid_100g(row[13]);
        entity.setStearic_acid_100g(row[14]);
        entity.setArachidic_acid_100g(row[15]);
        entity.setBehenic_acid_100g(row[16]);
        entity.setLignoceric_acid_100g(row[17]);
        entity.setCerotic_acid_100g(row[18]);
        entity.setMontanic_acid_100g(row[19]);
        entity.setMelissic_acid_100g(row[20]);
        entity.setMonounsaturated_fat_100g(row[21]);
        entity.setPolyunsaturated_fat_100g(row[22]);
        entity.setOmega_3_fat_100g(row[23]);
        entity.setAlpha_linolenic_acid_100g(row[24]);
        entity.setEicosapentaenoic_acid_100g(row[25]);
        entity.setDocosahexaenoic_acid_100g(row[26]);
        entity.setOmega_6_fat_100g(row[27]);
        entity.setLinoleic_acid_100g(row[28]);
        entity.setArachidonic_acid_100g(row[29]);
        entity.setGamma_linolenic_acid_100g(row[30]);
        entity.setDihomo_gamma_linolenic_acid_100g(row[31]);
        entity.setOmega_9_fat_100g(row[32]);
        entity.setOleic_acid_100g(row[33]);
        entity.setElaidic_acid_100g(row[34]);
        entity.setGondoic_acid_100g(row[35]);
        entity.setMead_acid_100g(row[36]);
        entity.setErucic_acid_100g(row[37]);
        entity.setNervonic_acid_100g(row[38]);
        entity.setTrans_fat_100g(row[39]);
        entity.setCholesterol_100g(row[40]);
        entity.setCarbohydrates_100g(row[41]);
        entity.setSugars_100g(row[42]);
        entity.setSucrose_100g(row[43]);
        entity.setGlucose_100g(row[44]);
        entity.setFructose_100g(row[45]);
        entity.setLactose_100g(row[46]);
        entity.setMaltose_100g(row[47]);
        entity.setMaltodextrins_100g(row[48]);
        entity.setStarch_100g(row[49]);
        entity.setPolyols_100g(row[50]);
        entity.setFiber_100g(row[51]);
        entity.setSoluble_fiber_100g(row[52]);
        entity.setInsoluble_fiber_100g(row[53]);
        entity.setProteins_100g(row[54]);
        entity.setCasein_100g(row[55]);
        entity.setSerum_proteins_100g(row[56]);
        entity.setNucleotides_100g(row[57]);
        entity.setSalt_100g(row[58]);
        entity.setSodium_100g(row[59]);
        entity.setAlcohol_100g(row[60]);
        entity.setVitamin_a_100g(row[61]);
        entity.setBeta_carotene_100g(row[62]);
        entity.setVitamin_d_100g(row[63]);
        entity.setVitamin_e_100g(row[64]);
        entity.setVitamin_k_100g(row[65]);
        entity.setVitamin_c_100g(row[66]);
        entity.setVitamin_b1_100g(row[67]);
        entity.setVitamin_b2_100g(row[68]);
        entity.setVitamin_pp_100g(row[69]);
        entity.setVitamin_b6_100g(row[70]);
        entity.setVitamin_b9_100g(row[71]);
        entity.setFolates_100g(row[72]);
        entity.setVitamin_b12_100g(row[73]);
        entity.setBiotin_100g(row[74]);
        entity.setPantothenic_acid_100g(row[75]);
        entity.setSilica_100g(row[76]);
        entity.setBicarbonate_100g(row[77]);
        entity.setPotassium_100g(row[78]);
        entity.setChloride_100g(row[79]);
        entity.setCalcium_100g(row[80]);
        entity.setPhosphorus_100g(row[81]);
        entity.setIron_100g(row[82]);
        entity.setMagnesium_100g(row[83]);
        entity.setZinc_100g(row[84]);
        entity.setCopper_100g(row[85]);
        entity.setManganese_100g(row[86]);
        entity.setFluoride_100g(row[87]);
        entity.setSelenium_100g(row[88]);
        entity.setChromium_100g(row[89]);
        entity.setMolybdenum_100g(row[90]);
        entity.setIodine_100g(row[91]);
        entity.setCaffeine_100g(row[92]);
        entity.setTaurine_100g(row[93]);
        entity.setPh_100g(row[94]);
        entity.setFruits_vegetables_nuts_100g(row[95]);
        entity.setFruits_vegetables_nuts_dried_100g(row[96]);
        entity.setFruits_vegetables_nuts_estimate_100g(row[97]);
        entity.setCollagen_meat_protein_ratio_100g(row[98]);
        entity.setCocoa_100g(row[99]);
        entity.setChlorophyl_100g(row[100]);
        entity.setCarbon_footprint_100g(row[101]);
        entity.setCarbon_footprint_from_meat_or_fish_100g(row[102]);
        entity.setGlycemic_index_100g(row[103]);
        entity.setWater_hardness_100g(row[104]);
        entity.setCholine_100g(row[105]);
        entity.setPhylloquinone_100g(row[106]);
        entity.setBeta_glucan_100g(row[107]);
        entity.setInositol_100g(row[108]);
        entity.setCarnitine_100g(row[109]);
        entity.setProductId(Long.valueOf(row[110]));
        nutrients.add(entity);
    }

    private Boolean isMigrationMode(ApplicationReadyEvent event) {
        return Arrays.stream(event.getArgs())
                .findFirst()
                .map(MIGRATION_MODE::equalsIgnoreCase)
                .orElse(false);
    }
}
