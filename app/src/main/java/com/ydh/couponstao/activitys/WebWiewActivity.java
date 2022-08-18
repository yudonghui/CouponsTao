package com.ydh.couponstao.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.utils.LogUtils;
import com.ydh.couponstao.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebWiewActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.prog)
    ProgressBar prog;
    private boolean oneTag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiew);
        unBind = ButterKnife.bind(this);
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
        LogUtils.e("360页面地址: " + url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    LogUtils.e("H5加载接口：" + url);
                    if (!TextUtils.isEmpty(url)) {
                        // 处理自定义scheme协议
                        if (!url.startsWith("http") && !url.startsWith("https")) {
                            try {
                                final Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.addCategory("android.intent.category.BROWSABLE");
                                intent.setComponent(null);
                                if (null != intent) {
                                    startActivity(intent);
                                }
                                return true;
                            } catch (Exception e) {
                                // 没有安装的情况
                                e.printStackTrace();
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (url.contains("http") || url.contains("https")) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        // webView.addJavascriptInterface(new JsOperation(), "clients");

        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * 当网页调用alert()来弹出alert弹出框前回调，用以拦截alert()函数
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                if (webView == null) return true;
                AlertDialog.Builder b = new AlertDialog.Builder(WebWiewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (prog == null) return;
                if (newProgress == 100) {
                    // 网页加载完成
                    if (prog != null)
                        prog.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    // 加载中
                    if (prog != null) {
                        //  prog.setVisibility(View.VISIBLE);//开始加载网页时显示进度条。只在刚开始的时候显示，不然会出现抖动的情况
                        prog.setProgress(newProgress);//设置进度值
                    }
                }
            }
        });
    }

}