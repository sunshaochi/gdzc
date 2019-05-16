package com.gengcon.android.fixedassets.common.module.scan;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.GApplication;
import com.gengcon.android.fixedassets.module.greendao.AssetBean;
import com.gengcon.android.fixedassets.module.greendao.AssetBeanDao;
import com.gengcon.android.fixedassets.module.inventory.view.ui.SearchAssetActivity;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.zxing.camera.CameraManager;
import com.gengcon.android.fixedassets.zxing.decoding.CaptureInventoryActivityHandler;
import com.gengcon.android.fixedassets.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.List;
import java.util.Vector;


public class ScanInventoryActivity extends Activity implements Callback, CaptureInventoryActivityHandler.DecodeCallback, View.OnClickListener {

    private String pd_no;
    private String user_id;

    private CaptureInventoryActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private boolean mIsLight;
    private AssetBeanDao assetBeanDao;
    private List<AssetBean> wp_asset;
    private List<AssetBean> yp_assrt;
    private int wp_count;
    private int yp_count;
    private TextView wp_textView, yp_textView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_inventory);
        pd_no = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
        user_id = getIntent().getStringExtra("user_id");
        if (pd_no == null) {
            finish();
        }
        assetBeanDao = GApplication.getDaoSession().getAssetBeanDao();
        wp_asset = assetBeanDao
                .queryBuilder()
                .where(assetBeanDao
                        .queryBuilder()
                        .and(AssetBeanDao.Properties.Pd_no.eq(pd_no),
                                AssetBeanDao.Properties.Pd_status.eq(1),
                                AssetBeanDao.Properties.User_id.eq(user_id)))
                .list();
        yp_assrt = assetBeanDao
                .queryBuilder()
                .where(assetBeanDao
                        .queryBuilder()
                        .and(AssetBeanDao.Properties.Pd_no.eq(pd_no),
                                AssetBeanDao.Properties.Pd_status.eq(2),
                                AssetBeanDao.Properties.User_id.eq(user_id)))
                .list();
        wp_count = wp_asset.size();
        yp_count = yp_assrt.size();
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.inventory_scan);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.iv_manual).setOnClickListener(this);
        wp_textView = findViewById(R.id.wp_textView);
        yp_textView = findViewById(R.id.yp_textView);
        wp_textView.setText(wp_count + "");
        yp_textView.setText(yp_count + "");
        CameraManager.init(getApplication());
        viewfinderView = findViewById(R.id.viewfinder_view);
        hasSurface = false;
        mIsLight = false;
        findViewById(R.id.iv_light).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_light:
                switchLight();
                break;
            case R.id.iv_manual:
                Intent intent = new Intent(this, SearchAssetActivity.class);
                intent.putExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID, pd_no);
                startActivityForResult(intent, Constant.REQUEST_CODE_INVENTORY_MANUAL);
                break;
            case R.id.iv_title_left:
                setResult(Constant.RESULT_OK_INVENTORY_SCAN);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    /**
     * 打开或关闭闪关灯
     */
    protected void switchLight() {
        boolean isCameraNull = CameraManager.get().isCameraNull();
        mIsLight = CameraManager.get().switchLight(!mIsLight);
        if (mIsLight) {
            ((ImageView) findViewById(R.id.iv_light)).setImageResource(R.drawable.ic_light_press);
        } else {
            ((ImageView) findViewById(R.id.iv_light)).setImageResource(R.drawable.ic_light_normal);
        }
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        String scanResult = resultString.replaceAll(" ", "");
        if (TextUtils.isEmpty(scanResult)) {
            ToastUtils.toastMessage(this, "Scan failed!");
        } else {
            if (scanResult.length() == 24 || scanResult.length() == 26) {
                AssetBean assetBean = assetBeanDao.queryBuilder()
                        .where(assetBeanDao.queryBuilder().and(AssetBeanDao.Properties.Pd_no.eq(pd_no),
                                AssetBeanDao.Properties.User_id.eq(user_id),
                                AssetBeanDao.Properties.Asset_id.eq(scanResult)))
                        .unique();
                if (assetBean != null) {
                    if (assetBean.getPd_status() == 2) {
                        ToastUtils.toastMessage(this, "该资产已经盘点过");
                        restartScan();
                    } else {
                        assetBean.setPd_status(2);
                        assetBean.setIsScanAsset(1);
                        assetBeanDao.update(assetBean);
                        wp_count--;
                        yp_count++;
                        wp_textView.setText(wp_count + "");
                        yp_textView.setText(yp_count + "");
                        ToastUtils.toastMessage(this, "盘点成功");
                        restartScan();
                    }
                } else {
                    ToastUtils.toastMessage(this, "未查询到此资产");
                    restartScan();
                }
            } else {
                ToastUtils.toastMessage(this, "非精臣固定资产有效二维码");
                restartScan();
            }
        }

    }

    private void restartScan() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.restartPreviewAndDecode();
            }
        }, 1000);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureInventoryActivityHandler(this, decodeFormats,
                    characterSet, viewfinderView, this);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.PREVIEW_CODE && resultCode == RESULT_OK) {
            onBackPressed();
        } else if (requestCode == Constant.REQUEST_CODE_INVENTORY_MANUAL && resultCode == Constant.RESULT_OK_INVENTORY_MANUAL) {
            List<String> assets_ids = (List<String>) data.getSerializableExtra("assets_ids");
            for (int i = 0; i < assets_ids.size(); i++) {
                wp_count--;
                yp_count++;
            }
            wp_textView.setText(wp_count + "");
            yp_textView.setText(yp_count + "");
        }
    }
}