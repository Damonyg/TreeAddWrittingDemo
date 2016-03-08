package com.example.treeaddwritedemo;

import com.example.treeaddwritedemo.global.MyApplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

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
		Log.v("yanggang", "onActivityResult");
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA:
			Bitmap photo = data.getParcelableExtra("data");
			if (photo != null) {
				// 跳转到记录页面
				// Intent intent = new Intent(MainActivity.this,
				// PhotoActivity.class);
				// intent.putExtra("aibum", gl_arr);
			}
			Log.v("yanggang", "onActivityResult1");
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyApplication.initDate();
	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
}
