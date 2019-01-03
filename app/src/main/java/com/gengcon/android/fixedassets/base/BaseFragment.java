package com.gengcon.android.fixedassets.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.htttp.URL;
import com.gengcon.android.fixedassets.ui.WebActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import io.reactivex.functions.Consumer;

public class BaseFragment extends Fragment implements Iview {

    protected ZLoadingDialog mLoadingDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new ZLoadingDialog(getActivity());
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
    public void showErrorMsg(int code, String msg) {
        if (!isNetworkConnected(getActivity())) {
            ToastUtils.toastMessage(getActivity(), "网络连接不可用");
        } else {
            ToastUtils.toastMessage(getActivity(), msg);
        }
        if (code == 401 || code == 301) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            Intent intent = new Intent(getActivity(), WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
            startActivity(intent);
        }
    }

    @Override
    public void showCodeMsg(String code, String msg) {
        if (!isNetworkConnected(getActivity())) {
            ToastUtils.toastMessage(getActivity(), "网络连接不可用");
        } else {
            ToastUtils.toastMessage(getActivity(), msg);
        }
        if (code.equals("CODE_401") || code.equals("CODE_301")) {
            SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
            Intent intent = new Intent(getActivity(), WebActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Constant.INTENT_EXTRA_KEY_URL, URL.HTTP_HEAD + URL.LOGIN);
            startActivity(intent);
        }
    }

    public void showPermissionDeniedTips() {
        ToastUtils.toastMessage(getActivity(), R.string.permission_denied_tips);
    }

    //Manifest.permission.CAMERA
    protected void requestPermission(Consumer<Permission> consumer, String... permission) {
        RxPermissions rxPermission = new RxPermissions(getActivity());
        rxPermission
                .requestEach(permission)
                .subscribe(consumer);
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

}
