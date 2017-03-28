package net.youlvke.yanyuke.network;

import android.content.Context;

import com.yolanda.nohttp.RequestMethod;

import net.youlvke.yanyuke.utils.JsonUtils;


/**
 *
 */
public class UploadRequest extends BaseRequest<UploadRequest> {
    public <T> UploadRequest(Context context, String url, T params) {
        this.url = url;
        this.context = context;
        this.params = JsonUtils.string(params);
    }

    public <T> void execute(Class<T> classOfT, RequestJsonListener<T> l) {
        RequestManager.loadArray(context, RequestMethod.POST, params, url, classOfT, isLoading, loadingTitle, timeOut, retry, l);
    }

    public void execute(RequestListener l) {
        RequestManager.loadUploadString(context, params, url, uploadFiles, isLoading, loadingTitle, l);
    }
}