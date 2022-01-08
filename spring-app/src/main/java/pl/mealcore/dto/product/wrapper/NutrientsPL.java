package pl.mealcore.dto.product.wrapper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import pl.mealcore.dto.product.Nutrients;
import pl.mealcore.helper.NumberHelper;

@Data
@EqualsAndHashCode
public class NutrientsPL {
    private Long id;
    private Long productId;
    private String energia;
    private String energia_z_tłuszczu;
    private String tłuszcz;
    private String tłuszcze_nasycone;
    private String omega3;
    private String omega6;
    private String omega9;
    private String cholesterol;
    private String węglowodany;
    private String cukry;
    private String laktoza;
    private String błonnik;
    private String białko;
    private String sól;
    private String sód;
    private String beta_karoten;
    private String witamina_A;
    private String witamina_D;
    private String witamina_E;
    private String witamina_K;
    private String witamina_C;
    private String witamina_B1;
    private String witamina_B2;
    private String witamina_Pp;
    private String witamina_B6;
    private String witamina_B9;
    private String witamina_B12;
    private String potas;
    private String wapń;
    private String fosfor;
    private String żelazo;
    private String magnez;
    private String cynk;
    private String miedź;
    private String mangan;
    private String kofeina;
    private String ph;
    private String indeks_glikemiczny;

    public NutrientsPL(Nutrients nutrients) {
        this.id = nutrients.getId();
        this.productId = nutrients.getProductId();
        this.energia = formatEnergyData(nutrients);
        this.energia_z_tłuszczu = formatData(nutrients.getEnergyFromFat());
        this.tłuszcz = formatData(nutrients.getFat());
        this.tłuszcze_nasycone = formatData(nutrients.getSaturatedFat());
        this.omega3 = formatData(nutrients.getOmega3Fat());
        this.omega6 = formatData(nutrients.getOmega6Fat());
        this.omega9 = formatData(nutrients.getOmega9Fat());
        this.cholesterol = formatData(nutrients.getCholesterol());
        this.węglowodany = formatData(nutrients.getCarbohydrates());
        this.laktoza = formatData(nutrients.getLactose());
        this.błonnik = formatData(nutrients.getFiber());
        this.białko = formatData(nutrients.getProteins());
        this.sól = formatData(nutrients.getSalt());
        this.sód = formatData(nutrients.getSodium());
        this.beta_karoten = formatData(nutrients.getBetaCarotene());
        this.witamina_A = formatData(nutrients.getVitaminA());
        this.witamina_D = formatData(nutrients.getVitaminD());
        this.witamina_E = formatData(nutrients.getVitaminE());
        this.witamina_K = formatData(nutrients.getVitaminK());
        this.witamina_C = formatData(nutrients.getVitaminC());
        this.witamina_B1 = formatData(nutrients.getVitaminB1());
        this.witamina_B2 = formatData(nutrients.getVitaminB2());
        this.witamina_Pp = formatData(nutrients.getVitaminPp());
        this.witamina_B6 = formatData(nutrients.getVitaminB6());
        this.witamina_B9 = formatData(nutrients.getVitaminB9());
        this.witamina_B12 = formatData(nutrients.getVitaminB12());
        this.potas = formatData(nutrients.getPotassium());
        this.wapń = formatData(nutrients.getCalcium());
        this.fosfor = formatData(nutrients.getPhosphorus());
        this.żelazo = formatData(nutrients.getIron());
        this.magnez = formatData(nutrients.getMagnesium());
        this.cynk = formatData(nutrients.getZinc());
        this.miedź = formatData(nutrients.getCopper());
        this.mangan = formatData(nutrients.getManganese());
        this.kofeina = formatData(nutrients.getCaffeine());
        this.ph = formatData(nutrients.getPh());
        this.indeks_glikemiczny = formatData(nutrients.getGlycemicIndex());
    }

    private String formatData(String data) {
        return StringUtils.isNotBlank(data) ? data + " g" : null;
    }

    private String formatEnergyData(Nutrients nutrients) {
        String result = "";
        String energy = nutrients.getEnergy();
        String energyKj = nutrients.getEnergyKj();
        String energyKcal = NumberHelper.format(nutrients.getKcal());
        if (StringUtils.isNotBlank(energy))
            result = energy + "kJ";
        if (StringUtils.isNotBlank(energyKj))
            result = energyKj + "kJ";
        if (StringUtils.isNotBlank(energyKcal))
            if (StringUtils.isNotBlank(result))
                result = result + " / " + energyKcal + "kcal";
            else
                result = energyKcal + " kcal";
        return result;
    }
}
