package com.zqsoft.dao;

import com.zqsoft.bean.StoreHouseItemBean;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class StoreHouseDAO {
	
	//从硬盘中读取仓库物品
	public static void GetUserStoreHouse(String UserId,HashMap<Integer, StoreHouseItemBean> storeList) throws IOException
	{
		String file = "user\\" + UserId + "\\store.txt";
		try {
			readStoreHouseList(file, storeList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//把仓库物品写入硬盘
	public static void SaveUserStoreHouse(String UserId,HashMap<Integer,StoreHouseItemBean> storeList) throws IOException
	{
		String file = "user\\" + UserId + "\\store.txt";
		try {
			saveStoreHouseList(file, storeList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 从文本中读取数据到各List中
	 */
	public static void readStoreHouseList(String filepath, HashMap<Integer,StoreHouseItemBean> storeList) throws IOException {
		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);
		String str = null;// 存放当前读取到的信息
		int cropID, count;
		while ((str = br.readLine()) != null) {
			StoreHouseItemBean tmpFruit = new StoreHouseItemBean();
			String[] tmp = str.split(":");
			cropID = Integer.valueOf(tmp[0]);
			count = Integer.valueOf(tmp[1]);
			tmpFruit.setCropBean(CropDAO.getCrop(cropID));
			tmpFruit.setItemCount(count);
			storeList.put(cropID,tmpFruit);
		}
		br.close();
		fr.close();

	}

	/*
	 * 保存数据到各List中
	 */
	public static void saveStoreHouseList(String filepath, HashMap<Integer,StoreHouseItemBean> storeList) throws IOException {
		FileWriter fw = new FileWriter(filepath);
		BufferedWriter bw = new BufferedWriter(fw);
		String str = null;// 存放当前读取到的信息

		for (Map.Entry<Integer, StoreHouseItemBean> entry : storeList.entrySet()) {
			int cropid = entry.getKey();
			StoreHouseItemBean cropBean = entry.getValue();
			int count = cropBean.getItemCount();
			str = cropid + ":" + count;
			bw.write(str);
			bw.newLine();
		}
		bw.close();
		fw.close();
	}
	
	
}
