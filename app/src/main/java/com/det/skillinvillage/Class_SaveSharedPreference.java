package com.det.skillinvillage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Class_SaveSharedPreference {
    static final String PREF_USER_NAME = "username";

    static final String PREF_TECHSUPPORTEMAIL = "email_techsuport";
    static final String PREF_TECHSUPPORTPHONE = "mobile_techsuport";

    static final String PREF_FLAG_USERMANUAL = "flag_usermanual";

    static final String PREF_MENU_link = "menu_link";


    static final String PREF_USER_MAILID= "user_mailID";
    static final String PREF_Flag_For_CustomGallery= "customgallery_flag";

    static final String PREF_RoleName= "user_roleName";


    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }


    public static void setSupportEmail(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TECHSUPPORTEMAIL, email);
        editor.commit();
    }

    public static String getSupportEmail(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_TECHSUPPORTEMAIL, "");
    }


    public static void setSupportPhone(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TECHSUPPORTPHONE, email);
        editor.commit();
    }

    public static String getSupportPhone(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_TECHSUPPORTPHONE, "");
    }


    public static void setPrefFlagUsermanual(Context ctx, String flag) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_FLAG_USERMANUAL, flag);
        editor.commit();
    }

    public static String getPrefFlagUsermanual(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_FLAG_USERMANUAL, "");
    }


    //PREF_MENU_link
    public static void setPREF_MENU_link(Context ctx, String flag) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_MENU_link, flag);
        editor.commit();
    }

    public static String getPREF_MENU_link(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_MENU_link, "");
    }


    public static void setUserMailID(Context ctx, String usermailid)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_MAILID, usermailid);
        editor.commit();
    }

    public static String getUsermailID(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_MAILID, "");
    }

    public static void setPREF_Flag_For_CustomGallery(Context ctx, String userName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_Flag_For_CustomGallery, userName);
        editor.apply();
    }

    public static String getPREF_Flag_For_CustomGallery(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_Flag_For_CustomGallery, " ");
    }


    public static String getPREF_RoleName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_RoleName, " ");
    }

    
    public static void setPREF_RoleName(Context ctx, String user_roleName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_RoleName, user_roleName);
        editor.apply();
    }
}