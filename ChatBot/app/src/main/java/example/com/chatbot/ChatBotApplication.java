package example.com.chatbot;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */
public class ChatBotApplication extends Application {

    public static final String TAG = ChatBotApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static ChatBotApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized ChatBotApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    // The following functions only if I need to cancel request

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}