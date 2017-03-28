package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23 0023.
 */

public class VoucherBean {

    /**
     * code : 1
     * data : [{"addTime":"2017-01-20 00:00:00","delFlag":0,"expiryDate":"2017-01-22 00:00:00","money":1,"userId":1,"validity":0,"vouchersId":1,"voucherstype":0},{"addTime":"2017-01-21 00:00:00","delFlag":0,"expiryDate":"2017-01-22 00:00:00","money":1,"userId":1,"validity":0,"vouchersId":2,"voucherstype":0},{"addTime":"2017-01-19 00:00:00","delFlag":0,"expiryDate":"2017-01-22 00:00:00","money":1,"userId":3,"validity":0,"vouchersId":3,"voucherstype":0},{"addTime":"2017-01-21 00:00:00","delFlag":0,"expiryDate":"2017-01-23 00:00:00","money":1,"userId":3,"validity":0,"vouchersId":4,"voucherstype":0},{"addTime":"2017-01-20 00:00:00","delFlag":0,"expiryDate":"2017-01-25 00:00:00","money":1,"userId":3,"validity":0,"vouchersId":5,"voucherstype":0},{"addTime":"2017-01-20 00:00:00","delFlag":0,"expiryDate":"2017-02-21 00:00:00","money":1,"userId":4,"validity":0,"vouchersId":6,"voucherstype":0}]
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

    public static class DataBean {
        /**
         * addTime : 2017-01-20 00:00:00
         * delFlag : 0
         * expiryDate : 2017-01-22 00:00:00
         * money : 1
         * userId : 1
         * validity : 0
         * vouchersId : 1
         * voucherstype : 0
         */

        private String addTime;
        private int delFlag;
        private String expiryDate;
        private int money;
        private long userId;
        private int validity;
        private long vouchersId;
        private int voucherstype;

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getValidity() {
            return validity;
        }

        public void setValidity(int validity) {
            this.validity = validity;
        }

        public long getVouchersId() {
            return vouchersId;
        }

        public void setVouchersId(long vouchersId) {
            this.vouchersId = vouchersId;
        }

        public int getVoucherstype() {
            return voucherstype;
        }

        public void setVoucherstype(int voucherstype) {
            this.voucherstype = voucherstype;
        }
    }
}
