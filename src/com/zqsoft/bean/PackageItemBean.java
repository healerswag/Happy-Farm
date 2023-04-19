package com.zqsoft.bean;

import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.bean.PackageItem;

public class PackageItemBean implements PackageItem {
	private CropBean cropBean = null;//农作物是哪一种
	private int ItemCount = 0;//农作物数量
	
	public PackageItemBean()
	{
	}

	//农作物图片
	@Override
	public String getItemPic()
	{
		return this.cropBean.getCropSeedPic();
	}
	
	//点击背包触发的事件
	@Override	  
	public boolean itemClick()
	{
		GameMember.mouseState = 1;//是否选择种子，1为选
		GameMember.selectedCropid = this.cropBean.getCropId();//选择哪一个种子种植
		return true;
	}

	//取消种植
	@Override
	public void cancelClick()
	{
		GameMember.mouseState = 0;
		GameMember.selectedCropid = 0;
	}
	
	//获得农作物名称
	@Override
	public String getItemName()
	{
		return this.cropBean.getName();
	}
	
	//背包中农作物数量
	@Override
	public int getItemCount()
	{
		return this.ItemCount;
	}
	
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
	  
}
