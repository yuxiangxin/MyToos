package yu.demo.mytoos.explorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class ExplorerAnalyze {

    private static final String TAG = "ExplorerAnalyze";

    public long getFileOrDirSize (File file) {
        long totalLength = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File file1 : file.listFiles()) {
                    totalLength += getFileOrDirSize(file1);
                }
            }
        } else {
            totalLength += file.length();
        }
        return totalLength;
    }

    public void print (String dirPath) {
        ArrayList<ExplorerBean> arrayList = new ArrayList<>();
        File file = new File(dirPath);
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File eachPath : list) {
                    ExplorerBean item = new ExplorerBean(eachPath);
                    item.setLength(getFileOrDirSize(eachPath));
                    arrayList.add(item);
                }
            }
        }
        System.out.println("文件分析完毕,开始打印..\n");
        Collections.sort(arrayList);
        for (ExplorerBean explorerBean : arrayList) {
            System.out.println(explorerBean.toString());
        }
    }

    public ArrayList<ExplorerBean> analyzeDir (String dirPath) {
        ArrayList<ExplorerBean> arrayList = new ArrayList<>();
        File file = new File(dirPath);
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            if (list != null) {
                for (File eachPath : list) {
                    ExplorerBean item = new ExplorerBean(eachPath);
                    item.setLength(getFileOrDirSize(eachPath));
                    arrayList.add(item);
                }
            }
        }
        System.out.println("文件分析完毕,开始打印..\n");
        Collections.sort(arrayList);
        return arrayList;
    }


    public static void main (String[] args) {
        new ExplorerAnalyze().print("C:\\");
    }
    /*
    文件:E:\sdk	43.06GB
文件:E:\wowoyi	7.79GB
文件:E:\sdk_tools	7.11GB
文件:E:\android_studio3.2.0	3.08GB
文件:E:\android_studio3	1.56GB
文件:E:\dev	1.35GB
文件:E:\android_studio_3.7bate	1.12GB
文件:E:\Android_Studio2	1.04GB
文件:E:\Android_Studio	790.04MB
文件:E:\eclipse	305.75MB
文件:E:\win7系统	158.93MB
文件:E:\fonttest	113.03MB
文件:E:\fonttest.rar	32.96MB
文件:E:\$RECYCLE.BIN	19.86MB
文件:E:\Program Files (x86)	2.86MB
文件:E:\import.jar	17.53KB
文件:E:\poster	1.29KB
文件:E:\tcfix	1.29KB
文件:E:\hide_identity_avatar.png	766bytes
文件:E:\System Volume Information	0bytes


文件:C:\Program Files	40.92GB
文件:C:\Users	39.86GB
文件:C:\Windows	15.07GB
文件:C:\ProgramData	5.20GB
文件:C:\Program Files (x86)	4.15GB
文件:C:\Recovery	208.80MB
文件:C:\MarkdownPad.2.4.2	67.79MB
文件:C:\$Recycle.Bin	46.84MB
文件:C:\Python27	44.49MB
文件:C:\pagefile.sys	16.00MB
文件:C:\Boot	13.92MB
文件:C:\temp	12.81MB
文件:C:\POLVB	465.55KB
文件:C:\bootmgr	374.79KB
文件:C:\RavBin	299.77KB
文件:C:\Intel	101.44KB
文件:C:\.idea	15.45KB
文件:C:\adcfg.json	9.21KB
文件:C:\BottomSelectDialog.java	5.81KB
文件:C:\NSI_DriverInstall.log	712bytes
文件:C:\CrackCaptcha.log	393bytes
文件:C:\CommonFramework	0bytes
文件:C:\dlcache	0bytes
文件:C:\Documents and Settings	0bytes
文件:C:\ESD	0bytes
文件:C:\PerfLogs	0bytes
文件:C:\System Volume Information	0bytes
     */
}
