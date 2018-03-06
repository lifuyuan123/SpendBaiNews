package com.example.lfy.spendbainews.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.adapter.CommonAdapter;
import com.example.lfy.spendbainews.databinding.FragmentNewsitemBinding;
import com.example.lfy.spendbainews.entity.NewsBean;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 子新闻页面
 */

public class NewsItemFragment extends BaseFragment {
    private FragmentNewsitemBinding binding;
    private CommonAdapter adapter;
    private LinearLayoutManager manager;
    private String name;//新闻类型
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_newsitem,container,false);
        //获取新闻类型
        name=getArguments().getString("name");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initView() {

        if (getActivity()!=null)
        manager=new LinearLayoutManager(getActivity());

        //获取新闻列表
        getNewsData();

    }

    //获取新闻列表
    private void getNewsData() {
        Map<String,String> map=new HashMap<>();
        map.put("newstype",name);
        httpLoader.GetApiNews(map, new ApiBaseResponseCallback<List<NewsBean>>() {
            @Override
            public void onSuccessful(List<NewsBean> newsBeans) {
                if (newsBeans!=null){
                    LogUtil.e(newsBeans.toString());
                }

            }

            @Override
            public void onFailure(String msg) {
                 if (msg!=null){
                     LogUtil.e(msg.toString());
                     toastShow(msg);
                 }
            }

            @Override
            public void onFinish() {

            }
        });


    }

    @Override
    public void setViewUp() {

    }
}
