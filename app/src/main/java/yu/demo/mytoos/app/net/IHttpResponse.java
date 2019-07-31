package yu.demo.mytoos.app.net;

public interface IHttpResponse {

    void onStart (Request req);

    void onCache (Request req, Response cache);

    void onSuccess (Request req, Response cache);

    void onCancel (Request req);

    void onFailed (Request req, Response cache);

    void onCompleted (Request req);

}
