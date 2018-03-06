package com.example.lfy.spendbainews.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.adapter.NewfragmentpagarAdapter;
import com.example.lfy.spendbainews.databinding.FragmentNewsBinding;
import com.example.lfy.spendbainews.entity.NewsType;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 新闻页面
 */

public class NewsFragment extends BaseFragment {

    private NewfragmentpagarAdapter adapter;
    private List<Fragment> fragments=new ArrayList<>();
    private FragmentNewsBinding binding;
    private List<String> stringList=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){

                //加载每一次项新闻页面
                for(int i=0;i<stringList.size();i++){
                    NewsItemFragment testFragment=new NewsItemFragment();
                    //将页卡标题传递过去
                    Bundle bundle=new Bundle();
                    bundle.putString("name",stringList.get(i));
                    testFragment.setArguments(bundle);
                    fragments.add(testFragment);
                }
                adapter=new NewfragmentpagarAdapter(getActivity().getSupportFragmentManager(),fragments,stringList);
                binding.newsViewpaper.setAdapter(adapter);
                binding.tablayout.setupWithViewPager(binding.newsViewpaper);

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initView() {

        //获取新闻分类
        getNewsType();

    }

    //获取新闻分类
    private void getNewsType() {
        httpLoader.GetNewsClass(new HashMap<String, String>(), new ApiBaseResponseCallback<List<NewsType>>() {
            @Override
            public void onSuccessful(List<NewsType> strings) {

                //加载成功分类后 将分类集传入到适配器中
                if (strings!=null){
                    LogUtil.e(strings.toString());
                    Message message=Message.obtain();
                    for (int i = 0; i < strings.size(); i++) {
                        stringList.add(strings.get(i).getName());
                    }
                    message.what=0;
                    handler.sendMessage(message);
                }

            }

            @Override
            public void onFailure(String msg) {
                if (msg!=null){
                    LogUtil.e(msg.toString());
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
