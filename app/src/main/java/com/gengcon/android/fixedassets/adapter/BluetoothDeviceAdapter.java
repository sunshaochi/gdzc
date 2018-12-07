package com.gengcon.android.fixedassets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.BluetoothBean;

import java.util.List;

/**
 * 文件名：BluetoothDeviceAdapter
 * 描  述：蓝牙列表适配器
 * 作  者：张彬
 * 时  间：2018/05/13
 * 版  权：武汉精臣智慧标识科技有限公司
 */
public class BluetoothDeviceAdapter extends BaseAdapter {
    private List<BluetoothBean> mBluetoothList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public BluetoothDeviceAdapter(List<BluetoothBean> bluetoothList, Context context) {
        mBluetoothList = bluetoothList;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBluetoothList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBluetoothList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView =mLayoutInflater.inflate(R.layout.bluetooth_item,null);
            viewHolder =new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tv_name);
            viewHolder.tvStatus = convertView.findViewById(R.id.tv_status);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BluetoothBean bluetooth = mBluetoothList.get(position);

        viewHolder.tvName.setText(bluetooth.getDeviceName());
        if(bluetooth.getDeviceStatus()==10){
            viewHolder.tvStatus.setText(R.string.default_status);
        }else if(bluetooth.getDeviceStatus()==12){
            viewHolder.tvStatus.setText(R.string.print_not_connect);
        }else  if(bluetooth.getDeviceStatus()==13){
            viewHolder.tvStatus.setText(R.string.print_connect);
        }

        return convertView;
    }


    class  ViewHolder{
        TextView tvName;
        TextView tvStatus;
    }
}
