package fr.aston.fragment.ui.meteo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {

    private static final String PREF_CITY = "city";

    private static SharedPreferences getPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    //getCity
    //setCity

    public static String getCity(Context context){
        return getPreference(context).getString(PREF_CITY, null);
    }

    // Preference.getCity(CONTEXT -> getContext)

    public static void setCity(Context context, String city){
        getPreference(context).edit().putString(PREF_CITY, city).commit();
    }

    // Preference.setCity(getContex, "Paris")

}
