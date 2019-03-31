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
    String launcherChangeEventName = "launcher.changed";
    String batteryChangeEventName = "battery.changed";

    public WxReceiver() {}

    public WxReceiver(WXSDKInstance wxInstance) {
        mWXSDKInstance = wxInstance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (intentAction.equals(Intent.ACTION_PACKAGE_REMOVED) || intentAction.equals(Intent.ACTION_PACKAGE_ADDED)) {
            WXLogUtils.v("WxReceiver onReceive called " + intentAction);

            Map<String,Object> params = new HashMap<>();
            mWXSDKInstance.fireGlobalEventCallback(launcherChangeEventName, params);
        } else if (intentAction.equals(Intent.ACTION_BATTERY_CHANGED)) {
            int batteryN = intent.getIntExtra("level", 0);
            int batteryV = intent.getIntExtra("voltage", 0);
            int batteryT = intent.getIntExtra("temperature", 0);
            WXLogUtils.e("Hello battery change");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("batteryN", batteryN);
            params.put("batteryV", batteryV);
            params.put("batteryT", batteryT);
            mWXSDKInstance.fireGlobalEventCallback(batteryChangeEventName, params);
        }
    }
}
