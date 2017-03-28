package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8 0008.
 */

public class Income {


    /**
     * code : 1
     * data : [{"delFlag":0,"rechargeAmount":30,"rechargeDate":"2017-01-17 18:11:23","rechargeId":99991484647882878,"rechargeState":0,"rechargeWay":0,"userId":1},{"delFlag":0,"rechargeAmount":300,"rechargeDate":"2017-01-17 18:18:32","rechargeId":99991484648311799,"rechargeState":0,"rechargeWay":0,"userId":1},{"delFlag":0,"rechargeAmount":10,"rechargeDate":"2017-01-17 18:25:59","rechargeId":99991484648758547,"rechargeState":0,"rechargeWay":0,"userId":1}]
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
         * delFlag : 0
         * rechargeAmount : 30.0
         * rechargeDate : 2017-01-17 18:11:23
         * rechargeId : 99991484647882878
         * rechargeState : 0
         * rechargeWay : 0
         * userId : 1
         */

        private int delFlag;
        private double rechargeAmount;
        private String rechargeDate;
        private long rechargeId;
        private int rechargeState;
        private int rechargeWay;
        private int userId;

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public double getRechargeAmount() {
            return rechargeAmount;
        }

        public void setRechargeAmount(double rechargeAmount) {
            this.rechargeAmount = rechargeAmount;
        }

        public String getRechargeDate() {
            return rechargeDate;
        }

        public void setRechargeDate(String rechargeDate) {
            this.rechargeDate = rechargeDate;
        }

        public long getRechargeId() {
            return rechargeId;
        }

        public void setRechargeId(long rechargeId) {
            this.rechargeId = rechargeId;
        }

        public int getRechargeState() {
            return rechargeState;
        }

        public void setRechargeState(int rechargeState) {
            this.rechargeState = rechargeState;
        }

        public int getRechargeWay() {
            return rechargeWay;
        }

        public void setRechargeWay(int rechargeWay) {
            this.rechargeWay = rechargeWay;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
