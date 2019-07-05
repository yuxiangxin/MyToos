package yu.demo.mytoos.engadapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import yu.demo.mytoos.fast.utils.IOUtil;

public class EngForIos {

    public static final String egPath = "C:\\Users\\Administrator\\Desktop\\eng\\en\\strings.xml";

    public static final String zhPath = "C:\\Users\\Administrator\\Desktop\\eng\\zh\\strings.xml";

    public static final String egTargetName = "strings4ios.xml";
    //"<string name=\"app_name\">Wowo</string>"
    //"\">.+</"
    static String matchRegex = "<string name=.+\\\">.+</";

    public static void main (String[] args) {
        toIos(egPath, egTargetName);
        toIos(zhPath, egTargetName);
    }

    public static void toIos (String srcPath, String targetName) {
        File target = new File(new File(srcPath).getParent(), targetName);
        if (target.exists()) {
            target.delete();
        }
        String egContent = null;
        try {
            egContent = IOUtil.getFileContent(new File(srcPath));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String[] lines = egContent.split("\n");
        ArrayList<String> allContent = new ArrayList<>(lines.length);
        Collections.addAll(allContent, lines);
        StringBuilder sb = new StringBuilder();
        for (String item : allContent) {
            String content = EngAdapter.matcherStr(item, matchRegex);
            if (content != null && !"".equals(content)) {
                content = content.replace("string name=", "").replace("</", "\"").replace("\">", "\" = \"").replace("<\"", "\"");
            }
            if (content == null)
                content = item;
            sb.append(content).append("\n");
        }
        System.out.print(sb.toString());
        try {
            IOUtil.writeContent(sb.toString(), target, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
