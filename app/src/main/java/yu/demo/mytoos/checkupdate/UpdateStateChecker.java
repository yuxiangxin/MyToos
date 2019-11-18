package yu.demo.mytoos.checkupdate;

import java.util.ArrayList;
import java.util.List;

import yu.demo.mytoos.utils.LogUtils;

public class UpdateStateChecker {

    private static final String TAG = "UpdateStateChecker";

    public static void main (String[] args) {
        List<ICheck> multiChannelCheck = new ArrayList<>();
        multiChannelCheck.add(new QQUpdateStateCheckImpl());
        //multiChannelCheck.add(new XiaoMiUpdateStateCheckImpl());
        for (ICheck iCheck : multiChannelCheck) {
            LogUtils.v(TAG, "" + iCheck.getUpdateState());
        }
    }
}
