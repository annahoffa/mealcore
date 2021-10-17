package pl.mealcore.dto.product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.product.AdditionEntity;

@Data
@EqualsAndHashCode(callSuper = true)
public class Addition extends BaseDto<AdditionEntity> {

    private String name;
    private String description;

    public Addition(AdditionEntity entity) {
        super(entity);
        name = entity.getName();
        description = entity.getDescription();
    }

    public Addition(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public AdditionEntity toEntity() {
        AdditionEntity entity = new AdditionEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setDescription(description);
        return entity;
    }
}
