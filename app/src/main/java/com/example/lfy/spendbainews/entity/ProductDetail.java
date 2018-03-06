package com.example.lfy.spendbainews.entity;

/**
 * 第三方借款产品详情
 */

public class ProductDetail {
    /**
     * id : 14
     * sid : null 分类id
     * title : 产品名称
     * keywords : 关键字
     * description : 产品描述
     * content : null//内容
     * t : 1500537281
     * o : 1
     * money : 1000-5000元
     * url : null
     * img : /Public/puload/201707/1500537260.png
     * deadline : 2个月 借款期限
     * s : 1 状态
     * loantime : 立即到账 特点
     * introduce : 产品介绍
     * condition : 申请条件
     * material : 申请材料
     * applycount : 4
     */

    private String em = "";

    private String id;
    private String sid;
    private String title;
    private String keywords;
    private String description;
    private String content;
    private String t;
    private String o;
    private String money;
    private String url;
    private String img;
    private String deadline;
    private String s;
    private String loantime;
    private String introduce;
    private String condition;
    private String material;
    private String applycount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title == null ? em : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords == null ? em : keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description == null ? em : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content == null ? em : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getT() {
        return t == null ? em : t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getO() {
        return o == null ? em : o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getMoney() {
        return money == null ? em : money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDeadline() {
        return deadline == null ? em : deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getS() {
        return s == null ? em : s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getLoantime() {
        return loantime == null ? em : loantime;
    }

    public void setLoantime(String loantime) {
        this.loantime = loantime;
    }

    public String getIntroduce() {
        return introduce == null ? em : introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getCondition() {
        return condition == null ? em : condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMaterial() {
        return material == null ? em : material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getApplycount() {
        return applycount == null ? em : applycount;
    }

    public void setApplycount(String applycount) {
        this.applycount = applycount;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "em='" + em + '\'' +
                ", id='" + id + '\'' +
                ", sid='" + sid + '\'' +
                ", title='" + title + '\'' +
                ", keywords='" + keywords + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", t='" + t + '\'' +
                ", o='" + o + '\'' +
                ", money='" + money + '\'' +
                ", url='" + url + '\'' +
                ", img='" + img + '\'' +
                ", deadline='" + deadline + '\'' +
                ", s='" + s + '\'' +
                ", loantime='" + loantime + '\'' +
                ", introduce='" + introduce + '\'' +
                ", condition='" + condition + '\'' +
                ", material='" + material + '\'' +
                ", applycount='" + applycount + '\'' +
                '}';
    }
}
