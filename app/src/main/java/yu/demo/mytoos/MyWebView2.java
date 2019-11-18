package yu.demo.mytoos;

import android.content.Context;
import android.net.http.SslError;
import android.os.Environment;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;


//import com.tencent.smtt.sdk.WebSettings;

/**
 * Created by yuxiangxin on 17/5/9
 * 描述: 抽取了常规设置方法的WebView
 */
public class MyWebView2 extends WebView {

    public MyWebView2 (Context context) {
        this(context, null);
    }

    public MyWebView2 (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


    public WebSettings initWebViewSettings () {
        return initWebViewSettings(true);
    }

    public WebSettings initWebViewSettings (boolean cache) {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        //webSetting.setDatabasePath();
        //webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);

        webSetting.setAppCachePath(Environment.getDataDirectory().getPath());
        if (cache) {
            webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
            webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

            webSetting.setAppCacheEnabled(true);
            webSetting.setDatabaseEnabled(true);
            webSetting.setDomStorageEnabled(true);
            webSetting.setGeolocationEnabled(true);
            webSetting.setAppCacheMaxSize(1024 * 1024 * 1024);
            webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        return webSetting;
    }

    public static void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
        doSslError(handler);
    }


    private static void doSslError (SslErrorHandler handler) {
        if (handler != null) {
            handler.proceed();
        }
    }
}
