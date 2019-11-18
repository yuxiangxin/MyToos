package yu.demo.mytoos.checkupdate;

import java.text.SimpleDateFormat;

public class UpdateState {
    private String version;
    private long versionCode;
    private String updateTime;
    private String name;

    public String getUpdateTime () {
        return updateTime;
    }

    public void setUpdateTime (String updateTime) {
        this.updateTime = updateTime;
    }

    public UpdateState (String name) {
        this.name = name;
    }

    public long getVersionCode () {
        return versionCode;
    }

    public void setVersionCode (long versionCode) {
        this.versionCode = versionCode;
    }


    public void setLastTime (long lastTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        setUpdateTime(simpleDateFormat.format(lastTime));
    }

    public String getVersion () {
        return version;
    }

    public void setVersion (String version) {
        this.version = version;
    }

    public String getName () {
        return name;
    }

    public boolean isOk () {
        return versionCode >= 0;
    }

    @Override
    public String toString () {
        return "UpdateState{" +
                "name='" + name + '\'' +
                ", updateTime=" + updateTime +
                ", version='" + version + '\'' +
                ", versionCode=" + versionCode +
                '}';
    }
}
