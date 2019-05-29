package com.gengcon.android.fixedassets.widget;

import android.content.Context;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.WheelBean;

import java.util.List;


public class PickerLinearLayout extends LinearLayout implements View.OnClickListener {

    private WheelView wva;
    private WheelBean select;
    private OnPickerListener onSelectListener;
    private TextView titleNameView;

    public PickerLinearLayout(Context context) {
        super(context);
        init(context);
    }

    public PickerLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PickerLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.picker_linear_layout, null);
        titleNameView = contentView.findViewById(R.id.titleNameView);
        wva = contentView.findViewById(R.id.main_wv);
        wva.setOnWheelViewListener(onWheelViewListener);
//        mPicker = contentView.findViewById(R.id.picker);
//        mPicker.setOnSelectListener(pickerListener);
        contentView.findViewById(R.id.btn_confirm).setOnClickListener(this);
        contentView.findViewById(R.id.btn_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.v_cancel).setOnClickListener(this);
        addView(contentView);
    }

    public void setOnSelectListener(OnPickerListener listener) {
        this.onSelectListener = listener;
    }

    public void setData(List<WheelBean> users, int type) {
        if (type == 1) {
            titleNameView.setText("管理员");
        } else if (type == 2) {
            titleNameView.setText("当前状态");
        } else {
            titleNameView.setText("");
        }
        wva.setData(users);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (onSelectListener != null) {
                    onSelectListener.onConfirm(select);
                }
                setVisibility(GONE);
                break;
            case R.id.v_cancel:
            case R.id.btn_cancel:
                if (onSelectListener != null) {
                    onSelectListener.onCancel();
                }
                setVisibility(GONE);
                break;
        }
    }

    //    // 滚动选择器选中事件
//    PickerScrollView.OnSelectListener pickerListener = new PickerScrollView.OnSelectListener() {
//
//        @Override
//        public void onSelect(User user) {
//            select = user;
//        }
//    };
//
    WheelView.OnWheelViewListener onWheelViewListener = new WheelView.OnWheelViewListener() {
        @Override
        public void onSelected(WheelBean item) {
            select = item;
        }
    };

    public interface OnPickerListener {
        void onConfirm(WheelBean user);

        void onCancel();
    }

}
