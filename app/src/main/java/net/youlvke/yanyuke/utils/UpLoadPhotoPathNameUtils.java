package net.youlvke.yanyuke.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 */
@SuppressLint("SimpleDateFormat")
public class UpLoadPhotoPathNameUtils {


    public static String getHeadPath() {
        long time = System.currentTimeMillis();
        String t = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(time));

        return "avatar-img/" + "20160128/" + t + "_" + getRandomNum() + ".jpg";
    }
    public static String getGoodsPath() {
        long time = System.currentTimeMillis();
        String t = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(time));

        return "goods-img/" + "20160128/" + t + "_" + getRandomNum() + ".jpg";
    }

    public static String getRandomNum() {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }
}
