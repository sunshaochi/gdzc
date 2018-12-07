package com.gengcon.android.fixedassets.bean;
/**
 * 文件名：BluetoothBean
 * 描  述：蓝牙设备类
 * 作  者：张彬
 * 时  间：2018/05/13
 * 版  权：武汉精臣智慧标识科技有限公司
 */
public class BluetoothBean {
    private String mDeviceName;//设备名称
    private String mDeviceAddress;//设备地址
    private int mDeviceStatus;//设备状态,10未配对,12已配对（未连接）,13已连接

    public BluetoothBean() {
        mDeviceName = "";
        mDeviceAddress = "";
        mDeviceStatus = 10;
    }

    public BluetoothBean(String deviceName, String deviceAddress, int deviceStatus) {
        mDeviceName = deviceName;
        mDeviceAddress = deviceAddress;
        mDeviceStatus = deviceStatus;
    }

    public String getDeviceName() {
        return mDeviceName;
    }

    public void setDeviceName(String deviceName) {
        mDeviceName = deviceName;
    }

    public String getDeviceAddress() {
        return mDeviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        mDeviceAddress = deviceAddress;
    }

    public int getDeviceStatus() {
        return mDeviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        mDeviceStatus = deviceStatus;
    }





}
