package com.ixinrun.base.webview;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import static android.app.Activity.RESULT_OK;

/**
 * 描述：WebView代理封装，可根据此基类进行扩展
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public class BaseWebView extends FrameLayout {
    protected final String TAG = getClass().getSimpleName();

    private ProgressBar mProgressBar;
    private WebView mWebView;
    private boolean mIsCleanHistory;

    public BaseWebView(Context context) {
        this(context, null);
    }

    public BaseWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        initWebView(context);
        initProgressBar(context);
    }

    /**
     * 初始化WebView
     *
     * @param context
     */
    private void initWebView(Context context) {
        try {
            mWebView = new WebView(context);
            addView(mWebView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            //webview相关设置
            webSettings(mWebView);
            mWebView.setHorizontalScrollBarEnabled(false);//水平不显示
            mWebView.setVerticalScrollBarEnabled(false); //垂直不显示
            mWebView.setWebViewClient(initWebViewClient());
            mWebView.setWebChromeClient(initWebViewChromeClient());
            mWebView.setDownloadListener(initDownloadListener());
            scrollSettings(mWebView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void webSettings(WebView webView) {
        WebSettings setting = webView.getSettings();
        //自适应屏幕
        setting.setUseWideViewPort(true);                 //设置成webview推荐使用的窗口，可任意比例缩放
        setting.setLoadWithOverviewMode(true);            //设置成webview加载的页面大小的模式

        //字体不随系统缩放
        setting.setTextZoom(100);

        //支持手动缩放
        setting.setSupportZoom(true);                     //支持缩放
        setting.setBuiltInZoomControls(true);             //设置内置的缩放控件（如果为false，则缩放不生效）
        setting.setDisplayZoomControls(false);            //隐藏原生的缩放控件

        //允许执行Javascript脚本
        setting.setJavaScriptEnabled(true);

        //支持缓存
        setting.setDomStorageEnabled(true);               //开启 DOM storage API 功能
        setting.setDatabaseEnabled(true);                 //开启 database storage API 功能
        setting.setAppCacheEnabled(true);                 //开启 Application Caches 功能
        setting.setCacheMode(WebSettings.LOAD_NO_CACHE);  //缓存模式

        //设置编码格式
        setting.setDefaultTextEncodingName("utf-8");
    }

    @SuppressLint("ClickableViewAccessibility")
    private void scrollSettings(WebView webView) {
        //解决缩放和滑动冲突
        webView.setOnTouchListener(new View.OnTouchListener() {
            float x;
            float y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (event.getPointerCount() >= 2) {
                            //多点触控下处理缩放冲突
                            ((WebView) v).requestDisallowInterceptTouchEvent(true);
                        } else {
                            //单点触控下处理滑动冲突
                            int deltaX = (int) Math.abs(event.getX() - x);
                            int deltaY = (int) Math.abs(event.getY() - y);
                            if (deltaX > deltaY) {
                                ((WebView) v).requestDisallowInterceptTouchEvent(true);
                            } else {
                                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        ((WebView) v).requestDisallowInterceptTouchEvent(false);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 初始化进度条
     *
     * @param context
     */
    private void initProgressBar(Context context) {
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dpi2px(context, 3)));
        addView(mProgressBar);
    }

    /**
     * 设置进度条显示, 默认开启
     */
    public boolean progressBarVisible() {
        return true;
    }

    /**
     * 加载页面
     *
     * @param url 请求地址
     */
    public void loadUrl(String url) {
        loadUrl(url, false);
    }

    /**
     * 加载页面
     *
     * @param url          请求地址
     * @param cleanHistory 是否清除历史记录
     */
    public void loadUrl(String url, boolean cleanHistory) {
        if (mWebView == null) {
            return;
        }
        this.mIsCleanHistory = cleanHistory;
        Log.i(TAG, "webview---------url:" + url);
        mWebView.loadUrl(url);
    }

    /**
     * 获取webview实例，用于webview设置扩展，注意判空
     */
    public WebView getWebView() {
        return mWebView;
    }

    /**
     * 初始化WebViewClient
     *
     * @return
     */
    public MyWebViewClient initWebViewClient() {
        return new MyWebViewClient();
    }

    /**
     * 初始化WebViewChromeClient
     *
     * @return
     */
    public MyWebViewChromeClient initWebViewChromeClient() {
        return new MyWebViewChromeClient();
    }

    /**
     * 初始化DownloadListener
     *
     * @return
     */
    public MyDownloadListener initDownloadListener() {
        return new MyDownloadListener();
    }

    /**
     * 默认WebViewClient，可通过重写initWebViewClient方法进行扩展
     */
    protected class MyWebViewClient extends WebViewClient {
        //页面内跳转
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            if (mIsCleanHistory) {
                view.clearHistory();
                mIsCleanHistory = false;
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    private final static int FILE_CHOOSER_RESULT_CODE = 10000;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;

    /**
     * 默认WebViewChromeClient，可通过重写initWebViewChromeClient方法进行扩展
     */
    protected class MyWebViewChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (!progressBarVisible()) {
                return;
            }
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
            } else {
                mProgressBar.setVisibility(VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> valueCallback) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android  >= 3.0
        public void openFileChooser(ValueCallback valueCallback, String acceptType) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        //For Android  >= 4.1
        public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
            uploadMessage = valueCallback;
            openImageChooserActivity();
        }

        // For Android >= 5.0
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            openImageChooserActivity();
            return true;
        }
    }

    private void openImageChooserActivity() {
        if (getContext() instanceof Activity) {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType("image/*");
            ((Activity) getContext()).startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
        }
    }

    /**
     * 默认下载规则，可通过重写initDownloadListener方法进行扩展
     */
    protected class MyDownloadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        }
    }

    /**
     * 如果需要支持文件上传，必须在Activivity的onActivityResult中调用此方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) {
                return;
            }
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    /**
     * 返回处理
     *
     * @return
     */
    public boolean canGoBack() {
        if (mWebView == null) {
            return false;
        }
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && canGoBack()) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }

    /**
     * 安全回收, 退出时在Activity的onDestroy中调用此方法。
     */
    public void onDestroy() {
        if (mWebView == null) {
            return;
        }
        mWebView.stopLoading();
        mWebView.getSettings().setJavaScriptEnabled(false);
        mWebView.clearHistory();
        mWebView.removeAllViews();
        ViewParent parent = mWebView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mWebView);
        }
        //一切资源回收后执行destroy.
        mWebView.destroy();
        mWebView = null;
    }

    private int dpi2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
