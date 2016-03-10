package com.example.treeaddwritedemo;

import java.util.ArrayList;

import com.example.treeaddwritedemo.adapter.PhotoAdapter;
import com.example.treeaddwritedemo.entities.PhotoItem;
import com.example.treeaddwritedemo.global.MyApplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

public class LocalAlbumFragment extends Fragment {

//	private ProgressDialog photo_mProgressDialog;
	private GridView photo_gv;
	private PhotoAdapter photo_adapter;

	private static final int PHOTO_PICKED_WITH_DATA = 3021;

	// 设置获取图片的字段信息
	private static final String[] STORE_IMAGES = { MediaStore.Images.Media.DISPLAY_NAME, // 显示的名称
			MediaStore.Images.Media.DATA, MediaStore.Images.Media.LONGITUDE, // 经度
			MediaStore.Images.Media._ID, // id
			MediaStore.Images.Media.BUCKET_ID, // dir id 目录
			MediaStore.Images.Media.BUCKET_DISPLAY_NAME // dir name 目录名字

	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getmAllImage().clear();
		getPhotoAlbum();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View parentView = inflater.inflate(R.layout.view_photo_localalbum, container, false);
		
		photo_gv = (GridView) parentView.findViewById(R.id.photo_gridview);
		

		return parentView;
		// return super.onCreateView(, container, savedInstanceState);
	}
	
	private Handler photo_mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
//			photo_mProgressDialog.dismiss();
			// 为View绑定数据
			photo2View();
		}
	};

	/**
	 * 为Photo_View绑定数据
	 */
	private void photo2View() {
		if (MyApplication.getmAllImage().size() < 1) {
			Toast.makeText(getActivity(), "擦，一张图片没扫描到", Toast.LENGTH_SHORT).show();
			return;
		}

		photo_adapter = new PhotoAdapter(getActivity(), MyApplication.getmAllImage(), R.layout.view_photo_grid_item);
		photo_gv.setAdapter(photo_adapter);
	};

	

	/**
	 * 方法描述：按相册获取图片信息
	 * 
	 * @author: why
	 * @time: 2013-10-18 下午1:35:24
	 */
	private void getPhotoAlbum() {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(getActivity(), "暂无外部存储", Toast.LENGTH_SHORT).show();
			return;
		}

		new Thread(new Runnable() {
			@Override
			public void run() {


				// Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				// ContentResolver mContentResolver = MainActivity.this
				// .getContentResolver();
				// 显示进度条
//				photo_mProgressDialog = ProgressDialog.show(getActivity(), null, "正在加载...");
				// 只查询jpeg和png的图片
				Cursor mCursor = MediaStore.Images.Media.query(getActivity().getContentResolver(),
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
//				 Cursor mCursor = mContentResolver.query(mImageUri, null,
//				 MediaStore.Images.Media.MIME_TYPE + "=? or "
//				 + MediaStore.Images.Media.MIME_TYPE + "=?",
//				 new String[] { "image/jpeg", "image/png" },
//				 MediaStore.Images.Media.DATE_MODIFIED);
				//// photo_aibum = new PhotoAibum();
				// BitmapDrawable wave_line_bitmapdrawable = (BitmapDrawable)
				//// getResources()
				// .getDrawable(R.drawable.cameia);
				// Bitmap wave_line_bitmap =
				//// wave_line_bitmapdrawable.getBitmap();
				// photo_aibum.setBitmap(wave_line_bitmap);
				// photo_aibum.setCount("1");
				// photo_aibum.setName("");
				ArrayList<PhotoItem> mAllImage = new ArrayList<PhotoItem>();
				while (mCursor.moveToNext()) {
					String path = mCursor.getString(1);
					String id = mCursor.getString(3);
					mAllImage.add(new PhotoItem(Integer.valueOf(id), path));
				}
				
				MyApplication.setmAllImage(mAllImage);
				mCursor.close();
				// 通知Handler扫描图片完成
				photo_mHandler.sendEmptyMessage(0x110);
			}
		}).start();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (photo_adapter != null) {
			photo_adapter.notifyDataSetChanged();
		}
	}

	protected void getImageFromCamera() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
			getImageByCamera.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(getImageByCamera, PHOTO_PICKED_WITH_DATA);

		} else {
			Toast.makeText(getActivity(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
		}
	}

	
}
