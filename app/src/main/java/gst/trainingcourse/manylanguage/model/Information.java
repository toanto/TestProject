package gst.trainingcourse.manylanguage.model;

public class Information {
    private String mHoTen;
    private String mLink;
    private String mLinkRss;
    private int mImage;

    public Information(String mHoTen, String mLink, String mLinkRss, int mImage) {
        this.mHoTen = mHoTen;
        this.mLink = mLink;
        this.mLinkRss = mLinkRss;
        this.mImage = mImage;
    }

    public String getmHoTen() {
        return mHoTen;
    }

    public void setmHoTen(String mHoTen) {
        this.mHoTen = mHoTen;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getmLinkRss() {
        return mLinkRss;
    }

    public void setmLinkRss(String mLinkRss) {
        this.mLinkRss = mLinkRss;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }
}
