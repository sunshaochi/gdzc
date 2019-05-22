package com.gengcon.android.fixedassets.module.login.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.bean.result.Industry;
import com.gengcon.android.fixedassets.bean.result.UserData;
import com.gengcon.android.fixedassets.common.module.http.URL;
import com.gengcon.android.fixedassets.module.login.presenter.RegisterLastPresenter;
import com.gengcon.android.fixedassets.module.main.view.ui.MainActivity;
import com.gengcon.android.fixedassets.util.CacheActivity;
import com.gengcon.android.fixedassets.util.SharedPreferencesUtils;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.module.login.view.RegisterLastView;
import com.gengcon.android.fixedassets.module.login.widget.IndustryPickerLinearLayout;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import androidx.annotation.Nullable;

public class RegisterLastActivity extends BaseActivity implements View.OnClickListener, RegisterLastView, IndustryPickerLinearLayout.OnPickerListener {

    private EditText userNameView, companyNameView;
    private TextView industryView;
    private LinearLayout industryLayout;
    private Button completeBtn;
    private String registerPhone;
    private String pwd;
    private RegisterLastPresenter presenter;
    private IndustryPickerLinearLayout pickerLayout;
    private Industry industry;
    private int ind_id;
    private WebView webView;
    private String url;
    private UserData user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_last);
        url = URL.HTTP_HEAD + URL.HOME;
        if (!CacheActivity.activityList.contains(RegisterLastActivity.this)) {
            CacheActivity.activityList.add(RegisterLastActivity.this);
        }
        presenter = new RegisterLastPresenter();
        presenter.attachView(this);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.set_return);
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        registerPhone = getIntent().getStringExtra("registerPhone");
        pwd = getIntent().getStringExtra("pwd");
        userNameView = findViewById(R.id.userNameView);
        companyNameView = findViewById(R.id.companyNameView);
        industryView = findViewById(R.id.industryView);
        industryLayout = findViewById(R.id.tv_select_industry_layout);
        completeBtn = findViewById(R.id.completeBtn);
        pickerLayout = findViewById(R.id.pickerLayout);
        webView = findViewById(R.id.webView);
        webView.loadUrl(url);
        final WebSettings webSettings = webView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webView.addJavascriptInterface(this, "android");
        completeBtn.setOnClickListener(this);
        completeBtn.setEnabled(false);
        industryLayout.setOnClickListener(this);
        userNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0 && !TextUtils.isEmpty(companyNameView.getText().toString()) && !TextUtils.isEmpty(industryView.getText().toString())) {
                    completeBtn.setEnabled(true);
                } else {
                    completeBtn.setEnabled(false);
                }
            }
        });

        userNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    presenter.getCheckRename(userNameView.getText().toString());
                }
            }
        });

        companyNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (companyNameView.getText().toString().length() < 2 || companyNameView.getText().toString().length() > 30) {
                        ToastUtils.toastMessage(RegisterLastActivity.this, "组织名称格式错误(2-30位,可包含中文,数字,字母,-,_,括号)");
                    }
                }
            }
        });

        companyNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0 && !TextUtils.isEmpty(userNameView.getText().toString()) && !TextUtils.isEmpty(industryView.getText().toString())) {
                    completeBtn.setEnabled(true);
                } else {
                    completeBtn.setEnabled(false);
                }
            }
        });

        industryView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0 && !TextUtils.isEmpty(userNameView.getText().toString()) && !TextUtils.isEmpty(companyNameView.getText().toString())) {
                    completeBtn.setEnabled(true);
                } else {
                    completeBtn.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (pickerLayout.getVisibility() == View.VISIBLE) {
            pickerLayout.setVisibility(View.GONE);
            return;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.tv_select_industry_layout:
                presenter.getIndustry();
                hideSoftInput();
                break;
            case R.id.completeBtn:
                presenter.getRegister(userNameView.getText().toString(), pwd, registerPhone, companyNameView.getText().toString(), ind_id);
                break;
        }
    }

    @Override
    public void showIndustry(List<Industry> industry) {
        pickerLayout.setVisibility(View.VISIBLE);
        pickerLayout.setData(industry);
        pickerLayout.setOnSelectListener(this);
    }

    @Override
    public void checkRename() {

    }

    @Override
    public void completeRegister(UserData userData) {
        user = userData;
        Gson gson = new Gson();
        String json = "";
        if (userData == null) {
            json = gson.toJson(JsonNull.INSTANCE);
        }
        try {
            json = gson.toJson(userData);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        final String finalJson = json;
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl("javascript:regitser(" + "'" + finalJson + "'" + ")");
            }
        });
    }

    @JavascriptInterface
    public void userSuccess(String message) {
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.TOKEN, user.getToken());
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.USER_ID, user.getUser_id());
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.IMG_URL, user.getFile_domain());
        SharedPreferencesUtils.getInstance().setParam(SharedPreferencesUtils.IS_SUPERADMIN, user.getIs_superadmin());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        CacheActivity.finishActivity();
    }

    @Override
    public void onConfirm(Industry industry) {
        this.industry = industry;
        ind_id = industry.getId();
        industryView.setText(industry.getIndustry_name());
        industryView.setTextColor(getResources().getColor(R.color.black_text));
    }

    @Override
    public void onCancel() {

    }
}
