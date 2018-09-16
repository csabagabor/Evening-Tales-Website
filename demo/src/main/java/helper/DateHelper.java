package helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateHelper {

    public static final String correctDatePattern = "yyyy-MM-dd";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(correctDatePattern);

    public static String parseLocalDateIntoCorrectFormat(LocalDate date) {
        String correctDateFormat = date.format(formatter);
        return correctDateFormat;
    }

}
