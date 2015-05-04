package com.bolex.jiaomimi;

import com.bolex.sqlhelper.mysqlhelper;

import bean.commitbean;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class commit extends Activity {

	Spinner spinner;
	Button commitbt;
	EditText biaotiname, cotentedit;
	private long exitTime = 0; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commit);
		inte();

		commitbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				commitbean cmb = new commitbean();
				cmb.setTitleB(biaotiname.getText().toString());
				cmb.setContentB(cotentedit.getText().toString());
				cmb.setMoodB(spinner.getSelectedItem().toString());
				if (biaotiname.getText().toString().equals("")||cotentedit.getText().toString().equals("")
						||spinner.getSelectedItem().toString().equals("")) {
				Toast.makeText(commit.this,"傻vv,日记当然要写完整，不然下次怎么看？", 9000).show();
				
				}
				else{
					Toast.makeText(commit.this,"保存成功", 3000).show();
					mysqlhelper mysql = new mysqlhelper(commit.this, "mydiary.db",
							null, 1);
					mysql.inset(mysql.getWritableDatabase(), cmb.getTitleB(), cmb.getContentB(), cmb.getMoodB());
		        /*  Intent in = new Intent(commit.this,MainActivity.class );
					startActivity(in);   //写日记页面启动
*/					 commit.this.finish();
				}
			}
		});

	}

	private void inte() {
		spinner = (Spinner) findViewById(R.id.spinner1);
		biaotiname = (EditText) findViewById(R.id.biaotiname);
		cotentedit = (EditText) findViewById(R.id.cotentedit);
		commitbt = (Button) findViewById(R.id.commitbut);

	}
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Intent intent=new Intent(getApplicationContext(),ImageLockActivity.class);
		intent.putExtra("view", "commit");
		startActivity(intent);
		
	}
	  public boolean onKeyDown(int keyCode, KeyEvent event) {  
	        if (keyCode == KeyEvent.KEYCODE_BACK  
	                && event.getAction() == KeyEvent.ACTION_DOWN) {  
	  
	            if ((System.currentTimeMillis() - exitTime) > 2000) {  
	                Toast.makeText(getApplicationContext(), "再按一次退出程序",  
	                        Toast.LENGTH_SHORT).show();  
	                exitTime = System.currentTimeMillis();  
	            } else {  
	            	Intent intent=new Intent(commit.this,ImageLockActivity.class);
					startActivity(intent);
	                finish();  
	              
	            }  
	            return true;  
	        }  
	        return super.onKeyDown(keyCode, event);  
	    }   
	                 

}
