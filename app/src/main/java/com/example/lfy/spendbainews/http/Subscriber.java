package com.example.lfy.spendbainews.http;


import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.Utils.RequestHelpr;
import com.example.lfy.spendbainews.Utils.Utils;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.entity.GuideBean;
import com.example.lfy.spendbainews.entity.Login;
import com.example.lfy.spendbainews.entity.NewsBean;
import com.example.lfy.spendbainews.entity.NewsType;
import com.example.lfy.spendbainews.entity.ProductDetail;
import com.example.lfy.spendbainews.entity.Products;
import com.example.lfy.spendbainews.entity.ProductsURL;
import com.example.lfy.spendbainews.entity.Verify;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.ResponseBody;


/**
 * author:ggband
 * data:2017/12/13 001311:17
 * email:ggband520@163.com
 * desc:
 */

public class Subscriber extends ObjectLoad{

    //请求接口
    private apiServer apiserver;
    //缓存接口
    private CacheProvider cacheProvider;
    //加密类
    private RequestHelpr requestHelpr;

    //初始化Subscriber后就通过retrofit将apiServer的实例返回
    public Subscriber() {
        apiserver=ApiClient.getInstence().create(apiServer.class);
        //缓存的服务
        cacheProvider = new RxCache.Builder()
                .persistence(Utils.getContext().getFilesDir(), new GsonSpeaker())
                .using(CacheProvider.class);
        requestHelpr = RequestHelpr.getInstance();
    }


    //通过实例此类得到apiserver接口
    private static Subscriber subscriber;
    public static Subscriber getInstemce(){
        if (subscriber==null){
            subscriber=new Subscriber();
        }
        return subscriber;
    }


    //获取干货图片的方法（不缓存）
//    public void getGank(String cout,String page,String type, ApiCallBack<Object> callBack){
//        //完成一次消息订阅
//        observe( apiserver.getGank(cout,page,type)).subscribe(callBack );//订阅
//    }

    //获取干货图片的方法（缓存）
    //EvictProvider  new出新对象传入布尔值  ture代表不缓存 直接网络请求 false代表用缓存
    //DynamicKey  new出新对象  传入page数  缓存的页面tag
    //DynamicKeyGroup存储有两个key,DynamicKeyGroup是在DynamicKey基础上的加强版,应用场景:请求同一个接口不仅需要分页,
    //每页又需要根据不同的登录人返回不同的数据,这时候构造DynamicKeyGroup时,在构造函数中第一个参数传页数,
    //第二个参数传用户标识符就可以了

//    public void getGank(String cout,String page,String type,boolean updata, ApiCallBack<Object> callBack){
//        //完成一次消息订阅
//        observe( cacheProvider.getGank(   apiserver.getGank(cout,page,type)   ,  new EvictProvider(updata),  new DynamicKey(page) )  ).subscribe(callBack );//订阅
//    }


    //公共请求
    public void common(String url, Map<String, String> stringMap, final ApiCommonCallback callback) {
      observe(apiserver.common(url, requestHelpr.getRequestParm(stringMap))).subscribe(callback);
    }



    //获取引导图（不缓存）
    public void getAppGraph( ApiBaseResponseCallback<List<GuideBean>> callBack){
        //完成一次消息订阅
        observe( apiserver.getAppGraph()).subscribe(callBack );//订阅
    }

    //账号登陆
    public void accountLogin(Map<String, String> parmes,ApiBaseResponseCallback<Login> callBack){
        observe( apiserver.accountLogin(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //短信登陆
    public void mesLogin(Map<String, String> parmes,ApiBaseResponseCallback<Login> callBack){
        observe( apiserver.mesLogin(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //短信登陆获取验证码
    public void verify(Map<String, String> parmes,ApiBaseResponseCallback<Verify> callBack){
        observe( apiserver.verify(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //修改用户信息
    public void updataUserInfo(Map<String, String> parmes,ApiBaseResponseCallback<Object> callBack){
        observe( apiserver.updataUserInfo(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //通用请求功能
    public void commonExecute(String url,Map<String, String> parmes,ApiBaseResponseCallback<Object> callBack){
        observe( apiserver.commonExecute(url,requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //获取新闻分类
    public void GetNewsClass(Map<String, String> parmes,ApiBaseResponseCallback<List<NewsType>> callBack){
        observe( apiserver.GetNewsClass(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //获取新闻列表
    public void GetApiNews(Map<String, String> parmes,ApiBaseResponseCallback<List<NewsBean>> callBack){
        observe( apiserver.GetApiNews(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //获取新闻列表
    public void GetApiProDetails(Map<String, String> parmes,ApiBaseResponseCallback<ProductDetail> callBack){
        observe( apiserver.GetApiProDetails(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //获取第三方跳转url
    public void loanProduct(Map<String, String> parmes,ApiBaseResponseCallback<ProductsURL> callBack){
        observe( apiserver.loanProduct(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //修改用户信息
    public void updataUserInfoo(Map<String, String> parmes,ApiBaseResponseCallback<Object> callBack){
        observe( apiserver.updataUserInfoo(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }

    //第三方列表
    public void products(Map<String, String> parmes,ApiBaseResponseCallback<List<Products>> callBack){
        observe( apiserver.products(requestHelpr.getRequestParm(parmes))).subscribe(callBack );//订阅
    }





}
