package yu.demo.mytoos;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import yu.demo.mytoos.utils.ToastUtil;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private MyWebView2 mWebView;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;


    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        assignViews();

        initWeb();

    }

    private void initWeb () {
        WebSettings webSettings = mWebView.initWebViewSettings();
        String htmlContent = readAssetsFileContent(this, "demo.html");
        mWebView.loadDataWithBaseURL("https://www.wowodx.com/", htmlContent, "text/html", "UTF-8", "");
        mWebView.addJavascriptInterface(this, "wst");
    }

    @android.webkit.JavascriptInterface
    public void startFunction (String current) {
        ToastUtil.refreshToast("startFunction 有参:" + current);
    }

    @android.webkit.JavascriptInterface
    public void startFunction () {
        ToastUtil.refreshToast("startFunction 无参");
    }


    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.btn1:
                mWebView.loadUrl("javascript:javacalljs()");
                break;
            case R.id.btn2:
                //mWebView.loadUrl("javascript:javacalljswithargs(onClick)");
                mWebView.loadUrl("javascript:javacalljswithargs('" + "onClick"+ "')");
                break;
            case R.id.btn3:
                break;
        }
    }

    private void assignViews () {
        mWebView = findViewById(R.id.web_view);
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
    }

    public static String readAssetsFileContent (Context context, String fileName) {
        if (TextUtils.isEmpty(fileName))
            return null;
        try {
            InputStream inStream = context.getAssets().open(fileName);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inStream.read(bytes)) > 0) {
                outStream.write(bytes, 0, len);
            }
            return outStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
