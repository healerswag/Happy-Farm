package zj.healer.MusicPlayer6157.Bean;

import java.io.Serializable;

public class Singer implements Serializable {

//    public static final int MODE_SINGER = 0 ;//单曲循环
//    public static final int MODE_ORDER = 1 ;//顺序播放
//    public static final int MODE_RANDOM = 2 ;//随机播放

    private String name;
    private String sex;
    private String work;
    private String success;
    private int imgId;

    public Singer() {
    }

    public Singer(String name, String sex, String work, String success, int imgId) {
        this.name = name;
        this.sex = sex;
        this.work = work;
        this.success = success;
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
