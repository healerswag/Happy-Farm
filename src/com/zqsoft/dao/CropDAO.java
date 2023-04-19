package com.zqsoft.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.zqsoft.bean.CropBean;
import com.zqsoft.bean.ShopItemBean;
import com.zqsoft.bean.UserBean;
import com.zqsoft.frame.Util;
import com.zqsoft.guiHelper.bean.ShopItem;
import com.zqsoft.guiHelper.net.bean.UserItem;


public class CropDAO {
	public static HashMap<Integer,CropBean> cropList = new HashMap<Integer,CropBean>();
	
	//获得所有作物信息用于商店接口
	public static List<ShopItem> getAllCrops() {
		List<ShopItemBean> pListBean = new ArrayList<ShopItemBean>();
		
		for (Map.Entry<Integer, CropBean> entry : cropList.entrySet()) {
			CropBean cropBean = entry.getValue();
			ShopItemBean shopItemBean = new ShopItemBean(cropBean);
			pListBean.add(shopItemBean);
		}
		Collections.sort(pListBean);
		
		List<ShopItem> pListItem = new ArrayList<ShopItem>();
		for(int i = 0; i < pListBean.size(); i++)
			pListItem.add(pListBean.get(i));
		
		return pListItem;
	}
	
	//初始化，读取所有农作物信息
	public static void InitAllCrop()
	{
		try {
			String path = "resources\\crops";
			File cropFileFolder = new File(path);
			String[] cropFile = cropFileFolder.list();//
			Properties properties = new Properties();

			for (int i = 1; i <= cropFile.length; i++) {
				String cropPath = cropFileFolder + "\\cron" + i + "\\cron.properties";
				FileInputStream fin = new FileInputStream(cropPath);
				//读取数据
				InputStreamReader isr = new InputStreamReader(fin, "GBK");
				properties.load(isr);
				CropBean tmpCropBean = new CropBean();
				
				int CropId = Integer.valueOf(properties.getProperty("ITEM_ID"));
				tmpCropBean.setCropId(CropId); //cropId
				tmpCropBean.setName(properties.getProperty("ITEM_NAME")); //name
				tmpCropBean.setPrice(Integer.valueOf(properties.getProperty("ITEM_PRICE"))); //seedMoney
				tmpCropBean.setSellPrice(Integer.valueOf(properties.getProperty("ITEM_SELL_MONEY"))); // sellMoney
							
				int maxstage = Integer.valueOf(properties.getProperty("ITEM_STAGE"));
				tmpCropBean.setStageCount(maxstage); //stageCount

				HashMap<Integer,Integer> pList = new HashMap<Integer,Integer>();
				for(int j = 1; j <= maxstage; j++)
				{
					String key = "ITEM_STAGE_NEXT_TIME_" + j;
					int StageTime = Integer.valueOf(properties.getProperty(key));
					pList.put(j, StageTime);
				}
				tmpCropBean.setStageTime(pList);
				
				//process stage pic
				HashMap<Integer,String> pPicList = new HashMap<Integer,String>();
				for(int j = 1; j <= maxstage; j++)
				{
					String key = "ITEM_STAGE_" + j;
					String StagePic = String.valueOf(properties.getProperty(key));
					pPicList.put(j, StagePic);
				}
				tmpCropBean.setStagePic(pPicList);
				
				tmpCropBean.setBuyLevel(Integer.valueOf(properties.getProperty("ITEM_NEED_LEVEL")));//buyLevel
				cropList.put(CropId, tmpCropBean);
				
				fin.close();
				properties.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//根据id获得对应信息
	public static CropBean getCrop(int cropID) {
		return cropList.get(cropID);
	}

}
