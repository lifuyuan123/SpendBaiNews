package com.example.lfy.spendbainews.ui.actvity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.lfy.spendbainews.R;
import com.example.lfy.spendbainews.Utils.LogUtil;
import com.example.lfy.spendbainews.databinding.ActivityCommonWebBinding;


/**
 * 公用webActivity
 */

public class CommonClientWebActivity extends BaseActivity {
    private String titel;//titelbar
    private String url;//加载url
    public static final String URL = "url";
    public static final String TITLE = "title";
    private boolean isClose;
    private ActivityCommonWebBinding binding;

    private String js = "javascript:function hideAd() {\n" +
            "    var adDiv0 = document.getElementsByClassName(\"download\");\n" +
            "    if(adDiv0 != null){\n" +
            "        var x;\n" +
            "        for (x= 0; x< adDiv0.length; x++) {\n" +
            "            adDiv0[x].style.display = \"none\";\n" +
            "        }\n" +
            "    }\n" +
            "\t}";//隐藏不必要的div
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (binding.x5View == null)
                return;
            binding.x5View.loadUrl(js); //加载js方法代码
            binding.x5View.loadUrl("javascript:hideAd();"); //调用js方法
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {


        //http://dai.moxtx.com/index.php/Wap/Credit/index.html
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ggband", "加载URL:" + url);
            Log.i("urlshould", url);
            if (url.contains("index.php")) {//若跳转官网主页面，就返回
//                view.stopLoading();
//                finish();
                view.loadUrl(url);
            } else
                view.loadUrl(url);
            return false;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("urlonPageFinished",  url);
            isClose = true;
            if(binding.x5View!=null){
                binding.x5View.loadUrl(js); //加载js方法代码
                binding.x5View.loadUrl("javascript:hideAd();"); //调用js方法
            }

        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            LogUtil.e("onPageStarted url:" + url);
            if (binding.x5View!=null){
                binding.x5View.loadUrl(js); //加载js方法代码
                binding.x5View.loadUrl("javascript:hideAd();"); //调用js方法
            }


            if (isClose) { //如果线程正在运行就不用重新开启一个线程了
                return;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isClose = true;
                    while (isClose) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0x001);
                    }
                }
            }).start();
        }
    };



    @Override
    protected void initView() {
        binding=DataBindingUtil.setContentView(this,R.layout.activity_common_web);
        binding.x5View.requestFocus(View.FOCUS_DOWN);
        url = getIntent().getStringExtra(URL) == null ? "" : getIntent().getStringExtra(URL);
        titel = getIntent().getStringExtra(TITLE) == null ? "" : getIntent().getStringExtra(TITLE);

        Log.e("url",url);
        if (titel != null)
            binding.easeTitlebar.setTitle(titel);
        binding.easeTitlebar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.x5View.loadUrl(url);
        binding.x5View.getSettings().setJavaScriptEnabled(true);
        binding.x5View.getSettings().setAppCacheEnabled(true);
        binding.x5View.getSettings().setDomStorageEnabled(true);
        //设置缓存模式
        binding.x5View.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        binding.x5View.setWebViewClient(webViewClient);
        binding.x5View.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (binding.pg == null)
                    return;
                if (newProgress == 100) {
                    binding.pg.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    binding.pg.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    binding.pg.setProgress(newProgress);//设置进度值
                }
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("title",title);
                if (binding.easeTitlebar!=null)
                    binding.easeTitlebar.setTitle(title);
            }

        });


    }



    @Override
    public void onBackPressed() {
        if (binding.x5View.canGoBack()) {
            binding.x5View.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isClose = false;
    }
}
