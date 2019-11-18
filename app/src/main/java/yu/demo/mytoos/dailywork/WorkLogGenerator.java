package yu.demo.mytoos.dailywork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkLogGenerator {

    public static void main (String[] args) {
        ArrayList<WorkLog> workLogs = new ArrayList<>();

        workLogs.add(WorkLog.format("ok:#40121 我的书架支持搜索"));
        workLogs.add(WorkLog.format("ok:#40149 个人首页增加聊天入口"));
        workLogs.add(WorkLog.format("ok:#40116 阅读器章节文案统一"));
        workLogs.add(WorkLog.format("start:#40065 空间站动态详情页增加评论弹幕").setProgress(10));

        printWorkLog(workLogs);


        //System.out.println("39094".length() << 1);

        //#38492 关于卧卧,卧卧号主页,任务详情页分享缓存加载优化
        //发布动态文字2个英文或数字算一个长度
        //2.知识宇宙、Up青年支持下拉刷新
        //3.推送通知取消默认震动提醒
        //"ok:#38951 个人中心增加微信/qq登陆"
    }

    public static void printWorkLog (List<WorkLog> workLogs) {
        //
        //`2019年7月4日`
        //#### 进行中

        StringBuilder sb = new StringBuilder();
        String head = String.format("`%s`\n #### 进行中\n", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
        sb.append(head);

        for (WorkLog workLog : workLogs) {
            sb.append(workLog.toString()).append("\n");
        }

        System.out.println("" + sb.toString());

    }


}
