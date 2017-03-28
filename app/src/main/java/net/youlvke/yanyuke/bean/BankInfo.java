package net.youlvke.yanyuke.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/17 0017.
 */

public class BankInfo implements Serializable {
    public String name;
    public String cardNum;
    public int bankIconInfo;

    public BankInfo(String name, String cardNum, int bankIconInfo) {
        this.name = name;
        this.cardNum = cardNum;
        this.bankIconInfo = bankIconInfo;
    }
}
