package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class ShowOrderBean {

    /**
     * code : 1
     * data : [{"evaluationId":1,"userId":1,"content":"\"哈哈哈哈哈，完美就会发生尽快答复后杀很大空间但撒的教科书的声卡后大叔的跨时代撒开大口径的哈的\"","goodsId":1,"sequenceId":1,"evaluationtype":1,"addTime":"2016-06-01 00:00:00","score":4,"evaPic":[{"pictureId":1,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_38.jpg","addTime":"2017-01-06 09:38:00"},{"pictureId":2,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","addTime":"2017-01-05 09:45:00"},{"pictureId":3,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","addTime":"2017-01-16 18:04:22"},{"pictureId":4,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","addTime":"2017-01-16 19:03:46"}],"avatar":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","nickname":"13606143405"},{"evaluationId":2,"userId":1,"content":"\"会大喊大叫卡的话阿迪哈登哈市健康的哈萨克觉得哈萨克即到哈市科技大厦空间的哈萨克激动哈赛科技我\"","goodsId":1,"sequenceId":1,"evaluationtype":1,"addTime":"2016-06-02 00:00:00","score":5,"evaPic":[{"pictureId":5,"userId":1,"evaluationId":2,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/542125221_11.jpg","addTime":"2017-01-16 19:05:55"},{"pictureId":6,"userId":1,"evaluationId":2,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_36.jpg","addTime":"2017-01-16 19:05:55"}],"avatar":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","nickname":"13606143405"}]
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
         * evaluationId : 1
         * userId : 1
         * content : "哈哈哈哈哈，完美就会发生尽快答复后杀很大空间但撒的教科书的声卡后大叔的跨时代撒开大口径的哈的"
         * goodsId : 1
         * sequenceId : 1
         * evaluationtype : 1
         * addTime : 2016-06-01 00:00:00
         * score : 4
         * evaPic : [{"pictureId":1,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_38.jpg","addTime":"2017-01-06 09:38:00"},{"pictureId":2,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","addTime":"2017-01-05 09:45:00"},{"pictureId":3,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","addTime":"2017-01-16 18:04:22"},{"pictureId":4,"userId":1,"evaluationId":1,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_42.jpg","addTime":"2017-01-16 19:03:46"}]
         * avatar : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
         * nickname : 13606143405
         */

        private long evaluationId;
        private long userId;
        private String content;
        private long goodsId;
        private long sequenceId;
        private int evaluationtype;
        private String addTime;
        private double score;
        private String avatar;
        private String nickname;
        private List<EvaPicBean> evaPic;

        public long getEvaluationId() {
            return evaluationId;
        }

        public void setEvaluationId(long evaluationId) {
            this.evaluationId = evaluationId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
        }

        public long getSequenceId() {
            return sequenceId;
        }

        public void setSequenceId(long sequenceId) {
            this.sequenceId = sequenceId;
        }

        public int getEvaluationtype() {
            return evaluationtype;
        }

        public void setEvaluationtype(int evaluationtype) {
            this.evaluationtype = evaluationtype;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public List<EvaPicBean> getEvaPic() {
            return evaPic;
        }

        public void setEvaPic(List<EvaPicBean> evaPic) {
            this.evaPic = evaPic;
        }

        public static class EvaPicBean {
            /**
             * pictureId : 1
             * userId : 1
             * evaluationId : 1
             * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/9234122155_38.jpg
             * addTime : 2017-01-06 09:38:00
             */

            private long pictureId;
            private long userId;
            private long evaluationId;
            private String pictureUrl;
            private String addTime;

            public long getPictureId() {
                return pictureId;
            }

            public void setPictureId(long pictureId) {
                this.pictureId = pictureId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public long getEvaluationId() {
                return evaluationId;
            }

            public void setEvaluationId(long evaluationId) {
                this.evaluationId = evaluationId;
            }

            public String getPictureUrl() {
                return pictureUrl;
            }

            public void setPictureUrl(String pictureUrl) {
                this.pictureUrl = pictureUrl;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }
        }
    }
}
