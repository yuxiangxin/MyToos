package yu.demo.mytoos.checkupdate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import yu.demo.mytoos.engadapter.net.HttpUtils;

public class HuaweiUpdateStateCHeckImpl implements ICheck {

    public static final String SITE_URL = "https://appstore.huawei.com/app/C10381944";
    private static final String TAG = "Huawei";

    // <span>2.2.3.2</span> </li>
    private static final String REGEX_VERSION_NAME = ">[\\d.]++<";
    //2019-11-16
    private static final String REGEX_UPDATE = ">[\\d-]++<";

    @Override
    public UpdateState getUpdateState () {
        try {
            String s = HttpUtils.getInstance().doGet(SITE_URL);
            Document parse = Jsoup.parse(s);
            Elements eles = parse.body().getElementsByClass("ul-li-detail");
            UpdateState huawei = new UpdateState("huawei");
            for (Element ele : eles) {
                String version = _360UpdateStateCheckImpl.matcherStr(ele.toString(), REGEX_VERSION_NAME);
                if (version != null) {
                    huawei.setVersion(version.replace(">", "").replace("<", ""));
                } else {
                    String date = _360UpdateStateCheckImpl.matcherStr(ele.toString(), REGEX_UPDATE);
                    if (date != null) {
                        huawei.setUpdateTime(date.replace(">", "").replace("<", ""));
                    }
                }
            }
            return huawei;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
