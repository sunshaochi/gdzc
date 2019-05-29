package com.gengcon.android.fixedassets.common.module.update;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.gengcon.android.fixedassets.BuildConfig;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;

import java.io.File;

/**
 * 文件名：ApkDownLoad
 * 描    述：版本更新Dialog
 * 作    者：杨兴
 * 时    间：2018.03.7
 * 版    权：武汉精臣智慧标识科技有限公司
 */
@SuppressLint("NewApi")
public class ApkDownLoad {

    private static String DOWNLOAD_FOLDER_NAME = "FixedAssets";
    private static String DOWNLOAD_FILE_NAME = "";
    public static String APK_DOWNLOAD_ID = "apkDownloadIdJC_FixedAssets";
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    private static Context mContext;
    private String url;
    private String notificationTitle;
    private String notificationDescription;

    private DownloadManager downloadManager;
    private CompleteReceiver completeReceiver;
    private DownLoadDialog downloadDialog;

    /**
     * @param context
     * @param url                     下载apk的url
     * @param notificationTitle       通知栏标题
     * @param notificationDescription 通知栏描述
     */
    @SuppressWarnings("static-access")
    public ApkDownLoad(Context context, String url, String notificationTitle, String notificationDescription) {
        super();
        this.mContext = context;
        this.url = url;
        this.notificationTitle = notificationTitle;
        this.notificationDescription = notificationDescription;
        DOWNLOAD_FILE_NAME = context.getPackageName() + SharedPreferencesUtils.getInstance().getParam("versionCode", 0).toString();
        downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        completeReceiver = new CompleteReceiver();
        downloadDialog = DownLoadDialog.getInstance(context, true);
        /** register download success broadcast **/
        context.registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @SuppressLint("NewApi")
    public void execute() {
        // 清除已下载的内容重新下载
        long downloadId = (long) SharedPreferencesUtils.getInstance().getParam(APK_DOWNLOAD_ID, -1L);
        if (downloadId != -1) {
            downloadManager.remove(downloadId);
            SharedPreferencesUtils.getInstance().clear(APK_DOWNLOAD_ID);
        }
        Request request = new Request(Uri.parse(url));
        // 设置Notification中显示的文字
        request.setTitle(notificationTitle);
        request.setDescription(notificationDescription);
        // 设置可用的网络类型
        request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
        // 设置状态栏中显示Notification
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 不显示下载界面
        request.setVisibleInDownloadsUi(false);
        // 设置下载后文件存放的位置
        File folder = Environment.getExternalStoragePublicDirectory(getDOWNLOAD_FOLDER_NAME());
        if (!folder.exists() || !folder.isDirectory()) {
            folder.mkdirs();
        }
        request.setDestinationInExternalPublicDir(getDOWNLOAD_FOLDER_NAME(), getDOWNLOAD_FILE_NAME());
        // 设置文件类型
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        // 保存返回唯一的downloadId
        SharedPreferencesUtils.getInstance().setParam(APK_DOWNLOAD_ID, downloadManager.enqueue(request));
        downloadDialog.show();
        DownloadChangeObserver downloadObserver = new DownloadChangeObserver(null);
        mContext.getContentResolver().registerContentObserver(CONTENT_URI, true, downloadObserver);
    }

    class DownloadChangeObserver extends ContentObserver {

        public DownloadChangeObserver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onChange(boolean selfChange) {
            long downloadId = (long) SharedPreferencesUtils.getInstance().getParam(APK_DOWNLOAD_ID, -1l);
            queryDownloadStatus(downloadManager, downloadId);
        }

    }

    private int queryDownloadStatus(DownloadManager dowanloadmanager, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        int status = -1;
        Cursor c = dowanloadmanager.query(query);
        if (c != null && c.moveToFirst()) {
            status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

            int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
            int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileSizeIdx = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            String title = c.getString(titleIdx);
            int fileSize = c.getInt(fileSizeIdx);
            int bytesDL = c.getInt(bytesDLIdx);

            // Translate the pause reason to friendly text.
            int reason = c.getInt(reasonIdx);
            // Display the status
            downloadDialog.setCurrentProgress(bytesDL, fileSize);
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("tag", "STATUS_PAUSED");
                case DownloadManager.STATUS_PENDING:
                    Log.v("tag", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    // 正在下载，不做任何事情
                    Log.v("tag", "STATUS_RUNNING");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    // 完成
                    Log.v("tag", "下载完成");
                    SharedPreferencesUtils.getInstance().clear(SharedPreferencesUtils.TOKEN);
                    downloadDialog.dismiss();
                    // dowanloadmanager.remove(downloadId);
                    break;
                case DownloadManager.STATUS_FAILED:
                    // 清除已下载的内容，重新下载
                    Log.v("tag", "STATUS_FAILED");
                    dowanloadmanager.remove(downloadId);
                    break;
            }
        }
        return status;
    }

    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * get the id of download which have download success, if the id is
             * my id and it's status is successful, then install it
             **/
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            long downloadId = (long) SharedPreferencesUtils.getInstance().getParam(APK_DOWNLOAD_ID, -1l);

            if (completeDownloadId == downloadId) {

                // if download successful
                if (queryDownloadStatus(downloadManager, downloadId) == DownloadManager.STATUS_SUCCESSFUL) {

                    // clear downloadId
                    SharedPreferencesUtils.getInstance().clear(APK_DOWNLOAD_ID);

                    // unregisterReceiver
                    context.unregisterReceiver(completeReceiver);

                    // install apk
                    String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                            .append(File.separator).append(getDOWNLOAD_FOLDER_NAME()).append(File.separator)
                            .append(getDOWNLOAD_FILE_NAME()).toString();
                    install(context, apkFilePath);
                }
            }
        }
    }

    /**
     * install app
     *
     * @param context
     * @param filePath
     * @return whether apk exist
     */
    public static boolean install(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkFile = new File(filePath);
        Log.e("ApkDownload", "install: " + filePath);
        if (apkFile != null && apkFile.length() > 0 && apkFile.exists() && apkFile.isFile()) {
//            i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public static String getDOWNLOAD_FOLDER_NAME() {
        return DOWNLOAD_FOLDER_NAME;
    }

    public void setDOWNLOAD_FOLDER_NAME(String dOWNLOAD_FOLDER_NAME) {
        DOWNLOAD_FOLDER_NAME = dOWNLOAD_FOLDER_NAME;
    }

    public static String getDOWNLOAD_FILE_NAME() {
        return DOWNLOAD_FILE_NAME;
    }

    public void setDOWNLOAD_FILE_NAME(String dOWNLOAD_FILE_NAME) {
        DOWNLOAD_FILE_NAME = dOWNLOAD_FILE_NAME;
    }

}
