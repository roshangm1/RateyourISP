package roshan.info.np.rateyourisp.extras;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;


public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {

        mInstance=this;
        super.onCreate();
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
