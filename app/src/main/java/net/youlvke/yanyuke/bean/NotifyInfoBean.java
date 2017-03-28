package net.youlvke.yanyuke.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class NotifyInfoBean implements Serializable {
    public int ITEM_TYPE;
    public String notifyTitle;
    public String notityDetail;

    public NotifyInfoBean(int ITEM_TYPE, String notifyTitle, String notityDetail) {
        this.ITEM_TYPE = ITEM_TYPE;
        this.notifyTitle = notifyTitle;
        this.notityDetail = notityDetail;
    }
}
