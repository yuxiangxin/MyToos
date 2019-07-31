package yu.demo.mytoos.engadapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import yu.demo.mytoos.fast.utils.IOUtil;

public class EngForIos {

    public static final String egPath = "E:\\wowoyi\\wowo2.0\\app\\src\\main\\res\\values-en\\strings.xml";

    public static final String zhPath = "E:\\wowoyi\\wowo2.0\\app\\src\\main\\res\\values\\strings.xml";

    public static final String targetPath = "C:\\Users\\Administrator\\Desktop\\xml4ios";

    public static final String egTargetName = "strings4ios.xml";
    //"<string name=\"app_name\">Wowo</string>"
    //"\">.+</"
    static String matchRegex = "<string name=.+\\\">.+</";

    public static void main (String[] args) {
        toIos(egPath, "en_"+egTargetName);
        toIos(zhPath, "zh_"+egTargetName);
    }

    public static void toIos (String srcPath, String targetName) {
        File target = new File(targetPath, targetName);
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
