package net.youlvke.yanyuke.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/13 0013.
 */

public class WinDetailBean implements Serializable {
      public String  winTime;
      public String  winId;
      public String  winName;

    public WinDetailBean(String winTime, String winId, String winName) {
        this.winTime = winTime;
        this.winId = winId;
        this.winName = winName;
    }
}
