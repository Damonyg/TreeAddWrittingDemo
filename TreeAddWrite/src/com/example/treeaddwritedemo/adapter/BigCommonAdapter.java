package com.example.treeaddwritedemo.adapter;

import java.util.List;

import com.example.treeaddwritedemo.utils.BigViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BigCommonAdapter<T> extends BaseAdapter {
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;

	public BigCommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final BigViewHolder bigViewHolder = getViewHolder(position, convertView, parent);
		convert(bigViewHolder, getItem(position));
		return bigViewHolder.getConvertView();

	}

	public abstract void convert(BigViewHolder helper, T item);

	private BigViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
		return BigViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
	}

}
