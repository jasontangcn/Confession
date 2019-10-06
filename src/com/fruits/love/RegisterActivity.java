package com.fruits.love;

import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		Button submitBTN = (Button)this.findViewById(R.id.register_submit);
		final TextView usernameTV = (TextView)this.findViewById(R.id.register_mobile);
		final TextView passwordTV = (TextView)this.findViewById(R.id.register_password);
		
		submitBTN.setOnClickListener(new OnClickListener(){
	        public void onClick(View v) {
	        	Map<String, String> map = HttpService.register(usernameTV.getText().toString(), passwordTV.getText().toString());
	        	if(!map.isEmpty()){
	        		SessionManager sessionMgr = new SessionManager(RegisterActivity.this.getApplicationContext());
	        		sessionMgr.logged(usernameTV.getText().toString(), passwordTV.getText().toString());
	        		
	        		Intent intent = new Intent(RegisterActivity.this.getApplicationContext(), MainActivity.class);
	        		RegisterActivity.this.startActivity(intent);
	        		RegisterActivity.this.finish();
	        	}
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
