package com.example.lfy.spendbainews.entity;

import java.io.Serializable;

/**
 * 联系人
 */
public class ContactsBean implements Serializable {

    private String name;
    private String tel;

    public ContactsBean(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public ContactsBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "{"+
                "\""+"name" +"\":"+"\""+name +"\""+
                ","+"\""+"tel"+"\":"+"\""+tel+"\""+
                '}';
    }
}
