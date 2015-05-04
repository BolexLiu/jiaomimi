package com.bolex.jiaomimi;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.view.NinePointLineView;

public class ImageLockActivity extends Activity {

    NinePointLineView NPLV;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,  
                WindowManager.LayoutParams. FLAG_FULLSCREEN); 
      //  View v=new NinePointLineView(this);
        setContentView(R.layout.activity_image_lock);
        NPLV=(NinePointLineView) findViewById(R.id.NPLV) ;
    }
    public void jinru(View v) {
    	
    	if (NPLV.getlock().equals("03678")) {
    		Intent intent=new Intent(ImageLockActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}else {
			Toast.makeText(ImageLockActivity.this, "¥Ì¿≤¥Ì¿≤", 8000).show();
		}
	}
}
