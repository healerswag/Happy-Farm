package com.zqsoft.dao;

import com.zqsoft.bean.UserData;

public class UserDataDAO {
	
	//读取玩家变化相对平缓的数据
  public static UserData getUserData(String userID)
  {
	  return UserDAO.allUserData.get(userID);
  }
  
  //更新变化数据
  public static void UpdateUserData(UserData userdata)
  {
	  String UserId = userdata.getUserID();
	  UserDAO.allUserData.remove(UserId);
	  UserDAO.allUserData.put(UserId, userdata);
  }
}
