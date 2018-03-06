package com.example.lfy.spendbainews.entity;

import java.io.Serializable;

/**
 * 借款产品
 */

public class Products implements Serializable {


    /**
     * id : 14
     * title : 现金借款
     * img : /Public/puload/201707/1500537260.png
     * loantime : 立即到账
     * applycount : 1
     */

    private String id;
    private String title;
    private String img;
    private String loantime;
    private String applycount;
    private String url;
    private String money;//额度
    private String deadline;//借款期限
    private String ad_slogan;//描述
    private int isaudit;//是否审核状态  1：审核  0：正式


    public int getIsaudit() {
        return isaudit;
    }

    public void setIsaudit(int isaudit) {
        this.isaudit = isaudit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLoantime() {
        return loantime;
    }

    public void setLoantime(String loantime) {
        this.loantime = loantime;
    }

    public String getApplycount() {
        return applycount;
    }

    public void setApplycount(String applycount) {
        this.applycount = applycount;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getAd_slogan() {
        return ad_slogan;
    }

    public void setAd_slogan(String ad_slogan) {
        this.ad_slogan = ad_slogan;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", loantime='" + loantime + '\'' +
                ", applycount='" + applycount + '\'' +
                ", url='" + url + '\'' +
                ", money='" + money + '\'' +
                ", deadline='" + deadline + '\'' +
                ", ad_slogan='" + ad_slogan + '\'' +
                ", isaudit=" + isaudit +
                '}';
    }
}
