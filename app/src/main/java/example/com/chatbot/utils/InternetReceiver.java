package example.com.chatbot.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class InternetReceiver extends BroadcastReceiver {

    public interface InternetCallback{
        void internetBack();
    }

    InternetCallback callback;

    public InternetReceiver(InternetCallback callback) {
        this.callback = callback;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                callback.internetBack();
            }
//            else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
//                callback.internetLost();
//            }
        }
    }
}