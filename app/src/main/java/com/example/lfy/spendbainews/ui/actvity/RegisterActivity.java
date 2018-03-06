package com.example.lfy.spendbainews.ui.actvity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.AccountValidatorUtil;
import com.example.lfy.spendbainews.Utils.AddSpaceTextWatcher;
import com.example.lfy.spendbainews.Utils.CodeUtils;
import com.example.lfy.spendbainews.Utils.EasyPermissionsEx;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.Utils.TextUtils;
import com.example.lfy.spendbainews.Utils.ToastUtils;
import com.example.lfy.spendbainews.databinding.ActivityRegisterBinding;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.entity.URL;
import com.example.lfy.spendbainews.entity.Verify;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;
import com.example.lfy.spendbainews.ui.view.CommonDialog;

import java.util.HashMap;
import java.util.Map;


/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {
    private boolean isRegister;
    private Verify verify;
    private boolean runningThree;
    private ActivityRegisterBinding binding;


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
        binding=DataBindingUtil.setContentView(this,R.layout.activity_register);
        binding.setClick(this);
        binding.imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
        AddSpaceTextWatcher addSpaceTextWatcher = new AddSpaceTextWatcher(binding.etName, 13);
        addSpaceTextWatcher.setSpaceType(AddSpaceTextWatcher.SpaceType.mobilePhoneNumberType);
        binding.easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    return;
                String phoneText = TextUtils.repaceTrim(binding.etName.getText().toString().trim());
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                Map<String, String> stringStringMap = new HashMap<>();
                stringStringMap.put("phone", phoneText);
                httpLoader.commonExecute(URL.PHONEEXIT, stringStringMap, new ApiBaseResponseCallback<Object>() {

                    @Override
                    public void onSuccessful(Object o) {

                    }

                    @Override
                    public void onFailure(String msg) {
                        isRegister = false;
                        if (msg!=null)
                        toastShow(msg);
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
            case R.id.bt_register:

                if (!binding.cbIsAgree.isChecked())
                    return;

                String pw = binding.etPs.getText().toString().trim();
                if (!isRegister) {
                    toastShow("手机号不可用");
                    return;
                }
                if (android.text.TextUtils.isEmpty(pw)) {
                    toastShow("密码不能为空");
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
                stringStringMap.put("phone", TextUtils.repaceTrim(binding.etName.getText().toString().trim()));
                stringStringMap.put("password", binding.etPs.getText().toString().trim());
                httpLoader.commonExecute(URL.REGISTER, stringStringMap, new ApiBaseResponseCallback<Object>() {


                    @Override
                    public void onSuccessful(Object o) {
                        toastShow("注册成功");
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

            case R.id.im_verify:
                binding.imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());

                break;

            case R.id.bt_agree:
                Intent intent = new Intent(RegisterActivity.this, CommonClientWebActivity.class);
                intent.putExtra(CommonClientWebActivity.TITLE, "注册协议");
                intent.putExtra(CommonClientWebActivity.URL, URL.AGREENMENT);
                startActivity(intent);

                break;

            case R.id.bt_cp:
                if (runningThree)
                    return;
                if (!isRegister) {
                    toastShow("手机号不可用");
                    return;
                }
                String phoneText = TextUtils.repaceTrim(binding.etName.getText().toString().trim());
                if (!AccountValidatorUtil.isMobile(phoneText)) {
                    toastShow("手机号格式有误");
                    return;
                }
                if (!CodeUtils.getInstance().getCode().equals(binding.etImcp.getText().toString().trim())) {
                    toastShow("验证码错误请重新输入");
                    binding.imVerify.setImageBitmap(CodeUtils.getInstance().createBitmap());
                    return;
                }
                downTimer.start();
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("phone", phoneText);
                httpLoader.verify(stringMap, new ApiBaseResponseCallback<Verify>() {
                    @Override
                    public void onSuccessful(Verify verify) {
                        RegisterActivity.this.verify = verify;
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

            //客服帮助
            case R.id.bt_help:
                if (EasyPermissionsEx.hasPermissions(RegisterActivity.this, Manifest.permission.CALL_PHONE)) {
                    //已经授权  拨打电话
                    final CommonDialog commonDialog=new CommonDialog(RegisterActivity.this);
                    commonDialog.setTitle("联系客服");
                    commonDialog.setContent("确定拨打客服电话“"+ Config.TEL+"”吗？");
                    commonDialog.setCancelClickListener("取消", new CommonDialog.CancelClickListener() {
                        @Override
                        public void clickCancel() {
                            commonDialog.dismiss();
                        }
                    });
                    commonDialog.setConfirmClickListener("确定", new CommonDialog.ConfirmClickListener() {
                        @Override
                        public void clickConfirm() {
                            startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + Config.TEL)));
                            commonDialog.dismiss();
                        }
                    });
                    commonDialog.show();
                } else {
                    EasyPermissionsEx.requestPermissions(RegisterActivity.this, "拨打电话需要授予拨号权限", 100, Manifest.permission.CALL_PHONE);
                }
                break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        downTimer.cancel();
    }

    //申请权限回掉
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //已经授权
                    //拨打电话
                    startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + 66661016)));
                } else {
                    ToastUtils.showShort("拒绝电话权限");
                }
                break;
        }

    }
}
