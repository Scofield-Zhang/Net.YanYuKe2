package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9 0009.
 */

public class HomeBean {

    /**
     * code : 1
     * data : {"hot":[{"goodsId":1,"goodsName":"楼兰红酒750ML","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg"},{"goodsId":7,"goodsName":"柯达s600相机","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_38.jpg"},{"goodsId":10,"goodsName":"大众甲壳虫使用权1年","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg"},{"goodsId":9,"goodsName":"相思梅","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_40.jpg"}],"top":[{"goodsId":1,"goodsName":"楼兰红酒750ML","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg"},{"goodsId":7,"goodsName":"柯达s600相机","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_38.jpg"},{"goodsId":10,"goodsName":"大众甲壳虫使用权1年","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg"},{"goodsId":9,"goodsName":"相思梅","pictureShape":0,"pictureType":1001,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_40.jpg"}]}
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
        private List<HotBean> hot;
        private List<TopBean> top;


        public List<HotBean> getHot() {
            return hot;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public List<TopBean> getTop() {
            return top;
        }

        public void setTop(List<TopBean> top) {
            this.top = top;
        }

        public static class HotBean {
            /**
             * goodsId : 1
             * goodsName : 楼兰红酒750ML
             * pictureShape : 0
             * pictureType : 1001
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             */

            private long goodsId;
            private String goodsName;
            private int pictureShape;
            private int pictureType;
            private String pictureUrl;

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

        public static class TopBean {
            /**
             * goodsId : 1
             * goodsName : 楼兰红酒750ML
             * pictureShape : 0
             * pictureType : 1001
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
             */

            private long goodsId;
            private String goodsName;
            private int pictureShape;
            private int pictureType;
            private String pictureUrl;

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
