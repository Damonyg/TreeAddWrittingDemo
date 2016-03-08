package com.example.treeaddwritedemo.global;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.example.treeaddwritedemo.entities.PhotoItem;
import com.example.treeaddwritedemo.util.TreeData;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	private static ArrayList<PhotoItem> mAllImage;
	private static List<PhotoItem> mSelectedImage;
	
	private static TreeData mTreeData;//树
	
	private static String cloudalbum_img_path;
	
	private static boolean recordVoice;
	public void onCreate() {
		super.onCreate();
	}

	public static void initDate() {
		mAllImage = new ArrayList<PhotoItem>();
		mSelectedImage = new LinkedList<PhotoItem>();
		mTreeData = new TreeData();
		cloudalbum_img_path ="";
		recordVoice = false;
	}

	public static String getCloudalbum_img_path() {
		return cloudalbum_img_path;
	}

	public static void setCloudalbum_img_path(String cloudalbum_img_path) {
		MyApplication.cloudalbum_img_path = cloudalbum_img_path;
	}

	public static boolean isRecordVoice() {
		return recordVoice;
	}

	public static void setRecordVoice(boolean recordVoice) {
		MyApplication.recordVoice = recordVoice;
	}

	public static ArrayList<PhotoItem> getmAllImage() {
		return mAllImage;
	}

	public static void setmAllImage(ArrayList<PhotoItem> mAllImage) {
		MyApplication.mAllImage = mAllImage;
	}

	public static List<PhotoItem> getmSelectedImage() {
		return mSelectedImage;
	}

	public static void setmSelectedImage(List<PhotoItem> mSelectedImage) {
		MyApplication.mSelectedImage = mSelectedImage;
	}

	public static TreeData getmTreeData() {
		return mTreeData;
	}

	public static void setmTreeData(TreeData mTreeData) {
		MyApplication.mTreeData = mTreeData;
	}
	

	

}
