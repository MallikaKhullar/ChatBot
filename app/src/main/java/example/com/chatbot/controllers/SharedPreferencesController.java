package example.com.chatbot.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import example.com.chatbot.model.ChatMessageContainer;
import example.com.chatbot.model.ChatThread;

/**
 * Created by Mallika Priya Khullar on 12/09/18.
 */

public class SharedPreferencesController {

    private final static String PREF_TRACKERS = "chatBotPrefs";
    private static SharedPreferencesController instance;
    private SharedPreferences prefs_trackers;

    private SharedPreferencesController(Context context) {
        refillPrefs(context);
    }

    public static SharedPreferencesController getInstance(Context context) {
        if (instance == null) instance = new SharedPreferencesController(context);
        return instance;
    }

    private void refillPrefs(Context context) {
        prefs_trackers = getSharedPreferencesFor(PREF_TRACKERS, context);
        prefs_trackers.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            }
        });
    }


    private SharedPreferences getSharedPreferencesFor(String prefName, Context context) {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public String getString(String variable, String defaultValue) {
        return prefs_trackers.getString(variable, defaultValue);
    }

    public boolean getBoolean(String variable, boolean defaultValue) {
        return prefs_trackers.getBoolean(variable, defaultValue);
    }

    public int getInteger(String variable, int defaultValue) {
        return prefs_trackers.getInt(variable, defaultValue);
    }

    public Long getLong(String variable, Long defaultValue) {
        return prefs_trackers.getLong(variable, defaultValue);
    }

    public void resetSharedPrefs(){
        prefs_trackers.edit().clear().apply();
    }


    public void putInteger(String string, Integer val) {
        SharedPreferences.Editor editor = prefs_trackers.edit();
        editor.putInt(string, val);
        editor.apply();
    }

    public void putBoolean(String string, Boolean val) {
        SharedPreferences.Editor editor = prefs_trackers.edit();
        editor.putBoolean(string, val);
        editor.apply();
    }

    public void putString(String string, String val) {
        SharedPreferences.Editor editor = prefs_trackers.edit();
        editor.putString(string, val);
        editor.apply();
    }

    public void putLong(String string, long val) {
        SharedPreferences.Editor editor = prefs_trackers.edit();
        editor.putLong(string, val);
        editor.apply();
    }

    public void addToAllChats(ChatMessageContainer chat){
        JSONArray unsentChats = null;
        try {
            unsentChats = new JSONArray(prefs_trackers.getString(Str.allChats, "[]"));
            if(unsentChats == null || unsentChats.length() == 0) unsentChats = new JSONArray();
            unsentChats.put(chat.putToJSON());
            SharedPreferences.Editor editor = prefs_trackers.edit();
            editor.putString( Str.allChats, unsentChats.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refreshAllChats(ChatThread thread){
        JSONArray unsentChats = new JSONArray();
        for(ChatMessageContainer msg : thread.getThreads()) unsentChats.put(msg.putToJSON());
        Log.d("\n\n\n\nTIME TO REFRESH", unsentChats.toString());
        SharedPreferences.Editor editor = prefs_trackers.edit();
        editor.putString( Str.allChats, unsentChats.toString());
        editor.apply();
    }

    public ChatThread getAllChats(String chats){
        JSONArray allChats = null;
        try {
            allChats = new JSONArray(prefs_trackers.getString(chats, "[]"));

            if(allChats == null || allChats.length() == 0) allChats = new JSONArray();
            return ChatThread.getFromJSON(allChats);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ChatThread();
        }
    }

    private JSONObject getJSONObject(String str){
        JSONObject obj = new JSONObject();
        try {
            obj = new JSONObject(getString(str,"{}"));
        } catch (JSONException e) {e.printStackTrace();}
        finally {
            return obj;
        }
    }


    private enum PrefsTypes {TRACKERS} //all different bundles of prefs stored

    //Holder for all strings used
    public static class Str {
        public static String allChats = "allChats";
    }
}

