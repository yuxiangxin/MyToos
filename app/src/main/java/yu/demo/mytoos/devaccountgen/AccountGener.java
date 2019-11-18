package yu.demo.mytoos.devaccountgen;

import com.alibaba.fastjson.JSON;

public class AccountGener {

    private static final String DECODE_ID = "com.yonglang.wowo";
    private static final String DECODE_KEY = "stky2316deeyl";

    public static void main (String[] args) {
        DevAccount devAccount = new DevAccount(
                CryptoUtil.encode("124165", DECODE_ID),
                CryptoUtil.encode("9bfe8ab52d9b424b93ca3e5699af8e68", DECODE_KEY));
        String json = JSON.toJSONString(devAccount);
        System.out.println("" + json);
        DevAccount parseObject = JSON.parseObject(json, DevAccount.class);

        System.out.println(CryptoUtil.decode(parseObject.getId(), DECODE_ID));
        System.out.println(CryptoUtil.decode(parseObject.getKey(), DECODE_KEY));
    }
}
