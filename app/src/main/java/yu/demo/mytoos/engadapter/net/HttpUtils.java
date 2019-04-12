package yu.demo.mytoos.engadapter.net;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Created by Yu xiangxin on 2016/7/8 - 18:19.
 * 
 * @desc ${TODD}
 */
public class HttpUtils {

	private static final String TAG = "HttpUtils";

	private static HttpUtils mInstance;
	private final OkHttpClient mOkHttpClient;

	private HttpUtils() {
		mOkHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
				.writeTimeout(5, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build();
	}

	public static HttpUtils getInstance() {
		if (mInstance == null) {
			synchronized (HttpUtils.class) {
				if (mInstance == null) {
					mInstance = new HttpUtils();
				}
			}
		}
		return mInstance;
	}

	public void doGet(String url, final Controller.CallBack callBack) {
		mOkHttpClient.newCall(new Request.Builder().url(url).build()).enqueue(new Callback() {

			@Override
			public void onFailure(Call call, IOException e) {
				if (callBack != null) {
					callBack.onFail("服务器无响应,请检查与服务器的连接" + e.getMessage());
				}
			}

			@Override
			public void onResponse(Call call, Response resp) throws IOException {
				//System.out.println("这里是响应的方法,线程是>--->" + Thread.currentThread().getName());
				if (callBack != null) {
					ResponseBody body = resp.body();
					String json = body.string();
					if (body != null)
						if(!isNullOrEmpty(json)) {
						callBack.onSuccess(json);
						//System.out.println("响应的json-->" + json);
					} else {
						callBack.onFail("服务器响应空");
					}
				}
			}

		});
	}

	public static String appendUrl(String baseUrl, Map<String, Object> map) {
		if (map == null || isNullOrEmpty(baseUrl)) {
			throw new NullPointerException("appendUrl的参数为空!");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append("&");
		for (String key : map.keySet()) {
			sb.append(key).append("=").append(map.get(key)).append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		return s;
	}

	public static String appendUrl(String baseUrl, String key, Object values) {
		if (isNullOrEmpty(baseUrl) || isNullOrEmpty(key) || (values == null)) {
			throw new NullPointerException("appendUrl的参数为空!");
		}
		StringBuilder sb = new StringBuilder();
		sb.append(baseUrl).append("?").append(key).append("=").append(values);
		return sb.toString();
	}

	public void doPost(String url, Map<String, Object> params, Controller.CallBack call) {
		OkHttpClient client = new OkHttpClient();
        FormBody.Builder paramsBuilder = new FormBody.Builder();
        for (String key : params.keySet()) {
        	paramsBuilder.add(key, params.get(key)+"");
		}
        Request request = new Request.Builder().url(url)
                .post(paramsBuilder.build()).build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String json = response.body().string();

                if (isNullOrEmpty(json)) {
                	call.onFail("服务器响应为空");
                	return;
                }
                call.onSuccess(json);
                return;
            } else {
            	call.onFail("服务器响应错误");
            	return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            call.onFail("网络连接异常~~");
        }
		
	}
	
	public static boolean isNullOrEmpty(String toTest) {
		return (toTest == null || toTest.length() == 0);
	}
}
