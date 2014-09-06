package com.fairchild.love;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class ConfessionsReceivedActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confessions_received_grid_view);
		
		GridView gridView = (GridView)this.findViewById(R.id.confessions_received);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				Map<String,String> map = (Map<String,String>)adapter.getItemAtPosition(position);
		        Intent intent = new Intent();  
	            intent.setClass(ConfessionsReceivedActivity.this, ConfessionDetailActivity.class);
	            intent.putExtra("from", map.get("from"));
	            intent.putExtra("said", map.get("said"));
	            ConfessionsReceivedActivity.this.startActivity(intent); 
			}
		});
		
		
		List<Map<String,String>> items = new ArrayList<Map<String,String>>();
		try{
    		SessionManager sessionMgr = new SessionManager(ConfessionsReceivedActivity.this.getApplicationContext());
    		Map<String,String> response = HttpService.confessionsReceived(sessionMgr.getStringPref(Constants.MAPKEY_USERNAME),sessionMgr.getStringPref(Constants.MAPKEY_JSESSIONID));
			String responseText = response.get("responseText");
			
			JSONObject jsonObject = new JSONObject(new String(responseText));
			Log.d("xxxxxxxxx", jsonObject.toString());
			Log.d("yyyyyyyyyyyyyy", jsonObject.getJSONArray("list").toString());
			
			JSONArray jsonArray = jsonObject.getJSONArray("list");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				String from = item.getString("from");
				String said = item.getString("said");
				Map<String, String> map = new HashMap<String, String>();
				map.put("from", from);
				map.put("said", said);
				items.add(map);
			}
		}catch(Exception exc){
			exc.printStackTrace();
		}


		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.confessions_received_grid_item, new String[]{"from"}, new int[]{R.id.confessions_received_grid_item_mobile});
		gridView.setAdapter(adapter);
		gridView.setNumColumns(2);
		gridView.setPadding(16, 16, 16, 16);
		/*
		gridView.setBackgroundColor(Color.rgb(214, 211, 214));
		gridView.setHorizontalSpacing(1);
		gridView.setVerticalSpacing(1);
		gridView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		*/
		gridView.setGravity(Gravity.CENTER);
	}	
}
