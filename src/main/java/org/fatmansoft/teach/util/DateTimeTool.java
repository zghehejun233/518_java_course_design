package org.fatmansoft.teach.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeTool {
    public static Date formatDateTime(String timeSrc, String f) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(f);
        sdFormat.setLenient(true);
        try {
            if (timeSrc == null || timeSrc.trim().equals(""))
                return null;

            Date tmpDate = sdFormat.parse(timeSrc);
            return tmpDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
//			e.printStackTrace();
            return null;
        }
    }
    public static String parseDateTime(Date timeSrc, String f) {
        if (timeSrc == null)
            return null;
        SimpleDateFormat sdFormat = new SimpleDateFormat(f);
        String result = sdFormat.format(timeSrc);
        if (result != null)
            return result;
        else
            return "";
    }

    public static Date nextDay(Date date) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DAY_OF_MONTH, 1);
            return c1.getTime();
        } else
            return null;
    }
    public static Date nextDay(Date date, int num) {
        if (date != null) {
            if(num == 0)
                return date;
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DAY_OF_MONTH, num);
            return c1.getTime();
        } else
            return null;
    }

    public static Date prevDay(Date date) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DAY_OF_MONTH, -1);
            return c1.getTime();
        } else
            return null;

    }
    public static Date prevDay(Date date, int n) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DAY_OF_MONTH, -n);
            return c1.getTime();
        } else
            return null;
    }

    public static Date nextWeek(Date date) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DAY_OF_MONTH, 7);
            return c1.getTime();
        } else
            return null;
    }

    public static Date prevWeek(Date date) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.DAY_OF_MONTH, -7);
            return c1.getTime();
        } else
            return null;
    }

    public static Date nextMonth(Date date) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.MONTH, 1);
            return c1.getTime();
        } else
            return null;
    }
    public static Date nextMonth(Date date,int n) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.MONTH, n);
            return c1.getTime();
        } else
            return null;
    }

    public static Date prevMonth(Date date) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.MONTH, -1);
            return c1.getTime();
        } else
            return null;
    }
    public static Date prevMonth(Date date, int n) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.MONTH, -n);
            return c1.getTime();
        } else
            return null;
    }

    public static Date nextYear(Date date,int n) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.YEAR, n);
            return c1.getTime();
        } else
            return null;
    }

    public static Date prevYear(Date date) {
        if (date != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            c1.add(Calendar.YEAR, -1);
            return c1.getTime();
        } else
            return null;
    }

    public static int getCurrentWeekDay(){
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        date= nextDay(date);
        date= nextDay(date);
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    public static void main(String args[]){
        System.out.println(getCurrentWeekDay());

    }
}
