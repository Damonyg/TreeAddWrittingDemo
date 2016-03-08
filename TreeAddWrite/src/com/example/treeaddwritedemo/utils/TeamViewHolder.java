package com.example.treeaddwritedemo.utils;

import java.util.List;

import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.adapter.SharedAlbumGridViewAdapter;
import com.example.treeaddwritedemo.adapter.TeamSpinnerAdapter;
import com.example.treeaddwritedemo.netimage.NetImageView;
import com.example.treeaddwritedemo.util.CloudAlbumImg;
import com.example.treeaddwritedemo.util.Subcate;
import com.example.treeaddwritedemo.view.MyGridView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class TeamViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context mContext;

	private TeamViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.mContext = context;
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static TeamViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		TeamViewHolder holder = null;
		if (convertView == null) {
			holder = new TeamViewHolder(context, parent, layoutId, position);
		} else {
			holder = (TeamViewHolder) convertView.getTag();
			holder.mPosition = position;
		}
		return holder;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * 为TextView设置字符串
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public TeamViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	/**
	 * 为Spinner设置数据
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public TeamViewHolder setSpinner(int viewId, List<Subcate> mDatas) {
		Spinner view = getView(viewId);
		TeamSpinnerAdapter team_adapter = new TeamSpinnerAdapter(mContext, mDatas, R.layout.view_team_spinner_item);
		view.setAdapter(team_adapter);
		// view.setText(text);
		return this;
	}

	/**
	 * 为GridView设置数据
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public TeamViewHolder setGridView(int viewId, List<CloudAlbumImg> mDatas) {
		MyGridView view = getView(viewId);
		SharedAlbumGridViewAdapter team_adapter = new SharedAlbumGridViewAdapter(mContext, mDatas,
				R.layout.view_photo_sharedalbum_grid_item);
		view.setAdapter(team_adapter);
		// view.setText(text);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public TeamViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public TeamViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public TeamViewHolder setImageByUrl(int viewId, String url) {
		NetImageView view = getView(viewId);
		view.setImageUrl(url);
//		SmallImageLoader.getInstance(3,Type.LIFO).loadImage(url, (ImageView) getView(viewId));
		return this;
	}

}
