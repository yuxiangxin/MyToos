package yu.demo.mytoos.checkupdate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import yu.demo.mytoos.engadapter.net.HttpUtils;

public class MeizuUpdateStateCheckImpl implements ICheck {

    private static final String TAG = "MeizuUpdateStateCheckImpl";
    private static final String SITE_URL = "http://app.meizu.com/apps/public/detail?package_name=com.yonglang.wowo";

    // 2.2.3.3
    private static final String REGEX_VERSION_NAME = "> +[\\d.]++ +<";
    //2019-11-16
    private static final String REGEX_UPDATE = "[\\d-]++";

    @Override
    public UpdateState getUpdateState () {
        try {
            String html = HttpUtils.getInstance().doGet(SITE_URL);
            Document doc = Jsoup.parseBodyFragment(html);
            Element theme_content = doc.body().getElementById("wrapper").getElementById("theme_content");
            String content = theme_content.getElementsByTag("ul").get(0).toString();
            content = content.replace("\n", "").replace("<li>", "").replace("</li> ", "\n");
            UpdateState meizu = new UpdateState("meizu");
            for (String s : content.split("\n")) {
                String version = _360UpdateStateCheckImpl.matcherStr(s, REGEX_VERSION_NAME);
                if (version != null) {
                    meizu.setVersion(version.replace(">", "").replace("<", "").replace(" ", ""));
                } else {
                    String time = _360UpdateStateCheckImpl.matcherStr(s, REGEX_UPDATE);
                    if (time != null && time.contains("-")) {
                        meizu.setUpdateTime(time);
                    }
                }
            }
            return meizu;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
