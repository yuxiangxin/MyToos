package yu.demo.mytoos.devaccountgen;


/**
 * Created by yuxiangxin on 2019/11/4
 * 描述:
 */
public class CryptoUtil {

    public static String decode (String value,String key) {
        char[] snNum = new char[value.length() / 3];
        String result = "";
        for (int i = 0, j = 0; i < value.length() / 3; i++, j++) {
            if (j == key.length()) {
                j = 0;
            }
            int n = Integer.parseInt(value.substring(i * 3, i * 3 + 3));
            snNum[i] = (char) ((char) n ^ key.charAt(j));
        }
        for (int k = 0; k < value.length() / 3; k++) {
            result += snNum[k];
        }
        return result;
    }

    public static String encode (String value, String key) {
        int[] snNum = new int[value.length()];
        String result = "";
        String temp = "";

        for (int i = 0, j = 0; i < value.length(); i++, j++) {
            if (j == key.length())
                j = 0;
            snNum[i] = value.charAt(i) ^ key.charAt(j);
        }
        for (int k = 0; k < value.length(); k++) {

            if (snNum[k] < 10) {
                temp = "00" + snNum[k];
            } else {
                if (snNum[k] < 100) {
                    temp = "0" + snNum[k];
                }else{
                    temp = "" + snNum[k];
                }
            }
            result += temp;
        }
        return result;
    }
}