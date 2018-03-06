package com.example.lfy.spendbainews.entity;


import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Transient;


@Entity
public class Login {


    /**
     * userid随机码
     * password密码
     * time注册时间
     * name真实姓名
     * state是否禁用（1和0）int
     * phone手机号码
     * source注册请求来源
     * grade  用户级别  1，小白会员 2，白银会员3 ，黄金会员 4，钻石会员
     * grade_txt  用户级别  小白会员  白银会员  黄金会员  钻石会员
     * card身份证号码
     * credit真信评分
     * uid(用户表的userid)
     * logons_cumulative登录次数
     * last_time最后一次登录时间
     * bank_card银行卡号
     * bank_belongs银行卡所属银行
     * images头像图片地址
     * usertype用户类型，（可能不会用到）
     * paypwd交易密码
     * mail邮箱
     * cname昵称
     * units单位名称
     * address常驻地址
     */

    @Id(autoincrement = true)
    private Long uID;
    private String userid;
    private String head_img;
    private String name;
    private String cname;
    private String card;
    private String units;
    private String mail;
    private String grade;
    private String grade_txt;
    private String phone;
    private String address;
    private int contacts;
    private int TheUserIsNotNewlyRegistered;//判断用户是否新注册  设置登录密码（短信注册）
    private String isMailList;//0:未添加  1：已添加通讯录

    public String getIsMailList() {
        return isMailList;
    }

    public void setIsMailList(String isMailList) {
        this.isMailList = isMailList;
    }

    public int getTheUserIsNotNewlyRegistered() {
        return TheUserIsNotNewlyRegistered;
    }

    public void setTheUserIsNotNewlyRegistered(int theUserIsNotNewlyRegistered) {
        TheUserIsNotNewlyRegistered = theUserIsNotNewlyRegistered;
    }

    public int getContacts() {
        return contacts;
    }

    public void setContacts(int contacts) {
        this.contacts = contacts;
    }

    @Transient
    private final String em = "";

    @Generated(hash = 777673892)
    @Keep
    public Login(Long uID, String userid, String head_img, String name, String cname,
                 String card, String units, String mail, String phone, String address, int contacts, int TheUserIsNotNewlyRegistered, String isMailList) {
        this.uID = uID;
        this.userid = userid;
        this.head_img = head_img;
        this.name = name;
        this.cname = cname;
        this.card = card;
        this.units = units;
        this.mail = mail;
        this.phone = phone;
        this.address = address;
        this.contacts = contacts;
        this.TheUserIsNotNewlyRegistered=TheUserIsNotNewlyRegistered;
        this.isMailList=isMailList;
    }

    @Generated(hash = 1699703042)
    public Login(Long uID, String userid, String head_img, String name, String cname, String card, String units, String mail, String grade,
                 String grade_txt, String phone, String address, int contacts, int TheUserIsNotNewlyRegistered, String isMailList) {
        this.uID = uID;
        this.userid = userid;
        this.head_img = head_img;
        this.name = name;
        this.cname = cname;
        this.card = card;
        this.units = units;
        this.mail = mail;
        this.grade = grade;
        this.grade_txt = grade_txt;
        this.phone = phone;
        this.address = address;
        this.contacts = contacts;
        this.TheUserIsNotNewlyRegistered = TheUserIsNotNewlyRegistered;
        this.isMailList = isMailList;
    }

    @Generated(hash = 1827378950)
    public Login() {
    }

    public Long getuID() {
        return uID;
    }

    public void setuID(Long uID) {
        this.uID = uID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getName() {
        return name == null ? em : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCname() {
        return cname == null ? em : cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCard() {
        return card == null ? em : card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getUnits() {
        return units == null ? em : units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getMail() {
        return mail == null ? em : mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone == null ? em : phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address == null ? em : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Login{" +
                "uID=" + uID +
                ", userid='" + userid + '\'' +
                ", head_img='" + head_img + '\'' +
                ", name='" + name + '\'' +
                ", cname='" + cname + '\'' +
                ", card='" + card + '\'' +
                ", units='" + units + '\'' +
                ", mail='" + mail + '\'' +
                ", grade='" + grade + '\'' +
                ", grade_txt='" + grade_txt + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", contacts=" + contacts +
                ", TheUserIsNotNewlyRegistered=" + TheUserIsNotNewlyRegistered +
                ", isMailList='" + isMailList + '\'' +
                ", em='" + em + '\'' +
                '}';
    }

    public Long getUID() {
        return this.uID;
    }

    public void setUID(Long uID) {
        this.uID = uID;
    }

    public String getUserName() {
        if (name == null || TextUtils.isEmpty(name))
            return "UID:" + userid;
        else
            return name;
    }

    public String getIdCard() {
        if (card == null || TextUtils.isEmpty(card))
            return "你还没有填写基本信息";
        else
            return com.example.lfy.spendbainews.Utils.TextUtils.dosubtext424(card);
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGrade_txt() {
        return this.grade_txt;
    }

    public void setGrade_txt(String grade_txt) {
        this.grade_txt = grade_txt;
    }

}
