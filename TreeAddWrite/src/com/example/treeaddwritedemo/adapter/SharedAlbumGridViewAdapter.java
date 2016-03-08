package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.global.MyApplication;
import com.example.treeaddwritedemo.util.CloudAlbumImg;
import com.example.treeaddwritedemo.utils.TeamViewHolder;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class SharedAlbumGridViewAdapter extends TeamCommonAdapter<CloudAlbumImg> {
	

	protected Context context;
	protected ArrayList<CloudAlbumImg> mDatas;

	public SharedAlbumGridViewAdapter(Context context, List<CloudAlbumImg> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		this.context = context;

		this.mDatas = new ArrayList<CloudAlbumImg>();
		for (int i = 0; i < mDatas.size(); i++) {
			this.mDatas.add(mDatas.get(i));
		}

	}

	@Override
	public void convert(final TeamViewHolder helper, final CloudAlbumImg item) {
		// 设置no_selected
		// 设置no_pic
				helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
				// 设置no_selected
				helper.setImageResource(R.id.id_item_select, R.drawable.picture_unselected);
				// 设置图片
				helper.setImageByUrl(R.id.id_item_image, MyApplication.getCloudalbum_img_path()+item.getThumb());

				final ImageView mImageView = helper.getView(R.id.id_item_image);
				final ImageView mSelect = helper.getView(R.id.id_item_select);
				final RelativeLayout mRelativeLayout = helper.getView(R.id.rlt_item_select);
				mImageView.setColorFilter(null);
				// 设置ImageView的点击事件
				mRelativeLayout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

//						// 已经选择过该图片
//						if (contains(MyApplication.getmSelectedImage(),item)) {
//							item.setSelect(false);
//							MyApplication.getmSelectedImage().remove(item);
//
//							mSelect.setImageResource(R.drawable.picture_unselected);
//						} else
//						// 未选择该图片
//						{
//							item.setSelect(true);
//							MyApplication.getmSelectedImage().add(item);
//
//							mSelect.setImageResource(R.drawable.pictures_selected);
//						}
//
					}
				});

				mImageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

//						 跳转到全屏选择
//						Intent intent = new Intent(context, AllImgSwitchActivity.class);
//						intent.putExtra("position", mDatas.indexOf(item));
//						context.startActivity(intent);

					}
				});
				

	
	}
	

}