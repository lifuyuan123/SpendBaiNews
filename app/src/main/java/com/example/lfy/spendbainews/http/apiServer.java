package com.example.lfy.spendbainews.http;



import com.example.lfy.spendbainews.entity.BaseResponse;
import com.example.lfy.spendbainews.entity.GuideBean;
import com.example.lfy.spendbainews.entity.Login;
import com.example.lfy.spendbainews.entity.NewsBean;
import com.example.lfy.spendbainews.entity.NewsType;
import com.example.lfy.spendbainews.entity.ProductDetail;
import com.example.lfy.spendbainews.entity.Products;
import com.example.lfy.spendbainews.entity.ProductsURL;
import com.example.lfy.spendbainews.entity.Verify;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * author:ggband
 * data:2017/12/13 001310:27
 * email:ggband520@163.com
 * desc:请求接口
 */

public interface apiServer {

    //公共域名
    String API_SERVER_URL = "http://dai.moxtx.com";

    //通用功能请求
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> common(@Url String path, @FieldMap Map<String, String> parmes);

    //引导页面图片
    @GET("/index.php/Home/Index/getAppGraph")
    Observable<BaseResponse<List<GuideBean>>>getAppGraph();

    //账号登陆
    @FormUrlEncoded
    @POST("/index.php/Home/Login/login")
    Observable<BaseResponse<Login>> accountLogin(@FieldMap Map<String, String> parmes);

    //短信登陆
    @FormUrlEncoded
    @POST("/index.php/Home/Login/SmsLogin")
    Observable<BaseResponse<Login>> mesLogin(@FieldMap Map<String, String> parmes);

    //短信登陆获取短信验证码
    @FormUrlEncoded
    @POST("/index.php/Home/User/SendSms")
    Observable<BaseResponse<Verify>> verify(@FieldMap Map<String, String> parmes);

    //修改用户信息
    @FormUrlEncoded
    @POST("/index.php/Home/User/SaveUserInfo")
    Observable<BaseResponse<Object>> updataUserInfo(@FieldMap Map<String, String> parmes);

    //通用功能请求
    @FormUrlEncoded
    @POST
    Observable<BaseResponse<Object>> commonExecute(@Url String path, @FieldMap Map<String, String> parmes);

    //获取新闻分类
    @FormUrlEncoded
    @POST("/index.php/Home/News/GetNewsClass")
    Observable<BaseResponse<List<NewsType>>> GetNewsClass(@FieldMap Map<String, String> parmes);

    //新闻列表
    @FormUrlEncoded
    @POST("/index.php/Home/News/GetApiNews")
    Observable<BaseResponse<List<NewsBean>>> GetApiNews(@FieldMap Map<String, String> parmes);

    //第三方产品列表详情
    @FormUrlEncoded
    @POST("/index.php/Home/Pro/GetApiProDetails")
    Observable<BaseResponse<ProductDetail>> GetApiProDetails(@FieldMap Map<String, String> parmes);

    //第三方跳转url
    @FormUrlEncoded
    @POST("/index.php/Home/Com/getUserIsVisible")
    Observable<BaseResponse<ProductsURL>> loanProduct(@FieldMap Map<String, String> parmes);

    //修改用户信息
    @FormUrlEncoded
    @POST("/index.php/Home/User/SaveUserInfo")
    Observable<BaseResponse<Object>> updataUserInfoo(@FieldMap Map<String, String> parmes);

    //第三方产品列表
    @FormUrlEncoded
    @POST("/index.php/Home/Pro/GetApiPro")
    Observable<BaseResponse<List<Products>>> products(@FieldMap Map<String, String> parmes);
}
