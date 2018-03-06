package com.example.lfy.spendbainews.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.ui.actvity.LogingActivity;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.http.Subscriber;
import com.example.lfy.spendbainews.ui.view.MainDialog;


/**
 * 只有有左滑菜单的页面全部需要继承这个类
 */
public abstract class BaseFragment extends Fragment {


    //标题栏
    // @BindView(R.id.title)
    protected TextView tv_title;

//    @BindView(R.id.ease_titlebar)
//    protected EaseTitleBar titleBar;


    protected Context context;
    protected FragmentActivity activity;
    protected InputMethodManager inputMethodManager;
    protected ProgressDialog progressDialog;
    // protected ApiStores apiStores;
    protected LayoutInflater inflater;
    protected String Tag = "ggband";
    protected FragmentManager fragmentManager;
    protected Fragment currentFragment;
    protected View view;
    //订阅者
    protected Subscriber httpLoader;
    protected MainDialog mainDialog;




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainDialog = MainDialog.showDialog(context);
        init();
        initView();
        setViewUp();
    }

    void init() {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //初始化订阅者
        httpLoader=Subscriber.getInstemce();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity= (FragmentActivity) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = activity.getSupportFragmentManager();
        inflater = LayoutInflater.from(context);


    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = getView();
        currentFragment = this;
    }


    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("执行中...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }

    protected ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    protected void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    protected void toastShow(String mes) {
        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
    }

    //跳转
    protected void startActivity(Class clazz,boolean isFinish) {
        if (getActivity()==null)
            return;
        startActivity(new Intent(getActivity(),clazz));
        if (isFinish) {
            getActivity().finish();
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


    public abstract void initView();

    public abstract void setViewUp();


}
