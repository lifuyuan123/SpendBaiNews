package com.example.lfy.spendbainews.ui.actvity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.adapter.GuideAdapter;
import com.example.lfy.spendbainews.databinding.ActivityGuideBinding;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.entity.GuideBean;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * 引导界面
 */

public class GuideActivity extends BaseNoStatusActivity implements View.OnClickListener{
    private List<GuideBean> list=new ArrayList<>();
    private GuideAdapter adapter;
    private ActivityGuideBinding guideBinding;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                if (guideBinding.viewpager==null)
                    return;
                guideBinding.viewpager.setAdapter(adapter);
                guideBinding.viewpager.setCurrentItem(0);
                guideBinding.indicator.setViewPager(guideBinding.viewpager);
                guideBinding.indicator.startAnimation();

                guideBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == list.size()-1) {
                            if (guideBinding.btStart!=null)
                                guideBinding.btStart.setVisibility(View.VISIBLE);
                        } else {
                            if (guideBinding.btStart!=null)
                                guideBinding.btStart.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
        }
    };



    @Override
    protected void initView() {
        guideBinding= DataBindingUtil.setContentView(this, R.layout.activity_guide);
        guideBinding.btStart.setOnClickListener(this);
       getAppGraph();//获取图片
    }



    //获取图片
    private void getAppGraph() {
        subscriber.getAppGraph(new ApiBaseResponseCallback<List<GuideBean>>() {
            @Override
            public void onSuccessful(List<GuideBean>  guideBeanList) {
                LogUtil.e(guideBeanList.toString());
                if (guideBeanList!=null&&guideBeanList.size()!=0){
                    list=guideBeanList;
                    adapter=new GuideAdapter(GuideActivity.this,guideBeanList);
                    Message message= Message.obtain();
                    message.what=1;
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onFailure(String msg) {
                if(msg!=null){
                    LogUtil.e(msg.toString());
                    toastShow(msg);
                }
            }

            @Override
            public void onFinish() {

            }


        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                if (Config.LOGINTYPE) {//强制登陆
                    boolean isLogin = SPUtils.getInstance().getBoolean(Config.ISLOGIN, false);
                    if (isLogin)
                        startActivity(new Intent(this,
                                MainActivity.class));
                    else
                        startActivity(new Intent(this,
                                LogingActivity.class));
                } else {
                    startActivity(new Intent(this,
                            MainActivity.class));
                }
                finish();
                break;
        }

    }
}
