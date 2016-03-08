package com.example.treeaddwritedemo.util;

import java.util.List;

public class Category {
	private int cate_id;
	private List<Subcate>  subcates;
	public int getCate_id() {
		return cate_id;
	}
	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}
	public List<Subcate> getSubcates() {
		return subcates;
	}
	public void setSubcates(List<Subcate> subcates) {
		this.subcates = subcates;
	}
	
}
