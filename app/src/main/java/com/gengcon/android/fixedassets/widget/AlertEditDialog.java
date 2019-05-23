package com.gengcon.android.fixedassets.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        private OnClickListener mPositiveListener, mNegativeListener;

        public Builder(Context context) {
            mContext = context;
            mContentView = LayoutInflater.from(mContext).inflate(
                    R.layout.alert_edit_dialog, null);
            editText = mContentView.findViewById(R.id.tv_text);
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
                editText.setSelection(name.length());
                editText.setText(name);
            }
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

    }

}

