package pl.mealcore.dto.sport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.helper.NumberHelper;
import pl.mealcore.model.sport.SportEntity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sport extends BaseDto<SportEntity> {
    private String name;
    private Integer kcalPerHour;
    private double duration;
    private double calculatedKcal;

    public Sport(SportEntity entity) {
        super(entity);
        this.name = entity.getName();
        this.kcalPerHour = entity.getKcal();
    }

    public Sport(Long id) {
        super(id);
    }

    @Override
    public SportEntity toEntity() {
        SportEntity entity = new SportEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        entity.setKcal(this.kcalPerHour);
        return entity;
    }

    public void calculateKcal() {
        calculatedKcal = NumberHelper.round(duration * kcalPerHour);
    }
}
