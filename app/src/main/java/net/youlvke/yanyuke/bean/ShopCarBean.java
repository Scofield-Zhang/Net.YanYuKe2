package net.youlvke.yanyuke.bean;

import java.io.Serializable;

/**
 * Created by 张涛 on 2016/12/6.
 */

public class ShopCarBean implements Serializable {

    public String ShopTitle;
    public String count;
    public String price;
    public Boolean isChoise;

    public ShopCarBean(String shopTitle, String count, String price, Boolean isChoise) {
        ShopTitle = shopTitle;
        this.count = count;
        this.price = price;
        this.isChoise = isChoise;
    }

    public String getShopTitle() {
        return ShopTitle;
    }

    public void setShopTitle(String shopTitle) {
        ShopTitle = shopTitle;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Boolean getChoise() {
        return isChoise;
    }

    public void setChoise(Boolean choise) {
        isChoise = choise;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
