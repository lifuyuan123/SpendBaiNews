package com.example.lfy.spendbainews.ui.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.ui.actvity.ResetLoginPsActivity;
import com.example.lfy.spendbainews.application.MyApplication;
import com.example.lfy.spendbainews.databinding.FragmentSlideMineBinding;
import com.example.lfy.spendbainews.entity.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 我的页面
 */

public class SlideMineFragment extends BaseFragment {
    private FragmentSlideMineBinding slideMineBinding;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        slideMineBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_slide_mine,container,false);
        return slideMineBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initView() {
        slideMineBinding.setMineclick(this);

    }

    @Override
    public void setViewUp() {

    }

    @Override
    public void onResume() {
        super.onResume();

        if (!SPUtils.getInstance().getBoolean(Config.ISLOGIN, false)){

        }else {
            slideMineBinding.mineTvLogin.setText(MyApplication.getLogin().getName());
            slideMineBinding.mineTvPhone.setText(MyApplication.getLogin().getPhone());
        }


    }

    public void onClick(View view){
        switch (view.getId()){
            //登陆
            case R.id.mine_tv_login:
                navigation2Login(activity, new Intent(activity, ResetLoginPsActivity.class));
                break;
            //测试分享
            case R.id.mine_tv_phone:
                new ShareAction(getActivity()).withText("hello").setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                toastShow("开始");
                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {

                            }
                        }).open();


                break;
        }

    }
}
