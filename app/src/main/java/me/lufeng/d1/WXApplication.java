package me.lufeng.d1;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;

import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.taobao.weex.utils.WXLogUtils;

import java.util.HashSet;
import java.util.Set;

import me.lufeng.component.AppIcon;
import me.lufeng.module.PageLocation;
import me.lufeng.module.PkgMananer;
import me.lufeng.module.WallPaper;

public class WXApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;

        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig imgConfig = ImagePipelineConfig.newBuilder(this)
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(this,imgConfig);

        InitConfig config = new InitConfig.Builder().setImgAdapter(new FrescoImageAdapter()).build();
        WXSDKEngine.initialize(this,config);

        this.registerMethod();
        this.registerComponent();
    }

    public void registerMethod() {
        try {
            WXSDKEngine.registerModule("WallPaper", WallPaper.class);
            WXSDKEngine.registerModule("PackageManager", PkgMananer.class);
            WXSDKEngine.registerModule("location", PageLocation.class);
        } catch (WXException e) {
            WXLogUtils.e("[WXSDKEngine] register:", e);
        }
    }

    public void registerComponent() {
        try {
            WXSDKEngine.registerComponent("appicon", AppIcon.class);
        } catch (WXException e) {
            WXLogUtils.e("[WXSDKEngine] register:", e);
        }
    }

    public static Context getAppContext() {
        return appContext;
    }
}
