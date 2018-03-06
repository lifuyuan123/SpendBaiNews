package com.example.lfy.spendbainews.ui.actvity;

import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.AccountValidatorUtil;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.application.MyApplication;
import com.example.lfy.spendbainews.databinding.ActivityUpdateLoginpwBinding;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.entity.Login;
import com.example.lfy.spendbainews.entity.Verify;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;

import java.util.HashMap;
import java.util.Map;


/**
 *修改登录密码
 */

public class UpdateLoginPsActivity extends BaseActivity {
    private Verify verify;
    private boolean runningThree;
    private Login login;
    private boolean booleanExtra;
    private ActivityUpdateLoginpwBinding binding;

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            binding.btCp.setText((l / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            runningThree = false;
            verify = null;
            binding.btCp.setText("重新发送");
        }
    };


    @Override
    protected void initView() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_update_loginpw);
        binding.setClick(this);
        booleanExtra = getIntent().getBooleanExtra("isdx",false);
        if (booleanExtra){
            binding.easeTitlebar.setTitle("设置密码");
        }

        login = MyApplication.getLogin();
        binding.easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_cp:
                if (runningThree)
                    return;
                downTimer.start();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("phone", login.getPhone());
                httpLoader.verify(stringMap, new ApiBaseResponseCallback<Verify>() {

                    @Override
                    public void onSuccessful(Verify verify) {
                        UpdateLoginPsActivity.this.verify = verify;
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

                break;

            case R.id.bt_updata:
                String pw = binding.etPs.getText().toString().trim();
                String pws = binding.etCps.getText().toString().trim();
                if (TextUtils.isEmpty(pw) || TextUtils.isEmpty(pws)) {
                    toastShow("密码不能为空");
                    return;
                }
                if (!pw.equals(pws)) {
                    toastShow("两次密码不一致");
                    return;
                }

                if (!AccountValidatorUtil.isLoginPw(pw)) {
                    Snackbar.make(binding.easeTitlebar, "密码必须字母、数字组合，长度为6-12位", Snackbar.LENGTH_LONG).setAction("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                    return;
                }

                String cp = binding.etCp.getText().toString().trim();
                if (verify == null || !String.valueOf(verify.getVerify()).equals(cp)) {
                    toastShow("验证码错误");
                    return;
                }

                mainDialog.show();
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("userid", verify.getUids());
                stringStringMap.put("phone", login.getPhone());
                stringStringMap.put("password", binding.etPs.getText().toString().trim());
                httpLoader.updataUserInfo(stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        if (booleanExtra){
//                            EventBus.getDefault().post(new Navagation(3));
                            finish();
                            return;
                        }
                        SPUtils.getInstance().put(Config.ISLOGIN, false);
//                        EventBus.getDefault().post(new LoginOut(true));
                        finish();
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


                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
    }
}
