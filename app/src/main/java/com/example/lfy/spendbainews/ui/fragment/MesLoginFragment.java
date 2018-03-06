package com.example.lfy.spendbainews.ui.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lfy.spendbainews.ui.actvity.MainActivity;
import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.AccountValidatorUtil;
import com.example.lfy.spendbainews.Utils.AddSpaceTextWatcher;
import com.example.lfy.spendbainews.Utils.CodeUtils;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.Utils.TextUtils;
import com.example.lfy.spendbainews.Utils.dedclick.AntiShake;
import com.example.lfy.spendbainews.ui.actvity.UpdateLoginPsActivity;
import com.example.lfy.spendbainews.application.MyApplication;
import com.example.lfy.spendbainews.databinding.FragmentMesLoginBinding;
import com.example.lfy.spendbainews.entity.Login;
import com.example.lfy.spendbainews.entity.Verify;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;

import java.util.HashMap;
import java.util.Map;


/**
 * 短信登陆
 */

public class MesLoginFragment extends BaseFragment implements View.OnClickListener{
    private Verify verify;

    private boolean runningThree;
    private FragmentMesLoginBinding mesLoginBinding;


    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            mesLoginBinding.btCp.setText((l / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            runningThree = false;
            verify = null;
            mesLoginBinding.btCp.setText("重新发送");
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mesLoginBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_mes_login,container,false);
        return mesLoginBinding.getRoot();
    }

    //初始化
    @Override
    public void initView() {
        mesLoginBinding.imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
        mesLoginBinding.imVerify.setOnClickListener(this);
        mesLoginBinding.btCp.setOnClickListener(this);
        mesLoginBinding.btHelp.setOnClickListener(this);
        AddSpaceTextWatcher addSpaceTextWatcher = new AddSpaceTextWatcher(mesLoginBinding.etName, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);

    }

    @Override
    public void setViewUp() {

    }


    //点击事件
    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }
        switch (view.getId()) {

            case R.id.im_verify:
                mesLoginBinding.imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
                break;

            case R.id.bt_help:
//                Intent intent = null;
//                intent = new Intent(activity, TelActivity.class);
//                startActivity(intent);
                break;

            case R.id.bt_cp:
                sendMes();

                break;
        }
    }

    //登陆
    private void login() {


        if(mesLoginBinding.etCp==null)
            return;
        String cp = mesLoginBinding.etCp.getText().toString().trim();
        if (verify == null || !String.valueOf(verify.getVerify()).equals(cp)) {
            toastShow("验证码错误");
            return;
        }
        mainDialog.show();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("phone", TextUtils.repaceTrim(mesLoginBinding.etName.getText().toString().trim()));
        stringStringMap.put("uids", verify.getUids());
        httpLoader.mesLogin(stringStringMap, new ApiBaseResponseCallback<Login>() {
            @Override
            public void onSuccessful(Login login) {
                LogUtil.e(login.toString());
                MyApplication.setLogin(login);
                activity.setResult(102);
//                EventBus.getDefault().post(new LoginOut(false));
//                JPushInterface.setAlias(context, login.getUserid(), new TagAliasCallback() {
//                    @Override
//                    public void gotResult(int i, String s, Set<String> set) {
//                    }
//                });

                //判断短信登陆用户是否是新用户   新用户需要设置密码
                if(login.getTheUserIsNotNewlyRegistered()==1){//0:老用户  1：新用户
                    Intent intent=new Intent(activity, UpdateLoginPsActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putBoolean("isdx",true);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    activity.finish();
                }


                if (activity.getIntent().getBooleanExtra("pro", false)) {
                    activity.finish();
                } else if (activity.getIntent().getBooleanExtra("credit", false)) {
//                    EventBus.getDefault().post(new Navagation(2));
                    activity.finish();
                } else if (activity.getIntent().getBooleanExtra("toWallet", false)) {
//                    EventBus.getDefault().post(new WalletLoanLog());
                    activity.finish();
                }else if (activity.getIntent().getBooleanExtra("resetPw", false)) {
                    activity.startActivity(new Intent(activity, MainActivity.class));
                    activity.finish();
                } else{
                    activity.finish();
//                    startActivity();
                }
            }

            @Override
            public void onFailure(String msg) {
                if (msg!=null)
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                if (mainDialog!=null)
                    mainDialog.dismiss();
            }

        });

    }

    //获取短信验证码
    private void sendMes() {
        if (runningThree) {
            return;
        }
        String phoneText = TextUtils.repaceTrim(mesLoginBinding.etName.getText().toString().trim());
        if (!AccountValidatorUtil.isMobile(phoneText)) {
            toastShow("手机号格式有误");
            return;
        }
        if (!CodeUtils.getInstance().getCode().equals(mesLoginBinding.etImcp.getText().toString().trim())) {
            toastShow("验证码错误请重新输入");
            mesLoginBinding.imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
            return;
        }
        downTimer.start();
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("phone", phoneText);
        httpLoader.verify(stringStringMap, new ApiBaseResponseCallback<Verify>() {
            @Override
            public void onSuccessful(Verify verify) {
                MesLoginFragment.this.verify = verify;
                LogUtil.e(verify.toString());
            }

            @Override
            public void onFailure(String msg) {
                  if (msg!=null)
                      toastShow(msg);
            }

            @Override
            public void onFinish() {
            }

        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
    }

    public void loging() {
        login();
    }


}
