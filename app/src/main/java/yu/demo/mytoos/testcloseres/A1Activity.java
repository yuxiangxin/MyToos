package yu.demo.mytoos.testcloseres;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yu.demo.mytoos.CloseableActivity;
import yu.demo.mytoos.R;

public class A1Activity extends CloseableActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);
    }

    public void toA2 (View view) {
        startActivity(new Intent(this, A2Activity.class));
    }
}
