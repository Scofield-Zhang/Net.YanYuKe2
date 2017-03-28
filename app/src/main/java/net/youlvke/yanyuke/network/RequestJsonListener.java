package net.youlvke.yanyuke.network;

import com.yolanda.nohttp.rest.Response;

/**
 *
 * @param <T>
 */
public abstract class RequestJsonListener<T> extends BaseRequestListener {
    /**
     * 成功
     */
    public abstract void onSuccess(Response<T> response);

}
