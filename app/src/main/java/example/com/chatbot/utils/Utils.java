package example.com.chatbot.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import example.com.chatbot.ChatBotApplication;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class Utils {
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) ChatBotApplication
                        .getInstance()
                        .getApplicationContext()
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
