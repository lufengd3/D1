package me.lufeng.module;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import me.lufeng.d1.MainActivity;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.ContextCompat.startActivity;

public class PermissionManager extends WXModule {
    static boolean checkUsagePermission(Context appContext) {
        AppOpsManager appOps = (AppOpsManager) appContext.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                android.os.Process.myUid(), appContext.getPackageName());
        boolean granted = mode == AppOpsManager.MODE_ALLOWED;

        return granted;
    }

    static boolean checkNetworkLocationPermission(Context appContext) {
        int result = ActivityCompat.checkSelfPermission(appContext, Manifest.permission.ACCESS_FINE_LOCATION);
        boolean granted = result == PackageManager.PERMISSION_GRANTED;

        return granted;
    }

    @JSMethod(uiThread = false)
    public boolean checkUsagePermission() {
        boolean granted = PermissionManager.checkUsagePermission(mWXSDKInstance.getContext());

        return granted;
    }

    @JSMethod(uiThread = false)
    public void requestUsagePermission() {
        Intent permissionIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivity(mWXSDKInstance.getContext(), permissionIntent, null);
    }

    @JSMethod(uiThread = false)
    public boolean checkNetworkLocationPermission() {
        boolean granted = PermissionManager.checkNetworkLocationPermission(mWXSDKInstance.getContext());

        return granted;
    }

    @JSMethod(uiThread = false)
    public void requestNetworkLocationPermission() {
        Activity activity = (Activity) mWXSDKInstance.getContext();
        requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
    }
}
