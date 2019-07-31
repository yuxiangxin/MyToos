package yu.demo.mytoos.app.net;

public class Request {

    private int action;
    private String id;

    public enum CacheMode{
        LOAD_DEFAULT,
        LOAD_NORMAL,
        LOAD_CACHE_ELSE_NETWORK,
        LOAD_NO_CACHE,
        LOAD_CACHE_ONLY
    }

}
