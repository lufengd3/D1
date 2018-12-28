package me.lufeng.d1;

import android.app.Application;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.utils.WXLogUtils;

public class WXApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        InitConfig config = new InitConfig.Builder().setImgAdapter(new FrescoImageAdapter()).build();
        WXSDKEngine.initialize(this,config);

        this.registerMethod();
    }

    public void registerMethod() {
        try {
            WXSDKEngine.registerModule("WallPaper", WallPaper.class);
        } catch (WXException e) {
            WXLogUtils.e("[WXSDKEngine] register:", e);
        }
    }
}
