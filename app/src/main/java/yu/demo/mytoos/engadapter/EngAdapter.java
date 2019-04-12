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
        String edText = " <string name=\"space_member_content_hit_count\">%s个成员，%s笔记，%s次浏览</string>\n" +
                "    <string name=\"space_check_tip\">空间站审核中...</string>\n" +
                "    <string name=\"space_the_station_cannot_share\">该空间站不允许分享或邀请</string>\n" +
                "    <string name=\"space_member_content_count\">成员 %s   笔记 %s</string>\n" +
                "    <string name=\"word_king\">站长</string>\n" +
                "    <string name=\"space_king_right_with_rule\">站长权益与规范</string>\n" +
                "    <string name=\"space_report_king\">投诉站长</string>\n" +
                "    <string name=\"space_admin\">联合站长</string>\n" +
                "    <string name=\"space_admin_right_with_rule\">站长权益与规范</string>\n" +
                "    <string name=\"space_station_card\">空间站名片</string>\n" +
                "    <string name=\"space_station_rule\">空间站权限</string>\n" +
                "    <string name=\"space_mgr_dynamic_show_new\">动态内显示空间站更新</string>\n" +
                "    <string name=\"space_mgr_only_aite_you_can_display_alter_close\">关闭后，只有@你的消息才能显示在动态内</string>\n" +
                "    <string name=\"space_mgr_exit_station\">退出空间站</string>\n" +
                "    <string name=\"space_mgr_my_card\">我的名片</string>\n" +
                "    <string name=\"space_mgr_the_station_card_tip\">在这里可以设置你在这个空间站的名片，这个名片会在此空间站内显示</string>\n" +
                "    <string name=\"space_mgr_join_type_any\">自由加入方式</string>\n" +
                "    <string name=\"space_mgr_join_type_rule\">需要审批</string>\n" +
                "    <string name=\"space_mgr\">空间站管理</string>\n" +
                "    <string name=\"space_mgr_join_type\">成员加入方式</string>\n" +
                "    <string name=\"space_mgr_join_comment\">成员加入自动回复</string>\n" +
                "    <string name=\"space_mgr_allow_out_search\">允许空间站外用户搜寻内容</string>\n" +
                "    <string name=\"space_mgr_can_be_search_recommend_ahter_open\">开启后，可被搜索、推荐，浏览部分主题</string>\n" +
                "    <string name=\"space_mgr_set_publish_rule\">设置发表权限</string>\n" +
                "    <string name=\"space_mgr_member_list\">成员列表</string>\n" +
                "    <string name=\"space_mgr_open_member_list\">开放空间站成员列表</string>\n" +
                "    <string name=\"space_mgr_normal_member_cannot_see_other_member\">关闭后，普通成员无法看到空间站内成员列表</string>\n" +
                "    <string name=\"space_mgr_allow_private_chat\">允许成员私聊</string>\n" +
                "    <string name=\"space_mgr_member_join_need_check\">成员加入需要审批</string>\n" +
                "    <string name=\"space_mgr_auto_comment\">自动通知</string>\n" +
                "    <string name=\"space_mgr_join_member_will_recever\">开启后，新加入的用户将会收到你设置的通知</string>\n" +
                "    <string name=\"space_mgr_who_can_publish\">谁可以发表 笔记</string>\n" +
                "    <string name=\"space_mgr_anybody\">所有人</string>\n" +
                "    <string name=\"space_mgr_only_king\">仅站长</string>\n" +
                "    <string name=\"space_mgr_king_and_admin\">站长和联合站长</string>\n" +
                "    <string name=\"space_mgr_member_default_order\">默认排序</string>\n" +
                "    <string name=\"space_mgr_member\">空间站成员</string>\n" +
                "    <string name=\"space_member_chat\">聊天</string>\n" +
                "    <string name=\"space_mgr_member_say_limit_msg\">禁言后，该成员将无法在空间站内进行任何互动和发言，包括发表话题、回复、点赞、群聊等</string>\n" +
                "    <string name=\"word_1_days\">1天</string>\n" +
                "    <string name=\"word_3_days\">3天</string>\n" +
                "    <string name=\"word_7_days\">7天</string>\n" +
                "    <string name=\"space_member_forever_limit\">永久禁言</string>\n" +
                "    <string name=\"space_mgr_cancel_admin\">取消联合站长</string>\n" +
                "    <string name=\"space_mgr_set_admin\">设为联合站长</string>\n" +
                "    <string name=\"space_mgr_member_quiet\">禁言</string>\n" +
                "    <string name=\"space_mgr_cancel_member_quiet\">解除禁言</string>\n" +
                "    <string name=\"space_mgr_remove_member\">踢出空间站</string>\n" +
                "    <string name=\"space_mgr_confirm_cancel_admin\">确认要取消联合站长吗？</string>\n" +
                "    <string name=\"space_mgr_modify_cannot_revoke\">修改不可撤回</string>\n" +
                "    <string name=\"space_mgr_remove_member_tip\">是否保留该成员的发言（包括发布的主题和回复）？</string>\n" +
                "    <string name=\"space_mgr_remove_with_clear_content\">踢出且删除所有发言</string>\n" +
                "    <string name=\"space_mgr_remove_with_keep_content\">踢出但保留发言</string>";
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

