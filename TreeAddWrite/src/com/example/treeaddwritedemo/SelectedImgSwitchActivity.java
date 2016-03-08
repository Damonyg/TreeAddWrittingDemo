package com.example.treeaddwritedemo;

import com.example.treeaddwritedemo.adapter.SelectedImgSwitchAdapter;
import com.example.treeaddwritedemo.entities.PhotoItem;
import com.example.treeaddwritedemo.global.MyApplication;
import com.example.treeaddwritedemo.view.DetailGallery;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectedImgSwitchActivity extends Activity {
	private Gallery myGallery;
	private int position; // 选中图片的位置
	private SelectedImgSwitchAdapter adapter;
	private ImageView mSelect; // 图片的状态
	private RelativeLayout mRelativeLayout;
	private PhotoItem currentPhotoItem;
	private TextView imageview_count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allimgswitch);
		position = (int) getIntent().getExtras().get("position");
		currentPhotoItem = MyApplication.getmSelectedImage().get(position);
		init();
	}

	// 初始化布局
	void init() {

		RelativeLayout RelativeLayout_back = (RelativeLayout) findViewById(R.id.RelativeLayout_back);
		RelativeLayout_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

		imageview_count = (TextView) findViewById(R.id.imageview_count);
		setTextCount(position + 1);

		myGallery = (DetailGallery) findViewById(R.id.myGallery);
		adapter = new SelectedImgSwitchAdapter(this, MyApplication.getmSelectedImage(), R.layout.allimgswitch_item);
		myGallery.setAdapter(adapter);
		myGallery.setSelection(position);

		myGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				initStatusView(arg2);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		mSelect = (ImageView) findViewById(R.id.id_item_select);

		mRelativeLayout = (RelativeLayout) findViewById(R.id.rlt_item_select);
		// 设置ImageView的点击事件
		mRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentPhotoItem.setSelect(false);
				MyApplication.getmSelectedImage().remove(currentPhotoItem);

				if (MyApplication.getmSelectedImage().size() == 0) {
					finish();
				} else if (MyApplication.getmSelectedImage().size() == position) {
					position--;
					initStatusView(position);
				} else {
					initStatusView(position);
				}
				adapter.notifyDataSetChanged();
			}
		});

	}

	// 获取新图片的状态
	private void initStatusView(int arg2) {
		position = arg2;
		setTextCount(arg2 + 1);
		currentPhotoItem = MyApplication.getmSelectedImage().get(arg2);
		mSelect.setImageResource(R.drawable.pictures_selected);

	}

	private void setTextCount(int mposition) {
		imageview_count.setText(mposition + "/" + MyApplication.getmSelectedImage().size());
	}

	protected void onResume() {
		super.onResume();
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something...
		}
		return super.onKeyDown(keyCode, event);
	}
}
