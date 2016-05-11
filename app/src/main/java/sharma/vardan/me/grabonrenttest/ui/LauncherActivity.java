package sharma.vardan.me.grabonrenttest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //move to product List
    startActivity(ProductListActivity.createIntent(this));
    finish();
  }
}
