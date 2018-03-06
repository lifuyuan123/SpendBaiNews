package com.example.lfy.spendbainews.entity;

/**
 * 引导页实体类
 */

public class GuideBean {
    private String img;

    public String getImg() {
            return img;
        }

    public void setImg(String img) {
            this.img = img;
        }

    @Override
    public String toString() {
        return "GuideBean{" +
                "img='" + img + '\'' +
                '}';
    }
}
