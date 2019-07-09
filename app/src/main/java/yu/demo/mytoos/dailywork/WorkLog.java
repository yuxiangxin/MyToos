package yu.demo.mytoos.dailywork;

import java.util.Locale;

public class WorkLog {

    //* 星球-空间站列表增加未读内容数和摘要 [#37460](http://r.yonglang.co/issues/37460) `90%` `待服务端支持`

    private String id;
    private String name;
    //0 未开始 100 完成 -1 进行中
    private int progress = 100;
    private String remarks;
    private boolean fromGitLib = false;

    public WorkLog () {
    }

    public boolean isFromGitLib () {
        return fromGitLib;
    }

    public WorkLog setFromGitLib (boolean fromGitLib) {
        this.fromGitLib = fromGitLib;
        return this;
    }

    public WorkLog (String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId () {
        return id;
    }

    public WorkLog setId (String id) {
        this.id = id;
        return this;
    }

    public String getName () {
        return name;
    }

    public WorkLog setName (String name) {
        this.name = name;
        return this;
    }

    public int getProgress () {
        return progress;
    }

    public WorkLog setProgress (int progress) {
        this.progress = progress;
        return this;
    }

    public String getRemarks () {
        if (remarks == null)
            return "";
        return "`" + remarks + "`";
    }

    public WorkLog setRemarks (String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getProgressString () {
        //0 未开始 100 完成
        String progressStr = null;
        if (progress == 100) {
            progressStr = "`已完成`";
        } else if (progress == 0) {
            progressStr = "`未开始`";
        } else if (progress == -1) {
            progressStr = "`进行中`";
        } else {
            progressStr = "`" + progress + "%`";
        }
        return progressStr;
    }

    @Override
    public String toString () {
        String temp = fromGitLib ? "* %s [#%s](http://g.wowodx.com/yans/wowoandroid-androidstudio/issues/%s) %s %s" :
                "* %s [#%s](http://r.yonglang.co/issues/%s) %s %s";
        return String.format(Locale.getDefault(), temp, name, id, id, getProgressString(), getRemarks());
    }
}
