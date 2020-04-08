package gst.trainingcourse.manylanguage.model;

public class LinkVideo {
    private String mName;
    private String[] mLink;

    public LinkVideo(String mName, String[] mLink) {
        this.mName = mName;
        this.mLink = mLink;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String[] getmLink() {
        return mLink;
    }

    public void setmLink(String[] mLink) {
        this.mLink = mLink;
    }
}
