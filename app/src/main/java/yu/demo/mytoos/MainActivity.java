package yu.demo.mytoos;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import yu.demo.mytoos.explorer.ExplorerAnalyze;

public class MainActivity extends CloseableActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toA1 (View view) {
        //startActivity(new Intent(this, WebActivity.class));
        ExplorerAnalyzeActivity.toNative(this, Environment.getExternalStorageDirectory().getAbsolutePath());
    }

}
