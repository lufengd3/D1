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

import java.lang.reflect.Field;
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
            case "important":
                apps = this.getFrequentlyApps();
                break;
            default:
                apps = this.getAllApps();
                break;
        }

        return apps;
    }

    @JSMethod(uiThread = false)
    public List getAppByName(String name) {
        PackageManager packageManager = mWXSDKInstance.getContext().getPackageManager();

        List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);

        List<AppInfo> res = new ArrayList<AppInfo>();
        for (int i=0; i<apps.size(); i++) {
            ApplicationInfo p = apps.get(i);
            WXLogUtils.e("hello " + p.loadLabel(packageManager).toString() + "   " + p.packageName);
        }

        return res;
    }

    @JSMethod(uiThread = false)
    public void runApp(String packageName) {
        PackageManager packageManager = mWXSDKInstance.getContext().getPackageManager();
        Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
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

        List<ApplicationInfo> apps = packageManager.getInstalledApplications(0);

        List<AppInfo> res = new ArrayList<AppInfo>();
        for (int i=0; i<apps.size(); i++) {
            ApplicationInfo p = apps.get(i);
            if (packageManager.getLaunchIntentForPackage(p.packageName) != null) {
                AppInfo newInfo = new AppInfo();
                newInfo.appName = p.loadLabel(packageManager).toString();
                newInfo.packageName = p.packageName;

                res.add(newInfo);
            }
        }

        return res;
    }

    private List getFrequentlyApps() {
        Context appContext = mWXSDKInstance.getContext();
        List apps = new ArrayList();

        boolean granted = PermissionManager.checkUsagePermission(appContext);
        if (!granted) return apps;

        UsageStatsManager usageManager = (UsageStatsManager) appContext.getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, -1);

        List<UsageStats> allAppUsageStats = usageManager.queryUsageStats(usageManager.INTERVAL_WEEKLY, cal.getTimeInMillis(), System.currentTimeMillis());
//            allAppUsageStats = this.sortAppUsageStatsByLaunchCount(allAppUsageStats);
//            List<UsageStats> topAppUsageStats = allAppUsageStats.subList(0, 3);

        PackageManager packageManager = appContext.getPackageManager();
        List<String> packageNameList = new ArrayList();
        for (int i = 0; i < allAppUsageStats.size(); i++) {
            try {
                UsageStats appUsageStats = allAppUsageStats.get(i);
                Class c = appUsageStats.getClass();
                ApplicationInfo appInfo = packageManager.getApplicationInfo(appUsageStats.getPackageName(), 0);
                // TODO: usagestatus 返回的内容有重复
//                if (packageNameList.contains(appInfo.packageName)) {
//                    continue;
//                } else {
//                    packageNameList.add(appInfo.packageName);
//                }

                if (packageManager.getLaunchIntentForPackage(appInfo.packageName) != null) {
                    AppInfo newInfo = new AppInfo();
                    newInfo.appName = appInfo.loadLabel(packageManager).toString();
                    newInfo.packageName = appInfo.packageName;

                    // @hide API，需要通过反射获取
                    Field launchCountField = c.getDeclaredField("mLaunchCount");
                    launchCountField.setAccessible(true);

                    Field totalTimeInForegroundField = c.getDeclaredField("mTotalTimeInForeground");
                    totalTimeInForegroundField.setAccessible(true);

                    newInfo.launchCount = launchCountField.getInt(appUsageStats);
                    newInfo.usageTime = totalTimeInForegroundField.getLong(appUsageStats);
                    newInfo.activeScore = newInfo.launchCount * newInfo.usageTime;
                    newInfo.lastTimeUsed = appUsageStats.getLastTimeUsed();

                    apps.add(newInfo);
                }
            } catch (Exception e) {
                WXLogUtils.e(e.getMessage());
            }
        }

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
