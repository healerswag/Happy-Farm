package com.zqsoft.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.zqsoft.bean.CropBean;
import com.zqsoft.bean.PackageItemBean;
import com.zqsoft.bean.ShopItemBean;
import com.zqsoft.frame.Util;


public class PackageDAO {
	//从硬盘中获取背包物品数据
	public static void GetUserPackage(String UserId,HashMap<Integer,PackageItemBean> bagList) throws IOException
	{
		String file = "user\\" + UserId + "\\bag.txt";
		try {
			readPackageList(file, bagList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//把背包物品数据存入硬盘文件
	public static void SaveUserPackage(String UserId,HashMap<Integer,PackageItemBean> bagList) throws IOException
	{
		String file = "user\\" + UserId + "\\bag.txt";
		try {
			savePackageList(file, bagList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 从文本中读取数据到各List中
	 */
	public static void readPackageList(String filepath, HashMap<Integer,PackageItemBean> bagList) throws IOException {
		FileReader fr = new FileReader(filepath);
		BufferedReader br = new BufferedReader(fr);
		String str = null;// 存放当前读取到的信息
		int cropID, count;
		while ((str = br.readLine()) != null) {
			PackageItemBean tmpFruit = new PackageItemBean();
			String[] tmp = str.split(":");
			cropID = Integer.valueOf(tmp[0]);
			count = Integer.valueOf(tmp[1]);
			tmpFruit.setCropBean(CropDAO.getCrop(cropID));
			tmpFruit.setItemCount(count);
			bagList.put(cropID,tmpFruit);
		}
		br.close();
		fr.close();

	}

	/*
	 * 保存数据到各List中
	 */
	public static void savePackageList(String filepath, HashMap<Integer,PackageItemBean> bagList) throws IOException {
		FileWriter fw = new FileWriter(filepath);
		BufferedWriter bw = new BufferedWriter(fw);
		String str = null;// 存放当前读取到的信息

		for (Map.Entry<Integer, PackageItemBean> entry : bagList.entrySet()) {
			int cropid = entry.getKey();
			PackageItemBean cropBean = entry.getValue();
			int count = cropBean.getItemCount();
			str = cropid + ":" + count;
			bw.write(str);
			bw.newLine();
		}
		bw.close();
		fw.close();
	}
	
	
}
