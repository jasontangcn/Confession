package com.fruits.love;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		//TODO: XXX
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		
		Button loginBTN = (Button)this.findViewById(R.id.login_submit);
		Button registerBTN = (Button)this.findViewById(R.id.login_register);
		final TextView usernameTV = (TextView)this.findViewById(R.id.login_username);
		final TextView passwordTV = (TextView)this.findViewById(R.id.login_password);
		
		loginBTN.setOnClickListener(new OnClickListener(){
	        public void onClick(View v) {
	        	Map<String,String> response = HttpService.login(usernameTV.getText().toString(), passwordTV.getText().toString());
	        	if(!response.isEmpty()) {
	        		SessionManager sessionMgr = new SessionManager(LoginActivity.this.getApplicationContext());
	        		sessionMgr.logged(usernameTV.getText().toString(), passwordTV.getText().toString());
	        		sessionMgr.putStringPref(Constants.MAPKEY_JSESSIONID, (String)response.get(Constants.MAPKEY_JSESSIONID));
	        		
	        		Intent intent = new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class);
	        		LoginActivity.this.startActivity(intent);
	        		LoginActivity.this.finish();
	        	}else{
	        		
	        	}
	        }
		});
		
		registerBTN.setOnClickListener(new OnClickListener(){
	        public void onClick(View v) {
        		Intent intent = new Intent(LoginActivity.this.getApplicationContext(), RegisterActivity.class);
        		LoginActivity.this.startActivity(intent);
        		LoginActivity.this.finish();
	        }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
