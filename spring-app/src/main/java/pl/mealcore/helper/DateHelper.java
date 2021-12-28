package pl.mealcore.helper;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Objects.nonNull;

@UtilityClass
public class DateHelper {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Get date without time, if date == null return today
     *
     * @param date to format
     * @param defaultDate default
     * @return formatted date
     */
    public Date getDateWithoutTime(Date date, Date defaultDate) {
        return DateUtils.truncate(nonNull(date) ? date : defaultDate, Calendar.DAY_OF_MONTH);
    }

    /**
     * Parse string to date format "yyyy-MM-dd"
     *
     * @param date to parse
     * @return parsed date
     */
    public Date parse(String date) {
        try {
            return DateUtils.parseDate(date, DATE_FORMAT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Format date to string "yyyy-MM-dd"
     *
     * @param date to format
     * @return formatted string
     */
    public String format(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return formatter.format(date);
    }

    /**
     * Add days to date
     *
     * @param date date
     * @param toAdd number of days to add
     * @return date + toAdd
     */
    public Date addDays(Date date, int toAdd) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, toAdd);
        return c.getTime();
    }
}
