package com.example.treeaddwritedemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.treeaddwritedemo.global.MyApplication;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	// view_photo
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	private TextView localalbum;
	private TextView sharedalbum;

	private FragmentTabHost mTabHost;
	private final Class[] fragments = { LocalAlbumFragment.class, SharedAlbumFragment.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_photo);
		MyApplication.initDate();
		initView();
	}

	private void initView() {

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec(i + "").setIndicator(i + "");
			mTabHost.addTab(tabSpec, fragments[i], null);
		}

		mTabHost.setCurrentTab(0);

		RelativeLayout RelativeLayout_back = (RelativeLayout) findViewById(R.id.RelativeLayout_back);
		RelativeLayout_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		localalbum = (TextView) findViewById(R.id.localalbum);
		sharedalbum = (TextView) findViewById(R.id.sharedalbum);
		localalbum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				localalbum.setTextColor(Color.parseColor("#61d7cd"));
				localalbum.setBackgroundResource(R.drawable.semi_round_backgroud_write);
				sharedalbum.setTextColor(Color.parseColor("#ffffffff"));
				sharedalbum.setBackgroundResource(R.drawable.semi_round_backgroud_green);
				mTabHost.setCurrentTab(0);
			}
		});

		sharedalbum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedalbum.setTextColor(Color.parseColor("#61d7cd"));
				sharedalbum.setBackgroundResource(R.drawable.semi_round_backgroud_write);
				localalbum.setTextColor(Color.parseColor("#ffffffff"));
				localalbum.setBackgroundResource(R.drawable.semi_round_backgroud_green);
				mTabHost.setCurrentTab(1);
			}
		});

		TextView photo_okbtn = (TextView) findViewById(R.id.okbtn);
		photo_okbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				// 跳转到录音页面
				Intent intent = new Intent(MainActivity.this, RecordActivity.class);
				startActivity(intent);

			}
		});
	}

	// // 树
	// private void initView_team() {
	// View view_team = viewList.get(VIEW_TEAM);
	//
	// RelativeLayout RelativeLayout_back = (RelativeLayout)
	// view_team.findViewById(R.id.RelativeLayout_back);
	// RelativeLayout_back.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View view) {
	// mViewPager.setCurrentItem(VIEW_RECORD);
	// }
	// });
	// }

	// 调用相机拍照返回
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Activity.RESULT_OK) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				Log.i("TestFile", "SD card is not avaiable/writeable right now.");
				return;
			}
			new DateFormat();
			String name = DateFormat.format("yyyyMMddhhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

			FileOutputStream b = null;
			 File file = new File("/sdcard/Image/");
			 file.mkdirs();// 创建文件夹
			String fileName = "/sdcard/Image/" + name;
			try {
				b = new FileOutputStream(fileName);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
				saveImgToGallery(fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					b.flush();
					b.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				// 将图片显示在ImageView里
			} catch (Exception e) {
				Log.e("error", e.getMessage());
			}

		}
	}

	public boolean saveImgToGallery(String filePath) {

		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在

		if (!sdCardExist)

			return false;

		try {

			ContentValues values = new ContentValues();

			values.put("datetaken", new Date().toString());

			values.put("mime_type", "image/jpg");

			values.put("_data", filePath);

			ContentResolver cr = this.getContentResolver();

			cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return true;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("yanggang", "onResume");
		
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}
