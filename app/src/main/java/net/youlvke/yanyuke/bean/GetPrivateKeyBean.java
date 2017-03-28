package net.youlvke.yanyuke.bean;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class GetPrivateKeyBean {


    /**
     * out_trade_no : 1483961037844
     * code : 1
     * partner : 2088911191049643
     * subject : 奔驰
     * total_fee : 1000
     * private_key : MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKih+DBkOQVNZ2JtNK+e5gZ8MSL8gh7qHj/mmb/ADxjzXtZvxZBBqFar6q31eQRE+Kf6BsasUImd+ec7TH/3E8N9m4u+3MwoJf13jmmzh5lSwFrUIYifoGXgoQ8RjYw34rr5xtZoZS0CFVO5rVLRgnGf/VbaIPERCzaBtlkglYexAgMBAAECgYBnFp0i+CHjm+4Xvar3savtSv6+5J6nR3g2mEJldoPEPQM9FBcymPDQC0jsD6Rrd76K8c4ypiSV/H1JXnblw6XQXXcFVbiB8nDB6rYWz4BfGWuhU4WCpIgGrAfdaw56x/V60ScYBB3hzbZ3+yDPJqyIzxap/P/NaKDlL9Jkn484AQJBAOLErC/56Vl1gALcQsoUBjb/dK0UGrNde3kBm4++tyCYB7Omfc7IAE+4JAUJrLWIIa/cTRs8sKCr74+ew47ww90CQQC+XtRvaUciMwSkL0eXg6J7yYU84yqWUIsyXfWipuxB7DdSLZQHr1W4wIYVz1cijGlSEy2bdIsEhgnn7Br5l+/lAkAFIobznykViX+YkPxCLynk3Ov7b1UbcqZ1GwA2xQ7IgijKfJ6krAq1w2mtY4axpZ99p/NnBnzJZptKGUI8Xl9RAkEAgkGMhXa5b1MNN/IJwEgXYy+t9M1SlGsj8yCLM/GW8jxYVehNwzxbIQDKPh4SdtcwMTJiIF4LyNqV6JSKmIE3aQJBAJfx6sF/d7aJKDPTC3Ur9vU5+7yd/vG5NX+Qi1GDtIOpbewlyR6eO/infwHtd3U6NXSDtbslSxSUs8O/f2Wu1QU=
     * notify_url : http://192.168.0.106:8089/sqdbSystem/alipay_callback
     * body : 元
     * message : 操作成功
     * seller_id : 642843526@qq.com
     */

    private String out_trade_no;
    private int code;
    private String partner;
    private String subject;
    private String total_fee;
    private String private_key;
    private String notify_url;
    private String body;
    private String message;
    private String seller_id;
    private String sign;
    public String getSign() {
        return sign;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public int getCode() {
        return code;
    }

    public String getPartner() {
        return partner;
    }

    public String getSubject() {
        return subject;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }

    public String getSeller_id() {
        return seller_id;
    }




}
