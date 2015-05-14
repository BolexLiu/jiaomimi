package com.bolex.jiaomimi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class rijixiangqing extends Activity {
	TextView titleview, timeview, moodview, contentview;
	Bundle b;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.rijixiangqing);
		init();
		titleview.setText(b.getString("title"));
		timeview.setText(b.getString("time"));
		moodview.setText(b.getString("mood"));
		contentview.setText(b.getString("content"));
	}

	private void init() {
		Intent in = getIntent();
		b = in.getBundleExtra("Myconteitem");
		titleview = (TextView) findViewById(R.id.titleview);
		timeview = (TextView) findViewById(R.id.timeview);
		moodview = (TextView) findViewById(R.id.moodview);
		contentview = (TextView) findViewById(R.id.contentview);
	};	
}
