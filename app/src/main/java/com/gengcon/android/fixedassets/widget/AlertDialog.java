package com.gengcon.android.fixedassets.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.util.DensityUtils;

public class AlertDialog extends Dialog {

    protected AlertDialog(Context context) {
        super(context, R.style.Dialog_General);
        setCancelable(false);
    }

    public static class Builder {
        private Context mContext;
        private String mTitle, mText, mNegativeText, mNeutralText, mPositiveText;
        private boolean mNegativeDismiss, mNeutralDismiss, mPositiveDismiss;
        private View mContentView;
        private TextView mTvText;
        private boolean isUpdate;
        private ImageView imgView;
        private String url;
        private boolean isImg;

        private OnClickListener mPositiveListener, mNeutralListener, mNegativeListener;

        public Builder(Context context, boolean isImg) {
            mContext = context;
            this.isImg = isImg;
            if (isImg) {
                mContentView = LayoutInflater.from(mContext).inflate(
                        R.layout.alert_dialog_img, null);
                imgView = mContentView.findViewById(R.id.summaryImg);
            } else {
                mContentView = LayoutInflater.from(mContext).inflate(
                        R.layout.alert_dialog, null);
                mTvText = mContentView.findViewById(R.id.tv_text);
            }
            mNegativeDismiss = true;
            mNeutralDismiss = true;
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

        public Builder setText(int resId) {
            mText = mContext.getString(resId);
            return this;
        }

        public Builder setImg(String url) {
            this.url = url;
            return this;
        }

        public Builder setUpDate(boolean isUpdate) {
            this.isUpdate = isUpdate;
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

        public Builder setNeutralButton(OnClickListener listener, String text) {
            mNeutralListener = listener;
            mNeutralText = text;
            return this;
        }

        public Builder setNeutralButtonClickable(boolean clickable) {
            ((Button) mContentView.findViewById(R.id.btn_neutral)).setTextColor(mContext.getResources().getColor(clickable ? R.color.blue : R.color.light_gray_text));
            (mContentView.findViewById(R.id.btn_neutral)).setClickable(clickable);
            return this;
        }

        public Builder setNeutralButtonColor() {
            ((Button) mContentView.findViewById(R.id.btn_neutral)).setTextColor(mContext.getResources().getColor(R.color.gray_text));
            return this;
        }

        public Builder setNegativeButtonClickable(boolean clickable) {
            ((Button) mContentView.findViewById(R.id.btn_negative)).setTextColor(mContext.getResources().getColor(clickable ? R.color.blue : R.color.light_gray_text));
            (mContentView.findViewById(R.id.btn_negative)).setClickable(clickable);
            return this;
        }

        public Builder setNeutralDismiss(boolean neutralDismiss) {
            mNeutralDismiss = neutralDismiss;
            return this;
        }

        public Builder changeNegativeText(String text) {
            mNegativeText = text;
            ((Button) mContentView.findViewById(R.id.btn_negative)).setText(text);
            return this;
        }

        public Builder changeText(String text) {
            mText = text;
            mTvText.setText(mText);
            return this;
        }

        public String getNegativeText() {
            return mNegativeText;
        }

        public AlertDialog create() {
            final AlertDialog dialog = new AlertDialog(mContext);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(mContentView);
            if (mText.contains("\n")) {
                if (mText.contains("相机权限")) {
                    mTvText.setGravity(Gravity.CENTER);
                    mTvText.setPadding(DensityUtils.dp2px(mContext, 20), 0, DensityUtils.dp2px(mContext, 20), 0);
                } else {
                    mTvText.setGravity(Gravity.LEFT | Gravity.CENTER);
                    mTvText.setPadding(DensityUtils.dp2px(mContext, isUpdate ? 20 : 50), 0, 0, 0);
                }
            }
            mTvText.setText(mText);
            if (TextUtils.isEmpty(mTitle)) {
                mContentView.findViewById(R.id.tv_title).setVisibility(View.GONE);
            } else {
                ((TextView) mContentView.findViewById(R.id.tv_title)).setText(mTitle);
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
                        if (mPositiveDismiss)
                            dialog.dismiss();
                        if (mPositiveListener != null) {
                            mPositiveListener.onClick(dialog, BUTTON_POSITIVE);
                        }
                    }
                });
            }

            if (TextUtils.isEmpty(mNeutralText)) {
                mContentView.findViewById(R.id.ll_neutral).setVisibility(View.GONE);
            } else {
                ((Button) mContentView.findViewById(R.id.btn_neutral)).setText(mNeutralText);
                mContentView.findViewById(R.id.btn_neutral).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (mNeutralDismiss)
                            dialog.dismiss();
                        if (mNeutralListener != null) {
                            mNeutralListener.onClick(dialog, BUTTON_NEUTRAL);
                        }
                    }
                });
            }
            return dialog;
        }

        public AlertDialog createImg() {
            final AlertDialog dialog = new AlertDialog(mContext);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(mContentView);
            Glide.with(mContext)
                    .load(url)
                    .into(imgView);

            if (TextUtils.isEmpty(mPositiveText)) {
                mContentView.findViewById(R.id.btn_positive).setVisibility(View.GONE);
            } else {
                ((Button) mContentView.findViewById(R.id.btn_positive)).setText(mPositiveText);
                mContentView.findViewById(R.id.btn_positive).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (mPositiveDismiss)
                            dialog.dismiss();
                        if (mPositiveListener != null) {
                            mPositiveListener.onClick(dialog, BUTTON_POSITIVE);
                        }
                    }
                });
            }

            if (TextUtils.isEmpty(mNeutralText)) {
                mContentView.findViewById(R.id.ll_neutral).setVisibility(View.GONE);
            } else {
                ((Button) mContentView.findViewById(R.id.btn_neutral)).setText(mNeutralText);
                mContentView.findViewById(R.id.btn_neutral).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (mNeutralDismiss)
                            dialog.dismiss();
                        if (mNeutralListener != null) {
                            mNeutralListener.onClick(dialog, BUTTON_NEUTRAL);
                        }
                    }
                });
            }
            return dialog;
        }

        public AlertDialog show() {
            if (isImg) {
                AlertDialog dialog = createImg();
                if (mContext instanceof Activity
                        && !((Activity) mContext).isFinishing()) {
                    dialog.show();
                }
                return dialog;
            } else {
                AlertDialog dialog = create();
                if (mContext instanceof Activity
                        && !((Activity) mContext).isFinishing()) {
                    dialog.show();
                }
                return dialog;
            }
        }

    }

}

