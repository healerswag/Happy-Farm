package zj.healer.MusicPlayer6157.Bean;

import java.util.ArrayList;
import java.util.List;

import zj.healer.MusicPlayer6157.R;

public class SingerUtils {
    private static final String singerName[] = {"周杰伦", "陈奕迅", "林俊杰", "邓紫棋", "薛之谦", "周深",
            "李荣浩", "许嵩", "张杰", "刘若英", "周笔畅", "华晨宇"};

    private static final String singerSex[] = {"男", "男", "男", "女", "男", "男", "男", "男", "男", "女", "女", "男"};

    private static final String singerWork[] = {"《七里香》、《花海》、《青花瓷》、《晴天》", "《爱情转移》、《十年》、《浮夸》、《K歌之王》", "《江南》、《曹操》、《可惜没如果》、《修炼爱情》",
    "《光年之外》、《泡沫》、《奇迹》、《倒数》", "《认真的雪》、《演员》、《丑八怪》、《绅士》", "《大鱼》、《化身孤岛的鲸》、《和光同尘》、《归处》", "《老街》、《李白》、《模特》、《喜剧之王》",
    "《自定义》、《寻雾启示》、《苏格拉没有底》、《梦游计》","《这，就是爱》、《勿忘心安》、《我们都一样》、《明天过后》", "《后来》、《很爱很爱你》、《成全》、《为爱痴狂》",
            "《笔记》、《岁月神偷》、《最美的期待》、《密友》", "《齐天》、《我管你》、《烟火里的尘埃》、《国王与乞丐》"
    };

    private static final String singerSuccess[] = {"四届世界音乐大奖最畅销亚洲艺人、中华区8次年度销量冠军（最多）、悉尼演唱会票房美国公告牌第二、美国CNN亚洲最具影响力人物、2012年福布斯中国名人榜第一、" +
            "MTV日本录影带奖亚洲杰出艺人、两届MTV亚洲大奖最受欢迎艺人、连续7年IFPI十大销量国语唱片、华语乐坛十年领袖人物、华语乐坛十年最佳男歌手、中国十大青年领袖、MTV亚洲大奖最受欢迎男歌手、" +
            "美国MTV电影最佳新人提名、世界十大鬼才音乐人之一、QQ音乐年度盛典最佳全能艺人", "香港十大劲歌金曲最受欢迎男歌手，香港十大中文金曲全国最佳男歌手，全球华语歌曲排行榜最佳男歌手",
            "新加坡杰出青年 两届台湾金曲奖最佳国语男歌手 第15届台湾金曲奖最佳新人 30个台湾Hito流行音乐奖项 27个新加坡词曲版权协会奖项 25个中国TOP排行榜奖项 25个中国原创音乐流行榜奖项 24个全球华语歌曲排行榜奖项 第5届通商中国青年奖",
            "第27届美国KCA亚洲最受欢迎艺人、IPFI香港唱片全年最高销量女歌手、第24届金曲奖最佳国语女歌手提名、最年轻登上红馆开个唱的女歌手、叱咤乐坛生力军金奖首位未成年获奖、第二届 QQ音乐年度盛典年度最佳港台女歌手、QQ音乐巅峰榜人气搜索奖",
            "2008雪碧中国原创音乐流行榜最具潜质歌手大奖、2009MusicRadio中国TOP排行榜年度最佳舞台演绎奖、第二十届华语榜中榜最受欢迎唱作歌手 、2016Music Radio年度最佳男歌手 、2016酷音乐亚洲盛典年度最受欢迎创作人",
            "2021中国歌曲top排行榜最受欢迎男歌手 、2020TMEA腾讯音乐娱乐盛典年度最佳内地男歌手、2020腾讯视频星光大赏年度最受欢迎歌手、第27届东方风云榜年度影视剧歌手、  第23届全球华语榜中榜内地地区榜中榜最受欢迎男歌手、第23届东方风云榜最佳新人",
            "第二届音悦v榜 年度最佳音悦人、第25届金曲奖最佳新人奖、第二届QQ音乐年度盛典年度乐坛新势力奖",
            "2018华人歌曲音乐盛典年度最佳创作男歌手 2018嗨典年度最佳男歌手 第14届中国华鼎奖华语年度最受欢迎原创歌手 第19、20届东方风云榜最佳唱作人 第17届全球华语榜中榜传媒推荐歌手 第7届中国移动无线音乐咪咕汇最佳人气男歌手 2012亚洲时尚盛典年度时尚原创音乐人 " +
                    "2011/2012中国歌曲排行榜年度金曲奖 第15届音乐风云榜年度媒体关注艺人 第6届腾讯星光大典内地年度男歌手 第19、20届东方风云榜十大金曲奖 第23届东方风云榜最佳作曲奖",
            "33次荣获最受欢迎男歌手奖、2014年全美音乐奖“年度国际艺人奖”、2010年韩国MAMA“亚洲之星”大奖、连续四年中歌榜最受欢迎男歌手奖、连续五届全球华语榜中榜最受欢迎男歌手、连续四年音乐之声最受欢迎男歌手、三届城市之音至尊金榜年度冠军王、香港TVB8“内地观众最爱男歌手”、蝉联两届中国TOP排行榜最佳男歌手、" +
                    "两届劲歌王内地最受欢迎男歌手、三届东南劲爆榜最受欢迎男歌手、两届雪碧榜最受欢迎男歌手、中国儿童慈善奖—-突出贡献奖",
            "两届亚太影展最佳女主角 大众电影百花奖最佳女主角 东京国际电影节最佳女主角 华语电影传媒大奖最佳女主角 香港电影金紫荆奖最佳女主角 香港电影金像奖最佳女配角 巴黎国际电影节特别表扬奖 台湾电影金马奖评审团大奖 台湾电影金马奖最佳电影歌曲",
            "亚洲音乐节最佳歌手 新加坡金曲奖海外杰出歌手 音乐风云榜最佳女歌手 音乐风云榜最受欢迎女歌手 东方风云榜亚洲人气歌手 东方风云榜最佳女歌手 中国TOP排行榜最佳女歌手 中国TOP排行榜最受欢迎女歌手 香港tvb8金曲榜全国最爱女歌手 香港新城国语力最受欢迎女歌手 破内地歌手台湾G-music最高销量",
            "2013快乐男声全国总冠军、第六届音乐风云榜新人盛典最受欢迎男歌手、亚洲新歌榜2016年度盛典 最佳男歌手、2018微博之夜 年度最佳歌手、2019第十三届音乐盛典咪咕汇 年度内地最受欢迎男歌手"
    };

    private final static int singerImage[] = {
            R.mipmap.zhoujielun, R.mipmap.chenyixun, R.mipmap.linjunjie, R.mipmap.dengziqi, R.mipmap.xuezhiqian, R.mipmap.zhoushen, R.mipmap.lironghao, R.mipmap.xusong, R.mipmap.zhangjie, R.mipmap.liuruoying, R.mipmap.zhoubichang, R.mipmap.huachenyu
    };

    public static List<Singer> getSingerList() {
        List<Singer> list = new ArrayList<>();
        for (int i = 0; i < singerName.length; i++) {
            Singer singer = new Singer(singerName[i], singerSex[i], singerWork[i], singerSuccess[i], singerImage[i]);
            list.add(singer);
        }
        return list;
    }

}
