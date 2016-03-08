package com.example.treeaddwritedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView{
	public CustomScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CustomScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		Log.v("yanggang", "CustomScrollView dispatchTouchEvent");
	
		return super.dispatchTouchEvent(ev);
	}

	  @Override
      public boolean onInterceptTouchEvent(MotionEvent ev) {
		  Log.v("yanggang", "CustomScrollView onInterceptTouchEvent");
		  return super.onInterceptTouchEvent(ev);
	  }
	
	

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.v("yanggang", "CustomScrollView onTouchEvent");
		return super.onTouchEvent(ev);
	}
}
