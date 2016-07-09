package roshan.info.np.rateyourisp.activities;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import roshan.info.np.rateyourisp.R;

public class AboutUs extends AppCompatActivity {

    BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        bottomBar = BottomBar.attach(this,savedInstanceState);
        bottomBar.setItems(R.menu.bottom_menu);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if(menuItemId==R.id.about_us) {

                } else {

                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

        bottomBar.mapColorForTab(0, "#f44336");
        bottomBar.mapColorForTab(1, "#ff4081");
    }
}
