package com.fairchild.love;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ConfessionDetailActivity extends Activity {
	public static final String ICON_PATTERN = "(\\[icon:)(.+?)(\\])";
	Pattern pattern = Pattern.compile(ConfessionDetailActivity.ICON_PATTERN);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confession_detail);

		Bundle bundle = this.getIntent().getExtras();
		String from = (String) bundle.get("from");
		String said = (String) bundle.get("said");
		
		//new AlertDialog.Builder(ConfessionDetail.this).setMessage(said).create().show();
		TextView fromTV = (TextView)this.findViewById(R.id.confession__detail_from);
		TextView saidTV = (TextView)this.findViewById(R.id.confession__detail_said);
		TextView okBTN = (TextView)this.findViewById(R.id.confession_detail_ok);

		fromTV.setText(from);
		okBTN.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				ConfessionDetailActivity.this.finish();
			}
		});
		
		SpannableStringBuilder builder = new SpannableStringBuilder(said);
        Matcher matcher = pattern.matcher(said);
        while (matcher.find()) {
            int resourceId = Integer.parseInt(matcher.group(2));
            builder.setSpan(new ImageSpan(this, resourceId), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        
        saidTV.append(builder);  
	}
}
