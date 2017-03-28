package net.youlvke.yanyuke.network;

import android.content.Context;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import net.youlvke.yanyuke.utils.JsonUtils;


/**
 * Created by 袁慎彬 on 2016/6/21.
 */
public class ArrayRequest<T> extends RestRequest<T> {
    private Class<T> classOfT;
    private Context context;

    public ArrayRequest(String url) {
        super(url);
        setAccept("application/json");
    }
    public ArrayRequest(Context context, String url, RequestMethod requestMethod, Class<T> classOfT) {
        super(url, requestMethod);
        setAccept("application/json");
        this.classOfT = classOfT;
        this.context = context;
    }


    @Override
    public T parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
        String result = StringRequest.parseResponseString(responseHeaders, responseBody);
        return JsonUtils.object(result, classOfT);
    }
}
