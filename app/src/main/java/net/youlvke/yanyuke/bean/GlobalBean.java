package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15 0015.
 */
public class GlobalBean {
    /**
     * code : 1
     * data : {"all":[{"goodsId":12,"goodsName":"zk10","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","goodsDescription":"zk10 ","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":13,"goodsName":"大撒旦撒旦","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"分段收费的身份的说法","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":14,"goodsName":"挂号费都很反感和法国","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"是大多数","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":15,"goodsName":"你们好好跟","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","goodsDescription":"分段收费的身份的身份的三分到手发抖","totalCount":100,"salesCount":50,"stockCount":50}],"New":[{"goodsId":12,"goodsName":"zk10","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","goodsDescription":"zk10 ","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":13,"goodsName":"大撒旦撒旦","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"分段收费的身份的说法","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":14,"goodsName":"挂号费都很反感和法国","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"是大多数","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":15,"goodsName":"你们好好跟","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","goodsDescription":"分段收费的身份的身份的三分到手发抖","totalCount":100,"salesCount":50,"stockCount":50}],"Generalize":[{"goodsId":12,"goodsName":"zk10","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","goodsDescription":"zk10 ","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":13,"goodsName":"大撒旦撒旦","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"分段收费的身份的说法","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":14,"goodsName":"挂号费都很反感和法国","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"是大多数","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":15,"goodsName":"你们好好跟","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","goodsDescription":"分段收费的身份的身份的三分到手发抖","totalCount":100,"salesCount":50,"stockCount":50}],"hot":[{"goodsId":12,"goodsName":"zk10","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","goodsDescription":"zk10 ","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":13,"goodsName":"大撒旦撒旦","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"分段收费的身份的说法","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":14,"goodsName":"挂号费都很反感和法国","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","goodsDescription":"是大多数","totalCount":100,"salesCount":50,"stockCount":50},{"goodsId":15,"goodsName":"你们好好跟","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_43.jpg","goodsDescription":"分段收费的身份的身份的三分到手发抖","totalCount":100,"salesCount":50,"stockCount":50}]}
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
        private List<AllBean> all;
        private List<NewBean> New;
        private List<GeneralizeBean> Generalize;
        private List<HotBean> hot;

        public List<AllBean> getAll() {
            return all;
        }

        public void setAll(List<AllBean> all) {
            this.all = all;
        }

        public List<NewBean> getNew() {
            return New;
        }

        public void setNew(List<NewBean> New) {
            this.New = New;
        }

        public List<GeneralizeBean> getGeneralize() {
            return Generalize;
        }

        public void setGeneralize(List<GeneralizeBean> Generalize) {
            this.Generalize = Generalize;
        }

        public List<HotBean> getHot() {
            return hot;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public static class AllBean {
            /**
             * goodsId : 12
             * goodsName : zk10
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             * goodsDescription : zk10
             * totalCount : 100
             * salesCount : 50
             * stockCount : 50
             */

            private long goodsId;
            private String goodsName;
            private String pictureUrl;
            private String goodsDescription;
            private int totalCount;
            private int salesCount;
            private int stockCount;

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

            public String getPictureUrl() {
                return pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getGoodsDescription() {
                return goodsDescription;
            }

            public void setGoodsDescription(String goodsDescription) {
                this.goodsDescription = goodsDescription;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getSalesCount() {
                return salesCount;
            }

            public void setSalesCount(int salesCount) {
                this.salesCount = salesCount;
            }

            public int getStockCount() {
                return stockCount;
            }

            public void setStockCount(int stockCount) {
                this.stockCount = stockCount;
            }
        }

        public static class NewBean {
            /**
             * goodsId : 12
             * goodsName : zk10
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             * goodsDescription : zk10
             * totalCount : 100
             * salesCount : 50
             * stockCount : 50
             */

            private long goodsId;
            private String goodsName;
            private String pictureUrl;
            private String goodsDescription;
            private int totalCount;
            private int salesCount;
            private int stockCount;

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

            public String getPictureUrl() {
                return pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getGoodsDescription() {
                return goodsDescription;
            }

            public void setGoodsDescription(String goodsDescription) {
                this.goodsDescription = goodsDescription;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getSalesCount() {
                return salesCount;
            }

            public void setSalesCount(int salesCount) {
                this.salesCount = salesCount;
            }

            public int getStockCount() {
                return stockCount;
            }

            public void setStockCount(int stockCount) {
                this.stockCount = stockCount;
            }
        }

        public static class GeneralizeBean {
            /**
             * goodsId : 12
             * goodsName : zk10
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             * goodsDescription : zk10
             * totalCount : 100
             * salesCount : 50
             * stockCount : 50
             */

            private long goodsId;
            private String goodsName;
            private String pictureUrl;
            private String goodsDescription;
            private int totalCount;
            private int salesCount;
            private int stockCount;

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

            public String getPictureUrl() {
                return pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getGoodsDescription() {
                return goodsDescription;
            }

            public void setGoodsDescription(String goodsDescription) {
                this.goodsDescription = goodsDescription;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getSalesCount() {
                return salesCount;
            }

            public void setSalesCount(int salesCount) {
                this.salesCount = salesCount;
            }

            public int getStockCount() {
                return stockCount;
            }

            public void setStockCount(int stockCount) {
                this.stockCount = stockCount;
            }
        }

        public static class HotBean {
            /**
             * goodsId : 12
             * goodsName : zk10
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             * goodsDescription : zk10
             * totalCount : 100
             * salesCount : 50
             * stockCount : 50
             */

            private long goodsId;
            private String goodsName;
            private String pictureUrl;
            private String goodsDescription;
            private int totalCount;
            private int salesCount;
            private int stockCount;

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

            public String getPictureUrl() {
                return pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getGoodsDescription() {
                return goodsDescription;
            }

            public void setGoodsDescription(String goodsDescription) {
                this.goodsDescription = goodsDescription;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public int getSalesCount() {
                return salesCount;
            }

            public void setSalesCount(int salesCount) {
                this.salesCount = salesCount;
            }

            public int getStockCount() {
                return stockCount;
            }

            public void setStockCount(int stockCount) {
                this.stockCount = stockCount;
            }
        }
    }
}
