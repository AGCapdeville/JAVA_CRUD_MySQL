package app;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateHandler {

    private static final String DATE_FORMAT = "yyy-MM-dd";
    private static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

    public DateHandler() {
    }

    public String newDate() {
        Date currentDate = new Date();
        return (dateFormat.format(currentDate));
    }

    public String etaDate(String dateStr) {
        try {
            Date date = dateFormat.parse(dateStr);

            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            int days = (int) ((Math.random() * ((30 - 7) + 1)) + 7);
            localDateTime = localDateTime.plusDays(days);

            Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            return (dateFormat.format(currentDatePlusOneDay));
        } catch (Exception e) {
            System.err.println("[ DateHandler ERROR: " + e + " ]\n");
        }
        return "null";
    }

}