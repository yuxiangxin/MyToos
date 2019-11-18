package yu.demo.mytoos.checkupdate;

import com.alibaba.fastjson.JSON;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import yu.demo.mytoos.engadapter.net.HttpUtils;

public class QQUpdateStateCheckImpl implements ICheck {

    public static final String url = "https://a.app.qq.com/o/simple.jsp?pkgname=com.yonglang.wowo&channel=0002160650432d595942&fromcase=60001";
    private static final String TAG = "QQUpdateStateCheckImpl";

    public static final String SCRIPT_REGEX = "^<script>";

    @Override
    public UpdateState getUpdateState () {
        try {
            String resp = HttpUtils.getInstance().doGet(url);
            Document parse = Jsoup.parse(resp);
            Elements script = parse.getElementsByTag("script");
            Element target = null;
            for (Element element : script) {
                if (element.toString().contains("appDetail") && _360UpdateStateCheckImpl.matcherStr(element.toString(), SCRIPT_REGEX) != null) {
                    target = element;
                }
            }

            if (target != null) {
                String s = _360UpdateStateCheckImpl.matcherStr(target.toString(), "\\{.+\\}");
                App app = JSON.parseObject(s, App.class);
                UpdateState qq = new UpdateState("QQ");
                qq.setLastTime(app.getAppDetail().publishTime);
                qq.setVersion(app.appDetail.versionName);
                qq.setVersionCode(app.appDetail.versionCode);
                return qq;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class App {
        private Detail appDetail;

        public Detail getAppDetail () {
            return appDetail;
        }

        public void setAppDetail (Detail appDetail) {
            this.appDetail = appDetail;
        }

        public static class Detail {
            private String versionName;
            private int versionCode;
            private long publishTime;

            public String getVersionName () {
                return versionName;
            }

            public void setVersionName (String versionName) {
                this.versionName = versionName;
            }

            public int getVersionCode () {
                return versionCode;
            }

            public void setVersionCode (int versionCode) {
                this.versionCode = versionCode;
            }

            public long getPublishTime () {
                return publishTime;
            }

            public void setPublishTime (long publishTime) {
                this.publishTime = publishTime;
            }
        }

    }
}
