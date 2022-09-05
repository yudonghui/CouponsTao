package com.ydh.couponstao.activitys;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class H5Activity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.prog)
    ProgressBar prog;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    private boolean oneTag = true;
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiew);
        unBind = ButterKnife.bind(this);
        from = getIntent().getIntExtra("from", 0);
        if (from == 1) {
            rlBar.setVisibility(View.VISIBLE);
            tvTitle.setText("用户协议和隐私政策");
        } else {
            rlBar.setVisibility(View.GONE);
        }
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
        LogUtils.e("H5接口：" + url);
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

    @OnClick(R.id.return_btn)
    public void onViewClicked() {
        finish();
    }
}