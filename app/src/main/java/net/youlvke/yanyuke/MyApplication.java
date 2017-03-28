package net.youlvke.yanyuke;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.cache.DBCacheStore;

import net.youlvke.yanyuke.service.LocationService;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class MyApplication extends Application {

    private static Application instance;
    public LocationService locationService;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NoHttp.initialize(this,new NoHttp.Config()
                .setConnectTimeout(30 * 1000)
                // 设置全局服务器响应超时时间，单位毫秒
                .setReadTimeout(30 * 1000)
                // 或者保存到SD卡
                .setCacheStore( new DBCacheStore(this))
                // 使用OkHttp
                .setNetworkExecutor(new OkHttpNetworkExecutor()));
        Logger.setDebug(true);
        Logger.setTag("nohttp");
        Config.DEBUG = true;//Umeng调试模式
        UMShareAPI.get(this);//初始化友盟
        locationService = new LocationService(getApplicationContext());

    }

    //初始化平台
    {
        PlatformConfig.setWeixin("wx6885adcef1e3ea88", "05e4086307f135085ae61fdb150019e4");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("1105836937", "9tLvRYrSihw1F7VK");
    }

    public static Application getInstance() {
        return instance;
    }

}
