package roshan.info.np.firebasetestapplication;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("notif", "From: " + remoteMessage.getFrom());
        Log.d("notif", "Notification Message Body: " + remoteMessage.getNotification().getBody());

    }
}
