package net.youlvke.yanyuke.utils;

import android.os.Environment;

/**
 * Created by 袁慎彬 on 2016/7/11.
 */
public class FileUtils {

    /**
     * 判断是否有SD卡
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 返回手机内存路径优先内存卡
     *
     * @return
     */
    public static String getCachePath() {
        if (isExistSDCard()) {
            return Environment.getExternalStorageDirectory() + "/jacky/";
        } else {
            return "/data" + "/jacky/";
        }
    }

    public static String modifyclauses(long SequenceId) {
        String newSequenceId = String.valueOf(SequenceId);
        if (newSequenceId.length() == 1) {
            newSequenceId = "0000000" + newSequenceId;
        } else if (newSequenceId.length() == 2) {
            newSequenceId = "000000" + newSequenceId;
        } else if (newSequenceId.length() == 3) {
            newSequenceId = "00000" + newSequenceId;
        } else if (newSequenceId.length() == 4) {
            newSequenceId = "0000" + newSequenceId;
        } else if (newSequenceId.length() == 5) {
            newSequenceId = "000" + newSequenceId;
        } else if (newSequenceId.length() == 6) {
            newSequenceId = "00" + newSequenceId;
        } else if (newSequenceId.length() == 7) {
            newSequenceId = "0" + newSequenceId;
        } else {
            return newSequenceId;
        }
        System.out.println(newSequenceId);
        return newSequenceId;
    }
}
