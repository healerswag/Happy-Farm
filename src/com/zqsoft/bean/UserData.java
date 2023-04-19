package com.zqsoft.bean;
//用于管理玩家经常变动的数据
public class UserData {
	private String userID = "";
	private int money = 200;// 金币
	private int exp = 0;// 经验
	private int level = 1;// 等级
	private String backgroud = ""; //地图背景
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getBackgroud() {
		return backgroud;
	}

	public void setBackgroud(String backgroud) {
		this.backgroud = backgroud;
	}

}
