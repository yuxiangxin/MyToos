package yu.demo.mytoos;

import android.os.Bundle;

import java.util.LinkedList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import yu.demo.mytoos.closeres.CloseableResHelp;
import yu.demo.mytoos.utils.LogUtils;

public abstract class CloseableActivity extends AppCompatActivity {

    private LinkedList<Object> mCloseableRes;
    protected String TAG = getTAG();

    public String getTAG () {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.v(TAG, "" + "onCreate");
    }

    public <T> T putCloseableRes (T obj) {
        if (mCloseableRes == null) {
            mCloseableRes = new LinkedList<Object>();
        }
        mCloseableRes.add(obj);
        return obj;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        LogUtils.v(TAG, "" + "onDestroy");
        if (mCloseableRes != null) {
            CloseableResHelp.closeRes(mCloseableRes);
        }
    }
}
