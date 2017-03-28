package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by hasee on 2016/11/3.
 */
//
public class GroupBean {
    /**
     * code : 1
     * data : [{"addTime":"2016-06-05 00:00:00","commissionId":5,"cotime":[{"addTime":"2016-06-05 00:00:00","commissionId":5,"commissiontype":1,"detailedtime":"2016-06-05 10:00:00","logictodelete":0,"money":3,"nickname":"宋志颖10","timeId":11},{"addTime":"2016-06-05 00:00:00","commissionId":5,"commissiontype":1,"detailedtime":"2016-06-05 00:00:00","logictodelete":0,"money":60,"nickname":"宋志颖9","timeId":10}],"total":63,"userId":4,"walletId":5},{"addTime":"2016-06-04 00:00:00","commissionId":4,"cotime":[{"addTime":"2016-06-04 00:00:00","commissionId":4,"commissiontype":1,"detailedtime":"2016-06-04 10:00:00","logictodelete":0,"money":10,"nickname":"宋志颖8","timeId":8},{"addTime":"2016-06-04 00:00:00","commissionId":4,"commissiontype":1,"detailedtime":"2016-06-04 00:00:00","logictodelete":0,"money":5,"nickname":"宋志颖7","timeId":7}],"total":13,"userId":4,"walletId":5},{"addTime":"2016-06-03 00:00:00","commissionId":3,"cotime":[{"addTime":"2016-06-03 00:00:00","commissionId":3,"commissiontype":1,"detailedtime":"2016-06-03 13:00:00","logictodelete":0,"money":25,"nickname":"宋志颖5","timeId":5},{"addTime":"2016-06-03 00:00:00","commissionId":3,"commissiontype":1,"detailedtime":"2016-06-03 10:00:00","logictodelete":0,"money":5,"nickname":"宋志颖6","timeId":6}],"total":20,"userId":4,"walletId":5},{"addTime":"2016-06-02 00:00:00","commissionId":2,"cotime":[{"addTime":"2016-06-02 00:00:00","commissionId":2,"commissiontype":1,"detailedtime":"2016-06-02 15:00:00","logictodelete":0,"money":5,"nickname":"宋志颖2","timeId":2},{"addTime":"2016-06-02 00:00:00","commissionId":2,"commissiontype":1,"detailedtime":"2016-06-02 12:00:00","logictodelete":0,"money":5,"nickname":"宋志颖4","timeId":4},{"addTime":"2016-06-02 00:00:00","commissionId":2,"commissiontype":1,"detailedtime":"2016-06-02 10:00:00","logictodelete":0,"money":5,"nickname":"宋志颖3","timeId":3}],"total":15,"userId":4,"walletId":5}]
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
         * addTime : 2016-06-05 00:00:00
         * commissionId : 5
         * cotime : [{"addTime":"2016-06-05 00:00:00","commissionId":5,"commissiontype":1,"detailedtime":"2016-06-05 10:00:00","logictodelete":0,"money":3,"nickname":"宋志颖10","timeId":11},{"addTime":"2016-06-05 00:00:00","commissionId":5,"commissiontype":1,"detailedtime":"2016-06-05 00:00:00","logictodelete":0,"money":60,"nickname":"宋志颖9","timeId":10}]
         * total : 63.0
         * userId : 4
         * walletId : 5
         */

        private String addTime;
        private int commissionId;
        private double total;
        private long userId;
        private long walletId;
        private boolean itemStatus;
        private List<CotimeBean> cotime;

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

        public List<CotimeBean> getCotime() {
            return cotime;
        }

        public void setCotime(List<CotimeBean> cotime) {
            this.cotime = cotime;
        }

        public boolean isItemStatus() {
            return itemStatus;
        }

        public void setItemStatus(boolean itemStatus) {
            this.itemStatus = itemStatus;
        }

        public static class CotimeBean {
            /**
             * addTime : 2016-06-05 00:00:00
             * commissionId : 5
             * commissiontype : 1
             * detailedtime : 2016-06-05 10:00:00
             * logictodelete : 0
             * money : 3.0
             * nickname : 宋志颖10
             * timeId : 11
             */

            private String addTime;
            private long commissionId;
            private int commissiontype;
            private String detailedtime;
            private int logictodelete;
            private double money;
            private String nickname;
            private long timeId;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public long getCommissionId() {
                return commissionId;
            }

            public void setCommissionId(long commissionId) {
                this.commissionId = commissionId;
            }

            public int getCommissiontype() {
                return commissiontype;
            }

            public void setCommissiontype(int commissiontype) {
                this.commissiontype = commissiontype;
            }

            public String getDetailedtime() {
                return detailedtime;
            }

            public void setDetailedtime(String detailedtime) {
                this.detailedtime = detailedtime;
            }

            public int getLogictodelete() {
                return logictodelete;
            }

            public void setLogictodelete(int logictodelete) {
                this.logictodelete = logictodelete;
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

            public long getTimeId() {
                return timeId;
            }

            public void setTimeId(long timeId) {
                this.timeId = timeId;
            }
        }
    }
}
