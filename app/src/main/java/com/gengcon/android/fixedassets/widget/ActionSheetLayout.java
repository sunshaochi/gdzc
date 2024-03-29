package com.gengcon.android.fixedassets.widget;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import io.reactivex.functions.Consumer;

public class ActionSheetLayout extends LinearLayout implements View.OnClickListener {

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback;
    private LinearLayout mLlAction;
    private TextView deleteImage;
    private TextView checkImage;
    private boolean isPermission = true;


    public ActionSheetLayout(Context context) {
        super(context);
        init(context);
    }

    public ActionSheetLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ActionSheetLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.actionsheet, null);
        deleteImage = contentView.findViewById(R.id.tv_deleteImage);
        checkImage = contentView.findViewById(R.id.checkImage);
        contentView.findViewById(R.id.tv_pick_phone).setOnClickListener(this);
        contentView.findViewById(R.id.tv_pick_camera).setOnClickListener(this);
        contentView.findViewById(R.id.tv_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.v_cancel).setOnClickListener(this);
        mLlAction = contentView.findViewById(R.id.ll_action);
        addView(contentView);
    }

    public void setHanlderResultCallback(GalleryFinal.OnHanlderResultCallback onHanlderResultCallback) {
        mOnHanlderResultCallback = onHanlderResultCallback;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pick_phone:
                GalleryFinal.openGallerySingle(Constant.REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                hide();
                break;
            case R.id.tv_pick_camera:
                requestPermission();
                hide();
                break;
            case R.id.tv_cancel:
                hide();
                break;
//            case R.id.v_cancel:
//                hide();
//                break;
        }
    }

    public void show() {
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.window_in);
        mLlAction.startAnimation(ani);
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.window_out);
        mLlAction.startAnimation(ani);
        setVisibility(View.GONE);
    }

    public TextView getDeleteImageView() {
        return deleteImage;
    }

    public TextView getCheckImageView() {
        return checkImage;
    }

    public boolean isVisibility() {
        return getVisibility() == GONE ? false : true;
    }

    private List<String> list = new ArrayList<>();

    private void requestPermission() {
        list.clear();
        RxPermissions rxPermission = new RxPermissions((Activity) getContext());
        rxPermission
                .requestEach(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            Log.i("同意",permission.name);
                            list.add(permission.name);
                            // 用户已经同意该权限
//                            if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                                GalleryFinal.openCamera(Constant.REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
//                            }
                            if(list.size()==2){//两个都要同意
                                GalleryFinal.openCamera(Constant.REQUEST_CODE_CAMERA, mOnHanlderResultCallback);
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            Log.i("拒绝",permission.name);
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                ToastUtils.toastMessage(getContext(), R.string.permission_write_tips);
                            } else {
                                ToastUtils.toastMessage(getContext(), R.string.permission_camera_tips);
                            }
                        } else {
                            Log.i("静止",permission.name);
                            // 用户拒绝了该权限，而且选中『不再询问』
                            if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                ToastUtils.toastMessage(getContext(), R.string.permission_write_tips);
                            } else {
                                ToastUtils.toastMessage(getContext(), R.string.permission_camera_tips);
                            }
                        }
                    }
                });
    }

}
