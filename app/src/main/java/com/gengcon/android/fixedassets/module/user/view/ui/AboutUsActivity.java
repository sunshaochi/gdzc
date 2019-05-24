package com.gengcon.android.fixedassets.module.user.view.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.common.module.http.URL;
import com.gengcon.android.fixedassets.common.module.update.ApkDownLoad;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.UpdateVersion;
import com.gengcon.android.fixedassets.module.login.view.ui.AgreementActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.util.Utils;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.tbruyelle.rxpermissions2.Permission;

import androidx.annotation.Nullable;
import io.reactivex.functions.Consumer;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    private UpdateVersion mVersion;
    private String versionName;
    private LinearLayout versionLayout;
    private LinearLayout hasNewVersionLayout;
    private LinearLayout contactLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mUpdateVersionPresenter.getVersion();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.about_us);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.permitLayout).setOnClickListener(this);
        findViewById(R.id.protectLayout).setOnClickListener(this);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        versionName = Utils.getVersionName(this);
        versionLayout = findViewById(R.id.versionLayout);
        hasNewVersionLayout = findViewById(R.id.hasNewVersionLayout);
        versionLayout.setOnClickListener(this);
        ((TextView) findViewById(R.id.versionName)).setText(versionName);
        contactLayout = findViewById(R.id.contactLayout);
        contactLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.versionLayout:
                updateVersion();
                break;
            case R.id.permitLayout:
                Intent webIntent = new Intent(AboutUsActivity.this, UserWebActivity.class);
                webIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.REGISTER_AGREEMENT);
                startActivity(webIntent);
                break;
            case R.id.protectLayout:
                Intent protectIntent = new Intent(AboutUsActivity.this, UserWebActivity.class);
                protectIntent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.SECRET);
                startActivity(protectIntent);
                break;
            case R.id.contactLayout:
                Intent intent = new Intent(this, ContactUsActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void updateVersion(UpdateVersion version) {
        super.updateVersion(version);
        mVersion = version;
        if (mVersion.getVersion_number() > Utils.getVersionCode(this)) {
            hasNewVersionLayout.setVisibility(View.VISIBLE);
        } else {
            hasNewVersionLayout.setVisibility(View.GONE);
        }
    }

    private void updateVersion() {
        if (mVersion.getVersion_number() > Utils.getVersionCode(this)) {
            showUpdateVersionDialog();
        } else {
            ToastUtils.toastMessage(this, "当前已是最新版本！");
        }
    }

    private void showUpdateVersionDialog() {
        if (mVersion.getVersion_number() > Utils.getVersionCode(this)) {
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
                    new ApkDownLoad(AboutUsActivity.this, mVersion.getUrl(), mVersion.getVersion_name(), getString(R.string.version_update))
                            .execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.toastMessage(AboutUsActivity.this, "下载地址不正确!");
                }
            } else if (permission.shouldShowRequestPermissionRationale) {
                requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ToastUtils.toastMessage(AboutUsActivity.this, R.string.permission_write_external_tips);
            } else {
                ToastUtils.toastMessage(AboutUsActivity.this, R.string.permission_write_external_tips);
                showUpdateVersionDialog();
            }
        }
    };
}
