package com.zqsoft.frame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import com.zqsoft.bean.ImageBean;
import com.zqsoft.bean.PackageItemBean;
import com.zqsoft.dao.CropDAO;


public class Util {
	
	private static Random rand = new Random();
	public static Vector<ImageBean> AllLandPic = new Vector<ImageBean>();
	//获取程序执行的当前路径
	public static String GetExecPath()
	{
		String path = "";
		try {
			File directory = new File(".");
			path = directory.getCanonicalPath();
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
		return path;
		
	}
	
	//获取所有背景图片
	public static Vector<ImageBean> getAllBackgroudPic() {
		Vector<ImageBean> items = new Vector<ImageBean>();
		File picFileFolder = new File("resources\\background\\");
		int count = picFileFolder.list().length;
		for (int i = 1; i <= count; i++) {
			ImageBean item = new ImageBean("resources\\background\\" + i + ".png");
			items.add(item);
		}
		return items;
	}
	
	//获取所有头像图片
	public static Vector<ImageBean> getAllFacePic() {
		Vector<ImageBean> items = new Vector<ImageBean>();
		File picFileFolder = new File("resources\\head\\");
		int count = picFileFolder.list().length;
		for (int i = 1; i <= count; i++) {
			ImageBean item = new ImageBean("resources\\head\\" + i + "-1.gif");
			items.add(item);
		}
		return items;
	}

	//获取所有土地图片
	public static void getAllLandPic() {
		AllLandPic.clear();
		File picFileFolder = new File("resources\\land\\");
		int count = picFileFolder.list().length;
		for (int i = 1; i <= count; i++) {
			ImageBean item = new ImageBean("resources\\land\\" + i + ".png");
			AllLandPic.add(item);
		}
	}

	//获得土块图片
	public static String getRandLandPic()
	{
		int size = AllLandPic.size();
		int randnumber = getRandNumber(size);
		return AllLandPic.get(randnumber).getPicPath();
	}

	//获得某个随机数
	public static int getRandNumber(int scale)
	{
		return rand.nextInt(scale);
	}
	
	//随机获得头像图片
	public static String getRandHead()
	{
		Vector<ImageBean> heads = getAllFacePic();
		int size = heads.size();
		int randnumber = getRandNumber(size);
		return heads.get(randnumber).getPicPath();
	}
	
	//随机获得背景图片
	public static String getRandBackgroud()
	{
		Vector<ImageBean> backgrouds = getAllBackgroudPic();
		int size = backgrouds.size();
		int randnumber = getRandNumber(size);
		return backgrouds.get(randnumber).getPicPath();
		
	}
	

	
}

