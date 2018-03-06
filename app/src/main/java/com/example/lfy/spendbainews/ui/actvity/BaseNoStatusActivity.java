package com.example.lfy.spendbainews.ui.actvity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.StatusBarUtil;
import com.example.lfy.spendbainews.Utils.StatusBarUtils;
import com.example.lfy.spendbainews.Utils.ToastUtils;
import com.example.lfy.spendbainews.http.Subscriber;



/**
 * Created by Administrator on 2017/4/26.
 */
public abstract class BaseNoStatusActivity extends AppCompatActivity {
    protected Context context;
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog progressDialog;
    protected Activity activity;
    //订阅者
    protected Subscriber subscriber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.darkMode(this);
//        StatusBarUtils.setColorNoTranslucent(this,getResources().getColor(R.color.app_theme));
        init();

    }


    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    protected ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置进度条是否为不明确
        progressDialog.setIndeterminate(true);
        // 设置进度条是否可以按退回键取消
        progressDialog.setCancelable(false);
        // 设置点击进度对话框外的区域对话框不消失
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    protected ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置进度条是否为不明确
        progressDialog.setIndeterminate(true);
        // 设置进度条是否可以按退回键取消
        progressDialog.setCancelable(false);
        // 设置点击进度对话框外的区域对话框不消失
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void toastShow(String mes) {
        ToastUtils.showShort(mes);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }




    private void init() {
        context = this;
        activity = this;
        subscriber=Subscriber.getInstemce();
        initView();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    //初始化
    protected abstract void initView();
    //获取layout







}
