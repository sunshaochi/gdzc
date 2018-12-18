package com.gengcon.android.fixedassets.ui;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.adapter.BluetoothDeviceAdapter;
import com.gengcon.android.fixedassets.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.BluetoothBean;
import com.gengcon.android.fixedassets.util.ClsUtils;
import com.gengcon.android.fixedassets.util.JCPrinter;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 文件名：BluetoothDeviceListActivity
 * 描  述：蓝牙连接页面
 * 作  者：张彬
 * 时  间：2018/05/13
 * 版  权：武汉精臣智慧标识科技有限公司
 */
public class BluetoothDeviceListActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    private ProgressBar mProgressBar;
    private CheckBox mCkBluetooth;
    private ListView mList;

    private List<BluetoothBean> mBluetoothList;//蓝牙列表数据集合
    private BluetoothDeviceAdapter mBluetoothDeviceAdapter;//蓝牙列表适配器
    private List<String> mDeviceAddress;//存储mac地址,去除重复添加设备到蓝牙列表中
    IntentFilter mIntentFilter;
    BluetoothReceiver mBluetoothReceiver;
    //选中的设备
    private BluetoothBean mBluetoothSelected;
    //蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_device_list);
        mBluetoothList = new ArrayList<>();
        initView();
    }

    @Override
    protected void initView() {
        mCkBluetooth = findViewById(R.id.ck_bluetooth);
        mProgressBar = findViewById(R.id.progress_bar);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_back);
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.bluetooth_connect);
        mList = findViewById(R.id.list);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mIntentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mIntentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        mIntentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mIntentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mBluetoothReceiver = new BluetoothReceiver();
        registerReceiver(mBluetoothReceiver, mIntentFilter);
        mBluetoothDeviceAdapter = new BluetoothDeviceAdapter(mBluetoothList, BluetoothDeviceListActivity.this);
        mList.setAdapter(mBluetoothDeviceAdapter);
        mBluetoothSelected = new BluetoothBean();
        mDeviceAddress = new ArrayList<>();

        if (JCPrinter.isPrinterConnected()) {
            ((TextView) findViewById(R.id.tv_current_connect)).setText(getString(R.string.current_connect_device) + ":" + readConnectDeviceName());
        }

        findViewById(R.id.btn_discovery).setOnClickListener(this);
        mCkBluetooth.setOnCheckedChangeListener(this);
        mList.setOnItemClickListener(this);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_discovery:
                if (mCkBluetooth.isChecked()) {
                    if (mBluetoothAdapter != null) {
                        requestPermission(mConsumer, Manifest.permission.ACCESS_COARSE_LOCATION);
//                    mBluetoothAdapter.startDiscovery();
//                    mProgressBar.setVisibility(View.VISIBLE);
                    }
                } else {
                    ToastUtils.toastMessage(this, "请打开蓝牙");
                }
                break;
            case R.id.iv_title_left:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        showLoading();
        mBluetoothAdapter.cancelDiscovery();
        mBluetoothSelected = mBluetoothList.get(position);
        mProgressBar.setVisibility(View.GONE);
        BluetoothDevice bluetoothDevice = mBluetoothAdapter.getRemoteDevice(mBluetoothSelected.getDeviceAddress());
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_NONE) {
            new Task().execute(bluetoothDevice);
        }
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED && !JCPrinter.isPrinterConnected()) {
            mProgressBar.setVisibility(View.GONE);
            connectPrint();
            return;
        }
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED && JCPrinter.isPrinterConnected()) {
            if (!bluetoothDevice.getName().equals(readConnectDeviceName())) {
                JCPrinter.closed();
                writeConnectDeviceName("");
                ((TextView) findViewById(R.id.tv_current_connect)).setText(getString(R.string.current_connect_device) + ":" + readConnectDeviceName());
                connectPrint();
            } else {
                hideLoading();
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        if (compoundButton.getId() == R.id.ck_bluetooth) {
            if (checked) {
                mBluetoothAdapter.enable();
            } else {
                mProgressBar.setVisibility(View.GONE);
                mBluetoothAdapter.disable();
                mBluetoothList.clear();
                mDeviceAddress.clear();
                writeConnectDeviceName("");
                mBluetoothDeviceAdapter.notifyDataSetChanged();
            }
        }
    }

    Consumer<Permission> mConsumer = new Consumer<Permission>() {
        @Override
        public void accept(Permission permission) {
            if (permission.granted) {
//                if(mBluetoothAdapter.isDiscovering()) {
//                    mBluetoothAdapter.cancelDiscovery();
//                }
                mBluetoothAdapter.startDiscovery();
                mProgressBar.setVisibility(View.VISIBLE);
            } else if (permission.shouldShowRequestPermissionRationale) {
                ToastUtils.toastMessage(BluetoothDeviceListActivity.this, R.string.permission_bluetooth_tips);
                requestPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            } else {
                ToastUtils.toastMessage(BluetoothDeviceListActivity.this, R.string.permission_bluetooth_tips);
            }
        }
    };


    /**
     * 文件名：BluetoothReceiver
     * 描  述：蓝牙广播，进行设备搜索、配对及连接状态监听
     * 作  者：张彬
     * 时  间：2018/05/03
     * 版  权：张彬
     */
    private class BluetoothReceiver extends BroadcastReceiver {
        String pin = "0000";//此处为你要连接的蓝牙设备的初始密钥,一般为1234或0000

        public BluetoothReceiver() {
        }

        //广播接收器,当远程蓝牙设备被发现时,回调函数onReceive()会被执行
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction(); //得到action
            BluetoothDevice btDevice = null;  //创建一个蓝牙device对象
            btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);// 从Intent中获取设备对象
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {  //发现设备

                if (!mDeviceAddress.contains(btDevice.getAddress()) && btDevice.getBluetoothClass().getDeviceClass() == 1664) {
                    mDeviceAddress.add(btDevice.getAddress());
                    Log.e("BluetoothDevice", "onReceive: " + btDevice.getAddress());
                    if (!TextUtils.isEmpty(readConnectDeviceName()) && readConnectDeviceName().equals(btDevice.getName())) {
                        if (JCPrinter.isPrinterConnected()) {
                            mBluetoothList.add(new BluetoothBean(btDevice.getName(), btDevice.getAddress(), 13));
                        } else {
                            mBluetoothList.add(new BluetoothBean(btDevice.getName(), btDevice.getAddress(), btDevice.getBondState()));
                        }
                    } else {
                        mBluetoothList.add(new BluetoothBean(btDevice.getName(), btDevice.getAddress(), btDevice.getBondState()));
                    }
                }

                mBluetoothDeviceAdapter.notifyDataSetChanged();

            } else if (action.equals(BluetoothDevice.ACTION_PAIRING_REQUEST)) { //再次得到的action，会等于PAIRING_REQUEST

                try {

//                    //1.确认配对
//                    ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
//                    //2.终止有序广播
                    abortBroadcast();//如果没有将广播终止，则会出现一个一闪而过的配对框。
//                    //3.调用setPin方法进行配对...
                    boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, pin);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                mProgressBar.setVisibility(View.GONE);
                Log.e("BluetoothDevice", "onReceive: ACTION_DISCOVERY_FINISHED");
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                switch (btDevice.getBondState()) {
                    case BluetoothDevice.BOND_BONDED:
                        if (btDevice.getName().equals(mBluetoothSelected.getDeviceName())) {
                            mBluetoothSelected.setDeviceStatus(12);
                            connectPrint();
                        }
                        break;
                    default:
                        break;
                }
            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                Log.e("BluetoothAdapter.state", "state: " + state);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        mCkBluetooth.setChecked(false);
                        break;
                    case BluetoothAdapter.STATE_ON:
                    case BluetoothAdapter.STATE_TURNING_ON:
                        mCkBluetooth.setChecked(true);
                        break;
                }
            }
        }
    }

    private void writeConnectDeviceName(String deviceName) {
        SharedPreferences.Editor editor = getSharedPreferences("Connect", MODE_PRIVATE).edit();
        editor.putString("device_name", deviceName);
        editor.apply();
    }

    private String readConnectDeviceName() {
        SharedPreferences read = getSharedPreferences("Connect", MODE_PRIVATE);
        return read.getString("device_name", "");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBluetoothReceiver);
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.cancelDiscovery();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (ClsUtils.getBlueToothStatus(mBluetoothAdapter)) {
            mCkBluetooth.setChecked(true);
            mBluetoothList.clear();
            mDeviceAddress.clear();
            mBluetoothDeviceAdapter.notifyDataSetChanged();
            requestPermission(mConsumer, Manifest.permission.ACCESS_COARSE_LOCATION);
//            mBluetoothAdapter.startDiscovery();
//            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mCkBluetooth.setChecked(false);
            mProgressBar.setVisibility(View.GONE);
            mBluetoothList.clear();
            mDeviceAddress.clear();
            writeConnectDeviceName("");
        }
    }

    private void connectPrint() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> emitter) {
                boolean flag = JCPrinter.isPrinterConnected();
                if (!flag) {
                    flag = JCPrinter.isSupported(mBluetoothSelected.getDeviceName());
                }
                if (flag) {
                    flag = JCPrinter.openPrinter(mBluetoothSelected.getDeviceName());
                }
                emitter.onNext(flag);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Boolean aBoolean) {
                hideLoading();
                if (JCPrinter.isPrinterConnected()) {
                    mBluetoothSelected.setDeviceStatus(13);
                    writeConnectDeviceName(mBluetoothSelected.getDeviceName());
                    mBluetoothDeviceAdapter.notifyDataSetChanged();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    ToastUtils.toastMessage(BluetoothDeviceListActivity.this, R.string.print_connect_fail);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        });
    }

    class Task extends AsyncTask<BluetoothDevice, Void, Void> {

        @Override
        protected Void doInBackground(BluetoothDevice... bluetoothDevices) {
            try {
                ClsUtils.createBond(bluetoothDevices[0].getClass(), bluetoothDevices[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

}
