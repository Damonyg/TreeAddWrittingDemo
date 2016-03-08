package com.example.treeaddwritedemo.util;

import java.util.ArrayList;

public class CloudAlbumImgs {
	private String date;
	private String place;
	private ArrayList<CloudAlbumImg> imgs;

	public CloudAlbumImgs() {
		date = "";
		place = "";
		imgs = new ArrayList<CloudAlbumImg>();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public ArrayList<CloudAlbumImg> getImgs() {
		return imgs;
	}

	public void setImgs(ArrayList<CloudAlbumImg> imgs) {
		this.imgs = imgs;
	}
}
