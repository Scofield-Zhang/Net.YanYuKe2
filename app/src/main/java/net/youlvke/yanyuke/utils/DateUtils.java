package net.youlvke.yanyuke.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/3 0003.
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {
    /**
     *
     * @param beginTime
     * @return
     */
    public static String format2;
    public static String formatDate(String beginTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");

            Date parse = format.parse(beginTime);
            format2 = format1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return format2;
    }
    public static String formatDatePoint(String beginTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd  HH:mm");

            Date parse = format.parse(beginTime);
            format2 = format1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return format2;
    }
    public static String formatDateAndHour(String beginTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd-HH:mm");

            Date parse = format.parse(beginTime);
            format2 = format1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return format2;
    }
    public static String[] formatSliteDate(String beginTime,String slite){

        String[] split = beginTime.split(slite);
        String formatDate = formatDate(split[0]);
        split[0] = formatDate;
        return split;
    }
    public static String HourAndMinute(String beginTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");

            Date parse = format.parse(beginTime);
            format2 = format1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return format2;
    }
    public static String getDate(String beginTime){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

            Date parse = format.parse(beginTime);
            format2 = format1.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return format2;
    }


}
