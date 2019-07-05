package yu.demo.mytoos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yu.demo.mytoos.testcloseres.A1Activity;

public class MainActivity extends CloseableActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toA1 (View view) {
        startActivity(new Intent(this, A1Activity.class));
    }

}
