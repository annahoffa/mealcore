package pl.mealcore.model.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.mealcore.model.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "nutrients_2")
@EqualsAndHashCode(callSuper = true)
public class NutrientsEntity extends BaseEntity {

    private Long productId;
    private String energy_kj_100g;
    private String energy_kcal_100g;
    private String energy_100g;
    private String energy_from_fat_100g;
    private String fat_100g;
    private String saturated_fat_100g;
    private String butyric_acid_100g;
    private String caproic_acid_100g;
    private String caprylic_acid_100g;
    private String capric_acid_100g;
    private String lauric_acid_100g;
    private String myristic_acid_100g;
    private String palmitic_acid_100g;
    private String stearic_acid_100g;
    private String arachidic_acid_100g;
    private String behenic_acid_100g;
    private String lignoceric_acid_100g;
    private String cerotic_acid_100g;
    private String montanic_acid_100g;
    private String melissic_acid_100g;
    private String monounsaturated_fat_100g;
    private String polyunsaturated_fat_100g;
    private String omega_3_fat_100g;
    private String alpha_linolenic_acid_100g;
    private String eicosapentaenoic_acid_100g;
    private String docosahexaenoic_acid_100g;
    private String omega_6_fat_100g;
    private String linoleic_acid_100g;
    private String arachidonic_acid_100g;
    private String gamma_linolenic_acid_100g;
    private String dihomo_gamma_linolenic_acid_100g;
    private String omega_9_fat_100g;
    private String oleic_acid_100g;
    private String elaidic_acid_100g;
    private String gondoic_acid_100g;
    private String mead_acid_100g;
    private String erucic_acid_100g;
    private String nervonic_acid_100g;
    private String trans_fat_100g;
    private String cholesterol_100g;
    private String carbohydrates_100g;
    private String sugars_100g;
    private String sucrose_100g;
    private String glucose_100g;
    private String fructose_100g;
    private String lactose_100g;
    private String maltose_100g;
    private String maltodextrins_100g;
    private String starch_100g;
    private String polyols_100g;
    private String fiber_100g;
    private String soluble_fiber_100g;
    private String insoluble_fiber_100g;
    private String proteins_100g;
    private String casein_100g;
    private String serum_proteins_100g;
    private String nucleotides_100g;
    private String salt_100g;
    private String sodium_100g;
    private String alcohol_100g;
    private String vitamin_a_100g;
    private String beta_carotene_100g;
    private String vitamin_d_100g;
    private String vitamin_e_100g;
    private String vitamin_k_100g;
    private String vitamin_c_100g;
    private String vitamin_b1_100g;
    private String vitamin_b2_100g;
    private String vitamin_pp_100g;
    private String vitamin_b6_100g;
    private String vitamin_b9_100g;
    private String folates_100g;
    private String vitamin_b12_100g;
    private String biotin_100g;
    private String pantothenic_acid_100g;
    private String silica_100g;
    private String bicarbonate_100g;
    private String potassium_100g;
    private String chloride_100g;
    private String calcium_100g;
    private String phosphorus_100g;
    private String iron_100g;
    private String magnesium_100g;
    private String zinc_100g;
    private String copper_100g;
    private String manganese_100g;
    private String fluoride_100g;
    private String selenium_100g;
    private String chromium_100g;
    private String molybdenum_100g;
    private String iodine_100g;
    private String caffeine_100g;
    private String taurine_100g;
    private String ph_100g;
    private String fruits_vegetables_nuts_100g;
    private String fruits_vegetables_nuts_dried_100g;
    private String fruits_vegetables_nuts_estimate_100g;
    private String collagen_meat_protein_ratio_100g;
    private String cocoa_100g;
    private String chlorophyl_100g;
    private String carbon_footprint_100g;
    private String carbon_footprint_from_meat_or_fish_100g;
    private String glycemic_index_100g;
    private String water_hardness_100g;
    private String choline_100g;
    private String phylloquinone_100g;
    private String beta_glucan_100g;
    private String inositol_100g;
    private String carnitine_100g;

    @Id
    @Override
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

}
