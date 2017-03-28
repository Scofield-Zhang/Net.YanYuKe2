package net.youlvke.yanyuke.network;


import android.content.Context;

import com.yolanda.nohttp.RequestMethod;

public class GetRequest extends BaseRequest<GetRequest> {


    public GetRequest(Context context, String url) {
        this.url = url;
        this.context = context;
    }

    public <T> void execute(Class<T> classOfT, RequestJsonListener<T> l) {
        RequestManager.loadArray(context, RequestMethod.GET, null, url, classOfT, isLoading, loadingTitle, timeOut, retry, l);
    }

    public void execute(RequestListener l) {
        RequestManager.loadString(context, RequestMethod.GET, null, url, isLoading, loadingTitle, timeOut, retry, l);
    }
}
