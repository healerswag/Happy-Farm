package com.zqsoft.bean;

public class ImageBean {
	private String picPath;//ַ

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public ImageBean(String picPath) {
		super();
		this.picPath = picPath;
	}

	public ImageBean() {
		super();
	}

	@Override
	public String toString() {
		return "Item [picPath=" + picPath + "]";
	}

}
