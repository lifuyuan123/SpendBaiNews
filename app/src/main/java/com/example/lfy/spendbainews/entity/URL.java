package com.example.lfy.spendbainews.entity;

/**
 * author:ggband
 * date:2017/8/17 14:45
 * email:ggband520@163.com
 * desc:url
 */
//https://github.com/ldoublem/AlpayRing
public class URL {

    //产品列表信息获取
    //http://dai.moxtx.com/index.php/Home/Pro/GetApiPro

    //source请求平台,page分页页数（第几页）
    //$_POST = array("appid"=>"10202820","sign"=>base64_encode('{"key":"SioXHEFuMVUPuJKLuHqZTlzWWFlfRQhe","page":"1"}'));


    //借款列表
    //http://dai.moxtx.com/index.php/Home/UserInfo/borrowingList

    //uid:用户USERID,page分页页数（第几页）
    //$_POST = array("appid"=>"10202820","sign"=>base64_encode('{"key":"SioXHEFuMVUPuJKLuHqZTlzWWFlfRQhe","uid":"89299316","page":"1"}'));



    //用户注册协议
    public static final String AGREENMENT = "http://dai.moxtx.com/index.php/Wap/Document/issue.html?issue_id=0";

    //帮助中心
    public static final String HELPCENTER = "http://dai.moxtx.com/index.php/Wap/Document/help.html";

    //反馈中心
    public static final String FKCENTER = "http://www.moxtx.com/Public/Port/img/app/wed/essay.html";

    //借款攻略
    public static final String JKCENTER = "http://dai.moxtx.com/index.php/Wap/Document/strategy.html";

    //借款秘籍
    public static final String JKMJ = "http://dai.moxtx.com/index.php/Wap/Document/issue.html?issue_id=1";

    //注册
    public static final String REGISTER = "http://dai.moxtx.com/index.php/Home/User/Register";


    //注册（判断用户名是否可用）
    public static final String PHONEEXIT = "http://dai.moxtx.com/index.php/Home/User/GetFindUserInfo";


    //微信 支付宝还款指南
    public static final String ISSUE = "http://dai.moxtx.com/index.php/Wap/Document/issue.html?issue_id=7";

/**
 * dao: 211.149.224.158  moxtxcom  F8t3x8z8
 * svn: https://211.149.224.158:8443/svn/java
 */
}