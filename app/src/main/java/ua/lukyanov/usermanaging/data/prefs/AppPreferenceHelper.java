package ua.lukyanov.usermanaging.data.prefs;

import android.content.Context;

public class AppPreferenceHelper extends PreferencesHelper {
    public static boolean isUserAuthorized(Context context) {
        return contains(context, Prefs.PREF_KEY_TOKEN);
    }

    public static void setLogin(Context context, String login) {
        setString(context, Prefs.PREF_KEY_LOGIN, login);
    }

    public static String getLogin(Context context) {
        return getString(context, Prefs.PREF_KEY_LOGIN, null);
    }

    public static boolean containsLogin(Context context) {
        return contains(context, Prefs.PREF_KEY_LOGIN);
    }

    public static void deleteLogin(Context context) {
        remove(context, Prefs.PREF_KEY_LOGIN);
    }

    public static void setToken(Context context, String token) {
        setString(context, Prefs.PREF_KEY_TOKEN, token);
    }

    public static String getToken(Context context) {
        return getString(context, Prefs.PREF_KEY_TOKEN, null);
    }

    public static boolean containsToken(Context context) {
        return contains(context, Prefs.PREF_KEY_TOKEN);
    }

    public static void deleteToken(Context context) {
        remove(context, Prefs.PREF_KEY_TOKEN);
    }

    public static void setUserObjectId(Context context, String userObjectId) {
        setString(context, Prefs.PREF_KEY_USER_OBJECT_ID, userObjectId);
    }

    public static String getUserObjectId(Context context) {
        return getString(context, Prefs.PREF_KEY_USER_OBJECT_ID, null);
    }

    public static boolean containsUserObjectId(Context context) {
        return contains(context, Prefs.PREF_KEY_USER_OBJECT_ID);
    }

    public static void deleteUserObjectId(Context context) {
        remove(context, Prefs.PREF_KEY_USER_OBJECT_ID);
    }

}
