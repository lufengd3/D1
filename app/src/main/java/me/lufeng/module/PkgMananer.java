package me.lufeng.module;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class PkgMananer extends WXModule {

    @JSMethod(uiThread = false)
    public List getApps() {
        PackageManager packageManager = mWXSDKInstance.getContext().getPackageManager();
        List<PackageInfo> apps = packageManager.getInstalledPackages(0);

        List<AppInfo> res = new ArrayList<AppInfo>();
        for(int i=0;i<apps.size();i++) {
            PackageInfo p = apps.get(i);
            Boolean isUserInstalledApp = (p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;

            if (isUserInstalledApp) {
                AppInfo newInfo = new AppInfo();
                newInfo.appName = p.applicationInfo.loadLabel(packageManager).toString();
                newInfo.packageName = p.packageName;
                res.add(newInfo);
            }
        }

        return res;
    }

    @JSMethod(uiThread = false)
    public void runApp(String packageName) {
        PackageManager packageManager = mWXSDKInstance.getContext().getPackageManager();
        Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(mWXSDKInstance.getContext(), launchIntent, null);
    }
}
