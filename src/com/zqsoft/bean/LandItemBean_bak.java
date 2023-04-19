package com.zqsoft.bean;

import com.zqsoft.dao.CropDAO;
import com.zqsoft.dao.LandDAO;
import com.zqsoft.dao.StoreHouseDAO;
import com.zqsoft.dao.UserDAO;
import com.zqsoft.frame.GameMember;
import com.zqsoft.guiHelper.bean.LandItem;
import com.zqsoft.ncfarm.core.MessageDialogHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LandItemBean_bak implements LandItem {
	private int landID = 0; // 每一块的土地编号
	private int cropID = -1; // 此土地上种植的农作物编号(-1代表没有种植农作物)
	private long startTime = 0;// 开始种植的时间
	// 当前所处的阶段(种子阶段为0，最大生长阶段+1为枯萎状态)
	private int nowStage = 0;// 当前的生长阶段
	private int stealCount = 0;// 被偷次数
	private int isEndStage = 0;// 是否是枯萎状态
	private int fruitNum = 0;// 成熟时果实数字
	private String stealName = "";
	private String LandPic = "";

	// 初始化标记变量
	boolean ishadbo = false;// 是否已经播种
	boolean isPick = false;// 是否已经采摘
	boolean isRipe = false;// 是否成熟
	private long leavetime;// 离开的时间
	private Timer timer;// 这块土地上的定时器
	private int nowCropFruitCount = 0;// 收获时的果实数量，刚开始播种时值为零
	//private CropGrowThread cropGrowThread = new CropGrowThread();

	public int getLandID() {
		return landID;
	}

	public void setLandID(int landID) {
		this.landID = landID;
	}

	public int getCropID() {
		return cropID;
	}

	public void setCropID(int cronid) {
		this.cropID = cronid;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public int getNowStage() {
		return nowStage;
	}

	public void setNowStage(int nowStage) {
		this.nowStage = nowStage;
	}

	public int getStealCount() {
		return stealCount;
	}

	public void setStealCount(int stealCount) {
		this.stealCount = stealCount;
	}

	public int getIsEndStage() {
		return isEndStage;
	}

	public void setIsEndStage(int isEndStage) {
		this.isEndStage = isEndStage;
	}

	public int getFruitNum() {
		return fruitNum;
	}

	public void setFruitNum(int fruitNum) {
		this.fruitNum = fruitNum;
	}

	public String getStealName() {
		return stealName;
	}

	public void setStealName(String stealName) {
		this.stealName = stealName;
	}

	//调用这个方法的时候变量都已经设置了
	public void InitLandState() {
		//cropGrowThread.setLandItemBean(this); //设置bean
		if (cropID != -1) {// 土地上有播种
			load(this.startTime);// 加载土地信息,传入开始时间
		}
	}

	//加载土地,因为数据可能保持文件，需要恢复
	private void load(long startTime) {
		ishadbo = true;
		CropBean nowCrop = CropDAO.cropList.get(cropID);
		isRipe = true;// 是否成熟
		isPick = this.isEndStage == 1 ? true : false;// 是否已经采摘
		if (isPick) {// 已经采摘，刷新枯萎图片即可
			flushPic(5);// 参数非0即可 刷新图片用isPick 判断是否枯萎
		} else {
			long nowTime = System.currentTimeMillis();
			long dTime = nowTime - startTime;// 算出时间差
			int i = 0;// 生长阶段
			int maxStageCount = nowCrop.getStageCount();
			for (i = 0; i < maxStageCount; i++) {
				int stage = i + 1;
				long stageTimes = nowCrop.getStageTime(stage) * 1000;
				dTime = dTime - stageTimes; //配置中是秒，转换为毫秒，每个阶段都是相对起始时间
				if (dTime <= 0) {// 到下一阶段时间不够，既还未成熟
					isRipe = false;
					break;
				} else {
					this.leavetime = dTime;// 剩余的时间
				}
			}
			nowStage = i; //当前是第几个阶段
			flushPic(nowStage);
			if (!isRipe) {// 还未成熟
				myTimer((int) leavetime);// 把扣除完当前生长阶段余下的时间设置给定时器
				timer.start();
			} else {
				isRipe = true;// 果实成熟
				int CurfruitNum = this.fruitNum;// 成熟时的果实数
				int CurstealNum = this.stealCount;// 被偷个数
				if (CurfruitNum == 0) {// 上次退出时还未成熟
					CurfruitNum = (int) (Math.random() * 49) + 1;// 随机1~49内个果实，50以内
					this.fruitNum = CurfruitNum;
				}
				nowCropFruitCount = CurfruitNum - CurstealNum;// 实际果实数=成熟时的果实-被偷个数
				if (nowCropFruitCount <= 0)
					nowCropFruitCount = 0;
				return;
			}
		}
	}

	/*
	 * 刷新图片 参数：生长阶段
	 */
	private void flushPic(int stage) {
		CropBean cropBean = CropDAO.cropList.get(cropID);
		if (stage == 0) {// 刚种下去
			LandPic = cropBean.getCropStartPic();
		} else if (isPick) {// 已经采摘
			LandPic = cropBean.getCropEndPic();
		} else {
			LandPic = cropBean.getNowStagePic(stage);
		}
	}

	/*
	 * 自定义定时器 参数：下一次刷新时间
	 */
	public void myTimer(int nextTime) {
		timer = new Timer(nextTime, new ActionListener() {
			// 时间到了，阶段++，刷新图片
			@Override
			public void actionPerformed(ActionEvent e) {
				nowStage++;// 还没到最大生长阶段，则阶段数++
				flushPic(nowStage);// 刷新图片
				CropBean nowCrop = CropDAO.cropList.get(cropID);
				if (nowStage != nowCrop.getStageCount()) {
					int nTimes = nowCrop.getStageTime(nowStage + 1) * 1000;
					timer.setDelay(nTimes);// 设置延迟,当前阶段到下一阶段所需时间为下一阶段乘以单位时间
				} else {
					timer.stop();
					isRipe = true;// 果实成熟
					fruitNum = (int) (Math.random() * 49) + 1;// 随机1~49内个果实，50以内
					nowCropFruitCount = fruitNum;
					return;
				}
			}
		});
	}

	@Override
	public String getItemPic() {
		return this.LandPic;
	}

	/*
	 * 播种
	 */
	@Override
	public void plantAction() {
		if (cropID > 0 || ishadbo == true)//表示有农作物
		{
			MessageDialogHelper.showConfirmDialog("菜已经种了，等收了再种吧!", "提示");
			return;
		}
		if (GameMember.selectedCropid <= 0) //表示还没有选择种子
		{
			MessageDialogHelper.showConfirmDialog("种子已经没有了，您先去商店购买一些种子!", "提示");
			return;
		}

		nowStage = 0;
		cropID = GameMember.selectedCropid; //种植这个农作物
		startTime = System.currentTimeMillis(); //当前时间的毫秒数

		try {
			boolean succ = GameMember.getUserWin().AddLoginUserPackageItemCount(GameMember.selectedCropid, -1); //种子减少1个
			if (succ == false)
				return; //处理数据错误
		} catch (IOException e) {
			e.printStackTrace();
		}

		//刷新图片
		flushPic(nowStage);
		//保存土块数据
		LandDAO.saveUserLand(GameMember.getUserWin().LoginUserID, GameMember.getUserWin().LoginUserLandList);

		//增加经验
		GameMember.getUserWin().flushUserData(GameMember.getUserWin().LoginUserID, 0, 5);
		//保存玩家数据
		UserDAO.saveUser(GameMember.getUserWin().LoginUserID);
		ishadbo = true;

		CropBean nowCrop = CropDAO.cropList.get(cropID);
		int nTimes = nowCrop.getStageTime(nowStage + 1) * 1000;
		myTimer(nTimes);
		timer.start();
	}

	//摘取果实
	@Override
	public void pickAction() {
		this.zaiquAction();
	}

	//采摘or偷取
	private void zaiquAction() {
		if (this.cropID == -1) //表示没有农作物
			return;

		if (isPick) //农作物已经枯萎
		{
			MessageDialogHelper.showConfirmDialog("农作物已经枯萎!", "提示");
			return;
		}

		if (this.isRipe == false) //果实还没有成熟
		{
			MessageDialogHelper.showConfirmDialog("别心急，还没熟透!", "提示");
			return;
		}

		if (nowCropFruitCount <= 0) //果实是否已经摘完
		{
			MessageDialogHelper.showConfirmDialog("已经摘取过了，等再播种后再摘取!", "提示");
			return;
		}

		String LoginUserId = GameMember.getUserWin().LoginUserID;
		String FriendUserId = GameMember.getUserWin().FriendUserID;
		if (LoginUserId == null) //表示数据错误，但是一般不会发生
		{
			MessageDialogHelper.showConfirmDialog("玩家数据错误，可能误删除!", "提示");
			return;
		}

		if (FriendUserId == null) //表示自己的农场
		{
			//农作物存入自己的仓库
			boolean bExist = GameMember.getUserWin().LoginUserStoreHouseList.containsKey(this.cropID); //获得仓库的农作物集合

			StoreHouseItemBean pStoreItem = null;
			if (bExist == false) //仓库还没有
			{
				pStoreItem = new StoreHouseItemBean();
				pStoreItem.setCropBean(CropDAO.cropList.get(this.cropID));
				pStoreItem.setItemCount(nowCropFruitCount);
				GameMember.getUserWin().LoginUserStoreHouseList.put(this.cropID, pStoreItem);
			} else {
				pStoreItem = GameMember.getUserWin().LoginUserStoreHouseList.get(this.cropID);
				int oriCount = pStoreItem.getItemCount();
				pStoreItem.setItemCount(oriCount + this.nowCropFruitCount);
			}
			//保存数据到仓库文件中
			try {
				StoreHouseDAO.SaveUserStoreHouse(LoginUserId, GameMember.getUserWin().LoginUserStoreHouseList);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
			GameMember.getUserWin().RefreshUserStoreHouse(LoginUserId);//刷新仓库数据
			GameMember.getUserWin().flushUserData(LoginUserId, 0, 1);//刷新经验

			this.nowCropFruitCount = 0; //表示已经都摘取了
			this.isPick = true;//表示已经都摘取了
			this.isEndStage = 1; //设置枯萎状态
			flushPic(this.nowStage); //设置枯萎图片
			LandDAO.saveUserLand(LoginUserId, GameMember.getUserWin().LoginUserLandList);//保存当前用户的土地信息
		}
		else //好友的农场
		{
			int stealNum = 1; //每次只能偷一个
			this.nowCropFruitCount = this.nowCropFruitCount - stealNum;
			if(this.nowCropFruitCount <= 0) //合法性检查
				this.nowCropFruitCount = 0;

			//农作物存入登入用户的仓库
			boolean bExist = GameMember.getUserWin().LoginUserStoreHouseList.containsKey(this.cropID); //获得仓库的农作物集合
			StoreHouseItemBean pStoreItem = null;
			if (bExist == false) //仓库还没有
			{
				pStoreItem = new StoreHouseItemBean();
				pStoreItem.setCropBean(CropDAO.cropList.get(this.cropID));
				pStoreItem.setItemCount(stealNum);
				GameMember.getUserWin().LoginUserStoreHouseList.put(this.cropID, pStoreItem);
			} else {
				pStoreItem = GameMember.getUserWin().LoginUserStoreHouseList.get(this.cropID);
				int oriCount = pStoreItem.getItemCount();
				pStoreItem.setItemCount(oriCount + stealNum);
			}
			//保存数据到仓库文件中
			try {
				StoreHouseDAO.SaveUserStoreHouse(LoginUserId, GameMember.getUserWin().LoginUserStoreHouseList);
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
			GameMember.getUserWin().RefreshUserStoreHouse(LoginUserId);//刷新仓库数据
			GameMember.getUserWin().flushUserData(LoginUserId, 0, 1);//刷新经验，方法里面有调用保存玩家数据

			this.stealCount += stealNum;  //保存偷的数量
			this.stealName += LoginUserId; //保存偷的玩家

			//保存好友的土块
			LandDAO.saveUserLand(FriendUserId, GameMember.getUserWin().FriendUserLandList);//保存好友的土地信息
		}

	}//end method

	//铲除
	@Override
	public void uprootAction() {
		this.chanchuAction();
	}

	private void chanchuAction()
	{
		String LoginUserId = GameMember.getUserWin().LoginUserID;
		String FriendUserId = GameMember.getUserWin().FriendUserID;
		if (LoginUserId == null) //表示数据错误，但是一般不会发生
		{
			MessageDialogHelper.showConfirmDialog("玩家数据错误，可能误删除!", "提示");
			return;
		}

		if (FriendUserId != null) //表示好友的农场 ,只能铲除自己的农场
			return;

		if(this.cropID == -1) //没有农作物无需铲除
			return;

		if(this.nowCropFruitCount > 0) //1.熟透了，还未摘取
		{
			boolean resutl = MessageDialogHelper.showConfirmDialog("土地上还有农作物是否需要铲除!", "提示");
			if(resutl == false) //不铲除
				return;
		}
		GameMember.getUserWin().flushUserData(LoginUserId, 0, 3);//刷新经验，方法里面有调用保存玩家数据
		//保存土块
		this.setCropID(-1);
		this.setFruitNum(0);
		this.setIsEndStage(0);
		this.setStartTime(0);
		this.setStealCount(0);
		this.setStealName(" ");
		this.LandPic = ""; //第三张土块图片
		// 初始化标记变量
		this.ishadbo = false;// 是否已经播种
		this.isPick = false;// 是否已经采摘
		this.isRipe = false;// 是否成熟

		LandDAO.saveUserLand(LoginUserId, GameMember.getUserWin().LoginUserLandList);//保存当前用户的土地信息
		GameMember.getUserWin().RefreshUserLand(LoginUserId);
	}

}//end class

