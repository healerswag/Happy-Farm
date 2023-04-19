package com.zqsoft.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.zqsoft.bean.UserBean;
import com.zqsoft.bean.UserData;
import com.zqsoft.frame.Util;
import com.zqsoft.guiHelper.net.bean.UserItem;

public class UserDAO {
	public static HashMap<String,UserBean> allUsers = new HashMap<String,UserBean>();
	public static HashMap<String,UserData> allUserData = new HashMap<String,UserData>();

	//读取所有用户
	public static void InitAllUser()
	{
		File picFileFolder = new File(Util.GetExecPath() + "\\user");
		String[] user = picFileFolder.list();
		for (int i = 0; i < user.length; i++) {
			readUser(user[i]);
		}
	}
	//T4,3(c
	public static List<UserItem> getAllUser(String curLoginUserId) {
		List<UserItem> pList = new ArrayList<UserItem>();
		
		UserBean curLoginBean = allUsers.get(curLoginUserId);
		pList.add(0, curLoginBean);//0:登录用户
		
	    //加入登录用户外的用户
		for (Map.Entry<String, UserBean> entry : allUsers.entrySet()) {
			UserBean userBean = entry.getValue();
			if(!userBean.getUserID().equals(curLoginUserId) )
				pList.add(userBean);
		}
		return pList;
	}
	
	public static void saveUser(String userID) {
		FileInputStream fin = null;
		InputStreamReader isr = null;
		FileOutputStream fout = null;
		Properties properties = null;
		try {
			UserBean user = allUsers.get(userID);
			
			File userFileFolder = new File("user\\" + userID);
			if (userFileFolder.exists()) {
				File userFile = new File("user\\" + userID + "\\user.txt");
				if (userFile.exists()) {
					properties = new Properties();//
													
					fin = new FileInputStream(userFile);// 
					isr = new InputStreamReader(fin, "GBK");// 
					properties.load(isr);// 
					fout = new FileOutputStream(userFile);//

					//从allUsers获得当前用户信息，写入文件
					properties.setProperty("userID", user.getUserID());//
					properties.setProperty("userName", user.getUserName());
					properties.setProperty("pass", user.getPass());
					properties.setProperty("nick", user.getNickName());
					properties.setProperty("userModText", user.getUserModText());
					properties.setProperty("notice", user.getNotice());
					String pic = user.getPic();
					properties.setProperty("pic",pic.substring(pic.lastIndexOf("\\") + 1));
					
					//从allUserData获得当前用户信息，写入文件
					UserData userData = allUserData.get(userID);
					properties.setProperty("money",String.valueOf(userData.getMoney()));
					properties.setProperty("exp", userData.getExp() + "");
					properties.setProperty("level", userData.getLevel() + "");
					String backpic = userData.getBackgroud();
					properties.setProperty("backgroundpic",backpic.substring(backpic.lastIndexOf("\\") + 1));
										
					properties.store(fout, "存盘用户数据");
				} else {// 
					userFile.createNewFile();//
				}
			} else {// 
				userFileFolder.mkdir();// 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fout.close();
				properties.clear();
				isr.close();
				fin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void readUser(String userID) {//T4,3用户有信息文件读写操作
		UserBean user = null;
		try {
			//读取目录user/%id%/user.txt
			Properties properties = new Properties();
			File userFile = new File("user\\" + userID + "\\user.txt");
			FileInputStream fin = new FileInputStream(userFile);
			InputStreamReader isr = new InputStreamReader(fin, "GBK");
			properties.load(isr);
			
			//用户相对不怎么变的基本信息读出来后设置到userbean里，并加入allUsers集合
			user = new UserBean();
			user.setUserID(userID);
			user.setUserName(properties.getProperty("userName"));
			user.setPass(properties.getProperty("pass"));
			user.setNick(properties.getProperty("nick"));
			user.setNotice(properties.getProperty("notice"));
			user.setPic("resources\\head\\" + properties.getProperty("pic"));
			allUsers.put(userID, user);
			
			//用户相对变化比较频繁的基本信息读出来后设置到userdata里，并加入allUser集合drBean();
			UserData userData = new UserData();
			userData.setBackgroud("resources\\background\\" + properties.getProperty("backgroundpic"));
			userData.setMoney(Integer.parseInt(properties.getProperty("money")));
			userData.setExp(Integer.parseInt(properties.getProperty("exp")));
			userData.setLevel(Integer.parseInt(properties.getProperty("level")));
			userData.setUserID(userID);
			allUserData.put(userID, userData);
			
			isr.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void initUser(String userID) {
		try {
			String path =Util.GetExecPath();//��ȡ��ǰ·��
			File userFileFolder = new File(path+"\\user\\" + userID);
			File userFile = new File(path+"\\user\\" + userID + "\\user.txt");
			File landFile = new File(path+"\\user\\" + userID + "\\land.txt");
			File bagFile = new File(path+"\\user\\" + userID + "\\bag.txt");
			File storeFile = new File(path+"\\user\\" + userID + "\\store.txt");
			if (!userFileFolder.exists()) {
				userFileFolder.mkdir();
			}
			if (!userFile.exists()) {
				userFile.createNewFile();
			}
			if (!landFile.exists()) {
				landFile.createNewFile();
			}
			if (!bagFile.exists()) {
				bagFile.createNewFile();
			}
			if (!storeFile.exists()) {
				storeFile.createNewFile();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void AddUser(String userId, UserBean userBean, UserData userData)
	{
		allUsers.put(userId, userBean);
		allUserData.put(userId, userData);
		saveUser(userId);
	}
}
