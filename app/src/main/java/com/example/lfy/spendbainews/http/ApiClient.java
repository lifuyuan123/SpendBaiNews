package com.example.lfy.spendbainews.http;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 日志拦截器   retrofit初始化
 */
public class ApiClient {
    //域名
    private static String API_BASE_URL="http://gank.io/";
    public static Retrofit mRetrofit;

  public static Retrofit getInstence(){
      if (mRetrofit==null){

          //日志拦截
          HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
              @Override
              public void log(String message) {
                  //打印retrofit日志
                  Log.e("RetrofitLog", "retrofitBack = " + message);
              }
          });

          //BASIC 请求/响应行  HEADER 请求/响应行 + 头   BODY 请求/响应行 + 头 + 体
          loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

          // 创建 OKHttpClient
          OkHttpClient.Builder builder = new OkHttpClient.Builder();
          builder.connectTimeout(6, TimeUnit.SECONDS);//连接超时时间  6s
          builder.writeTimeout(6,TimeUnit.SECONDS);//写操作 超时时间  6s
          builder.readTimeout(6,TimeUnit.SECONDS);//读操作超时时间    6s
          builder.addInterceptor(loggingInterceptor);//添加拦截器

          mRetrofit=new Retrofit.Builder()
                  .baseUrl(apiServer.API_SERVER_URL)//添加公共域名
                  .addConverterFactory(GsonConverterFactory.create())//添加gson转换工厂
                  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//支持Observable  需要RxJava2CallAdapterFactory  retrofit默认是DefaultCallAdapterFactory
                  .client(builder.build())//添加OkHttpClient  builder.build()返回的是一个OkHttpClient
                  .build();
      }

      return mRetrofit;
  }


}
