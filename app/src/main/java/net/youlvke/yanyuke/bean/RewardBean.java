package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/17 0017.
 */

public class RewardBean  {

    /**
     * code : 1
     * data : [{"commissiontype":3,"addtime":"2016-06-02 00:00:00","money":1},{"commissiontype":1,"addtime":"2016-06-02 10:00:00","money":5,"nickname":"宋志颖3"},{"commissiontype":1,"addtime":"2016-06-02 12:00:00","money":5,"nickname":"宋志颖4"},{"commissiontype":1,"addtime":"2016-06-02 15:00:00","money":5,"nickname":"宋志颖2"}]
     * message : 获取成功
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  {
        /**
         * commissiontype : 3
         * addtime : 2016-06-02 00:00:00
         * money : 1.0
         * nickname : 宋志颖3
         */

        private int commissiontype;
        private String addtime;
        private double money;
        private String nickname;

        public int getCommissiontype() {
            return commissiontype;
        }

        public void setCommissiontype(int commissiontype) {
            this.commissiontype = commissiontype;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
