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
//        Toast.makeText(mWXSDKInstance.getContext(),url,Toast.LENGTH_SHORT).show();

        /*Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.wp0);
        WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());

        try {
            manager.setBitmap(pic);
            Toast.makeText(this, "Wallpaper changed!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }*/

        try {
            InputStream img = (InputStream) new URL(url).getContent();
            WallpaperManager manager = WallpaperManager.getInstance(WXApplication.getAppContext());
            manager.setStream(img);
        } catch (Exception e) {
            WXLogUtils.e(e.getMessage());
        }
    }

}