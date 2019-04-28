package com.gengcon.android.fixedassets.module.addasset.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.CustomAttrWheelBean;

import java.util.List;

import androidx.annotation.Nullable;


public class CustomAttrPickerLinearLayout extends LinearLayout implements View.OnClickListener {

    private CustomAttrWheelView wva;
    private CustomAttrWheelBean select;
    private OnCustomAttrPickerListener onSelectListener;

    public CustomAttrPickerLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public CustomAttrPickerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomAttrPickerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.custom_attr_picker_linear_layout, null);
        wva = contentView.findViewById(R.id.main_wv);
        wva.setOnWheelViewListener(onWheelViewListener);
//        mPicker = contentView.findViewById(R.id.picker);
//        mPicker.setOnSelectListener(pickerListener);
        contentView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.v_cancel).setOnClickListener(this);
        addView(contentView);
    }

    public void setOnSelectListener(OnCustomAttrPickerListener listener) {
        this.onSelectListener = listener;
    }

    public void setData(List<CustomAttrWheelBean> customAttrWheelBeans) {
        wva.setData(customAttrWheelBeans);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (onSelectListener != null) {
                    onSelectListener.onCustomAttrConfirm(select);
                }
                setVisibility(GONE);
                break;
            case R.id.v_cancel:
            case R.id.btn_cancel:
                if (onSelectListener != null) {
                    onSelectListener.onCustomAttrCancel();
                }
                setVisibility(GONE);
                break;
        }
    }

    CustomAttrWheelView.OnWheelViewListener onWheelViewListener = new CustomAttrWheelView.OnWheelViewListener() {
        @Override
        public void onSelected(CustomAttrWheelBean item) {
            select = item;
        }
    };

    public interface OnCustomAttrPickerListener {
        void onCustomAttrConfirm(CustomAttrWheelBean customAttrWheelBean);

        void onCustomAttrCancel();
    }

}
