package com.example.treeaddwritedemo.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.treeaddwritedemo.R;
import com.example.treeaddwritedemo.entities.PhotoItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @blog http://blog.csdn.net/xiaanming
 * 
 * @author xiaanming
 *
 */
public class DragAdapter extends BaseAdapter implements DragGridBaseAdapter {
	private List<PhotoItem> list;
	private LayoutInflater mInflater;
	private int mHidePosition = -1;
	private Context context;

	public DragAdapter(Context mcontext, List<PhotoItem> list) {
		context = mcontext;
		this.list = list;
		mInflater = LayoutInflater.from(mcontext);
	}

	@Override
	public int getCount() {
		return list.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.view_record_grid_item, null);
		ImageView mImageView = (ImageView) convertView.findViewById(R.id.id_item_image);
		if (position == list.size()) {
			
			BitmapDrawable addmore_bitmapdrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.addmore);
			Bitmap addmore_bitmap = addmore_bitmapdrawable.getBitmap();
			
			mImageView.setImageBitmap(addmore_bitmap);
		} else {

			
			Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(),
					list.get(position).getPhotoID(), Thumbnails.MICRO_KIND, null);
			mImageView.setImageBitmap(bitmap);
		}
		if (position == mHidePosition) {
			convertView.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	@Override
	public void reorderItems(int oldPosition, int newPosition) {
		PhotoItem temp = list.get(oldPosition);
		if (oldPosition < newPosition) {
			for (int i = oldPosition; i < newPosition; i++) {
				Collections.swap(list, i, i + 1);
			}
		} else if (oldPosition > newPosition) {
			for (int i = oldPosition; i > newPosition; i--) {
				Collections.swap(list, i, i - 1);
			}
		}

		list.set(newPosition, temp);
	}

	@Override
	public void setHideItem(int hidePosition) {
		this.mHidePosition = hidePosition;
		notifyDataSetChanged();
	}

}
