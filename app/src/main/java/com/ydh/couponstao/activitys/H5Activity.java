package com.ydh.couponstao.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ydh.couponstao.R;
import com.ydh.couponstao.utils.LogUtils;

import butterknife.BindView;

public class H5Activity extends AppCompatActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.prog)
    ProgressBar prog;
    private boolean oneTag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiew);
        initView();
    }

    private void initView() {
        if (webView == null) return;
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setBuiltInZoomControls(true);//页面放大缩小

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            //拦截url
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e("接口：" + url);
                view.loadUrl(url);
                return true;
            }

            //加载完成后调用js
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}