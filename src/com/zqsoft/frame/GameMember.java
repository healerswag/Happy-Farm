package com.zqsoft.frame;

import java.util.*;

import com.zqsoft.bean.CropBean;
import com.zqsoft.dao.CropDAO;
import com.zqsoft.dao.UserDAO;
import com.zqsoft.guiHelper.GameHelper;
import com.zqsoft.guiHelper.bean.LandItem;
import com.zqsoft.guiHelper.bean.ShopItem;
import com.zqsoft.guiHelper.net.bean.UserItem;
import com.zqsoft.guiHelper.net.bean.UserWindow;

public class GameMember {
	private static GameHelper gameHelper = new GameHelper();
	private static UserWin userWin = null;
	private static List<ShopItem> items = new ArrayList<ShopItem>();
	public static GameHelper getGameHelper() { return gameHelper;}
	public static UserWin getUserWin() { return userWin;}

	public static int mouseState = 0; //0未点击 1:已经点击
	public static int selectedCropid = 0; //选中的种子编号

	private static void loadShop()
	{
		CropDAO.InitAllCrop();//加载所有农作物
		//初始化商店
		List<ShopItem> pList = CropDAO.getAllCrops();
		gameHelper.setShopItemList(pList);
	}
	
	private static void loadAllUser()
	{
		 UserDAO.InitAllUser();
	}

	private static void loadAllLand()
	{
		Util.getAllLandPic();
	}

	public static void loadBackGround()//背景图片
	{
		List<String> list = new ArrayList<String>();
		list.add("resources/background/1.png");
		list.add("resources/background/2.png");
		list.add("resources/background/3.png");
		list.add("resources/background/4.png");
		gameHelper.setBackground(list);
	}

	public static void main(String[] args) {
		try
		{
			loadAllUser(); //加载所有用户到集合中
			loadShop();//加载所有商店
			loadAllLand();//土地图片
			userWin = new UserWin();
			UserWindow userWindow = userWin;
			gameHelper.loadMod(userWindow);
		}
		catch(Exception ex)
		{
			System.out.println("Error:"+ex.toString());
		}
		
	}
	
	

}
