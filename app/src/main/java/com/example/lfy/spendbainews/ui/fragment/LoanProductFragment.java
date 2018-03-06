package com.example.lfy.spendbainews.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.dedclick.AntiShake;
import com.example.lfy.spendbainews.adapter.CommonAdapter;
import com.example.lfy.spendbainews.databinding.FragmentLoanproductBinding;
import com.example.lfy.spendbainews.databinding.ShopItemBinding;
import com.example.lfy.spendbainews.entity.Products;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;
import com.example.lfy.spendbainews.ui.actvity.CommonClientWebActivity;
import com.example.lfy.spendbainews.ui.actvity.ProductDetailsActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第三方产品列表
 */

public class LoanProductFragment extends BaseFragment {

    private FragmentLoanproductBinding loanproductBinding;
    private List<Products> productsList = new ArrayList<>();
    private CommonAdapter<Products> adapter;
    private int page = 1;
    private View footerView;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        loanproductBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_loanproduct,container,false);
        return loanproductBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void initView() {
        footerView = LayoutInflater.from(context).inflate(R.layout.view_list_nomore_footer, null);
        footerView.setVisibility(View.GONE);
        loanproductBinding.rvShop.setLayoutManager(new LinearLayoutManager(context));
        //初始化adapter
        adapter=new CommonAdapter<Products>(productsList,R.layout.shop_item) {
            @Override
            protected void bindViewItemData(ViewDataBinding binding, int position, Products products) {
                ShopItemBinding shopItemBinding= (ShopItemBinding) binding;

                    shopItemBinding.linBorrow.setVisibility(View.VISIBLE);
                    shopItemBinding.linLoan.setVisibility(View.VISIBLE);
                    shopItemBinding.tvCount.setVisibility(View.VISIBLE);

                shopItemBinding.tvCount.setText(products.getApplycount()+"人已放款");
                shopItemBinding.tvPName.setText(products.getTitle());
                shopItemBinding.tvBottom.setText(products.getAd_slogan());
                shopItemBinding.tvBorrowingLimit.setText(products.getMoney());
                shopItemBinding.tvTerm.setText(products.getDeadline());
                Glide.with(context)
                        .load(products.getImg())
                        .error(R.mipmap.ic_launcher)
                        .into(shopItemBinding.imShop);
            }

        };

        //添加尾布局
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        mHeaderAndFooterWrapper.addFootView(footerView);
        loanproductBinding.rvShop.setAdapter(mHeaderAndFooterWrapper);


        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View item, int position) {
                Products products = productsList.get((int) position);
                if (AntiShake.check(item.getId())) {    //判断是否多次点击
                    return;
                }
                //1:新闻页面   0：正式的详情页
                if(products.getIsaudit()==1){

                    Intent intent = new Intent(activity, CommonClientWebActivity.class);
                    intent.putExtra(CommonClientWebActivity.TITLE, products.getTitle());
                    intent.putExtra(CommonClientWebActivity.URL, products.getUrl());
                    navigation2Login(activity, intent);

                }else if(products.getIsaudit()==0){
                    Intent intent = new Intent(activity, ProductDetailsActivity.class);
                    intent.putExtra("products", products);
                    activity.startActivity(intent);
                }
            }

        });

        loanproductBinding.productSrl.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                initProduct(page);
                refreshlayout.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                initProduct(page);
                footerView.setVisibility(View.GONE);
                refreshlayout.finishRefresh();
            }
        });
    }

    @Override
    public void setViewUp() {
        initProduct(page);
    }


    //获取数据
    private void initProduct(final int page) {
        Map<String, String> rp = new HashMap<>();
        rp.put("page", String.valueOf(page));

        httpLoader.products(rp, new ApiBaseResponseCallback<List<Products>>() {
            @Override
            public void onSuccessful(List<Products> productses) {
                Log.e("products",productses.toString());
                if(page==1){
                    productsList.clear();
                }
                for (Products products : productses) {
                    productsList.add(products);
                }
                loanproductBinding.rvShop.setVisibility(View.VISIBLE);
                footerView.setVisibility(View.GONE);
                mHeaderAndFooterWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
                if (msg!=null){
                    if (msg.equals("网络似乎出问题了")){
                        loanproductBinding.emptyLayoutAndroid.showError();
                        footerView.setVisibility(View.GONE);
                        loanproductBinding.rvShop.setVisibility(View.INVISIBLE);
                    }else if (msg.equals("已经没有啦")) {
                        footerView.setVisibility(View.VISIBLE);
                        loanproductBinding.productSrl.setEnableAutoLoadmore(false);
                    }else if(msg.equals("没有此类信息")){
                        loanproductBinding.emptyLayoutAndroid.showEmpty(msg);
                        footerView.setVisibility(View.GONE);
                    } else{
                        loanproductBinding.emptyLayoutAndroid.showError();
                        footerView.setVisibility(View.GONE);
                    }
                    toastShow(msg);
                }

            }

            @Override
            public void onFinish() {
            }
        });

    }
}
