package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class ShopListBean {


    /**
     * code : 1
     * data : [{"goodsDescription":"楼兰红酒传承千楼兰古文化但是但是大多都是大三大四的撒旦撒旦是的是的撒打算","goodsId":1,"goodsName":"楼兰红酒750ML大都会杀毒沙地上的大口径的刷卡","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","salesCount":50,"stockCount":50,"totalCount":100},{"goodsDescription":"相思一样，情深一片。大撒旦撒打算大大缩短算得上是大多数大三大四的","goodsId":9,"goodsName":"相思梅大多数是大的","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_40.jpg","salesCount":34,"stockCount":66,"totalCount":100}]
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
         * goodsDescription : 楼兰红酒传承千楼兰古文化但是但是大多都是大三大四的撒旦撒旦是的是的撒打算
         * goodsId : 1
         * goodsName : 楼兰红酒750ML大都会杀毒沙地上的大口径的刷卡
         * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
         * salesCount : 50
         * stockCount : 50
         * totalCount : 100
         */

        private String goodsDescription;
        private long goodsId;
        private String goodsName;
        private String pictureUrl;
        private int salesCount;
        private int stockCount;
        private int totalCount;

        public String getGoodsDescription() {
            return goodsDescription;
        }

        public void setGoodsDescription(String goodsDescription) {
            this.goodsDescription = goodsDescription;
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

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
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

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}
