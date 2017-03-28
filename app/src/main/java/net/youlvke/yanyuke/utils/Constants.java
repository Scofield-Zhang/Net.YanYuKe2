package net.youlvke.yanyuke.utils;

/**
 * Created by Administrator on 2016/12/20 0020.
 * @author alex
 *  <p>常量<p/>
 */
public class Constants {

    //////////////////////////////////////////////////////////////
    ///    网络协议常量相关配置
    /////////////////////////////////////////////////////////////

    /*http://test.yanyuke.net:8080/sqdbSystem*/

    // public static final String BASEURL= "https://192.168.1.5:8443/sqdbSystem";

    //public static final String BASEURL = "http://192.168.1.4:8080/sqdbSystem";
    public static final String BASEURL = "http://1p6367w428.imwork.net/sqdbSystem";
    //用户模块
    public static final String BASEURL_YYKUSER = BASEURL + "/yykUser?";
    //系统模块
    public static final String BASEURL_YYKSYSTEM = BASEURL + "/yykSystem?";
    /**支付模块*/
    public static final String BASEURL_YYKORDER = BASEURL + "/yykOrder?";
    /** 商品模块*/
    public static final String BASEURL_YYKGOODS = BASEURL + "/yykGoods?";
    /** 阿里云Oss 参数设置 */
    public static final String OSS_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
    public static final String UPLOAD_URL = "http://sqdb-goods.oss-cn-hangzhou.aliyuncs.com/";



    //////////////////////////////////////////////////////////////
    ///    微信
    /////////////////////////////////////////////////////////////
    public static final String APP_ID = "wx6885adcef1e3ea88";


    //////////////////////////////////////////////////////////////
    ///    友盟的相关配置
    /////////////////////////////////////////////////////////////
    public static String url = "http://www.baidu.com";
    public static String text = "欢迎使用【烟雨客】一个专注于您的生活的平台，会让您的生活从此不同";
    public static String title = "【烟雨客】一个有意思的地方";
    // 网络分享图片链接地址，如果需要添加本地图片分享
    public static String imageurl = "https://mobile.umeng.com/images/pic/home/social/img-1.png";
    // public static String imageurl = "http://storage1.52mrmagic.com/201612200031_7ef8984523d3.jpg";
    //public static String videourl = "http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html";
    //public static String musicurl = "http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3";

    //////////////////////////////////////////////////////////////
    ///     运行sample前需要配置以下字段为有效的值
    /////////////////////////////////////////////////////////////
    //private static final String accessKeyId = "**************";
    //private static final String accessKeySecret = "*******************";

    private static final String uploadFilePath = "<upload_file_path>";

    public static final String ACCESS_KEY_ID = "LTAIB81oGBF8Qdm8";

    public static final String ACCESS_KEY_SECRET = "tBsGL5KOgYMrTBR7QkoZ1zozKTqRHY";

    private static final String bucket = "sqdb-test";

    private static final String uploadObject = "zhang_tao.jpg";
    
    private static final String downloadObject = "sampleObject";
    public static final String PROCOTOLADDRESS = "http://1p6367w428.imwork.net/sqdbSystem/instructions.html";
}
