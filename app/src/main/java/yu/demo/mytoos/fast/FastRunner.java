package yu.demo.mytoos.fast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import yu.demo.mytoos.fast.utils.IOUtil;


/**
 * Created by Yu yuxiangxin on 2019/4/11
 * <p>
 * 批量修改as项目本地配置文件
 */
public class FastRunner {

    public static void main (String[] args) {
        FastRunner fastRunner = new FastRunner("C:\\Users\\Administrator\\Desktop\\ad\\demo");
        fastRunner.addTask(new BuildGradleFast())
                .addTask(new GradlePropertiesFast())
                .addTask(new GradleWrapperPropertiesFast());
        String string = UUID.randomUUID().toString();
        fastRunner.run();
    }

    private List<IFast> mRuns;
    private String mParentPath;

    public FastRunner (String parentPath) {
        this.mRuns = new ArrayList<>();
        mParentPath = parentPath;
    }

    public FastRunner addTask (IFast task) {
        mRuns.add(task);
        return this;
    }

    public void run () {
        System.out.println("FastRunner start");
        for (IFast iFast : mRuns) {
            System.out.println(String.format(Locale.getDefault(), "FastRunner run: %d/%d", mRuns.indexOf(iFast) + 1, mRuns.size()));
            File target = new File(mParentPath, iFast.getTargetPath());
            try {
                String content = IOUtil.getFileContent(target);
                if (!isEmpty(content)) {
                    String[] lines = content.split(IOUtil.RN);
                    boolean replySuccess = false;
                    for (int i = 0; i < lines.length; i++) {
                        String line = lines[i];
                        if (!isEmpty(line) && line.trim().startsWith(iFast.getModifyLineStart())) {
                            String newContent = iFast.getModifyLineStart() + iFast.getModifyLineContent();
                            if (line.startsWith(iFast.getModifyLineStart())) {
                                lines[i] = newContent;
                            } else {
                                lines[i] = line.substring(0, line.indexOf(iFast.getModifyLineStart())) + newContent;
                            }
                            replySuccess = true;
                            break;
                        }
                    }
                    if (replySuccess) {
                        System.out.println(iFast.getTargetPath() + "替换成功");
                        StringBuilder sb = new StringBuilder();
                        for (String line : lines) {
                            sb.append(line).append(IOUtil.RN);
                        }
                        IOUtil.writeContent(sb.toString(), target, false);
                        System.out.println(iFast.getTargetPath() + "重新写入成功");
                    } else {
                        System.out.println(iFast.getTargetPath() + "替换失败");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("FastRunner finish!");
    }

    private boolean isEmpty (String content) {
        return content == null || content.equals("");
    }
}
