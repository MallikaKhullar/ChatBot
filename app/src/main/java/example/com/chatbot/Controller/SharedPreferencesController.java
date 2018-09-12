package example.com.chatbot.Controller;

import android.content.Context;
import android.content.SharedPreferences;

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

    private enum PrefsTypes {TRACKERS} //all different bundles of prefs stored

    //Holder for all strings used
    public static class Str {
        public static String currentLevel = "currentLevel";

    }
}

