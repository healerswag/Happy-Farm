package com.zqsoft.thread;

import com.zqsoft.bean.LandItemBean;

public class CropGrowThread extends Thread {
    private LandItemBean landItemBean = null;
    private boolean IsRun = true;
    public void StopGrown(){
        this.IsRun = false;
    }

    public void setLandItemBean(LandItemBean landItemBean){
        this.landItemBean = landItemBean;
        this.setName(String.valueOf("第" + landItemBean.getLandID()+"个土块线程"));
    }

    @Override
    public void run() {

       while(IsRun)
       {
           try{
               this.landItemBean.growing();
               Thread.sleep(300);
           }
           catch (InterruptedException e)
           {
               e.printStackTrace();
           }
       }
    }
}