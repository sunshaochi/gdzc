package com.gengcon.android.fixedassets.rfid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.util.Logger;

/**
 * author CYD
 * date 2018/11/26
 * email chengyd@idatachina.com
 */
public class MUtil {

    public static void show(String text) {
        Toast.makeText(GApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }


    public static void show(int rid) {
        Toast.makeText(GApplication.getInstance(), rid, Toast.LENGTH_SHORT).show();
    }

    private static ProgressDialog dialog;

    //8.0以上的废弃了ProgressDialog，不可用
    public static void showProgressDialog(String text, Context con) {
        if (dialog == null) {
            dialog = new ProgressDialog(con);
            dialog.setTitle(text);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    public static void cancleDialog() {
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    private static AlertDialog atdialog;

    public static void warningDialog(Context con) {

            atdialog = new AlertDialog.Builder(con).create();
            atdialog.setTitle("电源无法开启");
            atdialog.setMessage(
                    "1.请确认设置-〉用户自定义-〉EMSH设置-〉开启EMSH服务已勾选\n" +
                            "2.请确认PDA右上角有两个电池图标，且都显示有电；\n" +
                            "如果只有一个电池图标，请确认把枪和PDA连接是否可靠；\n如果左侧电池图标没电，请使用专用三合一座充充电\n" +
                            "3.如还有问题，请截取EMSH设置-〉EMSH状态的截图，发送给相关技术支持"
            );
             atdialog.setCancelable(true);
             atdialog.show();
    }

    public static void cancelWaringDialog() {
        if (atdialog != null) {
            atdialog.cancel();
            atdialog = null;
        }
    }

}
