package me.lufeng.module;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import static android.support.v4.content.ContextCompat.startActivity;

public class PermissionManager extends WXModule {
    static boolean checkUsagePermission(Context appContext) {
        AppOpsManager appOps = (AppOpsManager) appContext.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), appContext.getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;

        return granted;
    }

    @JSMethod(uiThread = false)
    public boolean checkUsagePermission() {
        boolean granted = PermissionManager.checkUsagePermission(mWXSDKInstance.getContext());
        WXLogUtils.e("hello1 " +Boolean.toString(granted));

        return granted;
    }

    @JSMethod(uiThread = false)
    public void requestUsagePermission() {
        Intent permissionIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(mWXSDKInstance.getContext(), permissionIntent, null);
    }
}
