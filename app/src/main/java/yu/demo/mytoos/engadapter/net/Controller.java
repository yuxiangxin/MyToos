package yu.demo.mytoos.engadapter.net;

import java.util.Map;

public class Controller {

	private static final int SUCCESS = 0;
	private static final int FAIL = -1;

	private HttpUtils httpUtil;

	public Controller() {
		httpUtil = HttpUtils.getInstance();
	}

	public void doGet(CallBack call, String url) {
		httpUtil.doGet(url, call);
	}

	public void doPost(CallBack call, String url, Map<String, Object> params) {
		httpUtil.doPost(url, params, call);
	}

	public interface CallBack {

		void onSuccess (String json);

		void onFail (String errorMsg);

	}

}
