package me.lufeng.component;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXLogUtils;

public class AppIcon extends WXComponent<ImageView> {
    public AppIcon(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected ImageView initComponentHostView(@NonNull Context context) {
        ImageView image = new ImageView(context);

        return image;
    }

    @WXComponentProp(name = "name")
    public void setName(String name) {
        ImageView image = getHostView();

        PackageManager packageManager = getContext().getPackageManager();
        try {
            Drawable appIcon = packageManager.getApplicationIcon(name);
            image.setImageDrawable(appIcon);
            image.getLayoutParams().height = 140;
            image.getLayoutParams().width = 140;
        } catch (Exception e) {
            WXLogUtils.e(e.getMessage());
        }
    }
}
