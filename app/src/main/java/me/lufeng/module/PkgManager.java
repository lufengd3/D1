package me.lufeng.module;

import android.app.AppOpsManager;
import android.app.Application;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class PkgManager extends WXModule {

    @JSMethod(uiThread = false)
    public List getApps(String queryRules) {
        List apps;

        switch (queryRules) {
            case "frequency":
                apps = this.getFrequentlyApps();
                break;
            default:
                apps = this.getAllApps();
                break;
        }

        return apps;
    }

    @JSMethod(uiThread = false)
    public void runApp(String packageName) {
        PackageManager packageManager = mWXSDKInstance.getContext().getPackageManager();
        Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(mWXSDKInstance.getContext(), launchIntent, null);
    }

    @JSMethod(uiThread = false)
    public void showAppInfo(String packageName) {
        Uri uri = Uri.fromParts("package", packageName, null);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);

        startActivity(mWXSDKInstance.getContext(), intent, null);
    }

    @JSMethod(uiThread = false)
    public void uninstallApp(String packageName) {
        Uri packageUri = Uri.parse("package:" + packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);

        startActivity(mWXSDKInstance.getContext(), uninstallIntent, null);
    }

    private List getAllApps() {
        PackageManager packageManager = mWXSDKInstance.getContext().getPackageManager();

        List<PackageInfo> apps = packageManager.getInstalledPackages(0);

        List<AppInfo> res = new ArrayList<AppInfo>();
        for (int i=0; i<apps.size(); i++) {
            PackageInfo p = apps.get(i);
            Boolean isUserInstalledApp = (p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0;

            //if (p.applicationInfo.packageName.contains("h2folio")) {
            //WXLogUtils.e("hello " + p.applicationInfo.category);
            //}

            if (isUserInstalledApp) {
                AppInfo newInfo = new AppInfo();
                newInfo.appName = p.applicationInfo.loadLabel(packageManager).toString();
                newInfo.packageName = p.packageName;
                newInfo.launchCount = 1;

                res.add(newInfo);
            }
        }

        return res;
    }

    private List getFrequentlyApps() {
        List apps = new ArrayList();
        Context appContext = mWXSDKInstance.getContext();

//        AppOpsManager appOps = (AppOpsManager) appContext.getSystemService(Context.APP_OPS_SERVICE);
//        int mode = appOps.checkOpNoThrow("android:get_usage_stats",
//                android.os.Process.myUid(), appContext.getPackageName());
//        boolean granted = mode == AppOpsManager.MODE_ALLOWED;
//
//        if (granted) {
//            UsageStatsManager usageManager = (UsageStatsManager) appContext.getSystemService(Context.USAGE_STATS_SERVICE);
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.MONTH, -1);
//
//            List<UsageStats> allAppUsageStats = usageManager.queryUsageStats(usageManager.INTERVAL_DAILY, cal.getTimeInMillis(), System.currentTimeMillis());
//            allAppUsageStats = this.sortAppUsageStatsByLaunchCount(allAppUsageStats);
//            List<UsageStats> topAppUsageStats = allAppUsageStats.subList(0, 3);
//
//            PackageManager packageManager = appContext.getPackageManager();
//            for (int i = 0; i < topAppUsageStats.size(); i++) {
//                try {
//                    UsageStats appUsageStats = topAppUsageStats.get(i);
//                    ApplicationInfo appInfo = packageManager.getApplicationInfo(appUsageStats.getPackageName(), 0);
//                    AppInfo newInfo = new AppInfo();
//                    newInfo.appName = appInfo.loadLabel(packageManager).toString();
//                    newInfo.packageName = appInfo.packageName;
//
//                    // TODO：通过反射获取 launchcount
////                    newInfo.launchCount = 1;
//
//                    apps.add(newInfo);
//                } catch (Exception e) {
//                    WXLogUtils.e(e.getMessage());
//                }
//            }
//
//        } else {
//            Intent permissionIntent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
//            startActivity(mWXSDKInstance.getContext(), permissionIntent, null);
//        }

        return apps;
    }

    private List<UsageStats> sortAppUsageStatsByLaunchCount(List<UsageStats> appUseageStats) {
//        return Collections.sort(appUseageStats);
        return appUseageStats;
    }
}

//class LaunchCountComparator implements Comparator<UsageStats> {
//    public final int compare(UsageStats a, UsageStats b) {
//        // return by descending order
//        return b.launchCount - a.launchCount;
//    }
//}
