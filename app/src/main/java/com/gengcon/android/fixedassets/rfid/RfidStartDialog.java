package com.gengcon.android.fixedassets.rfid;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;

public class RfidStartDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView wp_textView, tv_num, tv_zt, yp_textView, tv_wc;
    private DialogOnKeyDownListener downListener;

    public RfidStartDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public RfidStartDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rfid_start, null);
        wp_textView = view.findViewById(R.id.wp_textView);
        tv_num = view.findViewById(R.id.tv_num);
        tv_zt = view.findViewById(R.id.tv_zt);
        yp_textView = view.findViewById(R.id.yp_textView);
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
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (downListener != null) {
                    downListener.onKeyDownListener(keyCode, event);
                }
                return true;
            }
        });
        return this;
    }

    /**
     * @param num
     * @return
     */
    public RfidStartDialog setTotal(int num) {
        wp_textView.setText(num + "");
        return this;
    }

    public RfidStartDialog setYp(int num) {
        yp_textView.setText(num + "");
        return this;
    }

    /**
     * @param
     * @return
     */
    public RfidStartDialog setLeft(String text) {
        tv_zt.setText(text + "");
        return this;
    }

    public String getLeft() {
        return tv_zt.getText().toString();
    }

    /**
     * @param num
     * @return
     */
    public RfidStartDialog setNum(String num) {
        if (!TextUtils.isEmpty(num)) {
            tv_num.setText(num);
        } else {
            tv_num.setText(0 + "");
        }
        return this;
    }

    public void show() {
        dialog.show();
    }


    public void dismiss() {
        dialog.dismiss();
    }

    public void setStopClick(final StopListener listener) {
        tv_zt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }

    public void setCompleteClick(final CompileListener listener) {
        tv_wc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }

    public void setDialogOnKeyDownListener(final DialogOnKeyDownListener dialogOnKeyDownListener) {
        this.downListener = dialogOnKeyDownListener;
    }

    public interface DialogOnKeyDownListener {
        void onKeyDownListener(int keyCode, KeyEvent event);
    }

    public interface StopListener {
        void onClick();
    }

    public interface CompileListener {
        void onClick();
    }


}
