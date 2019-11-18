package yu.demo.mytoos.checkupdate;

public class APP {
    private String versionName;
    private long versionCode;
    private long updateTime;

    public String getVersionName () {
        return versionName;
    }

    public void setVersionName (String versionName) {
        this.versionName = versionName;
    }

    public long getVersionCode () {
        return versionCode;
    }

    public void setVersionCode (long versionCode) {
        this.versionCode = versionCode;
    }

    public long getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (long updateTime) {
        this.updateTime = updateTime;
    }
}
