package roshan.info.np.rateyourisp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import roshan.info.np.rateyourisp.R;
import roshan.info.np.rateyourisp.fragments.ISPList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_main));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder,new ISPList()).commit();

    }

}
