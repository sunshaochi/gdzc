package com.gengcon.android.fixedassets.rfid;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.gengcon.android.fixedassets.R;

public class RfidDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView tv_totlenum, yp_textView, tv_qx, tv_wc;

    public RfidDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public RfidDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rfid, null);
        tv_totlenum = view.findViewById(R.id.tv_totlenum);
        yp_textView = view.findViewById(R.id.yp_textView);
        tv_qx = view.findViewById(R.id.tv_qx);
        tv_wc = view.findViewById(R.id.tv_wc);
        dialog = new Dialog(context, R.style.Dialog_General);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager manager = dialogWindow.getWindowManager();
        Display d = manager.getDefaultDisplay();
        lp.width = d.getWidth();
        dialogWindow.setAttributes(lp);
        return this;
    }

    /**
     * @param num
     * @return
     */
    public RfidDialog setTotal(int num) {
        tv_totlenum.setText(num + "");
        return this;
    }

    public RfidDialog setYp(int num) {
        yp_textView.setText(num + "");
        return this;
    }

    public void show() {
        dialog.show();
    }


    public void dismiss() {
        dialog.dismiss();
    }

    public void setQuxiaoClick(final QuxiaoListener listener) {
        tv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }

    public void setStartClick(final StartListener listener) {
        tv_wc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }

    public interface QuxiaoListener {
        void onClick();
    }

    public interface StartListener {
        void onClick();
    }


}
