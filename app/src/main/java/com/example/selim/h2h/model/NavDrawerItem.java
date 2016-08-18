package com.example.selim.h2h.model;

/**
 * Created by Ravi on 29/07/15.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    int img ;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int img) {
        this.showNotify = showNotify;
        this.title = title;
        this.img= img;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
