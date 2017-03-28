package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */
public class AnnouncedBean {


    /**
     * code : 1
     * data : [{"sequenceId":1,"sequenceState":3,"goodsId":1,"totalCount":100,"salesCount":100,"stockCount":0,"crowdFundingPercent":100,"userId":1,"winningNumber":"0","releaseDate":"2017-01-09 10:00:00","announcedDate":"2017-01-10 10:00:00","Countdown":0,"count":2,"nickname":"13606143405","goodsinfo":{"goodsId":1,"goodsUid":"63651621","goodsName":"楼兰红酒750ML","goodsDescription":"楼兰红酒传承千楼兰古文化","goodsPrice":50,"goodsCategory":1,"goodsState":0,"goodsFlag":0,"goodsArea":"桐庐县","goodsProvince":"浙江省","goodsCity":"杭州市","goodsSequenceNum":50,"goodsNewFlag":0,"userId":1,"coverPic":[{"pictureId":1,"goodsId":1,"pictureType":1001,"pictureShape":0,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","pictureins":"黄焖鸡米饭","addTime":"2016-01-01 00:00:00"}]}}]
     * count : 1
     * message : 获取成功
     */

    private int code;
    private int count;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
         * sequenceId : 1
         * sequenceState : 3
         * goodsId : 1
         * totalCount : 100
         * salesCount : 100
         * stockCount : 0
         * crowdFundingPercent : 100
         * userId : 1
         * winningNumber : 0
         * releaseDate : 2017-01-09 10:00:00
         * announcedDate : 2017-01-10 10:00:00
         * Countdown : 0
         * count : 2
         * nickname : 13606143405
         * goodsinfo : {"goodsId":1,"goodsUid":"63651621","goodsName":"楼兰红酒750ML","goodsDescription":"楼兰红酒传承千楼兰古文化","goodsPrice":50,"goodsCategory":1,"goodsState":0,"goodsFlag":0,"goodsArea":"桐庐县","goodsProvince":"浙江省","goodsCity":"杭州市","goodsSequenceNum":50,"goodsNewFlag":0,"userId":1,"coverPic":[{"pictureId":1,"goodsId":1,"pictureType":1001,"pictureShape":0,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","pictureins":"黄焖鸡米饭","addTime":"2016-01-01 00:00:00"}]}
         */

        private long sequenceId;
        private int sequenceState;
        private long goodsId;
        private int totalCount;
        private int salesCount;
        private int stockCount;
        private int crowdFundingPercent;
        private long userId;
        private String winningNumber;
        private String releaseDate;
        private String announcedDate;
        private int Countdown;
        private int count;
        private String nickname;
        private GoodsinfoBean goodsinfo;

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

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
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

        public int getCrowdFundingPercent() {
            return crowdFundingPercent;
        }

        public void setCrowdFundingPercent(int crowdFundingPercent) {
            this.crowdFundingPercent = crowdFundingPercent;
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

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getAnnouncedDate() {
            return announcedDate;
        }

        public void setAnnouncedDate(String announcedDate) {
            this.announcedDate = announcedDate;
        }

        public int getCountdown() {
            return Countdown;
        }

        public void setCountdown(int Countdown) {
            this.Countdown = Countdown;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public GoodsinfoBean getGoodsinfo() {
            return goodsinfo;
        }

        public void setGoodsinfo(GoodsinfoBean goodsinfo) {
            this.goodsinfo = goodsinfo;
        }

        public static class GoodsinfoBean {
            /**
             * goodsId : 1
             * goodsUid : 63651621
             * goodsName : 楼兰红酒750ML
             * goodsDescription : 楼兰红酒传承千楼兰古文化
             * goodsPrice : 50
             * goodsCategory : 1
             * goodsState : 0
             * goodsFlag : 0
             * goodsArea : 桐庐县
             * goodsProvince : 浙江省
             * goodsCity : 杭州市
             * goodsSequenceNum : 50
             * goodsNewFlag : 0
             * userId : 1
             * coverPic : [{"pictureId":1,"goodsId":1,"pictureType":1001,"pictureShape":0,"pictureUrl":"http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg","pictureins":"黄焖鸡米饭","addTime":"2016-01-01 00:00:00"}]
             */

            private long goodsId;
            private String goodsUid;
            private String goodsName;
            private String goodsDescription;
            private int goodsPrice;
            private int goodsCategory;
            private int goodsState;
            private int goodsFlag;
            private String goodsArea;
            private String goodsProvince;
            private String goodsCity;
            private int goodsSequenceNum;
            private int goodsNewFlag;
            private long userId;
            private List<CoverPicBean> coverPic;

            public long getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(long goodsId) {
                this.goodsId = goodsId;
            }

            public String getGoodsUid() {
                return goodsUid;
            }

            public void setGoodsUid(String goodsUid) {
                this.goodsUid = goodsUid;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsDescription() {
                return goodsDescription;
            }

            public void setGoodsDescription(String goodsDescription) {
                this.goodsDescription = goodsDescription;
            }

            public int getGoodsPrice() {
                return goodsPrice;
            }

            public void setGoodsPrice(int goodsPrice) {
                this.goodsPrice = goodsPrice;
            }

            public int getGoodsCategory() {
                return goodsCategory;
            }

            public void setGoodsCategory(int goodsCategory) {
                this.goodsCategory = goodsCategory;
            }

            public int getGoodsState() {
                return goodsState;
            }

            public void setGoodsState(int goodsState) {
                this.goodsState = goodsState;
            }

            public int getGoodsFlag() {
                return goodsFlag;
            }

            public void setGoodsFlag(int goodsFlag) {
                this.goodsFlag = goodsFlag;
            }

            public String getGoodsArea() {
                return goodsArea;
            }

            public void setGoodsArea(String goodsArea) {
                this.goodsArea = goodsArea;
            }

            public String getGoodsProvince() {
                return goodsProvince;
            }

            public void setGoodsProvince(String goodsProvince) {
                this.goodsProvince = goodsProvince;
            }

            public String getGoodsCity() {
                return goodsCity;
            }

            public void setGoodsCity(String goodsCity) {
                this.goodsCity = goodsCity;
            }

            public int getGoodsSequenceNum() {
                return goodsSequenceNum;
            }

            public void setGoodsSequenceNum(int goodsSequenceNum) {
                this.goodsSequenceNum = goodsSequenceNum;
            }

            public int getGoodsNewFlag() {
                return goodsNewFlag;
            }

            public void setGoodsNewFlag(int goodsNewFlag) {
                this.goodsNewFlag = goodsNewFlag;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public List<CoverPicBean> getCoverPic() {
                return coverPic;
            }

            public void setCoverPic(List<CoverPicBean> coverPic) {
                this.coverPic = coverPic;
            }

            public static class CoverPicBean {
                /**
                 * pictureId : 1
                 * goodsId : 1
                 * pictureType : 1001
                 * pictureShape : 0
                 * pictureUrl : http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/214812542_41.jpg
                 * pictureins : 黄焖鸡米饭
                 * addTime : 2016-01-01 00:00:00
                 */

                private long pictureId;
                private long goodsId;
                private int pictureType;
                private int pictureShape;
                private String pictureUrl;
                private String pictureins;
                private String addTime;

                public long getPictureId() {
                    return pictureId;
                }

                public void setPictureId(long pictureId) {
                    this.pictureId = pictureId;
                }

                public long getGoodsId() {
                    return goodsId;
                }

                public void setGoodsId(long goodsId) {
                    this.goodsId = goodsId;
                }

                public int getPictureType() {
                    return pictureType;
                }

                public void setPictureType(int pictureType) {
                    this.pictureType = pictureType;
                }

                public int getPictureShape() {
                    return pictureShape;
                }

                public void setPictureShape(int pictureShape) {
                    this.pictureShape = pictureShape;
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

                public String getAddTime() {
                    return addTime;
                }

                public void setAddTime(String addTime) {
                    this.addTime = addTime;
                }
            }
        }
    }
}
