package com.gengcon.android.fixedassets.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.util.DensityUtils;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;


/**
 * Created by Administrator on 2018/8/27.
 */

public class LookDialog implements View.OnClickListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private ImageView iv_sl;
    private int width;

    public LookDialog(Context context) {
        this.context = context;
        this.width=width;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public LookDialog build() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_look, null);
        iv_sl = (ImageView) view.findViewById(R.id.iv_sl);
        ViewGroup.LayoutParams params = iv_sl.getLayoutParams();
        params.height = (int) (params.width*1.6);
        iv_sl.setLayoutParams(params);
        iv_sl.setOnClickListener(this);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.alert_dialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager manager = dialogWindow.getWindowManager();
        Display d = manager.getDefaultDisplay();
        lp.width = d.getWidth();
        lp.height = d.getHeight();
//        lp.x = 0;
//        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_sl:
                if (dialog.isShowing()) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }
                break;
        }
    }

    public LookDialog setLookImage(String url) {
//        iv_sl.setImageBitmap(bitmap);
        Glide.with(context).load(TextUtils.isEmpty(url) ? url : SharedPreferencesUtils.getInstance().getParam(SharedPreferencesUtils.IMG_URL, "") + "/" + url)
                .apply(new RequestOptions()
                        .error(R.drawable.ic_add_default)
                        .placeholder(R.drawable.ic_add_default)
                        .fallback(R.drawable.ic_add_default)).into(iv_sl);
        return this;
    }

    public LookDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public LookDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void dissmiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public LookDialog setResure(int id) {
        iv_sl.setImageResource(id);
        return this;
    }

}
