package com.fairchild.love;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
		ImageView confessionsReceivedIV = (ImageView)this.findViewById(R.id.main_confessions_received);
	    
		final SessionManager sessionMgr = new SessionManager(MainActivity.this.getApplicationContext());
		sessionMgr.checkLogged();
		
		confessionsReceivedIV.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ConfessionsReceivedActivity.class);
				MainActivity.this.startActivity(intent);	
			}
		});
		
		ImageView confession = (ImageView)this.findViewById(R.id.main_confession);
		confession.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, CreateConfessionActivity.class);
				MainActivity.this.startActivity(intent);	
			}
		});
		
		Button logout = (Button)this.findViewById(R.id.main_logout);
		logout.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
		    	SessionManager sessionMgr = new SessionManager(MainActivity.this.getApplicationContext());
		    	HttpService.logout(sessionMgr.getStringPref(Constants.MAPKEY_JSESSIONID));
				sessionMgr.logout();
			}
		});
	}
}
