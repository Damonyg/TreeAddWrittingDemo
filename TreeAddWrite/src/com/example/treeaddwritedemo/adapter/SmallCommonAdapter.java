package com.example.treeaddwritedemo.adapter;

import java.util.List;

import com.example.treeaddwritedemo.utils.SmallViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SmallCommonAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;

	public SmallCommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size() + 1;
	}

	@Override
	public T getItem(int position) {
		if (position == 0)
			return null;
		else
			return mDatas.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final SmallViewHolder smallViewHolder = getViewHolder(position, convertView, parent);
		convert(position, smallViewHolder, getItem(position));
		return smallViewHolder.getConvertView();
	}

	public abstract void convert(int position, SmallViewHolder helper, T item);

	private SmallViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
		return SmallViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
	}

}
