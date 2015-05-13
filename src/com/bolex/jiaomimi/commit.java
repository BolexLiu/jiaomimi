package com.bolex.jiaomimi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.bolex.sqlhelper.mysqlhelper;

import bean.commitbean;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class commit extends Activity {

	Spinner spinner;
	Button commitbt, takePhoto;
	EditText biaotiname, cotentedit;
	ImageView picture;
	private long exitTime = 0;
	Uri imageUri;
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commit);
		inte();
		takePhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 创建File对象，用于存储拍照后的图片
				File outputImage = new File(Environment
						.getExternalStorageDirectory(),"tempImage.jpg");
				try {
					if (outputImage.exists()) {
						outputImage.delete();
					}
					outputImage.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				imageUri = Uri.fromFile(outputImage);
				Intent intent = new Intent(
						"android.media.action.IMAGE_CAPTURE");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序
			}
		});
		commitbt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				commitbean cmb = new commitbean();
				cmb.setTitleB(biaotiname.getText().toString());
				cmb.setContentB(cotentedit.getText().toString());
				cmb.setMoodB(spinner.getSelectedItem().toString());
				if (biaotiname.getText().toString().equals("")
						|| cotentedit.getText().toString().equals("")
						|| spinner.getSelectedItem().toString().equals("")) {
					Toast.makeText(commit.this, "傻vv,日记当然要写完整，不然下次怎么看？", 9000)
							.show();

				} else {
					Toast.makeText(commit.this, "保存成功", 3000).show();
					mysqlhelper mysql = new mysqlhelper(commit.this,
							"mydiary.db", null, 1);
					mysql.inset(mysql.getWritableDatabase(), cmb.getTitleB(),
							cmb.getContentB(), cmb.getMoodB());
					/*
					 * Intent in = new Intent(commit.this,MainActivity.class );
					 * startActivity(in); //写日记页面启动
					 */commit.this.finish();
				}
			}
		});

	}

	private void inte() {
		spinner = (Spinner) findViewById(R.id.spinner1);
		biaotiname = (EditText) findViewById(R.id.biaotiname);
		cotentedit = (EditText) findViewById(R.id.cotentedit);
		commitbt = (Button) findViewById(R.id.commitbut);
		picture = (ImageView) findViewById(R.id.imgi);
		takePhoto = (Button) findViewById(R.id.commimgi);

	}

	protected void onRestart() {
		super.onRestart();
		Intent intent = new Intent(getApplicationContext(),
				ImageLockActivity.class);
		intent.putExtra("view", "commit");
		startActivity(intent);
		finish();

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				Intent intent = new Intent(commit.this, ImageLockActivity.class);
				startActivity(intent);
				finish();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
			}
			break;
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					Bitmap bitmap = BitmapFactory
							.decodeStream(getContentResolver().openInputStream(
									imageUri));
					picture.setImageBitmap(bitmap); // 将裁剪后的照片显示出来
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

}
