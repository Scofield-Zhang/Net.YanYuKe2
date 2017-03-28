package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class ShopDetailBean {


    /**
     * code : 1
     * data : {"Merchant":{"addTime":"2016-06-01 00:00:00","addressDetail":"杭州市桐庐县白云小区5区","avatar":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","duetime":"2017-06-01 00:00:00","loginState":1,"merchantname":"黄焖鸡米饭","merchanttel":"63651621","password":"","phone":"13057558530","userId":1,"usetime":"11：00-14：00 17：00-22：00"},"MerchantsPicture":[{"addTime":"2016-01-02 00:00:00","pictureId":1,"pictureShape":0,"pictureType":2002,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","pictureins":"hehe","userId":1},{"addTime":"2016-01-02 00:00:00","pictureId":2,"pictureShape":0,"pictureType":2002,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","pictureins":"haha","userId":1},{"addTime":"2016-01-02 00:00:00","pictureId":3,"pictureShape":0,"pictureType":2002,"pictureUrl":"(NULL)http://sqdb-goods.oss-cn-http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","pictureins":"huhu","userId":1}],"Par":0,"announcedDate":"2017-01-09 10:10:10","col":1,"crowdFundingPercent":97,"goodsId":2,"goodsinfo":{"goodsArea":"临安市","goodsCategory":3,"goodsCity":"杭州市","goodsDescription":"三星蓝调照相机 相遇，品味优雅 错过，没入平凡，不闪的才是健康的。","goodsFlag":0,"goodsId":2,"goodsName":"三星蓝调相机","goodsNewFlag":0,"goodsPrice":5000,"goodsProvince":"浙江省","goodsSequenceNum":49,"goodsState":0,"goodsUid":"64253789","userId":1},"releaseDate":"2017-01-09 11:21:11","salesCount":97,"sequenceId":2,"sequenceState":1,"stockCount":3,"totalCount":100,"userId":1,"winningNumber":"0"}
     * message : 获取成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * Merchant : {"addTime":"2016-06-01 00:00:00","addressDetail":"杭州市桐庐县白云小区5区","avatar":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","duetime":"2017-06-01 00:00:00","loginState":1,"merchantname":"黄焖鸡米饭","merchanttel":"63651621","password":"","phone":"13057558530","userId":1,"usetime":"11：00-14：00 17：00-22：00"}
         * MerchantsPicture : [{"addTime":"2016-01-02 00:00:00","pictureId":1,"pictureShape":0,"pictureType":2002,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","pictureins":"hehe","userId":1},{"addTime":"2016-01-02 00:00:00","pictureId":2,"pictureShape":0,"pictureType":2002,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","pictureins":"haha","userId":1},{"addTime":"2016-01-02 00:00:00","pictureId":3,"pictureShape":0,"pictureType":2002,"pictureUrl":"(NULL)http://sqdb-goods.oss-cn-http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","pictureins":"huhu","userId":1}]
         * Par : 0
         * announcedDate : 2017-01-09 10:10:10
         * col : 1
         * crowdFundingPercent : 97
         * goodsId : 2
         * goodsinfo : {"goodsArea":"临安市","goodsCategory":3,"goodsCity":"杭州市","goodsDescription":"三星蓝调照相机 相遇，品味优雅 错过，没入平凡，不闪的才是健康的。","goodsFlag":0,"goodsId":2,"goodsName":"三星蓝调相机","goodsNewFlag":0,"goodsPrice":5000,"goodsProvince":"浙江省","goodsSequenceNum":49,"goodsState":0,"goodsUid":"64253789","userId":1}
         * releaseDate : 2017-01-09 11:21:11
         * salesCount : 97
         * sequenceId : 2
         * sequenceState : 1
         * stockCount : 3
         * totalCount : 100
         * userId : 1
         * winningNumber : 0
         */

        private MerchantBean Merchant;
        private int Par;
        private String announcedDate;
        private int col;
        private int crowdFundingPercent;
        private long goodsId;
        private GoodsinfoBean goodsinfo;
        private String releaseDate;
        private int salesCount;
        private long sequenceId;
        private int sequenceState;
        private int stockCount;
        private int totalCount;
        private long userId;
        private String winningNumber;
        private List<MerchantsPictureBean> MerchantsPicture;

        public MerchantBean getMerchant() {
            return Merchant;
        }

        public void setMerchant(MerchantBean Merchant) {
            this.Merchant = Merchant;
        }

        public int getPar() {
            return Par;
        }

        public void setPar(int Par) {
            this.Par = Par;
        }

        public String getAnnouncedDate() {
            return announcedDate;
        }

        public void setAnnouncedDate(String announcedDate) {
            this.announcedDate = announcedDate;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
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

        public GoodsinfoBean getGoodsinfo() {
            return goodsinfo;
        }

        public void setGoodsinfo(GoodsinfoBean goodsinfo) {
            this.goodsinfo = goodsinfo;
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

        public String getWinningNumber() {
            return winningNumber;
        }

        public void setWinningNumber(String winningNumber) {
            this.winningNumber = winningNumber;
        }

        public List<MerchantsPictureBean> getMerchantsPicture() {
            return MerchantsPicture;
        }

        public void setMerchantsPicture(List<MerchantsPictureBean> MerchantsPicture) {
            this.MerchantsPicture = MerchantsPicture;
        }

        public static class MerchantBean {
            /**
             * addTime : 2016-06-01 00:00:00
             * addressDetail : 杭州市桐庐县白云小区5区
             * avatar : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             * duetime : 2017-06-01 00:00:00
             * loginState : 1
             * merchantname : 黄焖鸡米饭
             * merchanttel : 63651621
             * password :
             * phone : 13057558530
             * userId : 1
             * usetime : 11：00-14：00 17：00-22：00
             */

            private String addTime;
            private String addressDetail;
            private String avatar;
            private String duetime;
            private int loginState;
            private String merchantname;
            private String merchanttel;
            private String password;
            private String phone;
            private long userId;
            private String usetime;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public String getAddressDetail() {
                return addressDetail;
            }

            public void setAddressDetail(String addressDetail) {
                this.addressDetail = addressDetail;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDuetime() {
                return duetime;
            }

            public void setDuetime(String duetime) {
                this.duetime = duetime;
            }

            public int getLoginState() {
                return loginState;
            }

            public void setLoginState(int loginState) {
                this.loginState = loginState;
            }

            public String getMerchantname() {
                return merchantname;
            }

            public void setMerchantname(String merchantname) {
                this.merchantname = merchantname;
            }

            public String getMerchanttel() {
                return merchanttel;
            }

            public void setMerchanttel(String merchanttel) {
                this.merchanttel = merchanttel;
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

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUsetime() {
                return usetime;
            }

            public void setUsetime(String usetime) {
                this.usetime = usetime;
            }
        }

        public static class GoodsinfoBean {
            /**
             * goodsArea : 临安市
             * goodsCategory : 3
             * goodsCity : 杭州市
             * goodsDescription : 三星蓝调照相机 相遇，品味优雅 错过，没入平凡，不闪的才是健康的。
             * goodsFlag : 0
             * goodsId : 2
             * goodsName : 三星蓝调相机
             * goodsNewFlag : 0
             * goodsPrice : 5000
             * goodsProvince : 浙江省
             * goodsSequenceNum : 49
             * goodsState : 0
             * goodsUid : 64253789
             * userId : 1
             */

            private String goodsArea;
            private int goodsCategory;
            private String goodsCity;
            private String goodsDescription;
            private int goodsFlag;
            private long goodsId;
            private String goodsName;
            private int goodsNewFlag;
            private int goodsPrice;
            private String goodsProvince;
            private int goodsSequenceNum;
            private int goodsState;
            private String goodsUid;
            private long userId;

            public String getGoodsArea() {
                return goodsArea;
            }

            public void setGoodsArea(String goodsArea) {
                this.goodsArea = goodsArea;
            }

            public int getGoodsCategory() {
                return goodsCategory;
            }

            public void setGoodsCategory(int goodsCategory) {
                this.goodsCategory = goodsCategory;
            }

            public String getGoodsCity() {
                return goodsCity;
            }

            public void setGoodsCity(String goodsCity) {
                this.goodsCity = goodsCity;
            }

            public String getGoodsDescription() {
                return goodsDescription;
            }

            public void setGoodsDescription(String goodsDescription) {
                this.goodsDescription = goodsDescription;
            }

            public int getGoodsFlag() {
                return goodsFlag;
            }

            public void setGoodsFlag(int goodsFlag) {
                this.goodsFlag = goodsFlag;
            }

            public long getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(long goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public int getGoodsNewFlag() {
                return goodsNewFlag;
            }

            public void setGoodsNewFlag(int goodsNewFlag) {
                this.goodsNewFlag = goodsNewFlag;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public String getGoodsProvince() {
                return goodsProvince;
            }

            public void setGoodsProvince(String goodsProvince) {
                this.goodsProvince = goodsProvince;
            }

            public int getGoodsSequenceNum() {
                return goodsSequenceNum;
            }

            public void setGoodsSequenceNum(int goodsSequenceNum) {
                this.goodsSequenceNum = goodsSequenceNum;
            }

            public int getGoodsState() {
                return goodsState;
            }

            public void setGoodsState(int goodsState) {
                this.goodsState = goodsState;
            }

            public String getGoodsUid() {
                return goodsUid;
            }

            public void setGoodsUid(String goodsUid) {
                this.goodsUid = goodsUid;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }
        }

        public static class MerchantsPictureBean {
            /**
             * addTime : 2016-01-02 00:00:00
             * pictureId : 1
             * pictureShape : 0
             * pictureType : 2002
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg
             * pictureins : hehe
             * userId : 1
             */

            private String addTime;
            private long pictureId;
            private int pictureShape;
            private int pictureType;
            private String pictureUrl;
            private String pictureins;
            private long userId;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public long getPictureId() {
                return pictureId;
            }

            public void setPictureId(long pictureId) {
                this.pictureId = pictureId;
            }

            public int getPictureShape() {
                return pictureShape;
            }

            public void setPictureShape(int pictureShape) {
                this.pictureShape = pictureShape;
            }

            public int getPictureType() {
                return pictureType;
            }

            public void setPictureType(int pictureType) {
                this.pictureType = pictureType;
            }

            public String getPictureUrl() {
                return pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getPictureins() {
                return pictureins;
            }

            public void setPictureins(String pictureins) {
                this.pictureins = pictureins;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }
        }
    }
}
