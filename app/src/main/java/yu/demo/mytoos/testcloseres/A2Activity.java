package yu.demo.mytoos.testcloseres;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import java.lang.ref.WeakReference;

import yu.demo.mytoos.CloseableActivity;
import yu.demo.mytoos.R;
import yu.demo.mytoos.utils.LogUtils;

public class A2Activity extends CloseableActivity {
    MyAsyncTask mExecute;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);
    }


    public void addRes (View view) {
        mExecute = new MyAsyncTask(this);
        mExecute.execute();
    }

    static class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<A2Activity> mA2Activity;

        private String getTAG () {
            return mA2Activity != null && mA2Activity.get() != null ? mA2Activity.get().getTAG() : "close";
        }

        public MyAsyncTask (A2Activity a2Activity) {
            mA2Activity = new WeakReference<>(a2Activity);
        }

        @Override
        protected void onPreExecute () {
            super.onPreExecute();
            LogUtils.v(getTAG(), "" + "onPreExecute");
        }

        @Override
        protected void onPostExecute (Void aVoid) {
            super.onPostExecute(aVoid);
            LogUtils.v(getTAG(), "" + "onPostExecute");
        }

        @Override
        protected Void doInBackground (Void... voids) {
            LogUtils.v(getTAG(), "" + "doInBackground");
            SystemClock.sleep(10 * 1000);
            return null;
        }


    }


    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (mExecute != null) {
            mExecute.cancel(true);
            mExecute = null;
        }
    }
}
