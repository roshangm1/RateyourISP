package roshan.info.np.rateyourisp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import roshan.info.np.rateyourisp.fragments.ISPList;
import roshan.info.np.rateyourisp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new ISPList()).commit();

    }

}
