package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12 0012.
 */

public class BuyRecordBean  {

    /**
     * code : 1
     * data : [{"GoodsInfo":{"goodsArea":"桐庐县","goodsCategory":1,"goodsCity":"杭州市","goodsDescription":"楼兰红酒传承千楼兰古文化","goodsFlag":0,"goodsId":1,"goodsName":"楼兰红酒750ML","goodsNewFlag":0,"goodsPrice":50,"goodsProvince":"浙江省","goodsSequenceNum":50,"goodsState":0,"goodsUid":"63651621"},"count":1,"newsequenceId":"00000001","purchaseCount":1,"purchaseDate":"2016-01-23 16:00:00","purchaseId":1,"purchaseSate":1,"purchaseType":1,"sequenceId":1,"userId":1,"userIp":"192.168.0.102","userLat":23,"userLng":35,"userLocation":"桐庐","vouchersId":0},{"GoodsInfo":{"goodsArea":"临安市","goodsCategory":3,"goodsCity":"杭州市","goodsDescription":"三星蓝调照相机 相遇，品味优雅 错过，没入平凡，不闪的才是健康的。","goodsFlag":0,"goodsId":2,"goodsName":"三星蓝调相机","goodsNewFlag":0,"goodsPrice":5000,"goodsProvince":"浙江省","goodsSequenceNum":49,"goodsState":0,"goodsUid":"64253789"},"count":1,"newsequenceId":"00000002","purchaseCount":3,"purchaseDate":"2017-01-23 16:00:00","purchaseId":2,"purchaseSate":1,"purchaseType":1,"sequenceId":2,"userId":1,"userIp":"192.168.0.102","userLat":23,"userLng":36,"userLocation":"临安","vouchersId":0},{"GoodsInfo":{"goodsArea":"千岛湖","goodsCategory":4,"goodsCity":"杭州市","goodsDescription":"欢乐无限，bingo有约。","goodsFlag":0,"goodsId":3,"goodsName":"星乐迪6小时任意畅享","goodsNewFlag":0,"goodsPrice":200,"goodsProvince":"浙江省","goodsSequenceNum":999,"goodsState":0,"goodsUid":"63636633"},"count":1,"newsequenceId":"00000003","purchaseCount":5,"purchaseDate":"2017-01-20 15:00:00","purchaseId":3,"purchaseSate":1,"purchaseType":1,"sequenceId":3,"userId":1,"userIp":"192.168.0.103","userLat":25,"userLng":40,"userLocation":"温州","vouchersId":0},{"GoodsInfo":{"goodsArea":"西湖区","goodsCategory":2,"goodsCity":"杭州市","goodsDescription":"即使你把它拆得七零八落，它依然是位美人（国外）。驾乘乐趣，创新极限。","goodsFlag":0,"goodsId":4,"goodsName":"宝马750","goodsNewFlag":0,"goodsPrice":1000000,"goodsProvince":"浙江省","goodsSequenceNum":2,"goodsState":0,"goodsUid":"69874521"},"count":1,"newsequenceId":"00000004","purchaseCount":6,"purchaseDate":"2017-06-06 12:00:00","purchaseId":4,"purchaseSate":1,"purchaseType":1,"sequenceId":4,"userId":1,"userIp":"192.168.0.123","userLat":36,"userLng":25,"userLocation":"龙岗","vouchersId":0}]
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
         * GoodsInfo : {"goodsArea":"桐庐县","goodsCategory":1,"goodsCity":"杭州市","goodsDescription":"楼兰红酒传承千楼兰古文化","goodsFlag":0,"goodsId":1,"goodsName":"楼兰红酒750ML","goodsNewFlag":0,"goodsPrice":50,"goodsProvince":"浙江省","goodsSequenceNum":50,"goodsState":0,"goodsUid":"63651621"}
         * count : 1
         * newsequenceId : 00000001
         * purchaseCount : 1
         * purchaseDate : 2016-01-23 16:00:00
         * purchaseId : 1
         * purchaseSate : 1
         * purchaseType : 1
         * sequenceId : 1
         * userId : 1
         * userIp : 192.168.0.102
         * userLat : 23.0
         * userLng : 35.0
         * userLocation : 桐庐
         * vouchersId : 0
         */

        private GoodsInfoBean GoodsInfo;
        private int count;
        private String newsequenceId;
        private int purchaseCount;
        private String purchaseDate;
        private int purchaseId;
        private int purchaseSate;
        private int purchaseType;
        private int sequenceId;
        private int userId;
        private String userIp;
        private double userLat;
        private double userLng;
        private String userLocation;
        private int vouchersId;

        public GoodsInfoBean getGoodsInfo() {
            return GoodsInfo;
        }

        public void setGoodsInfo(GoodsInfoBean GoodsInfo) {
            this.GoodsInfo = GoodsInfo;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNewsequenceId() {
            return newsequenceId;
        }

        public void setNewsequenceId(String newsequenceId) {
            this.newsequenceId = newsequenceId;
        }

        public int getPurchaseCount() {
            return purchaseCount;
        }

        public void setPurchaseCount(int purchaseCount) {
            this.purchaseCount = purchaseCount;
        }

        public String getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(String purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public int getPurchaseId() {
            return purchaseId;
        }

        public void setPurchaseId(int purchaseId) {
            this.purchaseId = purchaseId;
        }

        public int getPurchaseSate() {
            return purchaseSate;
        }

        public void setPurchaseSate(int purchaseSate) {
            this.purchaseSate = purchaseSate;
        }

        public int getPurchaseType() {
            return purchaseType;
        }

        public void setPurchaseType(int purchaseType) {
            this.purchaseType = purchaseType;
        }

        public int getSequenceId() {
            return sequenceId;
        }

        public void setSequenceId(int sequenceId) {
            this.sequenceId = sequenceId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserIp() {
            return userIp;
        }

        public void setUserIp(String userIp) {
            this.userIp = userIp;
        }

        public double getUserLat() {
            return userLat;
        }

        public void setUserLat(double userLat) {
            this.userLat = userLat;
        }

        public double getUserLng() {
            return userLng;
        }

        public void setUserLng(double userLng) {
            this.userLng = userLng;
        }

        public String getUserLocation() {
            return userLocation;
        }

        public void setUserLocation(String userLocation) {
            this.userLocation = userLocation;
        }

        public int getVouchersId() {
            return vouchersId;
        }

        public void setVouchersId(int vouchersId) {
            this.vouchersId = vouchersId;
        }

        public static class GoodsInfoBean {
            /**
             * goodsArea : 桐庐县
             * goodsCategory : 1
             * goodsCity : 杭州市
             * goodsDescription : 楼兰红酒传承千楼兰古文化
             * goodsFlag : 0
             * goodsId : 1
             * goodsName : 楼兰红酒750ML
             * goodsNewFlag : 0
             * goodsPrice : 50
             * goodsProvince : 浙江省
             * goodsSequenceNum : 50
             * goodsState : 0
             * goodsUid : 63651621
             */

            private String goodsArea;
            private int goodsCategory;
            private String goodsCity;
            private String goodsDescription;
            private int goodsFlag;
            private int goodsId;
            private String goodsName;
            private int goodsNewFlag;
            private int goodsPrice;
            private String goodsProvince;
            private int goodsSequenceNum;
            private int goodsState;
            private String goodsUid;

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

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
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
        }
    }
}
