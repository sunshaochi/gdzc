package com.gengcon.android.fixedassets.base;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.presenter.UpdateVersionPresenter;
import com.gengcon.android.fixedassets.ui.WebActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.util.Utils;
import com.gengcon.android.fixedassets.version.ApkDownLoad;
import com.gengcon.android.fixedassets.view.UpdateVersionView;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.io.IOException;
import java.util.Calendar;

import io.reactivex.functions.Consumer;

public class BaseActivity extends AppCompatActivity implements Iview, UpdateVersionView {

    protected ZLoadingDialog mLoadingDialog;
    //    protected RolePresenter mRolePresenter;
    protected UpdateVersionPresenter mUpdateVersionPresenter;
    private UpdateVersion mVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mRolePresenter = new RolePresenter();
//        mRolePresenter.attachView(this);
        mUpdateVersionPresenter = new UpdateVersionPresenter();
        mUpdateVersionPresenter.attachView(this);
        mUpdateVersionPresenter.getVersion();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void initView() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mRolePresenter.detachView();
        mUpdateVersionPresenter.detachView();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new ZLoadingDialog(BaseActivity.this);
            mLoadingDialog.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE)//设置类型
                    .setLoadingColor(Color.BLACK)//颜色
                    .setHintText(getString(R.string.loading))
                    .setHintTextSize(16)
                    .create();
        }
        mLoadingDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    @Override
    public void showErrorMsg(int status, String msg) {
        if (!isNetworkConnected(this)) {
            ToastUtils.toastMessage(BaseActivity.this, "网络连接不可用");
        } else {
            ToastUtils.toastMessage(BaseActivity.this, msg);
        }
        if (status == 401) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            Intent intent = new Intent(BaseActivity.this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
            startActivity(intent);
        }
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        if (!isNetworkConnected(this)) {
            ToastUtils.toastMessage(BaseActivity.this, "网络连接不可用");
        } else {
            ToastUtils.toastMessage(BaseActivity.this, msg);
        }
        if (code.equals("CODE_401")) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            Intent intent = new Intent(BaseActivity.this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
            startActivity(intent);
        }
    }

    public void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showSoftInput(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
    }

    private void showUpdateVersionDialog() {
        if (mVersion.getVersion_number() > Utils.getVersionCode(this)) {
            if (!isUpdate() && mVersion.getUpdate_type() != 1) {
                return;
            }
            if (mVersion.getUpdate_type() == 1) {
                SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            }
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
            if (mVersion.getUpdate_type() != 1) {
                builder.setNegativeButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }, getString(R.string.cancel));
            }
            builder.show();
        }
    }

    public void showPermissionDeniedTips() {
        ToastUtils.toastMessage(BaseActivity.this, R.string.permission_denied_tips);
    }

    //Manifest.permission.CAMERA
    protected void requestPermission(Consumer<Permission> consumer, String... permission) {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(permission)
                .subscribe(consumer);
    }

    Consumer<Permission> permissionConsumer = new Consumer<Permission>() {
        @Override
        public void accept(Permission permission) throws Exception {
            if (permission.granted) {
                try {
                    new ApkDownLoad(BaseActivity.this, mVersion.getUrl(), mVersion.getVersion_name(), getString(R.string.version_update))
                            .execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.toastMessage(BaseActivity.this, "下载地址不正确!");
                }
            } else if (permission.shouldShowRequestPermissionRationale) {
                requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ToastUtils.toastMessage(BaseActivity.this, R.string.permission_write_external_tips);
            } else {
                ToastUtils.toastMessage(BaseActivity.this, R.string.permission_write_external_tips);
                showUpdateVersionDialog();
            }
        }
    };

    private boolean isUpdate() {
        Calendar calendar = Calendar.getInstance();
        int presentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferencesUtils.getInstance().setParam("presenttime", presentDay);
        int presentTime = (int) SharedPreferencesUtils.getInstance().getParam("presenttime", 0);
        int updateTime = (int) SharedPreferencesUtils.getInstance().getParam("updatetime", 0);
        if (presentTime != updateTime) {
            int updateDay = calendar.get(Calendar.DAY_OF_MONTH);
            SharedPreferencesUtils.getInstance().setParam("updatetime", updateDay);
            return true;
        }
        return false;
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public boolean isNetworkOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -c 3 www.baidu.com");
            int exitValue = ipProcess.waitFor();
            Log.i("Avalible", "Process:"+exitValue);
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateVersion(UpdateVersion version) {
        mVersion = version;
        showUpdateVersionDialog();
    }
}
