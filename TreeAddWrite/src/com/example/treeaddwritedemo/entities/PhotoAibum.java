package com.example.treeaddwritedemo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;


public class PhotoAibum implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;   //相册名字
	private String count; //数量
	private Bitmap  bitmap;  // 照相机
	
	private List<PhotoItem> bitList = new ArrayList<PhotoItem>();
	
	public PhotoAibum() {
		count = "1";
	}
	
	
	public PhotoAibum(String name, String count, Bitmap bitmap) {
		super();
		this.name = name;
		this.count = count;
		this.bitmap = bitmap;
	}


	public List<PhotoItem> getBitList() {
		return bitList;
	}


	public void setBitList(List<PhotoItem> bitList) {
		this.bitList = bitList;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	@Override
	public String toString() {
		return "PhotoAibum [name=" + name + ", count=" + count + ", bitmap="
				+ bitmap + ", bitList=" + bitList + "]";
	}
}
