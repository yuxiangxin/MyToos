package yu.demo.mytoos.checkupdate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yu.demo.mytoos.engadapter.net.HttpUtils;

public class _360UpdateStateCheckImpl implements ICheck {

    private static final String TAG = "_360UpdateStateCheckImpl";

    private static final String SITE_URL = "http://zhushou.360.cn/detail/index/soft_id/3136127?recrefer=SE_D_%E5%8D%A7%E5%8D%A7";

    //<td><strong>版本：</strong>2.2.3.3
    private static final String REGEX_VERSION_NAME = ">[\\d.]++";
    //<!--versioncode:456-->
    private static final String REGEX_VERSION_CODE = "versioncode:[\\d]++";
    //<!--updatetime:2019-11-15--></td>
    private static final String REGEX_UPDATE = "updatetime:[\\d-]++";

    public static final String oppo = "";

    /**
     * html
     */
    public static final String lenovo = "https://3g.lenovomm.com/redsea/com.yonglang.wowo";

    /**
     * html
     */
    public static final String uc = "https://wap.25pp.com/android/detail_6660309/";

    /**
     * unknow
     */
    public static final String baidu = "";

    public static final String googleplay = "";

    /**
     * html
     */
    public static final String appchina = "http://m.appchina.com/app/com.yonglang.wowo";

    public static String matcherStr (String text, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find())
            return m.group();
        return null;
    }

    @Override
    public UpdateState getUpdateState () {
        try {
            String html = HttpUtils.getInstance().doGet(SITE_URL);
            Document doc = Jsoup.parseBodyFragment(html);
            Element body = doc.body();
            Element elementById = body.getElementById("sdesc");
            Element baseInfoBody = elementById.getElementsByClass("base-info").get(0).getElementsByTag("tbody").get(0);
            Elements trEles = baseInfoBody.getElementsByTag("tr");
            Element target = null;
            for (Element item : trEles) {
                if (item.toString().contains("版本：")) {
                    target = item;
                }
            }
            UpdateState updateState = new UpdateState("_360");
            if (target != null) {
                String[] allLines = target.toString().split("\n");
                for (String line : allLines) {
                    if (Pattern.compile(REGEX_VERSION_NAME).matcher(line).find()) {
                        updateState.setVersion(matcherStr(line, REGEX_VERSION_NAME).replace(">", ""));
                    } else if (Pattern.compile(REGEX_VERSION_CODE).matcher(line).find()) {
                        updateState.setVersionCode(Long.valueOf(matcherStr(line, REGEX_VERSION_CODE).replace("versioncode:", "")));
                    } else if (Pattern.compile(REGEX_UPDATE).matcher(line).find()) {
                        updateState.setUpdateTime(matcherStr(line, REGEX_UPDATE).replace("updatetime:", "").replace("--", ""));
                    }
                }
            }
            return updateState;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
