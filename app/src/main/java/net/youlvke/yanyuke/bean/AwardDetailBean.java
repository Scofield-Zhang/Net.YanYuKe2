package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9 0009.
 *
 * 奖励详情
 */

public class AwardDetailBean  {

    /**
     * code : 1
     * data : [{"ApplyAmount":1,"addTime":"2016-06-02 00:00:00","commissionId":2,"total":15,"userId":4,"walletId":5},{"ApplyAmount":5,"addTime":"2016-06-03 00:00:00","commissionId":3,"total":20,"userId":4,"walletId":5},{"ApplyAmount":10,"addTime":"2016-06-04 00:00:00","commissionId":4,"total":13,"userId":4,"walletId":5},{"ApplyAmount":10,"addTime":"2016-06-05 00:00:00","commissionId":5,"total":63,"userId":4,"walletId":5}]
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
         * ApplyAmount : 1.0
         * addTime : 2016-06-02 00:00:00
         * commissionId : 2
         * total : 15.0
         * userId : 4
         * walletId : 5
         */

        private double ApplyAmount;
        private String addTime;
        private int commissionId;
        private double total;
        private long userId;
        private long walletId;

        public double getApplyAmount() {
            return ApplyAmount;
        }

        public void setApplyAmount(double ApplyAmount) {
            this.ApplyAmount = ApplyAmount;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public int getCommissionId() {
            return commissionId;
        }

        public void setCommissionId(int commissionId) {
            this.commissionId = commissionId;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getWalletId() {
            return walletId;
        }

        public void setWalletId(long walletId) {
            this.walletId = walletId;
        }
    }
}
