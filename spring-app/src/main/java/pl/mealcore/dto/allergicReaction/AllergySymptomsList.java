package pl.mealcore.dto.allergicReaction;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class AllergySymptomsList {
    private List<Long> symptomIds;
    private Instant date;
}
