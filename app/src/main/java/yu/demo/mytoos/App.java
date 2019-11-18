package yu.demo.mytoos;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;
import yu.demo.mytoos.utils.LogUtils;

public class App extends Application {
    private static Context sContext;

    @Override
    public void onCreate () {
        super.onCreate();
        sContext = this;
        //LeakCanary.install(this);
        LogUtils.v("yu.demo.mytoos.App", "" + "onCreate");
    }

    public static Context getContext () {
        return sContext;
    }

    @Override
    protected void attachBaseContext (Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
