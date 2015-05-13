package com.bolex.jiaomimi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
	SharedPreferences spf; // ����洢
	Editor ED; // �༭��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		init(savedInstanceState); // ��ʼ��
		btnComm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent in = new Intent(MainActivity.this, commit.class);
				startActivity(in); // д�ռ�ҳ������
			}
		});
		mylist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent in= new Intent(MainActivity.this,rijixiangqing.class);
				Bundle b=new Bundle();
				Map<String, Object> requstmapview=lists.get(position);
				b.putString("title", (String)requstmapview.get("title"));
				b.putString("mood", (String)requstmapview.get("Mood"));
				b.putString("content", (String)requstmapview.get("content"));
				b.putString("time", (String)requstmapview.get("time"));
				in.putExtra("Myconteitem", b);
				startActivity(in);
			}
			
		});
		
		
		 
	}

	private void getCursor() {
		if (Csr != null) { String[] columncount = Csr.getColumnNames(); while
		  (Csr.moveToNext()) { Map<String, Object> myMap = new HashMap<String,
		 Object>(); for (String Colsname : columncount) { myMap.put(Colsname,
							Csr.getString(Csr.getColumnIndex(Colsname))); } lists.add(myMap); }
		 
		  SA = new SimpleAdapter(getApplicationContext(), lists,
		  R.layout.zibuju, new String[] { "title", "content", "Mood", "time" },
		  new int[] { R.id.tilte, R.id.content, R.id.mood, R.id.time });
		  mylist.setAdapter(SA); }
		  SCA = new SimpleCursorAdapter(MainActivity.this, R.layout.zibuju,
		  Csr, new String[] { "title", "content", "Mood", "time" }, new int[] {
		  R.id.tilte, R.id.content, R.id.mood,R.id.time});
		  mylist.setAdapter(SCA);
		  
		  /*SCA = new SimpleCursorAdapter(getApplicationContext(), R.layout.zibuju,
			Csr, new String[] { "title", "content", "Mood", "time" },
			new int[] { R.id.tilte, R.id.content, R.id.mood, R.id.time }, 0);
	mylist.setAdapter(SCA); // �α����ȡ���������ݱ����ֶ��а���_id���ֶΣ��������������ݡ�
*/	
	}

	private void init(Bundle savedInstanceState) {
		mylist = (ListView) findViewById(R.id.mylist);
		btnComm = (Button) findViewById(R.id.fabiao);
		super.onCreate(savedInstanceState);
		mysql = new mysqlhelper(MainActivity.this, "mydiary.db", null, 1); // ��ʼ�����ݿ�

		SQLdb = mysql.getWritableDatabase();
		Csr = mysql.select(SQLdb);
		  getCursor();
			spf = this.getPreferences(MODE_PRIVATE);
		ED = spf.edit();
		ED.putInt("num", spf.getInt("num", 0) + 1);
		ED.commit();
		Toast.makeText(this, "���ǵ�" + spf.getInt("num", 0) + "�򿪱�Ӧ��", 9000)
				.show();
	}

	@Override
	protected void onStart() { // ���»ص���ͼʱˢ��
	
		super.onStart();
		Csr = mysql.select(SQLdb);
		  getCursor();
			}
}
