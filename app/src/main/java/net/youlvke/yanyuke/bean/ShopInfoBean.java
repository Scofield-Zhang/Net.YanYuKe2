package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

public class ShopInfoBean  {
    /**
     * code : 1
     * data : [{"GoodsInfo":{"goodsArea":"桐庐县","goodsCategory":1,"goodsCity":"杭州市","goodsDescription":"楼兰红酒传承千楼兰古文化","goodsFlag":0,"goodsId":1,"goodsName":"楼兰红酒750ML","goodsNewFlag":0,"goodsPrice":50,"goodsProvince":"浙江省","goodsSequenceNum":50,"goodsState":0,"goodsUid":"63651621"},"GoodsPictureInfo":[{"addTime":"2016-01-01 00:00:00","goodsId":1,"pictureId":1,"pictureShape":0,"pictureType":1001,"pictureUrl":"http://192.168.0.105:8080/upload/goods/20161213/214812542_41"}],"colId":1,"collectionState":0,"goodsId":1,"updateDate":"2017-01-09 10:00:00","userId":1},{"GoodsInfo":{"goodsArea":"临安市","goodsCategory":3,"goodsCity":"杭州市","goodsDescription":"三星蓝调照相机 相遇，品味优雅 错过，没入平凡，不闪的才是健康的。","goodsFlag":0,"goodsId":2,"goodsName":"三星蓝调相机","goodsNewFlag":0,"goodsPrice":5000,"goodsProvince":"浙江省","goodsSequenceNum":49,"goodsState":0,"goodsUid":"64253789"},"GoodsPictureInfo":[{"addTime":"2016-01-02 00:00:00","goodsId":2,"pictureId":2,"pictureShape":0,"pictureType":1001,"pictureUrl":"http://192.168.0.105:8080/upload/goods/20161213/542125221_11"}],"colId":2,"collectionState":0,"goodsId":2,"updateDate":"2017-01-07 10:00:00","userId":1},{"GoodsInfo":{"goodsArea":"千岛湖","goodsCategory":4,"goodsCity":"杭州市","goodsDescription":"欢乐无限，bingo有约。","goodsFlag":0,"goodsId":3,"goodsName":"星乐迪6小时任意畅享","goodsNewFlag":0,"goodsPrice":200,"goodsProvince":"浙江省","goodsSequenceNum":999,"goodsState":0,"goodsUid":"63636633"},"GoodsPictureInfo":[{"addTime":"2016-01-03 00:00:00","goodsId":3,"pictureId":3,"pictureShape":0,"pictureType":1001,"pictureUrl":"http://192.168.0.105:8080/upload/goods/20161213/656456555_22"}],"colId":3,"collectionState":0,"goodsId":3,"updateDate":"2017-01-08 10:00:00","userId":1}]
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
         * GoodsPictureInfo : [{"addTime":"2016-01-01 00:00:00","goodsId":1,"pictureId":1,"pictureShape":0,"pictureType":1001,"pictureUrl":"http://192.168.0.105:8080/upload/goods/20161213/214812542_41"}]
         * colId : 1
         * collectionState : 0
         * goodsId : 1
         * updateDate : 2017-01-09 10:00:00
         * userId : 1
         */

        private GoodsInfoBean GoodsInfo;
        private int colId;
        private int collectionState;
        private int goodsId;
        private String updateDate;
        private int userId;
        private List<GoodsPictureInfoBean> GoodsPictureInfo;

        public GoodsInfoBean getGoodsInfo() {
            return GoodsInfo;
        }

        public void setGoodsInfo(GoodsInfoBean GoodsInfo) {
            this.GoodsInfo = GoodsInfo;
        }

        public int getColId() {
            return colId;
        }

        public void setColId(int colId) {
            this.colId = colId;
        }

        public int getCollectionState() {
            return collectionState;
        }

        public void setCollectionState(int collectionState) {
            this.collectionState = collectionState;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<GoodsPictureInfoBean> getGoodsPictureInfo() {
            return GoodsPictureInfo;
        }

        public void setGoodsPictureInfo(List<GoodsPictureInfoBean> GoodsPictureInfo) {
            this.GoodsPictureInfo = GoodsPictureInfo;
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

        public static class GoodsPictureInfoBean {
            /**
             * addTime : 2016-01-01 00:00:00
             * goodsId : 1
             * pictureId : 1
             * pictureShape : 0
             * pictureType : 1001
             * pictureUrl : http://192.168.0.105:8080/upload/goods/20161213/214812542_41
             */

            private String addTime;
            private int goodsId;
            private int pictureId;
            private int pictureShape;
            private int pictureType;
            private String pictureUrl;

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public int getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(int goodsId) {
                this.goodsId = goodsId;
            }

            public int getPictureId() {
                return pictureId;
            }

            public void setPictureId(int pictureId) {
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
        }
    }
}
