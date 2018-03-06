package com.example.lfy.spendbainews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lfy.spendbainews.ui.fragment.AccountLoginFragment;
import com.example.lfy.spendbainews.ui.fragment.MesLoginFragment;

/**
 * 登陆方式选择
 */

public class LoginTypeViewPagerAdapter extends FragmentPagerAdapter {
    private String[] loginTypeTitle = {"账号登录", "短信登录"};
    private Fragment fragments[] = {new AccountLoginFragment(), new MesLoginFragment()};

    public LoginTypeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public AccountLoginFragment getAccountLoginFragment() {
        return (AccountLoginFragment) fragments[0];
    }

    public MesLoginFragment getMesLoginFragment() {
        return (MesLoginFragment) fragments[1];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return loginTypeTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return loginTypeTitle[position];
    }
}
