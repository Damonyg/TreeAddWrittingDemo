package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.global.MyApplication;
import com.example.treeaddwritedemo.util.Category;
import com.example.treeaddwritedemo.util.Tree;
import com.example.treeaddwritedemo.util.TreeCategory;
import com.example.treeaddwritedemo.util.TreeData;
import com.example.treeaddwritedemo.utils.TeamViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class TeamAdapter extends TeamCommonAdapter<TreeCategory> {

	protected Context context;
	protected ArrayList<TreeCategory> mDatas;

	public TeamAdapter(Context context, List<TreeCategory> mDatas, int itemLayoutId) {
		super(context, mDatas, itemLayoutId);
		this.context = context;

		this.mDatas = new ArrayList<TreeCategory>();
		for (int i = 0; i < mDatas.size(); i++) {
			this.mDatas.add(mDatas.get(i));
		}

	}

	@Override
	public void convert(final TeamViewHolder helper, final TreeCategory item) {
		// 设置no_selected
		helper.setImageResource(R.id.id_item_select, R.drawable.picture_unselected);

		// 需要优化 未考虑家的树
		TreeData mTreeData = MyApplication.getmTreeData();
		for (Tree mItem : mTreeData.getData()) {
			if (mItem.getId() == item.getTree_id()) {
				if (mItem.getType().equals("1")) {
					helper.setCategorySpinner(R.id.category_spinner, item.getCategories());
				}else{
					if (mItem.getDisplay_name().equals(""))
						helper.setText(R.id.textview_tree, mItem.getLabel());
					else
						helper.setText(R.id.textview_tree, mItem.getDisplay_name());
					
					for (Category mCategoryItem : item.getCategories()) {
						if (mCategoryItem.getCate_id() == 500)
							helper.setSubcateSpinner(R.id.category_spinner, mCategoryItem.getSubcates());
					}
				}
			}

		}

		

		final ImageView mSelect = helper.getView(R.id.id_item_select);
		final RelativeLayout mRelativeLayout = helper.getView(R.id.rlt_item_select);
		mSelect.setImageResource(R.drawable.picture_unselected);
		// 设置ImageView的点击事件
		mRelativeLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// 已经选择过该图片
				if (item.isSelect()) {
					item.setSelect(false);
					mSelect.setImageResource(R.drawable.picture_unselected);
				} else
				// 未选择该图片
				{
					item.setSelect(true);
					mSelect.setImageResource(R.drawable.pictures_selected);
				}

			}
		});

	}

}