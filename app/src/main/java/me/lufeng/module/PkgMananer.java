package me.lufeng.module;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import java.util.ArrayList;
import java.util.List;

public class PkgMananer extends WXModule {
    @JSMethod(uiThread = false)
    public List getApps() {
        PackageManager packageManager = mWXSDKInstance.getContext().getPackageManager();
        List<PackageInfo> apps = packageManager.getInstalledPackages(0);

        List<AppInfo> res = new ArrayList<AppInfo>();
        for(int i=0;i<apps.size();i++) {
            PackageInfo p = apps.get(i);

            AppInfo newInfo = new AppInfo();
            newInfo.appName = p.applicationInfo.loadLabel(packageManager).toString();
            newInfo.packageName = p.packageName;
            res.add(newInfo);
        }

        return res;
    }
}
