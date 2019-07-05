package yu.demo.mytoos.dailywork;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkLogGenerator {

    public static void main (String[] args) {


        ArrayList<WorkLog> workLogs = new ArrayList<>();
        workLogs.add(new WorkLog("#7871","您应看到一个生成器和一个分发服务器。"));
        workLogs.add(new WorkLog("#5454","不存在的问题。").setProgress(-1));
        workLogs.add(new WorkLog("#45","我不知道").setProgress(50));
        workLogs.add(new WorkLog("#4545","你在哪里").setProgress(0).setRemarks("待服务端完成"));


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
