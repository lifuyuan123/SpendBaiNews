package com.example.lfy.spendbainews.ui.actvity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.SPUtils;
import com.example.lfy.spendbainews.Utils.StatusBarUtil;
import com.example.lfy.spendbainews.Utils.dedclick.AntiShake;
import com.example.lfy.spendbainews.application.MyApplication;
import com.example.lfy.spendbainews.databinding.ActivityProductDetailsBinding;
import com.example.lfy.spendbainews.entity.Config;
import com.example.lfy.spendbainews.entity.ProductDetail;
import com.example.lfy.spendbainews.entity.Products;
import com.example.lfy.spendbainews.entity.ProductsURL;
import com.example.lfy.spendbainews.http.ApiBaseResponseCallback;
import com.example.lfy.spendbainews.ui.view.CommonDialog;

import java.util.HashMap;
import java.util.Map;

/**
 * 产品详情
 */
public class ProductDetailsActivity extends BaseActivity {

    private Products products;
    private ActivityProductDetailsBinding binding;

    @Override
    protected void initView() {
        binding=DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        binding.setClick(this);
        products = (Products) getIntent().getSerializableExtra("products");
        binding.easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.easeTitlebar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AntiShake.check(v.getId())) {    //判断是否多次点击
                    return;
                }
                //分享操作
                showShare();
            }
        });

        initProduct();

    }


    //分享功能
    private void showShare() {

    }

    public void onClick(View view) {
        if (AntiShake.check(view.getId())) {    //判断是否多次点击
            return;
        }

        switch (view.getId()) {

            case R.id.bt_ploan:
                boolean isLogin = SPUtils.getInstance().getBoolean(Config.ISLOGIN, false);
                if (!isLogin) {//没登陆
                    Intent intent = new Intent(ProductDetailsActivity.this, LogingActivity.class);
                    intent.putExtra("pro", true);
                    startActivity(intent);
                } else {
                    mainDialog.show();
                    Map<String, String> stringMap = new HashMap<>();
                    stringMap.put("uid", MyApplication.getLogin().getUserid());
                    stringMap.put("pro", products.getId());

                    httpLoader.loanProduct(stringMap, new ApiBaseResponseCallback<ProductsURL>() {
                        @Override
                        public void onSuccessful(ProductsURL productsURL) {
                            Log.e("loanProduct",productsURL.toString());
                            Log.e("loanProduct","链接"+products.toString());
                            switch (productsURL.getErrorcode()){
                                //跳接口返回url
                                case 0:
                                    if (products.getTitle().equals("拍拍贷")) {//拍拍贷sdk
//                                        PPDLoanAgent.getInstance().initLaunch(ProductDetailsActivity.this, MyApplication.getLogin().getPhone());
                                        return;
                                    }
                                    Intent intent = new Intent(ProductDetailsActivity.this, CommonClientWebActivity.class);
                                    intent.putExtra(CommonClientWebActivity.TITLE,"登陆" );
                                    intent.putExtra(CommonClientWebActivity.URL, productsURL.getUrl());
                                    navigation2Login(ProductDetailsActivity.this, intent);
                                    break;
                                //填写基本信息
                                case 10001:
                                    Intent intent1=new Intent(ProductDetailsActivity.this,OpenLoanActivity.class);
                                    intent1.putExtra("product","product");
                                    startActivity(intent1);
                                    break;
                                //积分不够（没认证完）
                                case 10002:
                                //需要继续认证（黑户或者没有认证）
                                case 10003:
                                    Toast.makeText(ProductDetailsActivity.this, "请到认证页面继续认证", Toast.LENGTH_SHORT).show();
                                    break;
                                //已认证，积分不够   跳第三方
                                case 10004:
                                    if (products.getTitle().equals("拍拍贷")) {//拍拍贷sdk
//                                        PPDLoanAgent.getInstance().initLaunch(ProductDetailsActivity.this, MyApplication.getLogin().getPhone());
                                        return;
                                    }
                                    Intent intent4 = new Intent(ProductDetailsActivity.this, CommonClientWebActivity.class);
                                    intent4.putExtra(CommonClientWebActivity.TITLE,"登陆");
                                    intent4.putExtra(CommonClientWebActivity.URL, productsURL.getUrl());
                                    navigation2Login(ProductDetailsActivity.this, intent4);
                                    break;
                            }

                        }

                        @Override
                        public void onFailure(String msg) {
                            mainDialog.dismiss();
                            toastShow(msg);
                        }

                        @Override
                        public void onFinish() {
                            if(mainDialog.isShowing())
                            mainDialog.dismiss();
                        }
                    });

                }


                break;

            //联系客服
            case R.id.bt_pcs:
                final CommonDialog commonDialog=new CommonDialog(ProductDetailsActivity.this);
                commonDialog.setTitle("联系客服");
                commonDialog.setContent("确定拨打客服电话“"+Config.TEL+"”吗？");
                commonDialog.setCancelClickListener("取消", new CommonDialog.CancelClickListener() {
                    @Override
                    public void clickCancel() {
                        commonDialog.dismiss();
                    }
                });
                commonDialog.setConfirmClickListener("确定", new CommonDialog.ConfirmClickListener() {
                    @Override
                    public void clickConfirm() {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Config.TEL));
                        startActivity(intent);
                        commonDialog.dismiss();
                    }
                });
                commonDialog.show();
                break;
        }

    }

    private void initProduct() {
        mainDialog.show();
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("pid", products.getId());
        httpLoader.GetApiProDetails(stringMap, new ApiBaseResponseCallback<ProductDetail>() {
            @Override
            public void onSuccessful(ProductDetail productDetail) {
                mainDialog.dismiss();
                if (productDetail!=null)
                //设置数据
                setData(productDetail);

            }

            @Override
            public void onFailure(String msg) {
               if(msg!=null){
                   mainDialog.dismiss();
                   toastShow(msg.toString());
               }
            }

            @Override
            public void onFinish() {

            }
        });
    }

    //设置数据
    private void setData(ProductDetail pd) {
        if (pd == null)
            return;
        Glide.with(ProductDetailsActivity.this).load(pd.getImg()).placeholder(R.mipmap.im_myhead).error(R.mipmap.im_myhead).into(binding.imHead);
        binding.tvEd.setText(pd.getMoney());
        binding.tvTitle.setText(pd.getTitle());
        binding.tvTime.setText(pd.getDeadline());
        binding.tvDec.setText(pd.getIntroduce());
        binding.tvPinfo.setText(pd.getLoantime());
        binding.tvLimit.setText(pd.getCondition());
    }





}
