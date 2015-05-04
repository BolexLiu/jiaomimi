package com.bolex.jiaomimi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Wecome extends Activity {
	private Handler handler=new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
		setContentView(R.layout.wecome);
		handler.postDelayed(new Runnable() {
			
			
			@Override
			public void run() {
				Intent intent=new Intent(Wecome.this,ImageLockActivity.class);
				startActivity(intent);
				finish();
			}
		}, 4000);
	}
}
