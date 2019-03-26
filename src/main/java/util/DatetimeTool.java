package util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DatetimeTool {
    public static Date getCurrentDate(){
        long millis = Calendar.getInstance().getTimeInMillis();
        return new Date(millis);
    }

    public static Timestamp getCurrentTimestamp(){
        long millis = Calendar.getInstance().getTimeInMillis();
        return  new Timestamp(millis);
    }
}
