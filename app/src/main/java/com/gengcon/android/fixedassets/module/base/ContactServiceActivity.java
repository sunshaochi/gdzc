package com.gengcon.android.fixedassets.module.base;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;

import java.io.FileInputStream;

public class ContactServiceActivity extends Activity implements View.OnClickListener {


    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public final static int FILECHOOSER_RESULTCODE = 1;
    public final static int FILECHOOSER_RESULTCODE_FOR_ANDORID_5 = 2;

    ProgressDialog progressBar;

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        initView();
    }

    private void initView() {
        webview = findViewById(R.id.webView);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText("在线客服");
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        progressBar = new ProgressDialog(this);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        webview.getSettings().setJavaScriptEnabled(true);
//        webview.addJavascriptInterface();//本地java对象映射给js调用
        webview.getSettings().setAppCacheEnabled(false);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置可以支持缩放
        webview.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        webview.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        webview.getSettings().setUseWideViewPort(true);
//自适应屏幕
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.loadUrl("https://kefu.easemob.com/webim/im.html?configId=d467f9bd-f7bc-4c92-a083-6dcfc114de88");
//        webview.reload();
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Log.e("WebviewActivity",request+"");
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //提示错误

            }
        });

        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //实现下载
                //跳转浏览器下载，也可以自己去实现下载类去下载
                Uri uri = Uri.parse("");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                ContactServiceActivity.this.runOnUiThread(new Runnable(){
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //title显示
//                if(title.equals("kefu.easemob.com/webim/im.html?configId=14bdb04a-bdea-45f5-8560-a2656b4dd7be"))
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    handler.sendEmptyMessage(1);//如果全部载入，隐藏进度对话框
                }

                super.onProgressChanged(view, newProgress);
            }

            //扩展支持alert事件
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("提示").setMessage(message).setPositiveButton("确定", null);
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();
                return true;
            }

            //扩展浏览器上传文件
            //3.0++版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooserImpl(uploadMsg);
            }

            //3.0--版本
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                openFileChooserImpl(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooserImpl(uploadMsg);
            }

            // For Android > 5.0
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
                openFileChooserImplForAndroid5(uploadMsg);
                return true;
            }

        });


    }

    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }

    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");

        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDORID_5);

//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
//        Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath()+ "/" + "temp.png")); // 传递路径
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 更改系统默认存储路径
//        startActivityForResult(intent, FILECHOOSER_RESULTCODE_FOR_ANDORID_5);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webview.canGoBack() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //获取历史列表
            WebBackForwardList mWebBackForwardList = webview.copyBackForwardList();
            //判断当前历史列表是否最顶端，其实canGoBack已经判断过
            if (mWebBackForwardList.getCurrentIndex() > 0) {
                webview.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else if (requestCode == FILECHOOSER_RESULTCODE_FOR_ANDORID_5) {
            FileInputStream fis = null;
            String path = Environment.getExternalStorageDirectory().getPath()+ "/" + "temp.png";
//
            if (null == mUploadMessageForAndroid5)
                return;
//            try {
//                fis = new FileInputStream(Environment.getExternalStorageDirectory().getPath()+ "/" + "temp.png");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
            mUploadMessageForAndroid5 = null;
        }
    }


    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {//定义一个Handler， 用于处理下载线程与UI间通讯
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        progressBar.show();//显示进度框
                        break;
                    case 1:
                        progressBar.hide();//隐藏进度对话框不可使用dismiss()、cancel()，否则再次调用show()时,显示的对话框小圆圈不会动
                        break;
                }
            }
            super.handleMessage(msg);
        }

        ;
    };


    @Override
    public void onClick(View v) {

    }
}
