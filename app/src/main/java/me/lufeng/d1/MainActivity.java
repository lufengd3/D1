package me.lufeng.d1;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.taobao.weex.IWXRenderListener;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.WXRenderStrategy;

public class MainActivity extends AppCompatActivity implements IWXRenderListener {
    WXSDKInstance mWXSDKInstance;
    BroadcastReceiver wxReceiver;

    String bundleUrl = "http://lfzy.space/js/index.bundle.min.js";
//    String bundleUrl = "http://30.10.92.193:9999/js/index.bundle.js";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.render();
    }

    private void render() {
        mWXSDKInstance = new WXSDKInstance(this);
        mWXSDKInstance.registerRenderListener(this);
        mWXSDKInstance.renderByUrl(this.bundleUrl, this.bundleUrl, null, null,WXRenderStrategy.APPEND_ASYNC);

        this.listenBroadcast();
    }

    public void reload() {
        if (mWXSDKInstance != null) {
            mWXSDKInstance.destroy();
            mWXSDKInstance = null;

            this.render();
        }
    }

    private void listenBroadcast() {
        IntentFilter broadcastIntentFilter = new IntentFilter();
        broadcastIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        broadcastIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        broadcastIntentFilter.addDataScheme("package");

        wxReceiver = new WxReceiver(mWXSDKInstance);
        this.registerReceiver(wxReceiver, broadcastIntentFilter);
    }

    @Override
    public void onViewCreated(WXSDKInstance instance, View view) {
        setContentView(view);
        Log.v("WXSample", "on view created");
    }
    @Override
    public void onRenderSuccess(WXSDKInstance instance, int width, int height) {
        Log.v("WXSample", "on render success");
    }
    @Override
    public void onRefreshSuccess(WXSDKInstance instance, int width, int height) {
        Toast.makeText(mWXSDKInstance.getContext(), "reload success", Toast.LENGTH_LONG).show();

        this.reload();
    }
    @Override
    public void onException(WXSDKInstance instance, String errCode, String msg) {
        Log.v("WXSample", "on exception");
        Log.v("WXSample", msg);
    }
    @Override
    protected void onResume() {
        overridePendingTransition(0,0);
        super.onResume();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityResume();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityPause();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(mWXSDKInstance!=null){
            mWXSDKInstance.onActivityStop();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mWXSDKInstance!=null) {
            mWXSDKInstance.onActivityDestroy();
        }

        this.unregisterReceiver(wxReceiver);
    }
}
