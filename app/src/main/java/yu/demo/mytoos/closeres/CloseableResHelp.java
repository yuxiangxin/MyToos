package yu.demo.mytoos.closeres;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.TimerTask;

public class CloseableResHelp {
    public static void closeRes (LinkedList<Object> res) {
        if (res != null && !res.isEmpty()) {
            for (Object item : res) {
                if (item == null)
                    continue;
                if (item instanceof AsyncTask) {
                    if (!((AsyncTask) item).isCancelled()) {
                        ((AsyncTask) item).cancel(true);
                    }
                } else if (item instanceof TimerTask) {
                    ((TimerTask) item).cancel();
                }
                item = null;
            }
            res.clear();
        }
        res = null;
    }
}
