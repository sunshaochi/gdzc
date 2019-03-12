package com.gengcon.android.fixedassets.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.gengcon.android.fixedassets.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class PhoneCodeLayout extends RelativeLayout implements View.OnFocusChangeListener, View.OnKeyListener {

    //设验证码有4位
    private EditText first, second, third, fourth;

    private OnInputFinishListener mInputListener;

    private long endTime = 0;

    private List<EditText> mEdits = new ArrayList<EditText>();

    public PhoneCodeLayout(Context context) {
        this(context, null);
    }

    public PhoneCodeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneCodeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.phone_code, this, true);
        first = findViewById(R.id.edit_first);
        second = findViewById(R.id.edit_second);
        third = findViewById(R.id.edit_third);
        fourth = findViewById(R.id.edit_fourth);

        mEdits.add(first);
        mEdits.add(second);
        mEdits.add(third);
        mEdits.add(fourth);

        first.setFocusable(true);
        first.addTextChangedListener(new MyTextWatcher());
        second.addTextChangedListener(new MyTextWatcher());
        third.addTextChangedListener(new MyTextWatcher());
        fourth.addTextChangedListener(new MyTextWatcher());

        first.setOnFocusChangeListener(this);
        first.setOnKeyListener(this);
        second.setOnFocusChangeListener(this);
        second.setOnKeyListener(this);
        third.setOnFocusChangeListener(this);
        third.setOnKeyListener(this);
        fourth.setOnFocusChangeListener(this);
        fourth.setOnKeyListener(this);
    }

    @Override
    public void setEnabled(boolean enabled) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    @Override
    public void onFocusChange(View view, boolean focus) {
        if (focus) {
            focus();
        }
    }

    public void setmInputListener(OnInputFinishListener mInputListener) {
        this.mInputListener = mInputListener;
    }

    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() != 0) {
                focus();
            }
        }
    }

    private void focus() {
        EditText editText;
        //利用for循环找出前面还没被输入字符的EditText
        for (int i = 0; i < mEdits.size(); i++) {
            editText = mEdits.get(i);
            if (editText.getText().length() < 1) {
                editText.requestFocus();

                return;
            } else {
                editText.setCursorVisible(false);
            }
        }
        EditText lastEditText = mEdits.get(mEdits.size() - 1);
        if (lastEditText.getText().length() > 0) {
            //收起软键盘 并不允许编辑 同时将输入的文本提交
            getResponse();
            lastEditText.setCursorVisible(false);
            InputMethodManager imm = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void getResponse() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mEdits.size(); i++) {
            sb.append(mEdits.get(i).getText().toString());
        }
        if (mInputListener != null) {
            mInputListener.onFinish(sb.toString());
        }
    }

    //对外封装一个重置或直接填写验证码的方法
    public void setText(String text) {
        if (text.length() == mEdits.size()) {
            StringBuilder sb = new StringBuilder(text);
            first.setText(sb.substring(0, 1));
            second.setText(sb.substring(1, 2));
            third.setText(sb.substring(2, 3));
            fourth.setText(sb.substring(3, 4));
        } else {
            first.setText("");
            second.setText("");
            third.setText("");
            fourth.setText("");
//            first.setCursorVisible(true);
            first.requestFocus();
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            backFocus();
        }
        return false;
    }

    private void backFocus() {
        //博主手机不好，经常点一次却触发两次`onKey`事件，就设置了一个防止多点击，间隔100毫秒。
        EditText editText;
        long startTime = System.currentTimeMillis();
        //循环检测有字符的`editText`，把其置空，并获取焦点。
        for (int i = mEdits.size() - 1; i >= 0; i--) {
            editText = mEdits.get(i);
            if (editText.getText().length() >= 1 && startTime - endTime > 100) {
                editText.setText("");
                editText.setCursorVisible(true);
                editText.requestFocus();
                endTime = startTime;
                return;
            }
        }
    }

    //一个监听输入结束的接口，以便外部回调结束后执行的方法
    public interface OnInputFinishListener {
        void onFinish(String code);
    }

}
