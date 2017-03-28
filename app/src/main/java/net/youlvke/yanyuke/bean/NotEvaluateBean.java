package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class NotEvaluateBean {
    /**
     * code : 1
     * data : [{"evaluationId":3,"goodsId":1,"goodsName":"楼兰红酒750ML","goodsPrice":50,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","userTime":"2016-06-02 00:00:00"},{"evaluationId":4,"goodsId":2,"goodsName":"三星蓝调相机红","goodsPrice":5000,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/542125221_11.jpg","userTime":"2016-06-02 00:00:00"}]
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
         * evaluationId : 3
         * goodsId : 1
         * goodsName : 楼兰红酒750ML
         * goodsPrice : 50
         * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
         * userTime : 2016-06-02 00:00:00
         */

        private long evaluationId;
        private long goodsId;
        private String goodsName;
        private int goodsPrice;
        private String pictureUrl;
        private String userTime;

        public long getEvaluationId() {
            return evaluationId;
        }

        public void setEvaluationId(long evaluationId) {
            this.evaluationId = evaluationId;
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

        public int getGoodsPrice() {
            return goodsPrice;
        }

        public void setGoodsPrice(int goodsPrice) {
            this.goodsPrice = goodsPrice;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getUserTime() {
            return userTime;
        }

        public void setUserTime(String userTime) {
            this.userTime = userTime;
        }
    }
}
