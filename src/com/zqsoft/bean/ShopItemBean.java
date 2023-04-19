package com.zqsoft.bean;

import com.zqsoft.dao.PackageDAO;
import com.zqsoft.dao.UserDAO;
import com.zqsoft.frame.GameMember;
import com.zqsoft.frame.UserWin;
import com.zqsoft.guiHelper.FaceHelper;
import com.zqsoft.guiHelper.bean.PackageItem;
import com.zqsoft.guiHelper.bean.ShopItem;
import com.zqsoft.ncfarm.core.MessageDialogHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopItemBean implements ShopItem, Comparable<ShopItemBean>{
	private CropBean corpBean = null;
	
	public ShopItemBean(CropBean corpBean)
	{
		this.corpBean = corpBean; 
	}
	
	public void setCorpBean(CropBean corpBean)
	{
		this.corpBean = corpBean; 
	}
	
	//获得商店物品名称
	@Override  
	public String getItemName()
	{
		return this.corpBean.getName();
	}
	//商店物品等级
	@Override
	public int getItemBuyLevel()
	{
		return this.corpBean.getBuyLevel();
	}
	//商店销售价格
	@Override
	public String getItemPrice()
	{
		return String.valueOf(this.corpBean.getPrice());
	}
	//获得商店物品种子图片
	@Override
	public String getItemPic()
	{
		return this.corpBean.getCropSeedPic();
	}
	//点击商店物品时触发的事件
	@Override
	public boolean itemClick()
	{
		int SeedLevel = corpBean.getBuyLevel();
		UserData userData = GameMember.getUserWin().getLoginUser();
		if(userData.getLevel() < SeedLevel) {
			MessageDialogHelper.showMessageDialog("你的等级达不到购买该种子的等级！", "错误");
			return false;
		}
		return true;
	}
	//输入的需购买数量，计算并返回总价
	@Override
	public int buyItem(int count)
	{
		int price = corpBean.getPrice();
		return price * count;
	}
	//商品购买，以下money为总价格，count为数量
	@Override
	public void doBuyItem(int money, int count)
	{
		UserData userData = GameMember.getUserWin().getLoginUser();
		int userMoney = userData.getMoney();
		if(userMoney < money)
		{
			MessageDialogHelper.showMessageDialog("金币数不够!", "错误");
			return;
		}
		//减少金币数
		int leftMoney = userMoney - money;
		userData.setMoney(leftMoney);

		//更新种子背包
		HashMap<Integer,PackageItemBean> LoginUserBagList = UserWin.LoginUserBagList;
		PackageItemBean item = LoginUserBagList.get(this.corpBean.getCropId());
		if(item == null) //新种子，增加
		{
			PackageItemBean packItemBean = new PackageItemBean();
			packItemBean.setCropBean(this.corpBean);
			packItemBean.setItemCount(count);
			LoginUserBagList.put(this.corpBean.getCropId(),packItemBean);
		}
		else//已有，更新数量
		{
			int sumcount = item.getItemCount() + count;
			item.setItemCount(sumcount);
		}
		//把玩家数据写入文件
		UserDAO.saveUser(userData.getUserID());
		try {
			PackageDAO.SaveUserPackage(userData.getUserID(),GameMember.getUserWin().LoginUserBagList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//刷新界面
		FaceHelper.setMoney(String.valueOf(userData.getMoney()));
		//刷新背包
		GameMember.getUserWin().RefreshLoginUserPackage();
	}
	//调用排序
	@Override
	public int compareTo(ShopItemBean obj )
	{
		return this.corpBean.compareTo(obj.corpBean);
	}
}
