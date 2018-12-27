package com.gengcon.android.fixedassets.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.AssetBean;
import com.gengcon.android.fixedassets.bean.UpdateImgRBean;
import com.gengcon.android.fixedassets.bean.request.AddAssetRequest;
import com.gengcon.android.fixedassets.bean.request.UpdateImgRequest;
import com.gengcon.android.fixedassets.bean.request.UpdateVersionRequest;
import com.gengcon.android.fixedassets.bean.result.Print;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ImageFactory;
import com.gengcon.android.fixedassets.util.JCPrinter;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.util.Utils;
import com.gengcon.android.fixedassets.version.ApkDownLoad;
import com.gengcon.android.fixedassets.widget.ActionSheetLayout;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tbruyelle.rxpermissions2.Permission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WebActivity extends BasePullRefreshActivity {

    private WebView mWebView;
    private ProgressBar mPg;
    private ActionSheetLayout mActionSheet;
    private ImageFactory imageFactory;
    private TextView deleteImageView;
    private TextView checkImageView;
    private View toolBar;
    private String url;
    private String webName;
    private String webTitle;
    private String webFrom;
    private String intentType;
    private int index;
    private int imgType;
    private int updateImg;
    private String versionName;
    private boolean isUpdate;
    private UpdateVersion mVersion;
    private String rgsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_URL);
        webName = getIntent().getStringExtra("webName");
        webTitle = getIntent().getStringExtra("webTitle");
        webFrom = getIntent().getStringExtra("webFrom");
        setContentView(R.layout.activity_web);
        toolBar = findViewById(R.id.toolBar);
        if (!TextUtils.isEmpty(webName)) {
            ((TextView) findViewById(R.id.tv_title_text)).setText(webName);
            if (!TextUtils.isEmpty(webFrom) && webFrom.equals("MainActivity")) {
                ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
            } else {
                ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
            }
            if (!TextUtils.isEmpty(webTitle)) {
                ((TextView) findViewById(R.id.tv_title_right)).setText(webTitle);
            }
        } else {
            toolBar.setVisibility(View.GONE);
        }
        initView();
        imageFactory = new ImageFactory();
        mActionSheet = findViewById(R.id.actionsheet);
        deleteImageView = mActionSheet.getDeleteImageView();
        checkImageView = mActionSheet.getCheckImageView();
        mActionSheet.setHanlderResultCallback(mOnHanlderResultCallback);
        mWebView = findViewById(R.id.webview);
        mPg = findViewById(R.id.pg);
        mWebView.loadUrl(url);
        final WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
//        webSettings.setBlockNetworkImage(true);
        mWebView.addJavascriptInterface(this, "android");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (!isNetworkConnected(WebActivity.this)) {
                    initDefault(NO_NET);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toolBar.setVisibility(View.GONE);
                    }
                }, 100);
                if (isNetworkConnected(WebActivity.this)) {
                    initDefault(NORMAL);
                }
                if (url.equals(URL.HTTP_HEAD + URL.BEDETAIL)) {
                    String id = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_ASSER_ID);
                    String isHistory = getIntent().getStringExtra(Constant.INTENT_IS_HISTORY_ASSER_ID);
                    String docId = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
                    mWebView.loadUrl("javascript:beDetail(" + "'" + isHistory + "&" + id + "&" + docId + "'" + ")");
                } else if (url.equals(URL.HTTP_HEAD + URL.BECALL)) {
                    intentType = getIntent().getStringExtra("intentType");
                    ArrayList<AssetBean> assets = (ArrayList<AssetBean>) getIntent().getSerializableExtra(Constant.INTENT_EXTRA_KEY_ASSETS);
                    ArrayList<String> ids = new ArrayList<>();
                    for (int i = 0; i < assets.size(); i++) {
                        ids.add(assets.get(i).getAsset_id());
                    }
                    mWebView.loadUrl("javascript:beCall(" + "'" + new Gson().toJson(new AddAssetRequest(assets, ids)) + "'" + ")");
                } else if (url.equals(URL.HTTP_HEAD + URL.ABOUTUS)) {
                    mWebView.loadUrl("javascript:checkVersion(" + "'" + new Gson().toJson(new UpdateVersionRequest(versionName, isUpdate)) + "'" + ")");
                } else if (url.equals(URL.HTTP_HEAD + URL.LOGIN)) {
                    rgsId = JPushInterface.getRegistrationID(WebActivity.this);
                    mWebView.loadUrl("javascript:setRegId(" + "'" + rgsId + "'" + ")");
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateImg == 1) {
                    mWebView.loadUrl("javascript:deleteImg(" + "'" + "'" + ")");
                } else if (updateImg == 2) {
                    mWebView.loadUrl("javascript:deleteImg(" + "'" + imgType + "==========" + index + "'" + ")");
                }
                mActionSheet.hide();
            }
        });
        checkImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateImg == 1) {
                    mWebView.loadUrl("javascript:checkImg(" + "'" + "'" + ")");
                } else if (updateImg == 2) {
                    mWebView.loadUrl("javascript:checkImg(" + "'" + imgType + "==========" + index + "'" + ")");
                }
                mActionSheet.hide();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
//                    webSettings.setBlockNetworkImage(false);
                    mPg.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mPg.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mPg.setProgress(newProgress);//设置进度值
                }

            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected(WebActivity.this)) {
                    mWebView.reload();
                } else {
                    ToastUtils.toastMessage(WebActivity.this, "网络连接不可用");
                }
            }
        });
    }

    @JavascriptInterface
    public void login(String token) throws JSONException {
        JSONObject object = new JSONObject(token);
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.TOKEN, object.getString("token"));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @JavascriptInterface
    public void go_out(String message) {
        SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
    }

    @JavascriptInterface
    public void back_home(String message) throws JSONException {
        if (TextUtils.isEmpty(message)) {
            finish();
        } else {
            JSONObject object = new JSONObject(message);
            String code = object.getString("msg");
            if (code.equals("CODE_406")) {
                ToastUtils.toastMessage(this, "您当前无操作权限");
                finish();
            } else {
                finish();
            }
        }
    }

    @JavascriptInterface
    public void backInventory(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (intentType.equals("edit")) {
                    ArrayList<AssetBean> assets = new Gson().fromJson(message, new TypeToken<ArrayList<AssetBean>>() {
                    }.getType());
                    ArrayList<String> assets_ids = new ArrayList<>();
                    for (int i = 0; i < assets.size(); i++) {
                        assets_ids.add(assets.get(i).getAsset_id());
                    }
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(Constant.INTENT_EXTRA_KEY_ASSETS, assets_ids);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    ArrayList<AssetBean> assets = new Gson().fromJson(message, new TypeToken<ArrayList<AssetBean>>() {
                    }.getType());
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(Constant.INTENT_EXTRA_KEY_ASSETS, assets);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

    @JavascriptInterface
    public void printing(String message) {
        int success = 0;
        Print printResult = new Gson().fromJson(message, Print.class);
        if (JCPrinter.isPrinterConnected()) {
            for (Print.ListBean list : printResult.getList()) {
                if (!JCPrinter.printLabel(printResult.getLabel(), list)) {
                    return;
                }
                success++;
            }
        }
        final int finalSuccess = success;
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:printingResult(" + "'" + finalSuccess + "'" + ")");
            }
        });
    }

    @JavascriptInterface
    public void printingDevice(String message) {
        Intent intent = new Intent(this, BluetoothDeviceListActivity.class);
        startActivityForResult(intent, Constant.PRINTRT_DEVICE);
    }

    @JavascriptInterface
    public void willPrint(String message) {
        mWebView.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:BluetoothState(" + "'" + (JCPrinter.isPrinterConnected() ? 1 : 0) + "'" + ")");
            }
        });
    }

    @JavascriptInterface
    public void addImg(String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateImg = 1;
                mActionSheet.show();
            }
        });
    }

    @JavascriptInterface
    public void update_img(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateImg = 2;
                UpdateImgRBean result = new Gson().fromJson(data, UpdateImgRBean.class);
                if (result != null) {
                    imgType = result.getType();
                    index = result.getIndex();
                    mActionSheet.show();
                }
            }
        });
    }

    @JavascriptInterface
    public void updateVersion(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showUpdateVersionDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mActionSheet.isVisibility()) {
            mActionSheet.hide();
            return;
        }
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            try {
                back_home("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PRINTRT_DEVICE && resultCode == RESULT_OK) {
            mWebView.loadUrl("javascript:BluetoothState(" + "'" + (JCPrinter.isPrinterConnected() ? 1 : 0) + "'" + ")");
        }
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
            if (resultList != null) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws IOException {
                        String bitmap = "data:image/jpeg;base64," + Base64.encodeToString(imageFactory.compressAndGenImage(imageFactory.getBitmap(resultList.get(0).getPhotoPath()), 1024), Base64.DEFAULT);
                        emitter.onNext(bitmap);
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {

                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String bitmap) {
                        if (updateImg == 1) {
                            mWebView.loadUrl("javascript:uploadImgBase64(" + "'" + bitmap + "'" + ")");
                        } else if (updateImg == 2) {
                            mWebView.loadUrl("javascript:uploadImgBase64(" + "'" + imgType + "==========" + index + "==========" + bitmap + "'" + ")");
//                            mWebView.loadUrl("javascript:uploadImgBase64(" + "'" + new Gson().toJson(new UpdateImgRequest(bitmap, index)) + "'" + ")");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            ToastUtils.toastMessage(WebActivity.this, errorMsg);
        }
    };

    @Override
    public void showErrorMsg(int code, String msg) {
        super.showErrorMsg(code, msg);
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        super.showCodeMsg(code, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.clearCache(true);
    }

    @Override
    public void updateVersion(UpdateVersion version) {
        super.updateVersion(version);
        mVersion = version;
        versionName = Utils.getVersionName(this);
        if (version.getVersion_number() > Utils.getVersionCode(this)) {
            isUpdate = true;
        } else {
            isUpdate = false;
        }
    }

    private void showUpdateVersionDialog() {
        if (mVersion.getVersion_number() > Utils.getVersionCode(this)) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setUpDate(true);
            builder.setTitle(getString(R.string.version_update));
            StringBuilder updateContent = new StringBuilder();
            updateContent.append("最新版本号:" + mVersion.getVersion_name());
            updateContent.append("\r\n");
            updateContent.append("当前版本号:" + Utils.getVersionName(this));
            if (!TextUtils.isEmpty(mVersion.getUpdate_content())) {
                updateContent.append("\r\n");
                updateContent.append("更新内容:" + "\n");
                String content = mVersion.getUpdate_content().replaceAll("\r\n", "");
                Log.e(content, "content: " + content);
                updateContent.append(content.replace(";", "\n"));
            }
            builder.setText(updateContent.toString());
            //非强制更新
            builder.setPositiveButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermission(permissionConsumer, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
            }, getString(R.string.update));
            builder.setNegativeButton(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }, getString(R.string.cancel));
            builder.show();
        }
    }

    Consumer<Permission> permissionConsumer = new Consumer<Permission>() {
        @Override
        public void accept(Permission permission) throws Exception {
            if (permission.granted) {
                try {
                    new ApkDownLoad(WebActivity.this, mVersion.getUrl(), mVersion.getVersion_name(), getString(R.string.version_update))
                            .execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.toastMessage(WebActivity.this, "下载地址不正确!");
                }
            } else if (permission.shouldShowRequestPermissionRationale) {
                requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ToastUtils.toastMessage(WebActivity.this, R.string.permission_write_external_tips);
            } else {
                ToastUtils.toastMessage(WebActivity.this, R.string.permission_write_external_tips);
                showUpdateVersionDialog();
            }
        }
    };
}
