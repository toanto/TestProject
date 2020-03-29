package gst.trainingcourse.manylanguage.model;

public class Information {
    private String mHoTen;
    private String mLink;
    private int mImage;

    public Information(String mHoTen, String mLink, int mImage) {
        this.mHoTen = mHoTen;
        this.mLink = mLink;
        this.mImage = mImage;
    }

    public String getmHoTen() {
        return mHoTen;
    }

    public void setmHoTen(String mHoTen) {
        this.mHoTen = mHoTen;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }
}
