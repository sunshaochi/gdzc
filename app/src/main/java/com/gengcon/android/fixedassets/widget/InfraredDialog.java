package com.gengcon.android.fixedassets.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;

public class InfraredDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView wp_textView, yp_textView, manualTextView, completeTextView;

    public InfraredDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public InfraredDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_infrared, null);
        wp_textView = view.findViewById(R.id.wp_textView);
        yp_textView = view.findViewById(R.id.yp_textView);
        manualTextView = view.findViewById(R.id.manualTextView);
        completeTextView = view.findViewById(R.id.completeTextView);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.Dialog_General);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager manager = dialogWindow.getWindowManager();
        Display d = manager.getDefaultDisplay();
        lp.width = d.getWidth();

//        lp.x = 0;
//        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public InfraredDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public InfraredDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * @param num
     * @return
     */
    public InfraredDialog setWpNum(int num) {
        wp_textView.setText(num + "");
        return this;
    }

    /**
     * @param num
     * @return
     */
    public InfraredDialog setYpNum(String num) {
        if (!TextUtils.isEmpty(num)) {
            yp_textView.setText(num);
        } else {
            yp_textView.setText(0 + "");
        }
        return this;
    }

    public void show() {
        dialog.show();
    }


    public void dismiss() {
        dialog.dismiss();
    }

    public void setManualClick(final ManualListener listener) {
        manualTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }

    public void setCompleteClick(final CompleteListener listener) {
        completeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }


    public interface ManualListener {
        public void onClick();
    }

    public interface CompleteListener {
        public void onClick();
    }


}
