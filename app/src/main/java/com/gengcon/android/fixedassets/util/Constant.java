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

    public static int RESULT_OK_BEORG = 11004;
    public static int REQUEST_BEORG = 11005;

    public static int RESULT_OK_EDIT_EMP = 11006;
    public static int REQUEST_CODE_EDIT_EMP = 11007;

    public static int RESULT_OK_INVENTORY_SCAN = 11008;
    public static int REQUEST_CODE_INVENTORY_SCAN = 11009;

    public static int RESULT_OK_INVENTORY_MANUAL = 11010;
    public static int REQUEST_CODE_INVENTORY_MANUAL = 11011;

    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    public static final String INTENT_EXTRA_KEY_SCAN_MODE = "qr_scan_mode";
    public static final String INTENT_EXTRA_KEY_INVENTORY_ID = "inventory_id";
    public static final String INTENT_EXTRA_KEY_APPROVAL_ID = "approvalId";
    public static final String INTENT_EXTRA_KEY_APPROVAL_TYPE = "approvalType";
    public static final String INTENT_EXTRA_KEY_ASSETS = "assets";
    public static final String INTENT_EXTRA_KEY_ASSET_IDS = "asset_ids";
    public static final String INTENT_EXTRA_KEY_ASSER_ID = "asset_id";
    public static final String INTENT_IS_HISTORY_ASSER_ID = "isHistory";
    public static final String INTENT_EXTRA_KEY_URL = "url";
    public static final String HOME_ASSET_TOTAL = "total";
    public static final String HOME_ASSET_IDLE = "idle";
    public static final String HOME_ASSET_USE = "use";

}
