package me.lufeng.component;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.dom.WXStyle;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXLogUtils;

public class AppIcon extends WXComponent<ImageView> {
    int viewport;

    public AppIcon(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);

        this.viewport = instance.getInstanceViewPortWidth();
    }

    @Override
    protected ImageView initComponentHostView(@NonNull Context context) {
        ImageView view = new ImageView(context);

        return view;
    }

    @WXComponentProp(name = "name")
    public void setName(String name) {
        ImageView image = getHostView();


//        WXImageStrategy imageStrategy = new WXImageStrategy();
//        IWXImgLoaderAdapter imgLoaderAdapter = getInstance().getImgLoaderAdapter();
//        if (imgLoaderAdapter != null) {
//            imgLoaderAdapter.setImage("https://gw.alicdn.com/tfs/TB1N8U_GyLaK1RjSZFxXXamPFXa-93-29.png", getHostView(), WXImageQuality.NORMAL, imageStrategy);
//        }

        PackageManager packageManager = getContext().getPackageManager();
        try {
            Drawable appIcon = packageManager.getApplicationIcon(name);
            image.setImageDrawable(appIcon);
            WXStyle style =  this.getDomObject().getStyles();
//            image.getLayoutParams().width = Math.round(style.getWidth(this.viewport));
//            image.getLayoutParams().height = Math.round(style.getHeight(this.viewport));
        } catch (Exception e) {
            WXLogUtils.e(e.getMessage());
        }
    }

}
