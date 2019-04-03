package com.gengcon.android.fixedassets.common.module.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;

import java.io.File;
import java.text.DecimalFormat;

public class DownLoadDialog extends Dialog implements View.OnClickListener {

    private CircleProgressView cpvProgress;
    private TextView tvTitle;
    private TextView tvCurrent;
    private TextView tvTotal;
    private Context context;
    private double progress;
    private static DownLoadDialog instance;

    /**
     * @param context
     * @param isNeedInstance 是否需要实例
     * @return
     */
    public static DownLoadDialog getInstance(Context context, boolean isNeedInstance) {
        if (instance == null) {
            if (isNeedInstance) {
                instance = new DownLoadDialog(context);
            }
        }
        return instance;
    }

    private DownLoadDialog(Context context) {
        super(context, R.style.custom_dialog);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_dialog);
        cpvProgress = findViewById(R.id.cpv_view);
        tvTitle = findViewById(R.id.tv_title);
        tvCurrent = findViewById(R.id.tv_current);
        tvTotal = findViewById(R.id.tv_total);
        setCanceledOnTouchOutside(false);
    }

    DecimalFormat df;
    double start, end;

    public void setCurrentProgress(int now, int total) {
        df = new DecimalFormat("######0.00");
        if (now > 0) {
            start = (double) now / 1024 / 1024;
//            tvCurrent.setText(df.format(start) + "mb");
            myHandler.sendEmptyMessage(0);
        }
        if (total > 0) {
            end = (double) total / 1024 / 1024;
//            tvTotal.setText(" 共" + df.format(end) + "mb");
            myHandler.sendEmptyMessage(1);
        }
        progress = (double) now / total * 100;
        cpvProgress.setCurrentPercent((int) progress);
        if (progress == 100) {
            myHandler.sendEmptyMessage(2);
        }
    }

    public Handler myHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {

                case 0:
                    tvCurrent.setText(df.format(start) + "mb");
                    break;

                case 1:
                    tvTotal.setText(" 共" + df.format(end) + "mb");
                    break;

                case 2:
                    tvTitle.setText("下载完成");
                    break;

                default:
                    break;

            }
            return false;
        }
    });

    public void setComplete() {
        if (((int) progress) == 100) {
            cpvProgress.setComplete();
            cpvProgress.setOnClickListener(this);
        }

    }

    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
        // if (keyCode == KeyEvent.KEYCODE_BACK) {
        // WaitDialog.this.cancel();
        // activity.finish();
        // }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.cpv_view:
                String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                        .append(File.separator).append(ApkDownLoad.getDOWNLOAD_FOLDER_NAME()).append(File.separator)
                        .append(ApkDownLoad.getDOWNLOAD_FILE_NAME()).toString();
                ApkDownLoad.install(context, apkFilePath);
                break;

            default:
                break;
        }
    }
}
