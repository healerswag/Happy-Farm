package com.zqsoft.dao;

import com.zqsoft.bean.LandItemBean;

import java.io.*;
import java.util.HashMap;

public class LandDAO {

	//从硬盘中读取出某个玩家的所有土块
    public static void getUserLand(String userID, HashMap<Integer, LandItemBean> lands)
    {
        ReadLands(userID,lands);
    }

    private static void ReadLands(String userID, HashMap<Integer, LandItemBean> lands) {
        try {
            String landFile = "user\\" + userID + "\\land.txt";
            FileReader fileReader = new FileReader(landFile);
            BufferedReader br = new BufferedReader(fileReader);
            int landID, cropID, isEnd, fruitNum, stealCount;
            long startTime;
            String stealName;
            String str = null;
            while ((str = br.readLine()) != null) {
                String[] data = str.split(",");
                landID = Integer.valueOf(data[0]);
                cropID = Integer.valueOf(data[1]);
                startTime = Long.valueOf(data[2]);
                isEnd = Integer.valueOf(data[3]);
                fruitNum = Integer.valueOf(data[4]);
                stealCount = Integer.valueOf(data[5]);
                stealName = data[6];
                LandItemBean landData = new LandItemBean();
                landData.setLandID(landID);
                landData.setCropID(cropID);
                landData.setStartTime(startTime);
                landData.setIsEndStage(isEnd);
                landData.setFruitNum(fruitNum);
                landData.setStealCount(stealCount);
                landData.setStealName(stealName);
                lands.put(landID,landData);
            }
            br.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //把土块信息保存到硬盘文件
    public static void saveUserLand(String userID, HashMap<Integer, LandItemBean> lands)
    {
        SaveLands(userID,lands);
    }

    private static void SaveLands(String userID,HashMap<Integer, LandItemBean> lands) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter("user\\" + userID + "\\land.txt");
            bw = new BufferedWriter(fw);
            String str = null;// 保存从landDataMap读取的土地信息
            int landID, cropID, isEnd, fruitNum, stealCount;
            String stealName = "";
            long startTime;
            for (LandItemBean landData : lands.values()) {
                landID = landData.getLandID();
                cropID = landData.getCropID();
                startTime = landData.getStartTime();
                isEnd = landData.getIsEndStage();
                fruitNum = landData.getFruitNum();
                stealCount = landData.getStealCount();
                stealName = landData.getStealName();
                str = landID + "," + cropID + "," + startTime + "," + isEnd
                        + "," + fruitNum + "," + stealCount + "," + stealName;
                bw.write(str);
                bw.newLine();//
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null && fw != null) {
                try {
                    bw.close();
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            bw=null;
            fw=null;
        }
    }

    //注册用户第一次初始化的土块信息
    public static void initUserLand(String userID, HashMap<Integer, LandItemBean> lands) {
        for (int i = 1; i <= 6; i++) {
            LandItemBean pItem = new LandItemBean();
            pItem.setLandID(i);
            pItem.setCropID(-1);
            pItem.setStartTime(0);
            pItem.setIsEndStage(0);
            pItem.setFruitNum(0);
            pItem.setStealCount(0);
            pItem.setStealName("-");
            lands.put(i, pItem);
        }
        SaveLands(userID,lands);
    }


}

