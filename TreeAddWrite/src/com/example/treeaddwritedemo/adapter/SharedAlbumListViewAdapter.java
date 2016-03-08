package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.util.CloudAlbumImgs;
import com.example.treeaddwritedemo.utils.TeamViewHolder;

import android.content.Context;
import android.util.Log;

public class SharedAlbumListViewAdapter extends TeamCommonAdapter<CloudAlbumImgs> {
	

	protected Context context;
	protected ArrayList<CloudAlbumImgs> mDatas;

	public SharedAlbumListViewAdapter(Context context, ArrayList<CloudAlbumImgs> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		this.context = context;
		this.mDatas =mDatas;
	}

	@Override
	public void convert(final TeamViewHolder helper, final CloudAlbumImgs item) {
		// 设置no_selected
		helper.setText(R.id.textview_date_item, item.getDate());
		helper.setText(R.id.textview_location_item, item.getPlace());
		helper.setGridView(R.id.sharedalbum_gridview, item.getImgs());
	
	}
	

}