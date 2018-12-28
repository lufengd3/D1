package me.lufeng.d1;

import android.widget.Toast;

import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

public class WallPaper  extends WXModule {

    //run ui thread
    @JSMethod(uiThread = false)
    public void update(String msg) {
        Toast.makeText(mWXSDKInstance.getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    //run JS thread
    @JSMethod (uiThread = false)
    public void fireEventSyncCall(){
        //implement your module logic here
    }
}