package net.youlvke.yanyuke.bean;

import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/1/5 0005.
 */

public class DragableBean {
    private int initX;
    private int initY;
    private int resourceId;
    private ImageView.ScaleType scaleType;
    private ViewGroup.LayoutParams lp;
    private String title;
    private int tag;

    public DragableBean(){

    }

    public DragableBean(int resourceId, ViewGroup.LayoutParams lp, ImageView.ScaleType scaleType, int initX, int initY){
        this.resourceId = resourceId;
        this.initX = initX;
        this.initY = initY;
        this.lp = lp;
        this.scaleType = scaleType;
    }

    public void setInitLocation(int x,int y){
        this.initX = x;
        this.initY = y;
    }

    public int getInitX() {
        return initX;
    }

    public int getInitY() {
        return initY;
    }

    public ViewGroup.LayoutParams getLp() {
        return lp;
    }

    public void setLp(ViewGroup.LayoutParams lp) {
        this.lp = lp;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public ImageView.ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.scaleType = scaleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
