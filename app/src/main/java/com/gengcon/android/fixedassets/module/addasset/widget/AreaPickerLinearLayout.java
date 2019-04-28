package com.gengcon.android.fixedassets.module.addasset.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.WheelBean;


import java.util.List;

import androidx.annotation.Nullable;


public class AreaPickerLinearLayout extends LinearLayout implements View.OnClickListener {

    private AreaWheelView wva;
    private WheelBean select;
    private OnAreaPickerListener onSelectListener;

    public AreaPickerLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public AreaPickerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AreaPickerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.area_picker_linear_layout, null);
        wva = contentView.findViewById(R.id.main_wv);
        wva.setOnWheelViewListener(onWheelViewListener);
//        mPicker = contentView.findViewById(R.id.picker);
//        mPicker.setOnSelectListener(pickerListener);
        contentView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.v_cancel).setOnClickListener(this);
        addView(contentView);
    }

    public void setOnSelectListener(OnAreaPickerListener listener) {
        this.onSelectListener = listener;
    }

    public void setData(List<WheelBean> customAttrWheelBeans) {
        wva.setData(customAttrWheelBeans);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (onSelectListener != null) {
                    onSelectListener.onAreaConfirm(select);
                }
                setVisibility(GONE);
                break;
            case R.id.v_cancel:
            case R.id.btn_cancel:
                if (onSelectListener != null) {
                    onSelectListener.onAreaCancel();
                }
                setVisibility(GONE);
                break;
        }
    }

    AreaWheelView.OnWheelViewListener onWheelViewListener = new AreaWheelView.OnWheelViewListener() {
        @Override
        public void onSelected(WheelBean item) {
            select = item;
        }
    };

    public interface OnAreaPickerListener {
        void onAreaConfirm(WheelBean wheelBean);

        void onAreaCancel();
    }

}
