package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.treeaddwritedemo.AllImgSwitchActivity;
import com.example.treeaddwritedemo.MainActivity;
import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.entities.PhotoItem;
import com.example.treeaddwritedemo.global.MyApplication;
import com.example.treeaddwritedemo.utils.SmallViewHolder;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class PhotoAdapter extends SmallCommonAdapter<PhotoItem> {

	protected Context context;
	protected ArrayList<PhotoItem> mDatas;

	public PhotoAdapter(Context context, List<PhotoItem> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		this.context = context;

		this.mDatas = new ArrayList<PhotoItem>();
		for (int i = 0; i < mDatas.size(); i++) {
			this.mDatas.add(mDatas.get(i));
		}

	}

	@Override
	public void convert(int position, final SmallViewHolder helper, final PhotoItem item) {
		if (position == 0) {
			// 设置no_pic
			helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
			helper.setImageResource(R.id.id_item_image, R.drawable.cameia);
			final ImageView mSelect = helper.getView(R.id.id_item_select);
			mSelect.setVisibility(View.INVISIBLE);
			final ImageView mImageView = helper.getView(R.id.id_item_image);
			mImageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					getImageFromCamera();

				}
			});
		} else {
			// 设置no_pic
			helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
			// 设置no_selected
			helper.setImageResource(R.id.id_item_select, R.drawable.picture_unselected);
			// 设置图片
			helper.setImageByUrl(R.id.id_item_image, item.getPath());

			final ImageView mImageView = helper.getView(R.id.id_item_image);
			final ImageView mSelect = helper.getView(R.id.id_item_select);
			mSelect.setVisibility(View.VISIBLE);
			final RelativeLayout mRelativeLayout = helper.getView(R.id.rlt_item_select);
			mImageView.setColorFilter(null);
			// 设置ImageView的点击事件
			mRelativeLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					// 已经选择过该图片
					if (contains(MyApplication.getmSelectedImage(), item)) {
						item.setSelect(false);
						MyApplication.getmSelectedImage().remove(item);

						mSelect.setImageResource(R.drawable.picture_unselected);
					} else
					// 未选择该图片
					{
						item.setSelect(true);
						MyApplication.getmSelectedImage().add(item);

						mSelect.setImageResource(R.drawable.pictures_selected);
					}

				}
			});

			mImageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					// 跳转到全屏选择
					Intent intent = new Intent(context, AllImgSwitchActivity.class);
					intent.putExtra("position", mDatas.indexOf(item));
					context.startActivity(intent);

				}
			});
			/**
			 * 已经选择过的图片，显示出选择过的效果
			 */
			if (contains(MyApplication.getmSelectedImage(), item)) {
				mSelect.setImageResource(R.drawable.pictures_selected);
			}
		}
	}

	private boolean contains(List<PhotoItem> pItems, PhotoItem pItem) {
		for (PhotoItem item : pItems)
			if (item.contains(pItem))
				return true;
		return false;
	}

	protected void getImageFromCamera() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
			getImageByCamera.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			((MainActivity) context).startActivityForResult(getImageByCamera, 3021);

		} else {
			Toast.makeText(context, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
		}
	}
}
