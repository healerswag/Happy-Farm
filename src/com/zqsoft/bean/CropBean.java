package com.zqsoft.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.zqsoft.guiHelper.bean.ShopItem;

public class CropBean implements Cloneable, Comparable<CropBean> {

	private int cropId;// 农作物ID ITEM_ID
	private String name;// 农作物名称 ITEM_NAME
	private int Price;// 农作物商店购买金币数 ITEM_PRICE
	private int sellPrice;// 农作物成熟销售的金币数ITEM_SELL_MONEY
	private String seedPic;// 种子图片ƬITEM_SEED_PIC
	private int stageCount;// 农作物生成总阶段ITEM_STAGE
	public HashMap<Integer,Integer> pStageTimeList = new HashMap<Integer,Integer>(); //农作物各个阶段的时间
	public HashMap<Integer,String> pStagePicList = new HashMap<Integer,String>(); //农作物各个阶段的图片

	//private int unitTime1 = 0;//ITEM_STAGE_NEXT_TIME_1
	//private int unitTime2 = 0;// ITEM_STAGE_NEXT_TIME_2
	//private int unitTime3 = 0;// ITEM_STAGE_NEXT_TIME_3
	//private int unitTime4 = 0;// ITEM_STAGE_NEXT_TIME_4
	//private int unitTime5 = 0;// ITEM_STAGE_NEXT_TIME_5
	private int buyLevel;// 农作物商店购买级别ITEM_NEED_LEVEL
	private String beginPic; //开发播种是在土地上的显示图片
	private String endPic; //农作物摘取之后的图片

	public CropBean() {
		super();
	}

	@Override
	public String toString() {
		return "";
	}

	public String getCropSeedPic() {
		return "resources\\crops\\cron" + cropId + "\\seed.png";
	}

	public String getCropStartPic() {
		return "resources\\crops\\cron" + cropId + "\\cron_start.png";
	}

	public String getCropEndPic() {
		return "resources\\crops\\cron" + cropId + "\\cron_end.png";
	}
	
	public String getCropMaxStageCountPic() {
		return "resources\\crops\\cron" + cropId + "\\" + stageCount + ".png";
	}

	public String getNowStagePic(int stageNow) {
		return "resources\\crops\\cron" + cropId + "\\" + stageNow + ".png";
	}

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int Price) {
		this.Price = Price;
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getStageCount() {
		return stageCount;
	}

	public void setStageCount(int stageCount) {
		this.stageCount = stageCount;
	}

	public int getStageTime(int stage) {
		if (stage < 0 || stage > this.pStageTimeList.size())
			return 0;
		return (int)this.pStageTimeList.get(stage);
	}

	public void setStageTime(HashMap<Integer,Integer> pList) {
		this.pStageTimeList = pList;
	}

	public String getStagePic(int stage) {
		if (stage < 0 || stage > this.pStagePicList.size())
			return "";
		return (String)this.pStagePicList.get(stage);
	}

	public void setStagePic(HashMap<Integer,String> pList) {
		this.pStagePicList = pList;
	}
	
	public int getBuyLevel() {
		return buyLevel;
	}

	public void setBuyLevel(int buyLevel) {
		this.buyLevel = buyLevel;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	//物品先按等级排序，再按价格排序
	@Override
	public int compareTo(CropBean obj )
	{
		int result =  this.buyLevel - obj.getBuyLevel();
		if(result == 0)
			result = this.Price - obj.getPrice();
		
		return result;
	}
	
}
