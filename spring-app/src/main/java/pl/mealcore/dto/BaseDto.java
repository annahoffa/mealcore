package pl.mealcore.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import pl.mealcore.model.BaseEntity;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@NoArgsConstructor
@EqualsAndHashCode
public abstract class BaseDto<T extends BaseEntity> implements Serializable {
    @Getter
    @Setter
    protected Long id;

    public BaseDto(Long id) {
        this.id = id;
    }

    public BaseDto(T entity) {
        this.id = entity.getId();
    }

    protected <E extends BaseEntity> E createEntityReference(@NonNull Class<E> clazz, BaseDto dto) {
        Long id = Optional.ofNullable(dto).map(BaseDto::getId).orElse(null);
        return createEntityReference(clazz, id);
    }

    protected Long getEntityId(BaseEntity entity) {
        return Optional.ofNullable(entity)
                .map(BaseEntity::getId)
                .orElse(null);
    }

    protected <E extends BaseEntity> E createEntityReference(@NonNull Class<E> clazz, Long id) {
        E result = null;
        if (id != null) {
            try {
                result = clazz.getDeclaredConstructor().newInstance();
                result.setId(id);
            } catch (Exception e) {
                log.error("Unable to create entity instance of class " + clazz.getSimpleName(), e);
            }
        }
        return result;
    }

    protected <E extends BaseEntity, D extends BaseDto> D createDto(@NonNull Function<E, D> dtoConstructor, E entity) {
        return entity != null ? dtoConstructor.apply(entity) : null;
    }

    public abstract T toEntity();

}
