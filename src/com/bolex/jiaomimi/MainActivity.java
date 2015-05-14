package com.bolex.jiaomimi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.bolex.sqlhelper.mysqlhelper;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	SQLiteDatabase SQLdb;
	Cursor Csr;
	SimpleCursorAdapter SCA;
	ListView mylist;
	SimpleAdapter SA;
	List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
	Button btnComm;
	mysqlhelper mysql;
	SharedPreferences spf; // 共享存储
	Editor ED; // 编辑器
	boolean lock = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		init(savedInstanceState); // 初始化
		btnComm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				lock = false;
				Intent in = new Intent(MainActivity.this, commit.class);
				startActivityForResult(in, 3);// 写日记页面启动
			}
		});
		mylist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				lock = false;
				Intent in = new Intent(MainActivity.this, rijixiangqing.class);
				Bundle b = new Bundle();
				Map<String, Object> requstmapview = lists.get(position);
				b.putString("title", (String) requstmapview.get("title"));
				b.putString("mood", (String) requstmapview.get("Mood"));
				b.putString("content", (String) requstmapview.get("content"));
				b.putString("time", (String) requstmapview.get("time"));
				in.putExtra("Myconteitem", b);
				startActivity(in);
			}

		});

	}

	private void getCursor() {
		if (Csr != null) {
			String[] columncount = Csr.getColumnNames();
			while (Csr.moveToNext()) {
				Map<String, Object> myMap = new HashMap<String, Object>();
				for (String Colsname : columncount) {
						myMap.put(Colsname,
								Csr.getString(Csr.getColumnIndex(Colsname)));
				}
				lists.add(myMap);
			}
			SA = new SimpleAdapter(getApplicationContext(), lists,
					R.layout.zibuju, new String[] { "title", "content", "Mood",
							"time", "_img" }, new int[] { R.id.tilte,
							R.id.content, R.id.mood, R.id.time, R.id.myimg });
		SA.setViewBinder(new ViewBinder() { //必须重写该方法才能让list上加载bitmap，因为list默认只认识id。
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					if(view instanceof ImageView && data instanceof Bitmap){  
				          ImageView i = (ImageView)view;  
				          i.setImageBitmap((Bitmap) data);  
				          return true;  
				      }
					return false;
				}
			});
			mylist.setAdapter(SA);
		}
	}

	private void init(Bundle savedInstanceState) {
		mylist = (ListView) findViewById(R.id.mylist);
		btnComm = (Button) findViewById(R.id.fabiao);
		super.onCreate(savedInstanceState);
		mysql = new mysqlhelper(MainActivity.this, "mydiary.db", null, 1); // 初始化数据库

		SQLdb = mysql.getWritableDatabase();
		Csr = mysql.select(SQLdb);
		getCursor();
		spf = this.getPreferences(MODE_PRIVATE);
		ED = spf.edit();
		ED.putInt("num", spf.getInt("num", 0) + 1);
		ED.commit();
		Toast.makeText(this, "您是第" + spf.getInt("num", 0) + "打开本应用", 9000)
				.show();
	}

	@Override
	protected void onStart() { // 重新回到视图时刷新
		super.onStart();
		lists.clear();
		Csr = mysql.select(SQLdb);
		getCursor();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			String returnedData = data.getStringExtra("view");
			Log.d("print", returnedData);
			lock = true;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (lock) {
			Intent intent = new Intent(MainActivity.this,
					ImageLockActivity.class);
			intent.putExtra("view", "commit");
			Log.e("print", "commit");
			startActivityForResult(intent, 3);
			lock = false;
		} else {
			lock = true;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		lock = false;
		finish();

		return super.onKeyDown(keyCode, event);
	}
}
