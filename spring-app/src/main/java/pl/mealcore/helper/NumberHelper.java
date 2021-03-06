package pl.mealcore.helper;

import lombok.experimental.UtilityClass;
import org.apache.commons.math3.util.Precision;

@UtilityClass
public class NumberHelper {

    /**
     * Format number
     *
     * @param x number to format
     * @param whenZero response when number is 0
     * @return rounded number as string
     */
    public String format(double x, String whenZero) {
        return x > 0 ? String.valueOf(Precision.round(x, 2)) : whenZero;
    }

    /**
     * Format number
     *
     * @param x number to format
     * @return rounded number as string, when 0 return empty string
     */
    public String format(double x) {
        return format(x, "");
    }

    /**
     * Round number
     *
     * @param x number to round
     * @return rounded number
     */
    public double round(double x) {
        return Precision.round(x, 2);
    }
}
