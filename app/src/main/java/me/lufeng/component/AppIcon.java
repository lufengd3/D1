package me.lufeng.component;

import android.content.Context;
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


        WXLogUtils.e("Helloe");

        return image;
    }

    @WXComponentProp(name = "tel")
    public void setTel(String appName) {
        WXLogUtils.v(appName);
    }
}
