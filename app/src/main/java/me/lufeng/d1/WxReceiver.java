package me.lufeng.d1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.WXSDKManager;
import com.taobao.weex.utils.WXLogUtils;

import java.util.HashMap;
import java.util.Map;

public class WxReceiver extends BroadcastReceiver {
    WXSDKInstance mWXSDKInstance;
    String launcherChangeEventName = "launcher.update";

    public WxReceiver(WXSDKInstance wxInstance) {
        mWXSDKInstance = wxInstance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        if (intentAction.equals("android.intent.action.PACKAGE_REMOVED")
                || intentAction.equals("android.intent.action.PACKAGE_ADDED")
        ) {
            WXLogUtils.v("WxReceiver onReceive called " + intentAction);

            Map<String,Object> params = new HashMap<>();
            mWXSDKInstance.fireGlobalEventCallback(launcherChangeEventName, params);
        }
    }
}
