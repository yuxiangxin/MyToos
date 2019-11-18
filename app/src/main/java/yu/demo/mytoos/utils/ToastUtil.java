package yu.demo.mytoos.utils;

import android.content.Context;
import android.widget.Toast;


import yu.demo.mytoos.App;

/**
 * Created by 余香鑫 on 16/8/9.
 */

public class ToastUtil {

    private static Toast mToast;

    /**
     * 不会产生新的Toast对象的Toast  多次连续的Toast时只是刷新文字,
     * <p>
     * 只适用系统的Toast
     */
    public static void refreshToast (Context context, String content) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_LONG);
        }
        if (content != null && mToast != null) {
            mToast.setText(content);
            mToast.show();
        }
    }

    public static void refreshToast (String content) {
        refreshToast(App.getContext(), content);
    }


    public static void refreshToast (Context context, int res) {
        refreshToast(context, context.getResources().getString(res));
    }

}
