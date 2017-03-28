package net.youlvke.yanyuke.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/14 0014.
 */

public class EvaluateInfoBean implements Serializable{
   public String shopEvaluteDetail;
   public String shopTitle;

    public EvaluateInfoBean(String shopEvaluteDetail, String shopTitle) {
        this.shopEvaluteDetail = shopEvaluteDetail;

        this.shopTitle = shopTitle;
    }
}
