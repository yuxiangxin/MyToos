package yu.demo.mytoos.engadapter;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yu.demo.mytoos.engadapter.net.Controller;
import yu.demo.mytoos.engadapter.net.SHA256Util;
import yu.demo.mytoos.fast.utils.IOUtil;

public class EngAdapter {

    public static final String stringContentRegex = "\">.+</";

    public static String matchContent (String text) {
        return matcherStr(text, stringContentRegex);
    }

    public static String matcherStr (String text, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        if (m.find())
            return m.group();
        return null;
    }

    public static String API_ID;
    public static String API_KEY;
    public static final String API_URL = "http://openapi.youdao.com/api";


    static {
        File file = new File("privateConfig.json");
        YoudaoApi youdaoApi = null;
        try {
            youdaoApi = JSON.parseObject(IOUtil.getFileContent(file), YoudaoApi.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        API_ID = youdaoApi.youdaoAppId;
        API_KEY = youdaoApi.youdaoAppKey;

    }

    public static void dpGet (final Controller api, final int index, final List<String> all) {
        if (index % 2 == 0) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (index >= all.size()) {
            System.out.println("任务结束");
            return;
        }
        final String string = all.get(index);
        String content = matchContent(string);
        if (content != null && !"".equals(content)) {
            content = content.replace("\">", "").replace("</", "");
        } else {
            System.out.println(string);
            dpGet(api, index + 1, all);
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        String salt = UUID.randomUUID().toString();
        String curtime = System.currentTimeMillis() / 1000 + "";
        params.put("q", content);
        params.put("from", "auto");
        params.put("to", "auto");
        params.put("appKey", API_ID);
        params.put("salt", salt);
        //(appKey+input+salt+curtime+密钥)
        //input=q前10个字符 + q长度 + q后十个字符（当q长度大于20）或 input=q字符串（当q长度小于等于20）。
        String input = null;
        if (content.length() <= 20) {
            input = content;
        } else {
            input = content.substring(0, 10) + content.length() + content.substring(content.length() - 10);
        }
        String sign = SHA256Util.getSHA256StrJava(API_ID + input + salt + curtime + API_KEY);
        params.put("sign", sign);
        params.put("signType", "v3");
        params.put("curtime", curtime);
        final String finalContent = content;
        final String finalContent1 = content;
        api.doGet(new Controller.CallBack() {
            @Override
            public void onSuccess (String json) {
                Resp resp = JSON.parseObject(json, Resp.class);
                onComplete(resp.getResult(finalContent1));
            }

            @Override
            public void onFail (String errorMsg) {
                onComplete(finalContent);
            }

            void onComplete (String result) {
                System.out.println(string.replace(finalContent, result));
                dpGet(api, index + 1, all);
            }
        }, appendUrl(API_URL, params, false));
    }


    public static String appendUrl (String baseUrl, Map<String, Object> map, boolean notEncodeValue) {
        if (map == null || map.size() == 0)
            return baseUrl;
        StringBuilder sb = new StringBuilder();
        sb.append(baseUrl).append("?");
        for (String key : map.keySet()) {
            Object value = map.get(key);
            value = notEncodeValue ? value : encodeValue(value + "");
            sb.append(key).append("=").append(value == null ? "" : value).append("&");
        }
        String result = sb.toString();
        if (result.endsWith("&")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }


    public static String encodeValue (String value) {
        if (value != null) {
            try {
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    static Controller mController = null;

    public static void main (String[] arg) {
        mController = new Controller();
        String edText = "<string name=\"word_un_save\">不保存</string>\n" +
                "    <string name=\"space_create_edit_config_save\">保存此次编辑？</string>\n" +
                "    <string name=\"word_comment\">回复</string>\n" +
                "    <string name=\"space_content_pic_count\">%d图</string>\n" +
                "    <string name=\"reply_total_comment_count\">总共%s条回复>></string>\n" +
                "    <string name=\"content_somecount_comments\">%d条评论</string>\n" +
                "    <string name=\"word_i\">我</string>\n" +
                "    <string name=\"word_likeed\">喜欢过</string>\n" +
                "    <string name=\"people_like_too\">人都喜欢</string>\n" +
                "    <string name=\"word_etc\">等</string>\n" +
                "    <string name=\"space_content_total_pic_count\">共%d张</string>";
        String[] split = edText.split("\n");
        ArrayList<String> allContent = new ArrayList<>();
        Collections.addAll(allContent, split);
        dpGet(mController, 0, allContent);
        //doLocCode(mController);
    }

    public static void doLocCode (final Controller api) {
        String json = "[{\"code\":\"86\",\"name\":\"中国\"}}";
        List<LocCode> locCodes = JSON.parseArray(json, LocCode.class);
        doForEachLocCode(api, locCodes, 0);
    }

    public static void doForEachLocCode (final Controller api, final List<LocCode> data, final int index) {
        if (index % 2 == 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (index >= data.size()) {
            System.out.println("任务结束");
            System.out.println(JSON.toJSONString(data));
            return;
        }
        final LocCode locCode = data.get(index);
        doTranslate(api, locCode.getName(), new TranslateCallBack() {
            @Override
            void onComplete (String result) {
                locCode.setEngName(result);
                doForEachLocCode(api, data, index + 1);
            }
        });
    }

    public static void doTranslate (Controller api, String content, Controller.CallBack call) {
        HashMap<String, Object> params = new HashMap<>();
        String salt = UUID.randomUUID().toString();
        String curtime = System.currentTimeMillis() / 1000 + "";
        params.put("q", content);
        params.put("from", "auto");
        params.put("to", "auto");
        params.put("appKey", API_ID);
        params.put("salt", salt);
        //(appKey+input+salt+curtime+密钥)
        //input=q前10个字符 + q长度 + q后十个字符（当q长度大于20）或 input=q字符串（当q长度小于等于20）。
        String input = null;
        if (content.length() <= 20) {
            input = content;
        } else {
            input = content.substring(0, 10) + content.length() + content.substring(content.length() - 10);
        }
        String sign = SHA256Util.getSHA256StrJava(API_ID + input + salt + curtime + API_KEY);
        params.put("sign", sign);
        params.put("signType", "v3");
        params.put("curtime", curtime);
        api.doGet(call, appendUrl(API_URL, params, false));
    }

    public static class LocCode {
        private String name;
        private String code;
        private String engName;

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getCode () {
            return code;
        }

        public void setCode (String code) {
            this.code = code;
        }

        public String getEngName () {
            return engName;
        }

        public void setEngName (String engName) {
            this.engName = engName;
        }
    }


    abstract static class TranslateCallBack implements Controller.CallBack {

        @Override
        public void onSuccess (String json) {
            Resp resp = JSON.parseObject(json, Resp.class);
            onComplete(resp.getResult("Def"));
        }

        @Override
        public void onFail (String errorMsg) {
            onComplete("ERROR");
        }

        abstract void onComplete (String result);
    }

    public static class Resp {
        private List<String> translation;

        public List<String> getTranslation () {
            return translation;
        }

        public void setTranslation (List<String> translation) {
            this.translation = translation;
        }

        public String getResult (String def) {
            return translation != null && !translation.isEmpty() ? translation.get(0) : def;
        }
    }


}

