package com.example.lfy.spendbainews.entity;

import java.io.Serializable;

/**
 * 新闻分类
 */

public class NewsType implements Serializable {

    private String name;
    private String classid;
    private String imgurl;
    private String s;
    private String o;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "name='" + name + '\'' +
                ", classid='" + classid + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", s='" + s + '\'' +
                ", o='" + o + '\'' +
                '}';
    }
}
