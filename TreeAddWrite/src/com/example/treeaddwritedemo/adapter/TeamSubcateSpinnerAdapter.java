package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.util.Category;
import com.example.treeaddwritedemo.util.Subcate;
import com.example.treeaddwritedemo.utils.TeamViewHolder;

import android.content.Context;

public class TeamSubcateSpinnerAdapter extends TeamCommonAdapter<Subcate> {
	

	protected Context context;
	protected ArrayList<Subcate> mDatas;

	public TeamSubcateSpinnerAdapter(Context context, List<Subcate> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		this.context = context;

		this.mDatas = new ArrayList<Subcate>();
		for (int i = 0; i < mDatas.size(); i++) {
			this.mDatas.add(mDatas.get(i));
		}

	}

	@Override
	public void convert(final TeamViewHolder helper, final Subcate item) {
		// 设置no_selected
		helper.setText(R.id.textview_spinner_item, item.getName());
	
	}
	

}