package yu.demo.mytoos.checkupdate;

import com.alibaba.fastjson.JSON;

import yu.demo.mytoos.engadapter.net.HttpUtils;

public class YonglangUpdateStateCheckImpl implements ICheck {

    private static final String site_url = "https://product.wowodx.com/wowoapi/api/checkVersion.json?sDeviceToken=359600060346339&sPlatform=2&sVersion=2.2.0&versionCode=400&model=android&nonce_str=1574078754991&appId=71a57676336c465b9da3328c0c7d3d1c&sign=f1a31c759fd4ecd8e76893e3cd6b9220";

    @Override
    public UpdateState getUpdateState () {
        try {
            String respJson = HttpUtils.getInstance().doGet(site_url);
            YonglangUpdate yonglangUpdate = JSON.parseObject(respJson, YonglangUpdate.class);

            if ("0".equals(yonglangUpdate.code) && yonglangUpdate.data != null) {
                YonglangUpdate.DataBean data = yonglangUpdate.data;
                UpdateState updateState = new UpdateState("yonglang");
                updateState.setVersionCode(data.versionCode);
                updateState.setVersion(data.number.replace("v", ""));
                updateState.setLastTime(data.time);
                return updateState;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class YonglangUpdate {
        private String code;
        private DataBean data;

        public String getCode () {
            return code;
        }

        public void setCode (String code) {
            this.code = code;
        }

        public DataBean getData () {
            return data;
        }

        public void setData (DataBean data) {
            this.data = data;
        }

        public static class DataBean {

            private String number;
            private long time;
            private int versionCode;

            public String getNumber () {
                return number;
            }

            public void setNumber (String number) {
                this.number = number;
            }

            public long getTime () {
                return time;
            }

            public void setTime (long time) {
                this.time = time;
            }

            public int getVersionCode () {
                return versionCode;
            }

            public void setVersionCode (int versionCode) {
                this.versionCode = versionCode;
            }
        }
    }
}
