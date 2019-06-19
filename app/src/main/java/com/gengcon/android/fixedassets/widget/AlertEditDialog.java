package com.gengcon.android.fixedassets.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;

public class AlertEditDialog extends Dialog {

    protected AlertEditDialog(Context context) {
        super(context, R.style.Dialog_General);
        setCancelable(false);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle, mText, mNegativeText, mPositiveText;
        private boolean mNegativeDismiss, mPositiveDismiss;
        private View mContentView;
        private EditText editText;
        private EditText et_code;
        private ImageView iv_delete;
        private LinearLayout ll_code;

        private OnClickListener mPositiveListener, mNegativeListener;

        public Builder(Context context) {
            mContext = context;
            mContentView = LayoutInflater.from(mContext).inflate(
                    R.layout.alert_edit_dialog, null);
            editText = mContentView.findViewById(R.id.tv_text);
            et_code = mContentView.findViewById(R.id.et_code);
            iv_delete = mContentView.findViewById(R.id.iv_delete);
            ll_code = mContentView.findViewById(R.id.ll_code);
            iv_delete.setVisibility(View.GONE);
            mNegativeDismiss = true;
            mPositiveDismiss = true;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setTitle(int resId) {
            mTitle = mContext.getString(resId);
            return this;
        }

        public Builder setText(String text) {
            mText = text;
            return this;
        }

        public Builder setPositiveButton(OnClickListener listener, String text) {
            mPositiveListener = listener;
            mPositiveText = text;
            return this;
        }

        public Builder setPositiveDismiss(boolean positiveDismiss) {
            mPositiveDismiss = positiveDismiss;
            return this;
        }

        public Builder setNegativeButton(OnClickListener listener, String text) {
            mNegativeListener = listener;
            mNegativeText = text;
            return this;
        }

        public Builder setNegativeDismiss(boolean negativeDismiss) {
            mNegativeDismiss = negativeDismiss;
            return this;
        }

        public Builder setNegativeButtonClickable(boolean clickable) {
            ((Button) mContentView.findViewById(R.id.btn_negative)).setTextColor(mContext.getResources().getColor(clickable ? R.color.blue : R.color.light_gray_text));
            (mContentView.findViewById(R.id.btn_negative)).setClickable(clickable);
            return this;
        }

        public String getNegativeText() {
            return mNegativeText;
        }

        public String getEditText() {
            return editText.getText().toString();
        }

        public void setEditText(String name) {
            if (!TextUtils.isEmpty(name)) {
                editText.setText(name);
                editText.setSelection(name.length());
            }
        }

        public Builder setEnable(boolean b) {
            et_code.setEnabled(b);
            iv_delete.setEnabled(b);
            if (b) {
                ll_code.setBackgroundResource(R.drawable.alert_edit_bg);
                et_code.addTextChangedListener(new MyTextWatcher());
                iv_delete.setOnClickListener(new MyOnClick());
                if (!TextUtils.isEmpty(et_code.getText())) {
                    et_code.setSelection(et_code.getText().toString().trim().length());
                    iv_delete.setVisibility(View.VISIBLE);
                }
            } else {
                et_code.addTextChangedListener(null);
                ll_code.setBackgroundResource(R.drawable.alert_edit_bg_enable);
                iv_delete.setVisibility(View.GONE);
            }
            return this;
        }


        public Builder setEditCode(String org_code) {
            et_code.setText(org_code);
            return this;
        }


        public AlertEditDialog create() {
            final AlertEditDialog dialog = new AlertEditDialog(mContext);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(mContentView);
            if (TextUtils.isEmpty(mTitle)) {
                mContentView.findViewById(R.id.tv_title).setVisibility(View.GONE);
            } else {
                ((TextView) mContentView.findViewById(R.id.tv_title)).setText(mTitle);
            }
            if (!TextUtils.isEmpty(mText)) {
                ((TextView) mContentView.findViewById(R.id.tv_text)).setText(mText);
            }
            if (TextUtils.isEmpty(mNegativeText)) {
                mContentView.findViewById(R.id.ll_negative).setVisibility(View.GONE);
            } else {
                ((Button) mContentView.findViewById(R.id.btn_negative)).setText(mNegativeText);
                mContentView.findViewById(R.id.btn_negative).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (mNegativeDismiss)
                            dialog.dismiss();
                        if (mNegativeListener != null) {
                            mNegativeListener.onClick(dialog, BUTTON_NEGATIVE);
                        }
                    }
                });
            }

            if (TextUtils.isEmpty(mPositiveText)) {
                mContentView.findViewById(R.id.btn_positive).setVisibility(View.GONE);
            } else {
                ((Button) mContentView.findViewById(R.id.btn_positive)).setText(mPositiveText);
                mContentView.findViewById(R.id.btn_positive).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (mPositiveListener != null) {
                            mPositiveListener.onClick(dialog, BUTTON_POSITIVE);
                        }
                    }
                });
            }

            return dialog;
        }

        public AlertEditDialog show() {
            AlertEditDialog dialog = create();
            if (mContext instanceof Activity
                    && !((Activity) mContext).isFinishing()) {
                dialog.show();
            }
            return dialog;
        }

        public String getEditCode() {
            return et_code.getText().toString().trim();
        }

        private class MyTextWatcher implements TextWatcher {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s.toString())) {
                    iv_delete.setVisibility(View.GONE);
                } else {
                    iv_delete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    iv_delete.setVisibility(View.GONE);
                } else {
                    iv_delete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }

        private class MyOnClick implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                et_code.setText("");
                v.setVisibility(View.GONE);
            }
        }
    }

}

