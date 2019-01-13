package me.lufeng.module;

//import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.common.WXRenderStrategy;


public class PageLocation extends WXModule {

    @JSMethod(uiThread = true)
    public void reload() {
        mWXSDKInstance.refreshInstance(mWXSDKInstance.getInstanceId());
    }

}
