package com.fruits.love;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
	private static final String SESSION_MANAGER = "session_manager";
	private static final String LOGGED = "logged";
	
	SharedPreferences pref;
	Context ctx;
	
	public SessionManager(Context context) {
		ctx = context;
		pref = context.getSharedPreferences(SESSION_MANAGER, context.MODE_PRIVATE);
	}

	public void logged(String name, String password) {
		Editor editor = pref.edit();
		editor.putBoolean(LOGGED, true);
		editor.putString(Constants.MAPKEY_USERNAME, name);
		editor.putString(Constants.MAPKEY_PASSWORD, password);
		editor.commit();
	}
	
	public void checkLogged() {
		if (!this.isLogged()) {
			Intent intent = new Intent(ctx, LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			ctx.startActivity(intent);
		}
	}
	
	public HashMap<String, String> getUser() {
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(Constants.MAPKEY_USERNAME, pref.getString(Constants.MAPKEY_USERNAME, null));
		return user;
	}

	public void logout() {
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
		
		Intent intent = new Intent(ctx, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		ctx.startActivity(intent);
	}

	public void putStringPref(String key, String value) {
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public String getStringPref(String key) {
		return pref.getString(key, null);
	}
	
	public void removePref(String key) {
		pref.edit().remove(key);
	}
	
	public boolean isLogged() {
		boolean loggedInAndroidAndHttpServer = false;
		boolean logged = pref.getBoolean(LOGGED, false);
		if(logged) {
			String jsessionid = this.getStringPref(Constants.MAPKEY_JSESSIONID);
			boolean isAlive = HttpService.isAlive(jsessionid);
			Log.d("[SessionManager.isAlive]", "JSESSIONID:  " + jsessionid + ", isAlive: " + isAlive);
			
			if(!isAlive) {
				this.removePref(Constants.MAPKEY_JSESSIONID);
				String username = this.getStringPref(Constants.MAPKEY_USERNAME);
				String password = this.getStringPref(Constants.MAPKEY_PASSWORD);
				Map response = HttpService.login(username, password);
				if(!response.isEmpty()){
	        		this.logged(username, password);
	        		this.putStringPref(Constants.MAPKEY_JSESSIONID, (String)response.get(Constants.MAPKEY_JSESSIONID));
	        		loggedInAndroidAndHttpServer = true;
				}
			}else{
				loggedInAndroidAndHttpServer = true;
			}
		}
		
		return loggedInAndroidAndHttpServer;
	}
}
