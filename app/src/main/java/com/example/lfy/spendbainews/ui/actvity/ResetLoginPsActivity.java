package com.example.lfy.spendbainews.ui.actvity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.AccountValidatorUtil;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.databinding.ActivityResetLoginpwBinding;
import com.example.lfy.spendbainews.entity.URL;
import com.example.lfy.spendbainews.entity.Verify;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;

import java.util.HashMap;
import java.util.Map;


/**
 * 重置登录密码
 */

public class ResetLoginPsActivity extends BaseActivity {
    private boolean isRegister;
    private ActivityResetLoginpwBinding binding;
    private Verify verify;
    private boolean runningThree;
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
        binding=DataBindingUtil.setContentView(this,R.layout.activity_reset_loginpw);
        binding.setClick(this);
        binding.easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.etTel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    return;
                String phoneText = binding.etTel.getText().toString().trim();
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("phone", binding.etTel.getText().toString().trim());
                httpLoader.commonExecute(URL.PHONEEXIT, stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        isRegister = true;
                    }

                    @Override
                    public void onFailure(String msg) {
                        isRegister = false;
                    }

                    @Override
                    public void onFinish() {

                    }

                });

            }
        });
    }



    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_cp:

                if (runningThree)
                    return;
                if (isRegister) {
                    toastShow("手机号不存在");
                    return;
                }
                String phoneText = binding.etTel.getText().toString().trim();
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                downTimer.start();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("phone", phoneText);
                httpLoader.verify(stringMap, new ApiBaseResponseCallback<Verify>() {

                    @Override
                    public void onSuccessful(Verify verify) {
                        ResetLoginPsActivity.this.verify = verify;
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

            case R.id.bt_play:

                String pw = binding.etPs.getText().toString().trim();
                String pws = binding.etCps.getText().toString().trim();
                if (isRegister) {
                    toastShow("手机号不存在");
                    return;
                }
                if (TextUtils.isEmpty(pw) || TextUtils.isEmpty(pws)) {
                    toastShow("密码不能为空");
                    return;
                }
                if (!pw.equals(pws)) {
                    toastShow("两次密码不一致");
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
                stringStringMap.put("phone", binding.etTel.getText().toString().trim());
                stringStringMap.put("password", binding.etPs.getText().toString().trim());
                httpLoader.updataUserInfo(stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {
                        toastShow("重置成功");
                        startActivity(new Intent(ResetLoginPsActivity.this, LogingActivity.class));
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
        downTimer.cancel();
        super.onDestroy();
    }
}
