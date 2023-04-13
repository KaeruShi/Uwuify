package kaerushi.weeabooify.uwuify.config;

import android.content.Context;
import android.content.SharedPreferences;

import kaerushi.weeabooify.uwuify.Weeabooify;

public class Prefs {

    static SharedPreferences pref = Weeabooify.getAppContext().getSharedPreferences(Weeabooify.getAppContext().getPackageName(), Context.MODE_PRIVATE);
    private static final String SharedPref = "kaerushi.weeabooify.uwuify";
    static SharedPreferences.Editor editor = pref.edit();


    // Save sharedPref config
    public static void savePrefBool(Context context, String key, boolean val) {
        SharedPreferences pref = context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static void savePrefInt(Context context, String key, int val) {
        SharedPreferences pref = context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public static void savePrefSettings(Context context, String key, String val) {
        SharedPreferences pref = context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, val);
        editor.apply();
    }

    // Load sharedPref config
    public static boolean loadPrefBool(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }

    public static int loadPrefInt(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        return pref.getInt(key, 0);
    }

    public static String loadPrefSettings(Context context, String key) {
        SharedPreferences pref = context.getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        return pref.getString(key, "null");
    }

    // Get Boolean
    public static boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean val) {
        editor.putBoolean(key, val).apply();
    }

    // Clear one specific sharedPref config
    public static void clearPref(String key) {
        editor.remove(key).apply();
    }

    // Clear all sharedPref config
    public static void clearAllPrefs() {
        editor.clear().apply();
    }
}
