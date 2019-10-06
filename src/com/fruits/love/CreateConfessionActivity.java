package com.fruits.love;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class CreateConfessionActivity extends Activity {
	//TODO: XXX
	public static int[] iconIds = new int[] {
		R.drawable.v033_24, R.drawable.v035_24, R.drawable.v038_24,
		R.drawable.v054_24, R.drawable.v081_24, R.drawable.v083_24,
		R.drawable.v090_24, R.drawable.v094_24, R.drawable.v136_24,
		R.drawable.v142_24, R.drawable.v143_24};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_confession);
		
		ImageView selectIcon = (ImageView)this.findViewById(R.id.confession_select_icon);
		
		final EditText toET = (EditText)this.findViewById(R.id.confession_mobile);
		final EditText saidET = (EditText)this.findViewById(R.id.confession_said);
		Button submit = (Button)this.findViewById(R.id.confession_submit);
		
		selectIcon.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				final Dialog iconDialog = new Dialog(CreateConfessionActivity.this);
				GridView gridView = createIconGridView();
				iconDialog.setContentView(gridView);
				iconDialog.setTitle("Select Icons");
				gridView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(), iconIds[position]);
						ImageSpan imageSpan = new ImageSpan(CreateConfessionActivity.this, bitmap);
						String iconId = String.valueOf(iconIds[position]);
						SpannableString spannableString = new SpannableString("[icon:" + iconId + "]");
						spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						saidET.append(spannableString);
						iconDialog.dismiss();
						//iconDialog.show();
					}
				});
				iconDialog.show();
			}	
		});	
		
		final SessionManager sessionMgr = new SessionManager(CreateConfessionActivity.this.getApplicationContext());
		
		submit.setOnClickListener(new OnClickListener(){
	        public void onClick(View v) {
	        	Map<String, String> response = HttpService.createConfession(sessionMgr.getStringPref(Constants.MAPKEY_USERNAME), toET.getText().toString(), saidET.getText().toString(),sessionMgr.getStringPref(Constants.MAPKEY_JSESSIONID));
	        	if(!response.isEmpty()){
	        		
	        		Intent intent = new Intent(CreateConfessionActivity.this.getApplicationContext(), MainActivity.class);
	        		CreateConfessionActivity.this.startActivity(intent);
	        		CreateConfessionActivity.this.finish();
	        	}
	        }
		});
		
	}
	
	private GridView createIconGridView() {
		LayoutInflater layout = LayoutInflater.from(this);
		GridView gridView = (GridView)layout.inflate(R.layout.icon_grid_view, null);
		
		List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
		for(int iconId : iconIds) {
	        Map<String,Object> item = new HashMap<String,Object>();
			item.put("image", iconId);
			items.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.icon_grid_item, new String[]{"image"}, new int[]{R.id.icon_grid_item_icon});
		gridView.setAdapter(adapter);
		gridView.setNumColumns(6);
		gridView.setBackgroundColor(Color.rgb(214, 211, 214));
		gridView.setHorizontalSpacing(1);
		gridView.setVerticalSpacing(1);
		gridView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		gridView.setGravity(Gravity.CENTER);
		return gridView;
	}
}

//new AlertDialog.Builder(Confession.this).setMessage(saysomething.getText().toString()).create().show();