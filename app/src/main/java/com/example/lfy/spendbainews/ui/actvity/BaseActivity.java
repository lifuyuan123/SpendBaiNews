package com.example.lfy.spendbainews.ui.actvity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.Utils.StatusBarUtil;
import com.example.lfy.spendbainews.Utils.StatusBarUtils;
import com.example.lfy.spendbainews.Utils.ToastUtils;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.http.Subscriber;
import com.example.lfy.spendbainews.ui.view.MainDialog;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;


/**
 *基类
 */

public abstract class BaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate {

    //订阅者
    protected Subscriber httpLoader;
    protected MainDialog mainDialog;

    //左滑返回帮助类
    protected BGASwipeBackHelper mSwipeBackHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.darkMode(this);
        StatusBarUtils.setColorNoTranslucent(this,getResources().getColor(R.color.app_theme));

        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        //禁止横屏，设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化订阅者
        httpLoader=Subscriber.getInstemce();
        mainDialog = MainDialog.showDialog(this);
        initView();


    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.mipmap.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();

    }

    //土司
    protected void toastShow(String mes) {
        ToastUtils.showShort(mes);
    }

    //跳转
    protected void startActivity(Class clazz,boolean isFinish) {
                startActivity(new Intent(this,clazz));
                if (isFinish) {
                        finish();
                    }
            }

    //登陆判断  未登陆走登陆页面  登陆则走传入的的页面
    protected  void navigation2Login(Activity activity, Intent intent) {
        boolean isLogin = SPUtils.getInstance().getBoolean(Config.ISLOGIN, false);
        if (!isLogin) {
            ComponentName componentName = new ComponentName(activity, LogingActivity.class);
            intent.putExtra("className", intent.getComponent().getClassName());
            intent.setComponent(componentName);
            activity.startActivity(intent);
        } else
            activity.startActivity(intent);
    }

    //初始化
    protected abstract void initView();


}
