package com.example.treeaddwritedemo.util;

public class CloudAlbumImg {
	private int status;
	private boolean owner;
	private String id;
	private String file;
	private String date;
	private String thumb;
	private String place;
	private CloudAlbumUser user;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isOwner() {
		return owner;
	}
	public void setOwner(boolean owner) {
		this.owner = owner;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public CloudAlbumUser getUser() {
		return user;
	}
	public void setUser(CloudAlbumUser user) {
		this.user = user;
	}
	
       
}
