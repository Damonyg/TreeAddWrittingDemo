package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.treeaddwritedemo.AllImgSwitchActivity;
import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.entities.PhotoItem;
import com.example.treeaddwritedemo.utils.BigViewHolder;
import com.example.treeaddwritedemo.utils.SmallViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SelectedImgSwitchAdapter extends BigCommonAdapter<PhotoItem> {

	protected Context context;
	protected ArrayList<PhotoItem> mDatas;

	public SelectedImgSwitchAdapter(Context context, List<PhotoItem> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		this.context = context;

		this.mDatas = new ArrayList<PhotoItem>();
		for (int i = 0; i < mDatas.size(); i++) {
			this.mDatas.add(mDatas.get(i));
		}

	}



	@Override
	public void convert(final BigViewHolder helper, final PhotoItem item) {
		// // 设置no_pic
		// helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		// // 设置no_selected
		// helper.setImageResource(R.id.id_item_select,
		// R.drawable.picture_unselected);
		// 设置图片
		helper.setImageByUrl(R.id.id_item_image, item.getPath());
		//
		final ImageView mImageView = helper.getView(R.id.id_item_image);
		// final ImageView mSelect = helper.getView(R.id.id_item_select);
		// final RelativeLayout mRelativeLayout =
		// helper.getView(R.id.rlt_item_select);
		// mImageView.setColorFilter(null);
		// // 设置ImageView的点击事件
		// mRelativeLayout.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		// // 已经选择过该图片
		// if (mSelectedImage.contains(item)) {
		// item.setSelect(false);
		// mSelectedImage.remove(item);
		//
		// mSelect.setImageResource(R.drawable.picture_unselected);
		// } else
		// // 未选择该图片
		// {
		// item.setSelect(true);
		// mSelectedImage.add(item);
		//
		// mSelect.setImageResource(R.drawable.pictures_selected);
		// }
		//
		// }
		// });
		//
		//
		// /**
		// * 已经选择过的图片，显示出选择过的效果
		// */
		// if (mSelectedImage.contains(item)) {
		// mSelect.setImageResource(R.drawable.pictures_selected);
		// }

	}
}