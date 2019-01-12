package nguyengquanghuy.mssv20141973.clerk_assitant;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {

    public static final String FORMMAT_STRING = "yyyy/MM/dd HH:mm:ss";
    public long convertStringToDate(String time, String format) {
        Date date = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public String convertDateToString(long time, String format) {
        String timeString;
        Date date = Calendar.getInstance().getTime();
        date.setTime(time);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
}
