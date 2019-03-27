package me.lufeng.module;

import android.app.WallpaperManager;
import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import java.io.InputStream;
import java.net.URL;

import me.lufeng.d1.WXApplication;

public class WallPaper  extends WXModule {

    @JSMethod(uiThread = false)
    public void update(String url) {
        final String imgUrl = url;
        final WallpaperManager manager = WallpaperManager.getInstance(WXApplication.getAppContext());

        try {
            InputStream img = (InputStream) new URL(imgUrl).getContent();
            manager.setStream(img);
        } catch (Exception e) {
            WXLogUtils.e("Unkonw error while update wallpaper");
        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    final InputStream img = (InputStream) new URL(imgUrl).getContent();
//
//                    if (mWXSDKInstance != null) {
//                        mWXSDKInstance.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    manager.setStream(img);
//                                } catch (Exception e) {
//                                    WXLogUtils.e("Unkonw error while update wallpaper");
//                                }
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    WXLogUtils.e(e.getMessage());
//                }
//
//
//            }
//        }).start();

    }

}