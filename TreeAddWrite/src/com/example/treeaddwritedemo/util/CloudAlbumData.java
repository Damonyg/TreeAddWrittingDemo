package com.example.treeaddwritedemo.util;

import java.util.ArrayList;

public class CloudAlbumData {
	private int total;
	private String img_path;
	private String avatar_path;
	private ArrayList<CloudAlbumImgs> imgs;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getImg_path() {
		return img_path;
	}
	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}
	public String getAvatar_path() {
		return avatar_path;
	}
	public void setAvatar_path(String avatar_path) {
		this.avatar_path = avatar_path;
	}
	public ArrayList<CloudAlbumImgs> getImgs() {
		return imgs;
	}
	public void setImgs(ArrayList<CloudAlbumImgs> imgs) {
		this.imgs = imgs;
	}

}
