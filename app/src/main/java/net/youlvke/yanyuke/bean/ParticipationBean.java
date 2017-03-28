package net.youlvke.yanyuke.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/11 0011.
 */

public class ParticipationBean {


    /**
     * code : 1
     * data : [{"avatar":"avatarImage/default.jpg","nickname":"13606143405","purchaseCount":1,"purchaseDate":"2016-01-23 16:00:00","purchaseId":1,"purchaseSate":1,"purchaseType":1,"sequenceId":1,"userId":1,"userIp":"192.168.0.102","userLat":23,"userLng":35,"userLocation":"桐庐","vouchersId":0},{"avatar":"avatarImage/default.jpg","nickname":"13606143405","purchaseCount":2,"purchaseDate":"2017-02-04 14:10:49","purchaseId":1486188649337,"purchaseSate":0,"purchaseType":2,"sequenceId":1,"userId":1,"userIp":"192.168.0.107","userLat":119.707837,"userLng":29.7931,"userLocation":"桐庐县","vouchersId":0},{"avatar":"avatarImage/default.jpg","nickname":"13606143405","purchaseCount":2,"purchaseDate":"2017-02-04 14:13:44","purchaseId":1486188823545,"purchaseSate":0,"purchaseType":1,"sequenceId":1,"userId":1,"userIp":"192.168.0.107","userLat":119.707837,"userLng":29.7931,"userLocation":"桐庐县","vouchersId":0},{"avatar":"avatarImage/default.jpg","nickname":"13606143405","purchaseCount":2,"purchaseDate":"2017-02-04 14:30:01","purchaseId":1486189801110,"purchaseSate":0,"purchaseType":2,"sequenceId":1,"userId":1,"userIp":"192.168.0.107","userLat":119.707804,"userLng":29.793128,"userLocation":"桐庐县","vouchersId":0},{"avatar":"avatarImage/default.jpg","nickname":"13606143405","purchaseCount":1,"purchaseDate":"2017-02-04 15:18:31","purchaseId":1486192710598,"purchaseSate":0,"purchaseType":2,"sequenceId":1,"userId":1,"userIp":"192.168.0.107","userLat":119.707814,"userLng":29.793113,"userLocation":"桐庐县","vouchersId":0}]
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
         * avatar : avatarImage/default.jpg
         * nickname : 13606143405
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

        private String avatar;
        private String nickname;
        private int purchaseCount;
        private String purchaseDate;
        private long purchaseId;
        private int purchaseSate;
        private int purchaseType;
        private long sequenceId;
        private long userId;
        private String userIp;
        private double userLat;
        private double userLng;
        private String userLocation;
        private int vouchersId;

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

        public long getPurchaseId() {
            return purchaseId;
        }

        public void setPurchaseId(long purchaseId) {
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

        public long getSequenceId() {
            return sequenceId;
        }

        public void setSequenceId(long sequenceId) {
            this.sequenceId = sequenceId;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
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
    }
}
