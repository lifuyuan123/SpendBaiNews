package com.example.lfy.spendbainews.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻类
 */

public class NewsBean implements Serializable {
        /**
         * classid : 51
         * title : AI革命：最先被AI淘汰的，居然是做AI的？
         * img : /Public/puload/201802/1519802677.jpg
         * source : admin
         * publisher : 小墨妹
         * url :
         * addtime : null
         * sort : 0
         * state : 1
         * type : 普通
         */

        private String classid;
        private String title;
        private String img;
        private String source;
        private String publisher;
        private String url;
        private Object addtime;
        private String sort;
        private String state;
        private String type;

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getAddtime() {
            return addtime;
        }

        public void setAddtime(Object addtime) {
            this.addtime = addtime;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    @Override
    public String toString() {
        return "NewsBean{" +
                "classid='" + classid + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", source='" + source + '\'' +
                ", publisher='" + publisher + '\'' +
                ", url='" + url + '\'' +
                ", addtime=" + addtime +
                ", sort='" + sort + '\'' +
                ", state='" + state + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
