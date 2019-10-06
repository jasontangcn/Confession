package com.fruits.love;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
	public static final String SHARED_PREFERENCES = "shared_preference";
	public static final String REMEMBER_CREDENTIAL = "remember_credential";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public static boolean getBoolean(Context ctx, String key) {
		return ctx.getSharedPreferences(PreferenceUtil.SHARED_PREFERENCES, RegisterActivity.MODE_PRIVATE).getBoolean(key, false);
	}
	
	public static String getString(Context ctx, String key) {
		return ctx.getSharedPreferences(PreferenceUtil.SHARED_PREFERENCES, RegisterActivity.MODE_PRIVATE).getString(key, null);
	}
	
	public static void putBoolean(Context ctx, String key, boolean value) {
		ctx.getSharedPreferences(PreferenceUtil.SHARED_PREFERENCES, RegisterActivity.MODE_PRIVATE).edit().putBoolean(key, value);
	}
	
	public static void putString(Context ctx, String key, String value) {
		ctx.getSharedPreferences(PreferenceUtil.SHARED_PREFERENCES, RegisterActivity.MODE_PRIVATE).edit().putString(key, value);
	}
}
