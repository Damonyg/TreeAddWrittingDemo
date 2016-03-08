package com.example.treeaddwritedemo.adapter;

import java.util.List;

import com.example.treeaddwritedemo.utils.SmallViewHolder;
import com.example.treeaddwritedemo.utils.TeamViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class TeamCommonAdapter<T> extends BaseAdapter
{
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mDatas;
	protected final int mItemLayoutId;

	public TeamCommonAdapter(Context context, List<T> mDatas, int itemLayoutId)
	{
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.mDatas = mDatas;
		this.mItemLayoutId = itemLayoutId;
	}

	@Override
	public int getCount()
	{
		return mDatas.size();
	}

	@Override
	public T getItem(int position)
	{
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		final TeamViewHolder smallViewHolder = getViewHolder(position, convertView,
				parent);
		convert(smallViewHolder, getItem(position));
		return smallViewHolder.getConvertView();

	}

	public abstract void convert(TeamViewHolder helper, T item);

	private TeamViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent)
	{
		return TeamViewHolder.get(mContext, convertView, parent, mItemLayoutId,
				position);
	}

}
