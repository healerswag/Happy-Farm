package com.zqsoft.frame;

import com.zqsoft.bean.*;
import com.zqsoft.dao.LandDAO;
import com.zqsoft.dao.PackageDAO;
import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.dao.UserDAO;
import com.zqsoft.guiHelper.FaceHelper;
import com.zqsoft.guiHelper.bean.PackageItem;
import com.zqsoft.guiHelper.bean.ShopItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import com.zqsoft.guiHelper.bean.StoreHouseItem;
import com.zqsoft.guiHelper.net.bean.UserItem;
import com.zqsoft.guiHelper.net.bean.UserWindow;
import com.zqsoft.ncfarm.core.MessageDialogHelper;

public class UserWin implements UserWindow {
	public String LoginUserID = null;  //登入玩家农场
	public String FriendUserID = null;  //可能是好友农场
	public static HashMap<Integer,PackageItemBean> LoginUserBagList = new HashMap<Integer,PackageItemBean>(); //当前登入用户的背包[cropid]=bean
	public static HashMap<Integer, LandItemBean> LoginUserLandList = new HashMap<Integer,LandItemBean>(); //当前登入用户的土地[landid]=bean
	public static HashMap<Integer, StoreHouseItemBean> LoginUserStoreHouseList = new HashMap<Integer,StoreHouseItemBean>(); //当前登入用户的仓库[cropid]=bean

	public static HashMap<Integer, LandItemBean> FriendUserLandList = new HashMap<Integer,LandItemBean>(); //好友的土地[landid]=bean

	public UserData getLoginUser()
	{
		UserData userdata = UserDAO.allUserData.get(LoginUserID);
		return userdata;
	}
	//修改密码
	@Override
	public boolean changePassword(String userName, String oldPassword, String checkPassword, String newPassword) {
		
		if (!Check.checkPass(checkPassword)) {// 密码验证֤
			MessageDialogHelper.showMessageDialog("密码长度必须在6-12位之间！", "错误");
			return false;
		}
		
		if (!Check.checkPass(newPassword)) {// 密码验证֤
			MessageDialogHelper.showMessageDialog("密码长度必须在6-12位之间！", "错误");
			return false;
		}
		
		if(!checkPassword.equals(newPassword))
		{
			MessageDialogHelper.showConfirmDialog("确认密码必须和密码相同！", "错误");
			return false;
		}
		
		UserBean user = UserDAO.allUsers.get(LoginUserID);
		if(!oldPassword.equals(user.getPass()))
		{
			MessageDialogHelper.showConfirmDialog("旧的密码不正确！", "错误");
			return false;
		}

		if(checkPassword.equals(user.getPass()))
		{
			MessageDialogHelper.showConfirmDialog("新旧密码不能相同！", "错误");
			return false;
		}

		user.setPass(checkPassword);
		UserDAO.saveUser(LoginUserID);
		
		return false;
	}

	//修改用户基本信息
	@Override
	public boolean changeUserInfo(String userName, String UserModText, String nickName, String headPic, String notice) {
		UserBean user = UserDAO.allUsers.get(LoginUserID);
		user.setNick(nickName);
		user.setNotice(notice);
		user.setPic(headPic);
		user.setUserModText(UserModText);
		UserDAO.saveUser(LoginUserID);
		return true;
	}

	//获取用户头像列表
	@Override
	public List<String> getUserFaceList() {
		Vector<ImageBean> heads = Util.getAllFacePic();
		
		List<String> pList = new Vector<String>();
		for(int i =0; i <heads.size();i++)
			pList.add(heads.get(i).getPicPath());
		return pList;
	}

	//获取用户列表
	@Override
	public List<UserItem> getUserList() {
		return UserDAO.getAllUser(LoginUserID);
	}

	//用户登录验证
	@Override
	public boolean loginCheckUser(String userName, String passWord) {
		if (!Check.checkName(userName)) {
			MessageDialogHelper.showConfirmDialog("用户名长度必须再3-10位之间", "错误");
			return false;
		}
		
		if (!Check.checkPass(passWord)) {// 密码验证֤
			MessageDialogHelper.showMessageDialog("密码长度必须在6-12位之间！", "错误");
			return false;
		}
		
		String path = Util.GetExecPath();
		File userFile = new File(path+"\\user\\" + userName);
		if (!userFile.exists()) {
			MessageDialogHelper.showConfirmDialog("用户不存在,请重新输入！", "错误");
			return false;
		}
		
		UserBean user = UserDAO.allUsers.get(userName);
		if (!user.getPass().equals(passWord)) 
		{
			MessageDialogHelper.showConfirmDialog("密码不正确，请重新输入！", "错误");
			return false;
		}

		InitLoginUser(userName);

		LoginUserID = userName;
		FriendUserID = null;

		return true;
	}

	//新用户注册
	@Override
	public boolean registerUser(String userName, String passWord, String checkPassword) {
		
		if (!Check.checkName(userName)) {// 用户名验证֤
			MessageDialogHelper.showMessageDialog("用户名长度必须再3-10位之间", "错误");
			return false;
		}
		
		if (!Check.checkPass(passWord)) {// 密码验证֤
			MessageDialogHelper.showMessageDialog("密码长度必须在6-12位之间！", "错误");
			return false;
		}
		
		if (!passWord.equals(checkPassword)) {
		    	MessageDialogHelper.showMessageDialog("两次密码不一致", "提示ʾ");
				return false;
		}
		
		String path = Util.GetExecPath();
		File file = new File(path+"\\user\\" + userName);
		if (!file.exists()) {// 用户不存在，可注册
			UserDAO.initUser(userName);// 初始化，为用户创建文件夹和所需文件
			LandDAO.initUserLand(userName,LoginUserLandList);// 初始化土地

			//获得随机头像和背景
			String headPic = Util.getRandHead();
			String backgroudPic = Util.getRandBackgroud();
			
			UserBean userBean = new UserBean();
			userBean.setUserID(userName);
			userBean.setUserName(userName);
			userBean.setNick(userName);
			userBean.setPass(passWord);
			userBean.setPic(headPic);
			
			UserData userData = new UserData();
			userData.setUserID(userName);
			userData.setBackgroud(backgroudPic);
			
			UserDAO.AddUser(userName,userBean,userData);// 保存用户信息
		}
		else{
			MessageDialogHelper.showMessageDialog("该用户已经被注册", "提示ʾ");
			return false;
		}
		
		return true;
	}

	//注意：T12中说切换农场要停止线程，其实可以不需要停止，维持原来旧农场进度继续执行
	public void ChangeFarm(String UserId) {//农场切换
		//判断无效数据
		if (LoginUserID == null || UserId == null)
			return;

		if (FriendUserID == null) //表示之前从来没有切换过好友农场
		{
			if (LoginUserID.equals(UserId)) //表示一直选择本农场
				return;
			//切换到好友农场
			boolean bSucc = InitFriend(UserId);
			if(bSucc) //切换成功
				FriendUserID = UserId;
		} else //之前切换过好友农场，目前正显示好友农场
		{
			if (FriendUserID.equals(UserId)) //一直选择同一个好友切换，不需要切换
				return;
			else {
				if (UserId.equals(LoginUserID)) //如果切换到当前登入用户的农场
				{
					InitLoginUser(UserId);
					FriendUserID = null;
				} else //切换到另一个好友的农场
				{
					boolean bSucc = InitFriend(UserId);
					if(bSucc) //切换成功
						 FriendUserID = UserId;
				}
			}
		}
	}

	//初始化登录用户到界面
	private void InitLoginUser(String userName)
	{
		UserData userdata = UserDAO.allUserData.get(userName);
		UserBean userbean = UserDAO.allUsers.get(userName);
		String notice = userbean.getNotice();
		if(notice == null || notice.trim().equals(""))
			notice = userName + "的开心农场";

		//更新玩家数据
		FaceHelper.setExp(String.valueOf(userdata.getExp()));
		FaceHelper.setLevel(String.valueOf(userdata.getLevel()));
		FaceHelper.setMoney(String.valueOf(userdata.getMoney()));
		FaceHelper.setBoardText(notice);
		FaceHelper.setBackGround(userdata.getBackgroud());

		//初始化土地
		LoginUserLandList.clear();
		LandDAO.getUserLand(userName,LoginUserLandList);
		for (Map.Entry<Integer, LandItemBean> entry : LoginUserLandList.entrySet()) {
			int key = entry.getKey();
			LandItemBean item = entry.getValue();
			item.InitLandState(); //开始初始化土块状态
			GameMember.getGameHelper().addLandItem(key, item);
		}

		//登入成功后设置用户的包裹
		try {
			LoginUserBagList.clear();
			PackageDAO.GetUserPackage(userName, LoginUserBagList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<PackageItem> pListBean = new ArrayList<PackageItem>();

		for (Map.Entry<Integer, PackageItemBean> entry : LoginUserBagList.entrySet()) {
			PackageItemBean PackageBean = entry.getValue();
			pListBean.add(PackageBean);
		}
		GameMember.getGameHelper().setPackageItemList(pListBean);

		//登入成功后设置用户的仓库
		try {
			LoginUserStoreHouseList.clear();
			StoreHouseDAO.GetUserStoreHouse(userName, LoginUserStoreHouseList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<StoreHouseItem> pListStoreBean = new ArrayList<StoreHouseItem>();

		for (Map.Entry<Integer, StoreHouseItemBean> entry : LoginUserStoreHouseList.entrySet()) {
			StoreHouseItemBean storeBean = entry.getValue();
			pListStoreBean.add(storeBean);
		}
		GameMember.getGameHelper().setStoreItemList(pListStoreBean);
	}

	//刷新仓库数据
	public void RefreshUserStoreHouse(String UserId)
	{
		if(UserId.equals(this.LoginUserID))
		{
			List<StoreHouseItem> pListStoreBean = new ArrayList<StoreHouseItem>();
			for (Map.Entry<Integer, StoreHouseItemBean> entry : LoginUserStoreHouseList.entrySet()) {
				StoreHouseItemBean storeBean = entry.getValue();
				pListStoreBean.add(storeBean);
			}
			GameMember.getGameHelper().setStoreItemList(pListStoreBean);
		}
		else if(UserId.equals(FriendUserID))
		{

		}
	}

	//刷新用户土地信息
	public void RefreshUserLand(String UserId)
	{
		if(UserId.equals(this.LoginUserID))
		{
			for (Map.Entry<Integer, LandItemBean> entry : LoginUserLandList.entrySet()) {
				int key = entry.getKey();
				LandItemBean item = entry.getValue();
				item.InitLandState(); //开始初始化土块状态
				GameMember.getGameHelper().addLandItem(key, item);
			}
		}
		else if(UserId.equals(FriendUserID))
		{
			for (Map.Entry<Integer, LandItemBean> entry : this.FriendUserLandList.entrySet()) {
				int key = entry.getKey();
				LandItemBean item = entry.getValue();
				item.InitLandState(); //开始初始化土块状态
				GameMember.getGameHelper().addLandItem(key, item);
			}
		}
	}

	//初始化好友基本信息
	private boolean InitFriend(String userName)
	{
		//切换到好友农场
		String path = Util.GetExecPath();
		File userFile = new File(path+"\\user\\" + userName);
		if (!userFile.exists()) {
			MessageDialogHelper.showConfirmDialog("好友不存在,可能误删除!", "错误");
			return false;
		}

		UserData userdata = UserDAO.allUserData.get(userName);
		UserBean userbean = UserDAO.allUsers.get(userName);
		String notice = userbean.getNotice();
		if(notice == null || notice.trim().equals(""))
			notice = userName + "的开心农场";
		//更新玩家数据
		FaceHelper.setExp(String.valueOf(userdata.getExp()));
		FaceHelper.setLevel(String.valueOf(userdata.getLevel()));
		FaceHelper.setMoney(String.valueOf(userdata.getMoney()));
		FaceHelper.setBoardText(notice);
		FaceHelper.setBackGround(userdata.getBackgroud());

		//初始化土地
		this.FriendUserLandList.clear();
		LandDAO.getUserLand(userName,this.FriendUserLandList);
		for (Map.Entry<Integer, LandItemBean> entry : this.FriendUserLandList.entrySet()) {
			int key = entry.getKey();
			LandItemBean item = entry.getValue();
			item.InitLandState(); //开始初始化土块状态
			GameMember.getGameHelper().addLandItem(key, item);
		}

		//切换农场设置商店为空
		GameMember.getGameHelper().setShopItemList(null);
		//切换农场设置好友的包裹
		GameMember.getGameHelper().setPackageItemList(null);
		//切换农场设置用户的仓库
		GameMember.getGameHelper().setStoreItemList(null);

		return true;
	}

	//刷新登入用户背包
	public void RefreshLoginUserPackage()
	{
		List<PackageItem> pListBean = new ArrayList<PackageItem>();

		for (Map.Entry<Integer, PackageItemBean> entry : LoginUserBagList.entrySet()) {
			PackageItemBean PackageBean = entry.getValue();
			pListBean.add(PackageBean);
		}
		GameMember.getGameHelper().setPackageItemList(pListBean);
	}

	/*
	 * 刷新用户信息，包括 等级，经验，金币等。 参数 type :类型，识别添加经验，或者其他。0：增加经验，等级 1：刷新金币，2：刷新提示
	 * 参数num 要添加的经验值
	 */
	//刷新玩家动态数据（经验变化→升级
	public void flushUserData(String UserId, int type, int num) {
		UserData user = UserDAO.allUserData.get(UserId);

		if (type == 0) {
			int exp = user.getExp() + num;
			int level = user.getLevel();
			if (exp >= level * 200) {
				level++;
				exp = exp - (level - 1) * 200;
			}
			user.setExp(exp);
			user.setLevel(level);
		}

		FaceHelper.setExp(String.valueOf(user.getExp()));
		FaceHelper.setLevel(String.valueOf(user.getLevel()));
		FaceHelper.setMoney(String.valueOf(user.getMoney()));
		UserDAO.saveUser(UserId);
	}

	//增加用户金币
	public void  AddLoginUserMoney(int money)
	{
		UserData user = UserDAO.allUserData.get(this.LoginUserID);

		int oriMoney = user.getMoney();
		user.setMoney(oriMoney + money);
		FaceHelper.setMoney(String.valueOf(user.getMoney()));
		UserDAO.saveUser(this.LoginUserID);
	}

	//减少包裹种农作无数量
	public boolean  AddLoginUserPackageItemCount(int cropID, int ncount) throws IOException {
		PackageItemBean itemBean = LoginUserBagList.get(cropID);
		if(itemBean == null)
		{
			MessageDialogHelper.showConfirmDialog("种子不存在，数据错误!", "提示");
			return false;
		}

		int oriCount = itemBean.getItemCount();
		int left = oriCount + ncount;
		if(left < 0)//说明农作物数据错误
		{
			MessageDialogHelper.showConfirmDialog("种子数据错误!", "提示");
			return false;
		}
		if(left > 0)
		{
			itemBean.setItemCount(left); //减少种子数量
		}

		if(left == 0 ) //说明种子用完了,需要删除包裹数据，不能再选择这个种子
		{
			GameMember.mouseState = 0;
			GameMember.selectedCropid = 0;
			LoginUserBagList.remove(cropID);
		}
		//保存包裹数据
		PackageDAO.SaveUserPackage(LoginUserID, LoginUserBagList);
		return true;
	}

}
