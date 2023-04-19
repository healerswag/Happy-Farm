package com.zqsoft.bean;

import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.net.bean.UserItem;
//用于管理不经常变动的数据
public class UserBean implements UserItem{
	private String userID;// 用户ID
	private String userName = "";// 用户登入名
	private String password;//用户密码 
	private String nickName = "";// 昵称
	private String userModText = ""; //个性签名
	private String pic = "1-1.gif";// 头像
	private String notice = "";// 用户公告信息
	
	@Override
	public String getNickName()
	{
		return this.nickName;
	}
	
	@Override
	public String getUserName()
	{
		return this.userName;
	}
	
	@Override	
	public void itemClick()
	{
		GameMember.getUserWin().ChangeFarm(this.userID);
	}
	
	@Override
	public String getUserModText()
	{
		return userModText;
	}
	
	@Override
	public String getPic()
	{
		return this.pic;
	}
		
	public void setUserModText(String UserModText) {
		this.userModText = UserModText;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return password;
	}

	public void setPass(String pass) {
		this.password = pass;
	}

	public void setNick(String nick) {
		this.nickName = nick;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
	
}
