package com.gengcon.android.fixedassets.rfid;

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

public class RfidDialog {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView tv_totlenum,tv_num,tv_zt,tv_qx,tv_wc;

    public RfidDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public boolean isShowing(){
        return dialog.isShowing();
    }

    public RfidDialog builder(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rfid, null);
        tv_totlenum=view.findViewById(R.id.tv_totlenum);
        tv_num=view.findViewById(R.id.tv_num);
        tv_zt=view.findViewById(R.id.tv_zt);
        tv_qx=view.findViewById(R.id.tv_qx);
        tv_wc=view.findViewById(R.id.tv_wc);
        // 定义Dialog布局和参数
        dialog = new Dialog(context,R.style.Dialog_General);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager manager=dialogWindow.getWindowManager();
        Display d=manager.getDefaultDisplay();
        lp.width=d.getWidth();

//        lp.x = 0;
//        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }


    public RfidDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public RfidDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * @param num
     * @return
     */
    public RfidDialog setTotal(int num) {
        tv_totlenum.setText(num+"");
        return this;
    }

    /**
     * @param
     * @return
     */
    public RfidDialog setLeft(String  text) {
        tv_zt.setText(text+"");
        return this;
    }

    public String getLeft(){
        return tv_zt.getText().toString();
    }

    /**
     * @param num
     * @return
     */
    public RfidDialog setNum(String num) {
        if(!TextUtils.isEmpty(num)) {
            tv_num.setText(num);
        }else {
            tv_num.setText(0+"");
        }
        return this;
    }

    public void show() {
        dialog.show();
    }


    public void dismiss(){
        dialog.dismiss();
    }

    public void setStopClick(final StopListener listener){
      tv_zt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              listener.onClick();
          }
      });
    }

    public void setQuxiaoClick(final QuxiaoListener listener){
        tv_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }

    public void setCompleteClick(final CompileListener listener){
        tv_wc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick();
            }
        });
    }


    public interface StopListener{
        public void onClick();
    }

    public interface QuxiaoListener{
        public void onClick();
    }

    public interface CompileListener{
        public void onClick();
    }








}
