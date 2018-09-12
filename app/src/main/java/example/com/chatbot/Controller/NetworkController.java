package example.com.chatbot.Controller;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import example.com.chatbot.BuildConfig;
import example.com.chatbot.ChatBotApplication;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */


public class NetworkController {

    public static String PARAM_API_KEY = "apiKey";
    public static String PARAM_MSG = "message";
    public static String PARAM_BOT_ID = "chatBotID";
    public static String PARAM_EXT_ID = "externalID";

    public interface NetworkCallback {
        void onResponse(JSONObject response);
        void onError(VolleyError error);
    }

    public static void sendMessage(String textMsg, NetworkCallback callback) {
        JSONObject params = new JSONObject();
        try {
            params.put(PARAM_MSG, textMsg);
            params.put(PARAM_EXT_ID, BuildConfig.ExtId);
            params.put(PARAM_BOT_ID, BuildConfig.BotId);
        } catch (JSONException e) {
            // TODO - log error msg
            e.printStackTrace();
        }

        addNewRequest(Request.Method.GET, Router.sendMessage(), params, callback);
    }


    public static void addNewRequest(int method, String url, JSONObject params, final NetworkCallback callback){
        params = updateParams(PARAM_API_KEY, BuildConfig.ApiKey, params);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(method,
                url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(callback!=null) callback.onResponse(response);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(callback!=null) callback.onError(error);
            }
        });

        ChatBotApplication.getInstance().addToRequestQueue(jsonObjReq);
        // TODO - add request to JSON Q here
    }

    /**
     * Builds upon the JSON request (adds the API key, ChatBot key etc)
     * @param key
     * @param val
     * @param params
     * @return
     */
    static JSONObject updateParams(String key, String val, JSONObject params){
        try {
            params.put(key, val);
        } catch (JSONException e) { e.printStackTrace(); }
        return params;
    }
}
