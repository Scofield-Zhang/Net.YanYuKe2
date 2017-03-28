package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/12 0012.
 */

public class OldAnnounceBean {

    /**
     * code : 1
     * data : [{"announcedDate":"2017-01-10 10:00:00","count":3,"crowdFundingPercent":98,"goodsId":1,"releaseDate":"2017-01-09 10:00:00","salesCount":98,"sequenceId":1,"sequenceState":3,"stockCount":2,"totalCount":100,"userId":1,"userinfo":{"addTime":"2017-02-09 18:35:50","avatar":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","gender":1,"loginState":1,"nickname":"13606143405","password":"","phone":"13606143405","referralCode":"407909","userId":1,"userStatu":1,"userlevel":0},"winningNumber":"0"}]
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
         * announcedDate : 2017-01-10 10:00:00
         * count : 3
         * crowdFundingPercent : 98
         * goodsId : 1
         * releaseDate : 2017-01-09 10:00:00
         * salesCount : 98
         * sequenceId : 1
         * sequenceState : 3
         * stockCount : 2
         * totalCount : 100
         * userId : 1
         * userinfo : {"addTime":"2017-02-09 18:35:50","avatar":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","gender":1,"loginState":1,"nickname":"13606143405","password":"","phone":"13606143405","referralCode":"407909","userId":1,"userStatu":1,"userlevel":0}
         * winningNumber : 0
         */

        private String announcedDate;
        private int count;
        private int crowdFundingPercent;
        private long goodsId;
        private String releaseDate;
        private int salesCount;
        private long sequenceId;
        private int sequenceState;
        private int stockCount;
        private int totalCount;
        private long userId;
        private UserinfoBean userinfo;
        private String winningNumber;

        public String getAnnouncedDate() {
            return announcedDate;
        }

        public void setAnnouncedDate(String announcedDate) {
            this.announcedDate = announcedDate;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCrowdFundingPercent() {
            return crowdFundingPercent;
        }

        public void setCrowdFundingPercent(int crowdFundingPercent) {
            this.crowdFundingPercent = crowdFundingPercent;
        }

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public int getSalesCount() {
            return salesCount;
        }

        public void setSalesCount(int salesCount) {
            this.salesCount = salesCount;
        }

        public long getSequenceId() {
            return sequenceId;
        }

        public void setSequenceId(long sequenceId) {
            this.sequenceId = sequenceId;
        }

        public int getSequenceState() {
            return sequenceState;
        }

        public void setSequenceState(int sequenceState) {
            this.sequenceState = sequenceState;
        }

        public int getStockCount() {
            return stockCount;
        }

        public void setStockCount(int stockCount) {
            this.stockCount = stockCount;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public String getWinningNumber() {
            return winningNumber;
        }

        public void setWinningNumber(String winningNumber) {
            this.winningNumber = winningNumber;
        }

        public static class UserinfoBean {
            /**
             * addTime : 2017-02-09 18:35:50
             * avatar : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             * gender : 1
             * loginState : 1
             * nickname : 13606143405
             * password :
             * phone : 13606143405
             * referralCode : 407909
             * userId : 1
             * userStatu : 1
             * userlevel : 0
             */

            private String addTime;
            private String avatar;
            private int gender;
            private int loginState;
            private String nickname;
            private String password;
            private String phone;
            private String referralCode;
            private long userId;
            private int userStatu;
            private int userlevel;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getLoginState() {
                return loginState;
            }

            public void setLoginState(int loginState) {
                this.loginState = loginState;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getReferralCode() {
                return referralCode;
            }

            public void setReferralCode(String referralCode) {
                this.referralCode = referralCode;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public int getUserStatu() {
                return userStatu;
            }

            public void setUserStatu(int userStatu) {
                this.userStatu = userStatu;
            }

            public int getUserlevel() {
                return userlevel;
            }

            public void setUserlevel(int userlevel) {
                this.userlevel = userlevel;
            }
        }
    }
}
