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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.main.view.ui.ScanLoginActivity;
import com.gengcon.android.fixedassets.module.main.presenter.ScanLoginPresenter;
import com.gengcon.android.fixedassets.util.Constant;
import com.gengcon.android.fixedassets.util.StringIsDigitUtil;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.zxing.camera.CameraManager;
import com.gengcon.android.fixedassets.zxing.decoding.CaptureActivityHandler;
import com.gengcon.android.fixedassets.zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;


public class ScanActivity extends Activity implements Callback, CaptureActivityHandler.DecodeCallback, View.OnClickListener {

    public static final int QR_SCAN_LOGIN_MODE = 0x01;
    public static final int QR_SCAN_ASSET_MODE = 0x02;

    private ArrayList<String> mAssetIds;
    private String mDocId;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private boolean mIsLight;
    private ScanLoginPresenter mPresenter;

    private int mMode;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mMode = getIntent().getIntExtra(Constant.INTENT_EXTRA_KEY_SCAN_MODE, QR_SCAN_ASSET_MODE);
        if (mMode == QR_SCAN_ASSET_MODE) {
            mDocId = getIntent().getStringExtra(Constant.INTENT_EXTRA_KEY_INVENTORY_ID);
            mAssetIds = (ArrayList<String>) getIntent().getSerializableExtra(Constant.INTENT_EXTRA_KEY_ASSET_IDS);
            if (mDocId == null || mAssetIds == null) {
                finish();
            }
        }
        mPresenter = new ScanLoginPresenter();
        initView();
    }

    private void initView() {
        CameraManager.init(getApplication());
        viewfinderView = findViewById(R.id.viewfinder_view);
        hasSurface = false;
        mIsLight = false;
        findViewById(R.id.iv_light).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
    }

//    private void requestPermission(String... permissions) {
//        AndPermission.with(this)
//                .runtime()
//                .permission(permissions)
//                .rationale(new RuntimeRationale())
//                .onGranted(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> permissions) {
//                    }
//                })
//                .onDenied(new Action<List<String>>() {
//                    @Override
//                    public void onAction(@NonNull List<String> permissions) {
//                        if (AndPermission.hasAlwaysDeniedPermission(ScanActivity.this, permissions)) {
//                        }
//                    }
//                })
//                .start();
//    }

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
            case R.id.iv_close:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.INTENT_EXTRA_KEY_QR_SCAN, mAssetIds);
        resultIntent.putExtras(bundle);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    /**
     * 打开或关闭闪关灯
     */
    protected void switchLight() {
        boolean isCameraNull = CameraManager.get().isCameraNull();
//        if (isCameraNull) {
//            requestPermissions();
//            return;
//        }
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
        String scanResult = resultString.replaceAll(" ","");
        if (TextUtils.isEmpty(scanResult)) {
            ToastUtils.toastMessage(this, "Scan failed!");
        } else {
            if (mMode == QR_SCAN_LOGIN_MODE) {
                if (StringIsDigitUtil.isLetterDigit(scanResult)) {
                    if (scanResult.length() == 24) {
                        Intent intent = getIntent();
                        intent.putExtra("resultString", scanResult);
                        setResult(Constant.RESULT_QR_CODE, intent);
                        finish();
                    } else {
                        ToastUtils.toastMessage(this, "非精臣固定资产有效二维码");
                        finish();
                    }
                } else {
                    Log.e("ScanLoginActivity", "handleDecode:" + scanResult);
                    int i = scanResult.indexOf("qr_string=");
                    if (i == -1) {
                        ToastUtils.toastMessage(this, "非精臣固定资产有效二维码");
                        finish();
                        return;
                    }
                    int index = scanResult.lastIndexOf("=");
                    Intent intent = new Intent(this, ScanLoginActivity.class);
                    intent.putExtra("uuid", scanResult.substring(index + 1, scanResult.length()));
                    startActivity(intent);
                    Log.e("ScanLoginActivity", "handleDecode222:" + scanResult.substring(index + 1, scanResult.length()));
                    mPresenter.scanLogin(scanResult.substring(index + 1, scanResult.length()), 1);
                    finish();
                }
            }
        }

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
            handler = new CaptureActivityHandler(this, decodeFormats,
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

    private boolean duplicate(String id) {
        for (String assetId : mAssetIds) {
            if (id.equals(assetId)) {
                return false;
            }
        }
        return true;
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
        }
    }
}