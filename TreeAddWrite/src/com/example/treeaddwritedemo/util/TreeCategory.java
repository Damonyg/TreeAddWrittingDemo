package com.example.treeaddwritedemo.util;

import java.util.ArrayList;

public class TreeCategory {
	private int tree_id;
	private boolean select;
	private ArrayList<Category> categories;

	public TreeCategory() {
		tree_id = 0;
		select = false;
		categories = new ArrayList<Category>();
	}

	public int getTree_id() {
		return tree_id;
	}

	public void setTree_id(int tree_id) {
		this.tree_id = tree_id;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

}
