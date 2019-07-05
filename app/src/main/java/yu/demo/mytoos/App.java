package yu.demo.mytoos;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import yu.demo.mytoos.utils.LogUtils;

public class App extends Application {
    @Override
    public void onCreate () {
        super.onCreate();
        LeakCanary.install(this);
        LogUtils.v("yu.demo.mytoos.App", ""+"onCreate");
    }
}
