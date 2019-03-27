package me.lufeng.module;

import android.provider.Settings;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

public class SystemStatus extends WXModule {
    @JSMethod(uiThread = false)
    public double getScreenTime() {
        double screenOffTimeout = 12.22;

        WXLogUtils.v("hello " + String.valueOf(screenOffTimeout));
        return screenOffTimeout;
    }
}
