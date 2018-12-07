package com.gengcon.android.fixedassets.util;

/**
 * 常量
 */
public class Constant {
    // request参数
    public static final int REQ_QR_CODE = 11002; //打开扫描界面请求码

    public static final int RESULT_QR_CODE = 11003;

    public static final int GET_ASSET_CODE = 12002;//打开资产列表
    public static final int PRINTRT_DEVICE = 12003;//打开蓝牙连接页面
    public static final int PREVIEW_CODE = 12004;//Scan打开预览界面
    public static final int REQUEST_CODE_CAMERA = 10000;
    public static final int REQUEST_CODE_GALLERY = 10001;

    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    public static final String INTENT_EXTRA_KEY_SCAN_MODE = "qr_scan_mode";
    public static final String INTENT_EXTRA_KEY_INVENTORY_ID = "inventory_id";
    public static final String INTENT_EXTRA_KEY_ASSETS = "assets";
    public static final String INTENT_EXTRA_KEY_ASSET_IDS = "asset_ids";
    public static final String INTENT_EXTRA_KEY_ASSER_ID = "asset_id";
    public static final String INTENT_EXTRA_KEY_URL = "url";

}
