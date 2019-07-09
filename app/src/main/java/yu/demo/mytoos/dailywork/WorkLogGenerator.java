package yu.demo.mytoos.dailywork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkLogGenerator {

    public static void main (String[] args) {
        ArrayList<WorkLog> workLogs = new ArrayList<>();
        workLogs.add(new WorkLog("38437","修改昵称页面默认显示当前用户昵称"));
        workLogs.add(new WorkLog("38405","退出空间站取消加载视图"));
        workLogs.add(new WorkLog("38387","修改头像流程更改,支持剪裁").setProgress(10));
        printWorkLog(workLogs);
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
