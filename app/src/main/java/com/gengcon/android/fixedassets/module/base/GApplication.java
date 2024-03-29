package com.gengcon.android.fixedassets.module.base;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.RequiresApi;

import android.util.Log;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.loader.GlideImageLoader;
import com.gengcon.android.fixedassets.module.base.loader.GlidePauseOnScrollListener;
import com.gengcon.android.fixedassets.module.greendao.DaoMaster;
import com.gengcon.android.fixedassets.module.greendao.DaoSession;
import com.gengcon.android.fixedassets.util.EmshConstant;
import com.gengcon.android.fixedassets.util.FontUtils;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.multidex.MultiDex;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.jpush.android.api.JPushInterface;
import realid.rfidlib.MyLib;

public class GApplication extends Application {
    private static GApplication instance;
    private MyLib idataLib;
    private int activityCount;//activity的count数
    private boolean isForeground;//是否在前台

    private static DaoSession daoSession;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setUpDatabase();
        MultiDex.install(this);
        CrashReport.initCrashReport(getApplicationContext(), "fee0e191d8", true);
        SharedPreferencesUtils.getInstance().setContext(this);
        EaseUiHelper.getInstance().init(this);//初始化环信
        idataLib = new MyLib(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        FontUtils.getInstance().replaceSystemDefaultFontFromAsset(this, "fonts/PingFangRegular.ttf");
        initGallery();
        registerActivityLifecycle();//监控app从前后台互相转化用来更新
    }

    private void registerActivityLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (0 == activityCount) {
                    isForeground = false;
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }



    public static GApplication getInstance() {
        return instance;
    }

    public MyLib getIdataLib() {
        return idataLib;
    }


    private void initGallery() {
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.parseColor("#5a8dff"))
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarIconColor(Color.BLACK)
                .setFabNornalColor(Color.parseColor("#5a8dff"))
                .setFabPressedColor(Color.parseColor("#4a7def"))//BULE
                .setCropControlColor(Color.parseColor("#5a8dff"))
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.BLACK)
                .setIconBack(R.drawable.ic_back)
                //下拉
                .setIconFolderArrow(R.color.blue)
                .build();
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(false)
                .setEnableRotate(false)
                .setCropSquare(true)
                .setForceCrop(false)
                .setEnablePreview(false)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GlideImageLoader(), theme)
//                .setTakePhotoFolder(new File("/sdcard/GalleryFinal/edittemp/"))  //拍照不显示两张
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .build();
        GalleryFinal.init(coreConfig);
    }

    public void PowerUp() {
        setPowerState_UHF(true);
        enableUartComm_UHF(true);
    }

    private void setUpDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "inventory.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * power down for the UHF
     */
    public void PowerDown() {
        setPowerState_UHF(false);
        enableUartComm_UHF(false);
    }

    private void setPowerState_UHF(boolean bPowerOn) {
        Intent intent = new Intent(EmshConstant.Action.INTENT_EMSH_REQUEST);
        intent.putExtra(EmshConstant.IntentExtra.EXTRA_COMMAND, EmshConstant.Command.CMD_REQUEST_SET_POWER_MODE);
        if (bPowerOn) {
            intent.putExtra(EmshConstant.IntentExtra.EXTRA_PARAM_1, EmshConstant.EmshBatteryPowerMode.EMSH_PWR_MODE_DSG_UHF);
        } else {
            intent.putExtra(EmshConstant.IntentExtra.EXTRA_PARAM_1, EmshConstant.EmshBatteryPowerMode.EMSH_PWR_MODE_STANDBY);
        }
        sendBroadcast(intent);
        Log.d(intent.getAction(), "Set UHF module power state to " + (bPowerOn ? "POWER_ON" : "POWER_OFF"));
    }

    private void enableUartComm_UHF(boolean bEnable) {
        Intent intent = new Intent(EmshConstant.Action.INTENT_EMSH_REQUEST);
        intent.putExtra(EmshConstant.IntentExtra.EXTRA_COMMAND, EmshConstant.Command.CMD_REQUEST_ENABLE_UHF_COMM);
        intent.putExtra(EmshConstant.IntentExtra.EXTRA_PARAM_1, (int) (bEnable ? 1 : 0));
        sendBroadcast(intent);
        Log.d(intent.getAction(), (bEnable ? "Enable" : "Disable") + " UHF module communication.");
    }
}
