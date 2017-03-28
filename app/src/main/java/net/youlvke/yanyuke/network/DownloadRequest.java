package net.youlvke.yanyuke.network;

import android.content.Context;

import com.yolanda.nohttp.download.DownloadListener;


public class DownloadRequest extends BaseRequest<DownloadRequest> {
    public DownloadRequest(Context context, String url) {
        this.url = url;
        this.context = context;
    }

    public void execute(DownloadListener l) {
        RequestManager.loadDownload(context, url, what, fileFolder, fileName, isRange, isDeleteOld,l);
    }
}
