package com.bolex.sqlhelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class mysqlhelper extends SQLiteOpenHelper {

	public mysqlhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists stutb(_id integer primary key autoincrement,"
				+ "title text ,content text ,Mood text,time text)");
	}
	public Cursor select(SQLiteDatabase db) {
		return	db.query("stutb", new String[]{"_id","title","content","Mood","time"}, null, null, null, null, null, null);
	}
	public int inset(SQLiteDatabase db, String title, String content,
			String Mood) {
		ContentValues cv = new ContentValues();
		cv.put("title", title);
		cv.put("content", content);
		cv.put("Mood", Mood);
		cv.put("time", time());
		/*
		 * SQLdb.execSQL(
		 * "insert into stutb(title,content,Mood,time)values('≤‚ ‘','ƒ⁄»›','∫√','" +
		 * time() + "')");
		 */
		return (int) db.insert("stutb", null, cv);
	}
	public String time() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		System.out.println(time);
		return time;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
