package com.example.treeaddwritedemo.adapter;

public interface DragGridBaseAdapter {
	/**
	 * ������������
	 * @param oldPosition
	 * @param newPosition
	 */
	public void reorderItems(int oldPosition, int newPosition);
	
	
	/**
	 * ����ĳ��item����
	 * @param hidePosition
	 */
	public void setHideItem(int hidePosition);
	

}
