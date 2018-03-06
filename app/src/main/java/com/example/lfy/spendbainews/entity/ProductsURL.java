package com.example.lfy.spendbainews.entity;

/**
 * 第三方跳转链接类
 */
public class ProductsURL {

    /**
     *    errorcode: 0, //错误code  0       没有错误
     10001  用户基本信息未完善
     10002  用户积分不够
     10003  用户身份认证未完善

     byborrow: 1, //可以借款
     isvisible: 1, //可以查看
     message: "可以查看"  //提示信息
      */


    private int errorcode;
    private int byborrow;
    private int isvisible;
    private String message;
    private String url;

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public int getByborrow() {
        return byborrow;
    }

    public void setByborrow(int byborrow) {
        this.byborrow = byborrow;
    }

    public int getIsvisible() {
        return isvisible;
    }

    public void setIsvisible(int isvisible) {
        this.isvisible = isvisible;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isLoan(){
        return getErrorcode() == 0 && getByborrow() == 1;
    }

    @Override
    public String toString() {
        return "ProductsURL{" +
                "errorcode=" + errorcode +
                ", byborrow=" + byborrow +
                ", isvisible=" + isvisible +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
