package com.example.lfy.spendbainews.ui.actvity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.entity.Config;


/**
 * Created by Administrator on 2017/7/13.
 */

public class SplashActivity extends Activity {
    private static final int SHOW_TIME_MIN = 2000;// 最小显示时间
    private long mStartTime;// 开始时间

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:// 如果城市列表加载完毕，就发送此消息
                    long loadingTime = System.currentTimeMillis() - mStartTime;// 计算一下总共花费的时间
                    if (loadingTime < SHOW_TIME_MIN) {// 如果比最小显示时间还短，就延时进入MainActivity，否则直接进入
                        mHandler.postDelayed(goToMainActivity, SHOW_TIME_MIN
                                - loadingTime);
                    } else {
                        mHandler.post(goToMainActivity);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    //进入下一个Activity
    Runnable goToMainActivity = new Runnable() {

        @Override
        public void run() {
            boolean isUpdate = SPUtils.getInstance().getBoolean(Config.ISUPDATA, true);
            if (isUpdate) {
                SPUtils.getInstance().put(Config.ISUPDATA, false);
                SplashActivity.this.startActivity(new Intent(SplashActivity.this,
                        GuideActivity.class));
            } else {

                if (Config.LOGINTYPE) {//强制登陆
                    boolean isLogin = SPUtils.getInstance().getBoolean(Config.ISLOGIN, false);
                    if (isLogin)
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this,
                                MainActivity.class));
                    else
                        SplashActivity.this.startActivity(new Intent(SplashActivity.this,
                                LogingActivity.class));
                }else {
                    SplashActivity.this.startActivity(new Intent(SplashActivity.this,
                            MainActivity.class));
                }

            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mStartTime = System.currentTimeMillis();//记录开始时间，
        mHandler.sendEmptyMessage(0);
    }
}
