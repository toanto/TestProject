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
        mList.add(new Information("Nguyễn Thị Phi Yến", "https://www.instagram.com/jennyyen249/", R.drawable.img_dj_jenny));
        mList.add(new Information("Vân nguyễn", "https://www.instagram.com/_vanminn_/", R.drawable.img_van_min));
        mList.add(new Information("Võ Ngọc Trân", "https://www.instagram.com/vox.ngoc.traan/", R.drawable.img_vo_ngoc_tran));
        mList.add(new Information("Nhật Anh", "https://www.instagram.com/sunnaaaaaaaaa/", R.drawable.img_nhat_anh));
        mList.add(new Information("vsbg.limited", "https://www.instagram.com/vsbg.limited/", R.drawable.img_h_g));
        mList.add(new Information("69pretty.official", "https://www.instagram.com/69pretty.official/", R.drawable.img_h_g2));
    }

    public ArrayList<Information> getmList() {
        return mList;
    }
}
