package gst.trainingcourse.manylanguage.model;

import java.util.ArrayList;

import gst.trainingcourse.manylanguage.R;

public class Data {

    private ArrayList<Information> mList;

    public Data() {
        addData();
    }

    private void addData() {
        mList = new ArrayList<>();
        mList.add(new Information("Nguyễn Thị Phi Yến", "https://www.instagram.com/jennyyen249/", "https://rss.app/feeds/SwcZYsQfiP6GZQYa.xml", R.drawable.img_dj_jenny));
        mList.add(new Information("Vân nguyễn", "https://www.instagram.com/_vanminn_/", "https://rss.app/feeds/Zdy3mObWwkGAfgY4.xml", R.drawable.img_van_min));
        mList.add(new Information("Võ Ngọc Trân", "https://www.instagram.com/vox.ngoc.traan/", "https://rss.app/feeds/GE7DdkDeVWM3A3hI.xml", R.drawable.img_vo_ngoc_tran));
        mList.add(new Information("Trangzin69", "https://www.instagram.com/trangzin69/", "https://rss.app/feeds/0pBtbQgwYPa8Sv1q.xml", R.drawable.img_trang_zin69));
        mList.add(new Information("Vsbg.limited", "https://www.instagram.com/vsbg.limited/", "https://rss.app/feeds/B0K25Cb8w8X8vk4k.xml", R.drawable.img_h_g));
        mList.add(new Information("69pretty.official", "https://www.instagram.com/69pretty.official/", "https://rss.app/feeds/OANr3dbgMwggS5a2.xml", R.drawable.img_h_g2));
        mList.add(new Information("Hotgirl_asia", "https://www.instagram.com/hotgirl_asia_/", "https://rss.app/feeds/DYUXFnGOSDFmJmUo.xml", R.drawable.img_hg_asia));
        mList.add(new Information("Girl.xinh.365", "https://www.instagram.com/girl.xinh.365/", "https://rss.app/feeds/SsssYiYnrA3w7Nyo.xml", R.drawable.img_g_365));
        mList.add(new Information("Gaixinh.abc", "https://www.instagram.com/gaixinh.abc", "https://rss.app/feeds/xoTvULbSPisiBpQe.xml", R.drawable.img_gx_abc));
        mList.add(new Information("Girl_official_2k2", "https://www.instagram.com/girl_official_2k2/", "https://rss.app/feeds/gqczjMxmnHtAvYJl.xml", R.drawable.img_g_2k2));
    }

    public ArrayList<Information> getmList() {
        return mList;
    }
}
