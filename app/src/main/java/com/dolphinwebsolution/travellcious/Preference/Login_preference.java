package com.dolphinwebsolution.travellcious.Preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Login_preference {
    public static SharedPreferences mPrefs;
    public static SharedPreferences.Editor prefsEditor;

   ///Logindata
   public static void setcustomer_id(Context context, String value)
   {
       mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
       prefsEditor = mPrefs.edit();
       prefsEditor.putString("customer_id", value);
       prefsEditor.commit();
   }
    public static String getcustomer_id(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("customer_id", "");
    }
    public static void setEmailid(Context context, String value)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString("email", value);
        prefsEditor.commit();
    }
    public static String getEmailid(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("email", "");
    }

    public static void setlang(Context context, String value)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString("lang", value);
        prefsEditor.commit();
    }
    public static String getlang(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("lang", "");
    }
    public static void setloginflag(Context context, String value)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefsEditor = mPrefs.edit();
        prefsEditor.putString("flag", value);
        prefsEditor.commit();
    }
    public static String getloginflag(Context context)
    {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return mPrefs.getString("flag", "");
    }

}
