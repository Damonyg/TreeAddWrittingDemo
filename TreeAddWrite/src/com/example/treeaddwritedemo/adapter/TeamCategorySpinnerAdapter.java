package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.util.Category;
import com.example.treeaddwritedemo.util.Subcate;
import com.example.treeaddwritedemo.utils.TeamViewHolder;

import android.content.Context;

public class TeamCategorySpinnerAdapter extends TeamCommonAdapter<Category> {
	

	protected Context context;
	protected ArrayList<Category> mDatas;

	public TeamCategorySpinnerAdapter(Context context, List<Category> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		this.context = context;

		this.mDatas = new ArrayList<Category>();
		for (int i = 0; i < mDatas.size(); i++) {
			this.mDatas.add(mDatas.get(i));
		}

	}

	@Override
	public void convert(final TeamViewHolder helper, final Category item) {
		
	
	}
	

}