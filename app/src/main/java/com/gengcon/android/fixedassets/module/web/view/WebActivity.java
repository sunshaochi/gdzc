package com.gengcon.android.fixedassets.module.web.view;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Base64;
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
import com.gengcon.android.fixedassets.bean.Asset;
import com.gengcon.android.fixedassets.bean.LoginUserBean;
import com.gengcon.android.fixedassets.bean.UpdateImgRBean;
import com.gengcon.android.fixedassets.bean.request.AddAssetRequest;
import com.gengcon.android.fixedassets.bean.result.Print;
import com.gengcon.android.fixedassets.common.module.htttp.URL;
import com.gengcon.android.fixedassets.module.base.BasePullRefreshActivity;
import com.gengcon.android.fixedassets.module.login.view.ui.ForgetPwdFirstActivity;
import com.gengcon.android.fixedassets.module.login.view.ui.RegisterFirstActivity;
import com.gengcon.android.fixedassets.module.main.view.ui.MainActivity;
import com.gengcon.android.fixedassets.module.print.BluetoothDeviceListActivity;
import com.gengcon.android.fixedassets.module.web.presenter.WebPresenter;
import com.gengcon.android.fixedassets.util.CacheActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ImageFactory;
import com.gengcon.android.fixedassets.module.print.JCPrinter;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.ActionSheetLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    private String rgsId;
    private WebPresenter webPresenter;
    String invalid_type;
    private boolean backHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_URL);
        webName = getIntent().getStringExtra("webName");
        webTitle = getIntent().getStringExtra("webTitle");
        webFrom = getIntent().getStringExtra("webFrom");
        invalid_type = getIntent().getStringExtra("invalid_type");
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
        webPresenter = new WebPresenter();
        webPresenter.attachView(this);
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
                    ArrayList<String> assetIds = (ArrayList<String>) getIntent().getSerializableExtra(Constant.INTENT_EXTRA_KEY_ASSETS);
                    mWebView.loadUrl("javascript:beCall(" + "'" + new Gson().toJson(new AddAssetRequest(assetIds)) + "'" + ")");
                } else if (url.equals(URL.HTTP_HEAD + URL.LOGIN)) {
                    backHome = true;
                    if (!TextUtils.isEmpty(invalid_type)) {
                        mWebView.loadUrl("javascript:ToastMsg(" + "'" + invalid_type + "'" + ")");
                        invalid_type = null;
                    }
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
    public void login(String token) {
        LoginUserBean userBean = new Gson().fromJson(token, LoginUserBean.class);
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.TOKEN, userBean.getToken());
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.USER_ID, userBean.getUser_id());
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.IMG_URL, userBean.getImgurl());
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.IS_SUPERADMIN, userBean.getIs_superadmin());
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.COMPANY_NAME, userBean.getCompany_name());

        if(userBean.getIs_demo().equals("true")){
            SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.USER_ID, "demo");
        }else {
            SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.USER_ID, userBean.getUser_id());
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
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
                    ArrayList<Asset> assets = new Gson().fromJson(message, new TypeToken<ArrayList<Asset>>() {
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
                    ArrayList<Asset> assets = new Gson().fromJson(message, new TypeToken<ArrayList<Asset>>() {
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
                    if (printResult.getLabel().getSize_id() != 1 && printResult.getLabel().getSize_id() != 2) {
                        ToastUtils.toastMessage(this, "移动端暂不支持该模板的打印");
                        return;
                    }
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
                String printTag = readPrintName();
                webPresenter.getPrintTag(printTag);
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
    public void loginRegister(String message) {
        if (!CacheActivity.activityList.contains(WebActivity.this)) {
            CacheActivity.activityList.add(WebActivity.this);
        }
        Intent intent = new Intent(this, RegisterFirstActivity.class);
        startActivity(intent);
    }

    @JavascriptInterface
    public void loginForget(String message) {
        Intent intent = new Intent(this, ForgetPwdFirstActivity.class);
        startActivity(intent);
    }

    @JavascriptInterface
    public void callPhone(String message) {
        onCall("4008608800");
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.clearCache(true);
    }

    private String readPrintName() {
        SharedPreferences read = getSharedPreferences("Connect", MODE_PRIVATE);
        String name = read.getString("device_name", "");
        if (TextUtils.isEmpty(name)) {
            name = "UNKNOWN";
        }
        if (name.startsWith("B50")) {
            if (name.contains("B50W")) {
                name = "JC-B50W";
            } else {
                name = "JC-B50";
            }
        } else if (name.startsWith("T6")) {
            name = "JC-T6";
        } else if (name.startsWith("T7")) {
            name = "JC-T7";
        } else {
            name = "UNKNOWN";
        }
        return name;
    }

    public void callPhoneNumber(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    //动态权限申请后处理
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted callDirectly(mobile);
                } else {
                    // Permission Denied Toast.makeText(MainActivity.this,"CALL_PHONE Denied", Toast.LENGTH_SHORT) .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onCall(String mobile) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CALL_PHONE
                }, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhoneNumber(mobile);
            }
        } else {
            callPhoneNumber(mobile);
        }
    }
}
