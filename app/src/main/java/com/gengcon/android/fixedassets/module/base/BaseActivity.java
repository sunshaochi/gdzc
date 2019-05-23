package com.gengcon.android.fixedassets.module.base;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.common.module.http.URL;
import com.gengcon.android.fixedassets.common.module.update.ApkDownLoad;
import com.gengcon.android.fixedassets.common.module.update.UpdateVersionPresenter;
import com.gengcon.android.fixedassets.common.module.update.UpdateVersionView;
import com.gengcon.android.fixedassets.module.web.view.WebActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.util.Utils;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.Calendar;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import io.reactivex.functions.Consumer;

public class BaseActivity extends AppCompatActivity implements Iview, UpdateVersionView {

    protected ZLoadingDialog mLoadingDialog;
    //    protected RolePresenter mRolePresenter;
    protected UpdateVersionPresenter mUpdateVersionPresenter;
    private UpdateVersion mVersion;

    public boolean wasBackground = false;    //声明一个布尔变量,记录当前的活动背景

    protected final int NO_SEARCH = 0x01;
    protected final int NO_DATA = 0x02;
    protected final int NO_NET = 0x03;
    protected final int NORMAL = 0x04;

    protected LinearLayout mLlNoData, mLlNoSearch, mLlNoNet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mRolePresenter = new RolePresenter();
//        mRolePresenter.attachView(this);
        mUpdateVersionPresenter = new UpdateVersionPresenter();
        mUpdateVersionPresenter.attachView(this);
//        mUpdateVersionPresenter.getVersion();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected void initView() {
        mLlNoData = findViewById(R.id.ll_no_data);
        mLlNoSearch = findViewById(R.id.ll_no_search);
        mLlNoNet = findViewById(R.id.ll_no_net);
    }

    protected void initDefault(int status) {
        mLlNoData.setVisibility(View.GONE);
        mLlNoSearch.setVisibility(View.GONE);
        mLlNoNet.setVisibility(View.GONE);
        if (status == NO_DATA) {
            mLlNoData.setVisibility(View.VISIBLE);
        } else if (status == NO_NET) {
            mLlNoNet.setVisibility(View.VISIBLE);
        } else if (status == NO_SEARCH) {
            mLlNoSearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasBackground) {
//            Log.e("aa", "从后台回到前台");
            mUpdateVersionPresenter.getVersion();
        }
        wasBackground = false;//不在后台
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isApplicationBroughtToBackground())
            wasBackground = true;//在后台
    }

    /**
     * 判断前台还是在后台
     *
     * @return
     */
    private boolean isApplicationBroughtToBackground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(getPackageName())) {
                return true;
            }
        }
        return false;
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
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
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
        }
        if (status == 301) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            Intent intent = new Intent(BaseActivity.this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
            startActivity(intent);
        }
        if (status == 500 || status == 400 || status == 406) {
            ToastUtils.toastMessage(this, msg);
        }
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        if (!isNetworkConnected(this)) {
            ToastUtils.toastMessage(BaseActivity.this, "网络连接不可用");
        } else {
            ToastUtils.toastMessage(BaseActivity.this, msg);
        }
        if (code.equals("CODE_301")) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            Intent intent = new Intent(BaseActivity.this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
            startActivity(intent);
        }
    }

    @Override
    public void showInvalidType(int invalid_type) {
        if (invalid_type == 2) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            Intent intent = new Intent(BaseActivity.this, WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
            intent.putExtra("invalid_type", invalid_type + "");
            startActivity(intent);
        }
    }

    public void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (BaseActivity.this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this, false);
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
                    if (!(e instanceof WindowManager.BadTokenException)) {
                        ToastUtils.toastMessage(BaseActivity.this, "下载地址不正确!");
                    }
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

    @Override
    public void updateVersion(UpdateVersion version) {
        mVersion = version;
        showUpdateVersionDialog();
    }

    public void showContractExpireDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, false);
        builder.setTitle("温馨提示");
        String content = "  您的账号使用期限已到期" + "\n" + "如需继续使用，请联系客服";
        builder.setText(content);
        builder.setUpDate(false);
        builder.setNeutralButtonColor();
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onCall("4008608800");
                dialog.dismiss();
            }
        }, "立即联系");
        builder.setNeutralButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                    mPresenter.getReadEditNotice(userPopupNotice.getList().getId());
                dialog.dismiss();
            }
        }, "稍后联系");

        builder.show();
    }

    private void callPhoneNumber(String phoneNum) {
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
