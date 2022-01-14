package pl.mealcore.dto.allergicReaction;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AllergySymptomsList {
    private List<Long> symptomIds;
    private Date date;
}
