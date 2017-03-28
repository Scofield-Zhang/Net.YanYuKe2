package net.youlvke.yanyuke.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class SearchBean implements Serializable {

    /**
     * code : 1
     * data : [{"goodsId":1,"goodsName":"楼兰红酒750ML","pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","salesCount":50,"stockCount":50,"totalCount":100}]
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

    public static class DataBean implements Serializable{
        /**
         * goodsId : 1
         * goodsName : 楼兰红酒750ML
         * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
         * salesCount : 50
         * stockCount : 50
         * totalCount : 100
         */

        private long goodsId;
        private String goodsName;
        private String pictureUrl;
        private int salesCount;
        private int stockCount;
        private int totalCount;

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
