package com.example.lfy.spendbainews.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.databinding.FragmentCreditBinding;

/**
 * 信用页面
 */

public class CreditFragment extends BaseFragment {
    private FragmentCreditBinding creditBinding;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        creditBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_credit,container,false);
        return creditBinding.getRoot();
    }

    @Override
    public void initView() {
        creditBinding.setOnclick(this);
        creditBinding.refreshLayout.setColorSchemeColors(
                Color.parseColor("#ff00ddff"),
                Color.parseColor("#ff99cc00"),
                Color.parseColor("#ffffbb33"),
                Color.parseColor("#ffff4444"));

        if ( creditBinding.scrollView != null) {//解决ScroView和creditBinding.refreshLayout冲突
            creditBinding.scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (creditBinding.refreshLayout != null) {
                        creditBinding.refreshLayout.setEnabled( creditBinding.scrollView.getScrollY() == 0);
                    }
                }
            });
        }

        creditBinding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toastShow("刷新");
                creditBinding.refreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void setViewUp() {

    }

    public void onClick(){

    }
}
