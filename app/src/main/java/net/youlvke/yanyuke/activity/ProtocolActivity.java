package net.youlvke.yanyuke.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import net.youlvke.yanyuke.R;

import static net.youlvke.yanyuke.utils.Constants.PROCOTOLADDRESS;


public class ProtocolActivity extends BaseActivity {

    private TextView tvTitle;
    private WebView wvProtocol;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_protocol;
    }

    @Override
    protected void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        wvProtocol = (WebView) findViewById(R.id.wv_protocol);
        WebSettings webSettings = wvProtocol.getSettings();

        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        wvProtocol.loadUrl(PROCOTOLADDRESS);
        wvProtocol.setWebViewClient(new webViewClient());
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        tvTitle.setText("用户协议");
    }

    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
