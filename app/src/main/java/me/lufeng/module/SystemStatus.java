package me.lufeng.module;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;
import com.taobao.weex.utils.WXLogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class SystemStatus extends WXModule {
    private final static String TAG = "SCREEN_TIME";
    private static final int TYPE_SINCE_CHARGED = 0x1;
    private static final int TYPE_SINCE_UNPLUGED = 0x2;

    @JSMethod(uiThread = false)
    public String getScreenTime(int type) {
        try {
            Process process = Runtime.getRuntime().exec("dumpsys batterystats");
            int wF = process.waitFor();
            Log.d(TAG, "wF is :" + wF);
            BufferedReader inputreader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = null;
            int which = -1;
            while ((line = inputreader.readLine()) != null) {
                Log.d(TAG, "line is " + line);
                if (line.startsWith("Statistics since last charge")) {
                    which = TYPE_SINCE_CHARGED;
                    continue;
                } else if (line.startsWith("Statistics since last unplugged")) {
                    which = TYPE_SINCE_UNPLUGED;
                    continue;

                }
                if (line.contains("Screen on")) {
                    String tmp[] = line.split(",");
                    switch (which & type) {
                        case TYPE_SINCE_CHARGED:
                            return tmp[0];
                        case TYPE_SINCE_UNPLUGED:
                            return tmp[0];
                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "unknown time";
    }

    @JSMethod(uiThread = false)
    public HashMap<String, Double> getNetworkLocation() {
        Context context = mWXSDKInstance.getContext();
        HashMap<String, Double> locationInfo = new HashMap<String, Double>();

        if (PermissionManager.checkNetworkLocationPermission(context)) {
            Location location = null;
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            //获取最后的network定位信息
            location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locationInfo.put("latitude", location.getLatitude());
            locationInfo.put("longitude", location.getLongitude());
        }

        return locationInfo;
    }

    @JSMethod(uiThread = false)
    public HashMap<String, String> getHardwareStatus() {
        Context context = mWXSDKInstance.getContext();
        HashMap<String, String> hardwareStatus = new HashMap<String, String>();
        long megaByte = 1024 * 1024;
        long gigabyte = 1024 * megaByte;

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        hardwareStatus.put("availableMemory", String.valueOf(mi.availMem / megaByte));
        hardwareStatus.put("totalMemory", String.valueOf(mi.totalMem / megaByte));

        long storageFreeSpace = Environment.getDataDirectory().getFreeSpace();
        long storageTotalSpace = Environment.getDataDirectory().getTotalSpace();
        hardwareStatus.put("availableStorage", String.valueOf(storageFreeSpace / gigabyte));
        hardwareStatus.put("totalStorage", String.valueOf(storageTotalSpace / gigabyte));

        return hardwareStatus;
    }
}
