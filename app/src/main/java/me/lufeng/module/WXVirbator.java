package me.lufeng.module;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

import static android.support.v4.content.ContextCompat.getSystemService;

public class WXVirbator extends WXModule {
    @JSMethod(uiThread = true)
    public void run(int ms) {
        Vibrator v = (Vibrator) mWXSDKInstance.getContext().getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(ms, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(ms);
        }
    }
}
