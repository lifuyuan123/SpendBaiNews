package com.example.lfy.spendbainews.ui.actvity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.KeyEvent;
import android.view.View;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.adapter.LoginTypeViewPagerAdapter;
import com.example.lfy.spendbainews.databinding.ActivityLogingBinding;

/**
 * 登陆
 */

public class LogingActivity extends BaseActivity {
    private ActivityLogingBinding binding;
    private LoginTypeViewPagerAdapter loginTypeViewPagerAdapter;



    @Override
    protected void initView() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_loging);
        binding.setClick(this);
        if (getIntent().getBooleanExtra("resetPw", false))
            binding.easeTitlebar.getLeftImage().setVisibility(View.GONE);
        else
            binding.easeTitlebar.getLeftImage().setVisibility(View.VISIBLE);
        binding.easeTitlebar.getRightImage().setVisibility(View.GONE);
        binding.easeTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogingActivity.this, RegisterActivity.class));

            }
        });
        binding.easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //控制radiobutton下标
                setResult(102);
                finish();
            }
        });

        loginTypeViewPagerAdapter = new LoginTypeViewPagerAdapter(getSupportFragmentManager());
        binding.vpLoginType.setAdapter(loginTypeViewPagerAdapter);
        binding.tlLoginType.setupWithViewPager(binding.vpLoginType);

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login:

                if (binding.tlLoginType.getSelectedTabPosition() == 0)
                    loginTypeViewPagerAdapter.getAccountLoginFragment().loging();
                else
                    loginTypeViewPagerAdapter.getMesLoginFragment().loging();
                break;

            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            //控制radiobutton下标
            setResult(102);
            finish();
        }
        return true;
    }
}
