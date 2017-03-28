package net.youlvke.yanyuke.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/16 0016.
 */

public class SearchResultBean implements Serializable {


    public String SearchTitle;
    public int  progressBar;
    public String SearchResidue;
    public String SearchProgress;

    public SearchResultBean(String searchTitle, int progressBar, String searchResidue, String searchProgress) {
        SearchTitle = searchTitle;
        this.progressBar = progressBar;
        SearchResidue = searchResidue;
        SearchProgress = searchProgress;
    }
}
