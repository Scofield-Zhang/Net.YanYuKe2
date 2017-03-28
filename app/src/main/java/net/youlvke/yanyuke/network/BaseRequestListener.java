package net.youlvke.yanyuke.network;

import android.widget.Toast;

import net.youlvke.yanyuke.MyApplication;

/**
 * Created by 袁慎彬 on 2016/5/19.
 */
public abstract class BaseRequestListener extends OnUploadListener {

    /**
     * 错误
     */
    public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

        Toast.makeText(MyApplication.getInstance(), "网络不给力···", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(int what) {
        super.onStart(what);

    }
}
