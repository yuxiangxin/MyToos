package yu.demo.mytoos.demo;


import java.util.Locale;

public class Test {

    public static void main (String[] args) {
        String url = "https://photo.wowodx.com/spacestation/show/image/12200039239/99520465-bf72-48d5-8327-0165e1191247.png";

        System.out.println(url);
        String indexFor = ".wowodx.com/";
        int i = url.indexOf(indexFor);
        if (i != -1) {
            url = safeSubString(url, i+indexFor.length(), url.length());
        }
        System.out.println(url +"  "+ i);
    }

    public static String safeSubString (String string, int start, int end) {
        if (end > string.length() || end <= 0)
            return string;
        return string.substring(start, end);
    }

    private static void measeJialiDun () {
        float cur = 955F * 12;
        float app = 0.00F;
        int year = 30;
        float varCur = cur;
        float total = 0;
        for (int i = 1; i <= year; i++) {
            varCur = calcValue(varCur, app);
            System.out.println(String.format(Locale.getDefault(), "第%d年需要%f", i, varCur));
            total += varCur;
        }
        System.out.println(String.format(Locale.getDefault(), "%d年共需要%f", year, total));
    }

    private static float calcValue (float cur, float appreciation) {
        return cur + cur * appreciation;
    }
}
