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
        //convert string date to 
        try{
            // System.out.println("INPUT: "+dateStr);
            
            Date date = dateFormat.parse(dateStr);
            // System.out.println("step 0: dateStr to -> date: "+date+" \n");

            // convert date to localdatetime
            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            // System.out.println("step 1: date to -> localDateTime: "+localDateTime+" \n");

            // delay on date
            int days = (int) ( ( Math.random() * ((30 - 7) + 1)) + 7 );
            // System.out.println("step 2: adding days: "+days+",  onto localDateTime:"+localDateTime+" \n");
            
            localDateTime = localDateTime.plusDays(days);
            // System.out.println("step 3: altered localDateTime: "+localDateTime+" \n");

            // convert LocalDateTime to date
            Date currentDatePlusOneDay = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            // System.out.println("step 4: altered after convert: "+currentDatePlusOneDay);

            // System.out.println("step 5: OUTPUT: "+dateFormat.format(currentDatePlusOneDay));
            return(dateFormat.format(currentDatePlusOneDay));
        }catch(Exception e){
            System.err.println("[ DateHandler ERROR: "+e+" ]\n");
        }
        return "null";
    }





}