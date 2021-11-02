package pl.mealcore.dto.allergicReaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.mealcore.dto.BaseDto;
import pl.mealcore.model.allergicReaction.AllergicReactionEntity;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AllergicReaction extends BaseDto<AllergicReactionEntity> {
    private String name;

    public AllergicReaction(AllergicReactionEntity entity) {
        super(entity);
        this.name = entity.getName();
    }

    public AllergicReaction(Long id) {
        super(id);
    }

    @Override
    public AllergicReactionEntity toEntity() {
        AllergicReactionEntity entity = new AllergicReactionEntity();
        entity.setId(this.id);
        entity.setName(this.name);
        return entity;
    }

}
