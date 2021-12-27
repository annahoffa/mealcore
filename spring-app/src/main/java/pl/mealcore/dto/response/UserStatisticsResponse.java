package pl.mealcore.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mealcore.dto.statistic.StatisticNutrients;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserStatisticsResponse extends BasicResponse {
    List<StatisticNutrients> statistics;

    public UserStatisticsResponse(List<StatisticNutrients> statistics) {
        this.statistics = statistics;
    }

    @Override
    public UserStatisticsResponse withSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
