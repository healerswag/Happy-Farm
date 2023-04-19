package com.zqsoft.bean;

import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.bean.StoreHouseItem;
import com.zqsoft.ncfarm.core.MessageDialogHelper;

import java.io.IOException;

public class StoreHouseItemBean implements StoreHouseItem {
	private CropBean cropBean = null;
	private int ItemCount = 0; //农作物数量

	public int getCropId()
	{
		return this.cropBean.getCropId();
	}
	public void setItemCount(int ItemCount)
	{
		this.ItemCount = ItemCount;
	}
	public void setCropBean(CropBean cropBean)
	{
		this.cropBean = cropBean;
	}
	
	@Override
	public String getItemPic()
	{
		return this.cropBean.getCropSeedPic();
	}

	@Override
	public String getItemPrice()
	{
		return String.valueOf(this.cropBean.getSellPrice());
	}

	@Override
	public int getItemCount()
	{
		return this.ItemCount;
	}

	@Override
	public String getItemName()
	{
		return this.cropBean.getName();
	}

	@Override	  
	public boolean itemClick()
	{
		return true;
	}

	@Override
	public int sellItem(int count)
	{
		int SelPrice = this.cropBean.getSellPrice();
		return SelPrice * count;
	}

	@Override
	public void doSellItem(int money, int count)
	{
		if (count > this.ItemCount) {
			MessageDialogHelper.showConfirmDialog("输入的数量太多，仓库不足!", "提示");
			return;
		}

		String UserId = GameMember.getUserWin().LoginUserID;
		int CurCorpID = this.cropBean.getCropId();

		this.ItemCount -= count;//先减少农作物数量
		GameMember.getUserWin().AddLoginUserMoney(money);//增加用户金币,并刷新界面,保存玩家数据

		if(this.ItemCount == 0) //都买完了，需要删除
		{
			GameMember.getUserWin().LoginUserStoreHouseList.remove(CurCorpID);
			try {
				StoreHouseDAO.SaveUserStoreHouse(UserId, GameMember.getUserWin().LoginUserStoreHouseList);
				GameMember.getUserWin().RefreshUserStoreHouse(UserId);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
