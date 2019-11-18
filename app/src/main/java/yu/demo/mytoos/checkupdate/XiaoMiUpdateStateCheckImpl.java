package yu.demo.mytoos.checkupdate;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import yu.demo.mytoos.engadapter.net.HttpUtils;

public class XiaoMiUpdateStateCheckImpl implements ICheck {

    private static final String SITE_URL = "https://app.market.xiaomi.com/apm/app/package/com.yonglang.wowo?os=1.1.1&model=unknown&ro=unknown&marketVersion=1914102&imei=cfcd208495d565ef66e7dff9f98764da&miuiBigVersionName=unknown&resolution=1080*1920&webResVersion=0&clientId=cfcd208495d565ef66e7dff9f98764da&densityScaleFactor=3&co=CN&pageConfigVersion=0&session=cfcd208495d565ef66e7dff9f98764da&deviceType=0&la=zh&sdk=19&mobileWeb=1&newUser=true&page=0&combine=1&h5=1&supportSlide=0&ref=search&refs=";
    private static final String TAG = "XiaoMiUpdateStateCheckImpl";

    @Override
    public UpdateState getUpdateState () {
        try {
            String resp = HttpUtils.getInstance().doGet(SITE_URL);
            XiaomiAppParse xiaomiAppParse = JSON.parseObject(resp, XiaomiAppParse.class);
            return xiaomiAppParse != null ? xiaomiAppParse.coverUpdateState() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class XiaomiAppParse {
        private APP app;

        public UpdateState coverUpdateState () {
            UpdateState updateState = new UpdateState("xiaomi");
            if (app != null) {
                updateState.setLastTime(app.getUpdateTime());
                updateState.setVersion(app.getVersionName());
                updateState.setVersionCode(app.getVersionCode());
            }
            return updateState;
        }

        public APP getApp () {
            return app;
        }

        public void setApp (APP app) {
            this.app = app;
        }

    }
}
