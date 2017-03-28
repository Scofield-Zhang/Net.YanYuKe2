package net.youlvke.yanyuke.network;

import android.content.Context;

import com.yolanda.nohttp.RequestMethod;

import net.youlvke.yanyuke.utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;



public class PostRequest extends BaseRequest<PostRequest> {

    String version = "100";
    String appkey = "sqdb_Yanyuke_20161008_fcfKhdf7ldg559inFDsgrTlme";

    public <T> PostRequest(Context context, String url, T params) throws JSONException {
        this.url = url;
        this.context = context;
        if (params instanceof Map){
            this.params = createParams((Map)params);
        }else {
            this.params = params.toString();//之前的是JsonUtils.toJson()连不上网
        }
    }

    public <T> void execute(Class<T> classOfT, RequestJsonListener<T> l) {
        RequestManager.loadArray(context, RequestMethod.POST, params, url, classOfT, isLoading, loadingTitle, timeOut, retry, l);
    }

    public void execute(RequestListener l) {
        RequestManager.loadString(context, RequestMethod.POST, params,url, isLoading, loadingTitle, timeOut, retry, l);
    }

    private String createParams(Map param) throws JSONException {
        String nonce = randomString();
        //String sign = MD5.digest("nonce=" + randomNum + "&version=200&appkey=Wzxc_Version2_id4fsniD44fO54dtbegM").toUpperCase();
        JSONObject jsonObject = new JSONObject(param);
        String sign = MD5.digest("nonce=" + nonce + "&version="+version+"&appkey="+appkey).toUpperCase();//注册
        jsonObject.put("sign", sign);
        jsonObject.put("version", version);
        jsonObject.put("nonce", nonce);
        return jsonObject.toString();//yykGoods userId   pageNum 20160201
    }

    public String randomString() {
        StringBuffer array = new StringBuffer();
        for (int i = 0; i <= 7; i++) {
            array.append(i);
        }
        // 小写字母暂时不要
        // for (int i = (int) 'a'; i <= (int) 'z'; i++) {
        // array.append((char) i);
        // }
        for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
            array.append((char) i);
        }
        int length = array.length();

        // 假设n现在为100
        int n = 8;
        // 存储最后生成的字符串
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < n; i++) {
            // 获得随机位置的字符
            char c = array.charAt((int) (Math.random() * length));
            str.append(c);
        }
        return str.toString();
    }
}
