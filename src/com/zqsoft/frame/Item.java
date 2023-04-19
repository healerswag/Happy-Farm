package com.zqsoft.frame;

public class Item {
	private String userName;// �û���
	private String picPath;// �û�ͷ���ַ

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Item(String userName, String picPath) {
		super();
		this.userName = userName;
		this.picPath = picPath;
	}

	public Item() {
		super();
	}

	@Override
	public String toString() {
		return "Item [userName=" + userName + ", picPath=" + picPath + "]";
	}

}
