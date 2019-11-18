package yu.demo.mytoos.devaccountgen;


/**
 * Created by yuxiangxin on 2019/11/4
 * 描述:appID&key配置
 */
public class DevAccount {

    private String id;
    private String key;

    public DevAccount () {
    }

    public DevAccount (String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getKey () {
        return key;
    }

    public void setKey (String key) {
        this.key = key;
    }
}
