package com.gengcon.android.fixedassets.module.addasset.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.gengcon.android.fixedassets.R;
import com.gengcon.android.fixedassets.bean.Area;
import com.gengcon.android.fixedassets.bean.CustomAttrWheelBean;
import com.gengcon.android.fixedassets.bean.User;
import com.gengcon.android.fixedassets.bean.WheelBean;
import com.gengcon.android.fixedassets.bean.result.AddAsset;
import com.gengcon.android.fixedassets.bean.result.AddAssetCustom;
import com.gengcon.android.fixedassets.bean.result.AddAssetList;
import com.gengcon.android.fixedassets.module.addasset.presenter.AddAssetPresenter;
import com.gengcon.android.fixedassets.module.addasset.widget.AreaPickerLinearLayout;
import com.gengcon.android.fixedassets.module.addasset.widget.CustomAttrPickerLinearLayout;
import com.gengcon.android.fixedassets.module.base.BaseActivity;
import com.gengcon.android.fixedassets.util.ImageFactory;
import com.gengcon.android.fixedassets.util.ToastUtils;
import com.gengcon.android.fixedassets.widget.ActionSheetLayout;
import com.gengcon.android.fixedassets.widget.AlertDialog;
import com.gengcon.android.fixedassets.widget.PickerLinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AddAssetActivity extends BaseActivity implements AddAssetListView, View.OnClickListener, PickerLinearLayout.OnPickerListener, CustomAttrPickerLinearLayout.OnCustomAttrPickerListener, AreaPickerLinearLayout.OnAreaPickerListener {

    private LinearLayout currentLayout;
    private LinearLayout customLayout;
    private LinearLayout otherLayout;
    private LinearLayout otherClickLayout;
    private LinearLayout customClickLayout;
    private AddAssetPresenter assetPresenter;
    private PickerLinearLayout userPicker;
    private AreaPickerLinearLayout areaPickerLinearLayout;
    private CustomAttrPickerLinearLayout customAttrPicker;
    private TextView customName;

    private ImageFactory imageFactory;
    private ActionSheetLayout mActionSheet;
    private TextView deleteImageView;
    private TextView checkImageView;

    private TextView classificationView, beOrgView, useOrgView, userNameView, areaNameView, adminView;
    private ImageView classificationClearView, beOrgClearView, useOrgClearView, userNameClearView, areaNameClearView, adminClearView;
    private ImageView classificationArrowView, beOrgArrowView, useOrgArrowView, userNameArrowView, areaNameArrowView, adminArrowView;

    public static int RESULT_OK_CLASSIFICATION = 1113;
    public static int RESULT_OK_BEORG = 1115;
    public static int RESULT_OK_USEORG = 1117;
    public static int REQUEST_CLASSIFICATION = 1114;
    public static int REQUEST_BEORG = 1116;
    public static int REQUEST_USEORG = 1118;

    public static int RESULT_OK_CHOOSE_USER = 1119;
    public static int REQUEST_CHOOSE_USER = 1120;

    private LayoutInflater mLayoutInflater;
    private Stack<View> mStack;
    private boolean isHideLayout;
    private int userId;
    private List<AddAssetList.ListBean> customList;
    private ImageView isRetractImg;
    private AddAsset administrator, assetBeOrg, assetUseOrg, assetClassification, assetUser, assetArea;
    private EditText assetCodeView;
    private JSONObject object;
    private JSONObject jsonObject;
    private String tpl_id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);
        mStack = new Stack<>();
        assetPresenter = new AddAssetPresenter();
        assetPresenter.attachView(this);
        assetPresenter.getAddAssetList();
        assetPresenter.getArea();
        assetPresenter.getUsers();
        imageFactory = new ImageFactory();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        ((TextView) findViewById(R.id.tv_title_text)).setText(R.string.add_asset_home);
        ((ImageView) findViewById(R.id.iv_title_left)).setImageResource(R.drawable.ic_home);
        ((TextView) findViewById(R.id.tv_title_right)).setText("保存");
        findViewById(R.id.iv_title_left).setOnClickListener(this);
        findViewById(R.id.tv_title_right).setOnClickListener(this);
        mLayoutInflater = LayoutInflater.from(this);
        currentLayout = findViewById(R.id.currentLayout);
        customLayout = findViewById(R.id.customLayout);
        otherLayout = findViewById(R.id.otherLayout);
        isRetractImg = findViewById(R.id.isRetractImg);
        userPicker = findViewById(R.id.userPickerLayout);
        areaPickerLinearLayout = findViewById(R.id.areaPickerLayout);
        customAttrPicker = findViewById(R.id.customAttrPickerLayout);
        customName = findViewById(R.id.customName);
        mActionSheet = findViewById(R.id.actionsheet);
        deleteImageView = mActionSheet.getDeleteImageView();
        checkImageView = mActionSheet.getCheckImageView();
        mActionSheet.setHanlderResultCallback(mOnHandlerResultCallback);

        otherClickLayout = findViewById(R.id.otherClickLayout);
        customClickLayout = findViewById(R.id.customClickLayout);

        otherClickLayout.setOnClickListener(this);
        customClickLayout.setOnClickListener(this);
        object = new JSONObject();
        jsonObject = new JSONObject();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK_CLASSIFICATION && requestCode == REQUEST_CLASSIFICATION) {
            int id = data.getIntExtra("id", -1);
            String name = data.getStringExtra("name");
            if (classificationView != null) {
                classificationView.setText(name);
                assetClassification.setContent(id + "");
                classificationClearView.setVisibility(View.VISIBLE);
                classificationArrowView.setVisibility(View.GONE);
            }
            try {
                object.put(assetClassification.getAttr_id(), assetClassification.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK_BEORG && requestCode == REQUEST_BEORG) {
            int id = data.getIntExtra("id", -1);
            String name = data.getStringExtra("name");
            if (beOrgView != null) {
                beOrgView.setText(name);
                assetBeOrg.setContent(id + "");
                beOrgClearView.setVisibility(View.VISIBLE);
                beOrgArrowView.setVisibility(View.GONE);
            }
            try {
                object.put(assetBeOrg.getAttr_id(), assetBeOrg.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK_USEORG && requestCode == REQUEST_USEORG) {
            if (userNameView != null) {
                userNameView.setText("");
                userNameClearView.setVisibility(View.GONE);
                userNameArrowView.setVisibility(View.VISIBLE);
                try {
                    object.put(assetUser.getAttr_id(), "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            int id = data.getIntExtra("id", -1);
            String name = data.getStringExtra("name");
            if (useOrgView != null) {
                useOrgView.setText(name);
                assetUseOrg.setContent(id + "");
                useOrgClearView.setVisibility(View.VISIBLE);
                useOrgArrowView.setVisibility(View.GONE);
            }
            try {
                object.put(assetUseOrg.getAttr_id(), assetUseOrg.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK_CHOOSE_USER && requestCode == REQUEST_CHOOSE_USER) {
            int id = data.getIntExtra("id", -1);
            String name = data.getStringExtra("name");
            if (userNameView != null) {
                userNameView.setText(name);
                assetUser.setContent(id + "");
                userNameClearView.setVisibility(View.VISIBLE);
                userNameArrowView.setVisibility(View.GONE);
            }
            try {
                object.put(assetUser.getAttr_id(), assetUser.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addView(final AddAsset asset, int type) {
        LinearLayout textLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_text, null);
        LinearLayout editLayoutType1 = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_edit, null);
        LinearLayout editLayoutType2 = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_edit_type2, null);
        LinearLayout imgLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_img, null);
        final TextView textName = textLayout.findViewById(R.id.assetText);
        TextView typeText = textLayout.findViewById(R.id.typeText);
        TextView typeEdit = editLayoutType1.findViewById(R.id.typeText);
        final EditText editText = editLayoutType1.findViewById(R.id.assetEdit);
        TextView typeEdit2 = editLayoutType2.findViewById(R.id.typeText);
        final EditText editText2 = editLayoutType2.findViewById(R.id.assetEdit);
        final Button editClearButton1 = editLayoutType1.findViewById(R.id.clearButton);
        final Button editClearButton2 = editLayoutType2.findViewById(R.id.clearButton);
        final ImageView clearImg = textLayout.findViewById(R.id.clearImageView);
        final ImageView arrowImg = textLayout.findViewById(R.id.arrowImageView);
        TextView typeImg = imgLayout.findViewById(R.id.typeText);
        ImageView assetImg = imgLayout.findViewById(R.id.assetImg);
        ImageView textIsRequiredImg = textLayout.findViewById(R.id.isRequiredImg);
        ImageView editIsRequiredImg = editLayoutType1.findViewById(R.id.isRequiredImg);
        ImageView edit2IsRequiredImg = editLayoutType2.findViewById(R.id.isRequiredImg);
        ImageView imgIsRequiredImg = imgLayout.findViewById(R.id.isRequiredImg);
        final int attrType = asset.getAttr_type();
        String attrName = asset.getAttr_name();
        if (type == 1) {
            if (attrType == 1) {
                editLayoutType1.setTag(asset);
                if (asset.getIs_required() == 1) {
                    editIsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    editIsRequiredImg.setVisibility(View.GONE);
                }
                typeEdit.setText(attrName);
                editText.setHint("请输入" + attrName);
                currentLayout.addView(editLayoutType1);
                mStack.push(editLayoutType1);
                if (asset.getAttr_id().equals("asset_code")) {
                    assetCodeView = editText;
                }
                editClearButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText("");
                    }
                });
                getEditContent(editText, asset, editClearButton1);
            } else if (attrType == 2) {
                editLayoutType2.setTag(asset);
                if (asset.getIs_required() == 1) {
                    edit2IsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    edit2IsRequiredImg.setVisibility(View.GONE);
                }
                typeEdit2.setText(attrName);
                editText2.setHint("请输入" + attrName);
                currentLayout.addView(editLayoutType2);
                mStack.push(editLayoutType2);
                editClearButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText2.setText("");
                    }
                });
                getEditContent(editText2, asset, editClearButton2);
            } else if (attrType == 3) {
                imgLayout.setTag(asset);
                if (asset.getIs_required() == 1) {
                    imgIsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    imgIsRequiredImg.setVisibility(View.GONE);
                }
                typeImg.setText(attrName);
                currentLayout.addView(imgLayout);
                mStack.push(imgLayout);
                assetImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActionSheet.show();
                    }
                });
            } else if (attrType == 4 || attrType == 5) {
                textLayout.setTag(asset);
                if (asset.getIs_required() == 1) {
                    textIsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    textIsRequiredImg.setVisibility(View.GONE);
                }
                typeText.setText(attrName);
                textName.setHint("请选择" + attrName);
                currentLayout.addView(textLayout);
                mStack.push(textLayout);
                clearImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearImg.setVisibility(View.GONE);
                        arrowImg.setVisibility(View.VISIBLE);
                        textName.setText("");
                        asset.setContent("");
                        try {
                            object.put(asset.getAttr_id(), asset.getContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (clearImg == useOrgClearView)
                            if (userNameView != null) {
                                userNameView.setText("");
                                userNameClearView.setVisibility(View.GONE);
                                userNameArrowView.setVisibility(View.VISIBLE);
                                assetUser.setContent("");
                                try {
                                    object.put(assetUser.getAttr_id(), assetUser.getContent());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                    }
                });
                textLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attrType == 5) {
                            getTime(textName, asset, arrowImg, clearImg);
                        }
                        if (asset.getAttr_id().equals("admin_userid")) {
                            hideSoftInput();
                            administrator = asset;
                            adminView = textName;
                            adminClearView = clearImg;
                            adminArrowView = arrowImg;
                            userPicker.setVisibility(View.VISIBLE);
                        } else if (asset.getAttr_id().equals("org_id")) {
                            assetBeOrg = asset;
                            beOrgView = textName;
                            beOrgClearView = clearImg;
                            beOrgArrowView = arrowImg;
                            Intent intent = new Intent(AddAssetActivity.this, AddAssetDataActivity.class);
                            intent.putExtra("addAssetType", 1);
                            startActivityForResult(intent, REQUEST_BEORG);
                        } else if (asset.getAttr_id().equals("custom_type_id")) {
                            assetClassification = asset;
                            classificationView = textName;
                            classificationClearView = clearImg;
                            classificationArrowView = arrowImg;
                            Intent intent = new Intent(AddAssetActivity.this, AddAssetDataActivity.class);
                            intent.putExtra("addAssetType", 2);
                            startActivityForResult(intent, REQUEST_CLASSIFICATION);
                        } else if (asset.getAttr_id().equals("use_org_id")) {
                            assetUseOrg = asset;
                            useOrgView = textName;
                            useOrgClearView = clearImg;
                            useOrgArrowView = arrowImg;
                            Intent intent = new Intent(AddAssetActivity.this, AddAssetDataActivity.class);
                            intent.putExtra("addAssetType", 3);
                            startActivityForResult(intent, REQUEST_USEORG);
                        } else if (asset.getAttr_id().equals("emp_id")) {
                            if (assetUseOrg == null) {
                                ToastUtils.toastMessage(AddAssetActivity.this, "请先选择使用组织");
                                return;
                            }
                            if (TextUtils.isEmpty(assetUseOrg.getContent())) {
                                ToastUtils.toastMessage(AddAssetActivity.this, "请先选择使用组织");
                            } else {
                                assetUser = asset;
                                userNameView = textName;
                                userNameClearView = clearImg;
                                userNameArrowView = arrowImg;
                                Intent intent = new Intent(AddAssetActivity.this, ChooseUserActivity.class);
                                intent.putExtra("org_id", assetUseOrg.getContent());
                                startActivityForResult(intent, REQUEST_CHOOSE_USER);
                            }
                        } else if (asset.getAttr_id().equals("area_id")) {
                            hideSoftInput();
                            assetArea = asset;
                            areaNameView = textName;
                            areaNameClearView = clearImg;
                            areaNameArrowView = arrowImg;
                            areaPickerLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        } else if (type == 2) {
            if (attrType == 1) {
                editLayoutType1.setTag(asset);
                if (asset.getIs_required() == 1) {
                    editIsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    editIsRequiredImg.setVisibility(View.GONE);
                }
                typeEdit.setText(attrName);
                editText.setHint("请输入" + attrName);
                otherLayout.addView(editLayoutType1);
                mStack.push(editLayoutType1);
                editClearButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText("");
                    }
                });
                getEditContent(editText, asset, editClearButton1);
            } else if (attrType == 2) {
                editLayoutType2.setTag(asset);
                if (asset.getIs_required() == 1) {
                    edit2IsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    edit2IsRequiredImg.setVisibility(View.GONE);
                }
                typeEdit2.setText(attrName);
                editText2.setHint("请输入" + attrName);
                otherLayout.addView(editLayoutType2);
                mStack.push(editLayoutType2);
                editClearButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText2.setText("");
                    }
                });
                getEditContent(editText2, asset, editClearButton2);
            } else if (attrType == 3) {
                imgLayout.setTag(asset);
                if (asset.getIs_required() == 1) {
                    imgIsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    imgIsRequiredImg.setVisibility(View.GONE);
                }
                typeImg.setText(attrName);
                otherLayout.addView(imgLayout);
                mStack.push(imgLayout);
                assetImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActionSheet.show();
                    }
                });
            } else if (attrType == 4 || attrType == 5) {
                textLayout.setTag(asset);
                if (asset.getIs_required() == 1) {
                    textIsRequiredImg.setVisibility(View.VISIBLE);
                } else {
                    textIsRequiredImg.setVisibility(View.GONE);
                }
                typeText.setText(attrName);
                textName.setHint("请选择" + attrName);
                otherLayout.addView(textLayout);
                mStack.push(textLayout);
                clearImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clearImg.setVisibility(View.GONE);
                        arrowImg.setVisibility(View.VISIBLE);
                        textName.setText("");
                        asset.setContent("");
                        try {
                            object.put(asset.getAttr_id(), asset.getContent());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                textLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (attrType == 5) {
                            getTime(textName, asset, arrowImg, clearImg);
                        }
                    }
                });
            }
        }
    }

    private void addCustomView(final AddAssetCustom asset) {
        LinearLayout textLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_text, null);
        LinearLayout editLayoutType1 = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_edit, null);
        LinearLayout editLayoutType2 = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_edit_type2, null);
        LinearLayout imgLayout = (LinearLayout) mLayoutInflater.inflate(R.layout.item_add_asset_img, null);
        final TextView textName = textLayout.findViewById(R.id.assetText);
        TextView typeText = textLayout.findViewById(R.id.typeText);
        TextView typeEdit = editLayoutType1.findViewById(R.id.typeText);
        final EditText editText = editLayoutType1.findViewById(R.id.assetEdit);
        TextView typeEdit2 = editLayoutType2.findViewById(R.id.typeText);
        final EditText editText2 = editLayoutType2.findViewById(R.id.assetEdit);
        TextView typeImg = imgLayout.findViewById(R.id.typeText);
        ImageView assetImg = imgLayout.findViewById(R.id.assetImg);
        final Button editClearButton1 = editLayoutType1.findViewById(R.id.clearButton);
        final Button editClearButton2 = editLayoutType2.findViewById(R.id.clearButton);
        final ImageView clearImg = textLayout.findViewById(R.id.clearImageView);
        final ImageView arrowImg = textLayout.findViewById(R.id.arrowImageView);
        ImageView textIsRequiredImg = textLayout.findViewById(R.id.isRequiredImg);
        ImageView editIsRequiredImg = editLayoutType1.findViewById(R.id.isRequiredImg);
        ImageView edit2IsRequiredImg = editLayoutType2.findViewById(R.id.isRequiredImg);
        ImageView imgIsRequiredImg = imgLayout.findViewById(R.id.isRequiredImg);
        final int attrType = asset.getAttr_type();
        String attrName = asset.getAttr_name();
        if (attrType == 1) {
            editLayoutType1.setTag(asset);
            if (asset.getIs_required() == 1) {
                editIsRequiredImg.setVisibility(View.VISIBLE);
            } else {
                editIsRequiredImg.setVisibility(View.GONE);
            }
            typeEdit.setText(attrName);
            editText.setHint("请输入" + attrName);
            customLayout.addView(editLayoutType1);
            mStack.push(editLayoutType1);
            editClearButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText("");
                }
            });
            getCustomEditContent(editText, asset, editClearButton1);
        } else if (attrType == 2) {
            editLayoutType2.setTag(asset);
            if (asset.getIs_required() == 1) {
                edit2IsRequiredImg.setVisibility(View.VISIBLE);
            } else {
                edit2IsRequiredImg.setVisibility(View.GONE);
            }
            typeEdit2.setText(attrName);
            editText2.setHint("请输入" + attrName);
            customLayout.addView(editLayoutType2);
            mStack.push(editLayoutType2);
            editClearButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText2.setText("");
                }
            });
            getCustomEditContent(editText2, asset, editClearButton2);
        } else if (attrType == 3) {
            imgLayout.setTag(asset);
            if (asset.getIs_required() == 1) {
                imgIsRequiredImg.setVisibility(View.VISIBLE);
            } else {
                imgIsRequiredImg.setVisibility(View.GONE);
            }
            typeImg.setText(attrName);
            customLayout.addView(imgLayout);
            mStack.push(imgLayout);
            assetImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActionSheet.show();
                }
            });
        } else if (attrType == 4 || attrType == 5) {
            textLayout.setTag(asset);
            if (asset.getIs_required() == 1) {
                textIsRequiredImg.setVisibility(View.VISIBLE);
            } else {
                textIsRequiredImg.setVisibility(View.GONE);
            }
            typeText.setText(attrName);
            textName.setHint("请选择" + attrName);
            customLayout.addView(textLayout);
            mStack.push(textLayout);
            clearImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearImg.setVisibility(View.GONE);
                    arrowImg.setVisibility(View.VISIBLE);
                    textName.setText("");
                    asset.setContent("");
                    try {
                        object.put(asset.getAttr_id(), asset.getContent());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            textLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (attrType == 5) {
                        getCustomTime(textName, asset, arrowImg, clearImg);
                    } else if (attrType == 4) {
                        getCustomAttrContent(textName, asset, arrowImg, clearImg);
                    }
                }
            });
        }
    }

    @Override
    public void showAddAssetList(AddAssetList addAssetList) {
        assetPresenter.getAssetCode();
        tpl_id = addAssetList.getDefault_tplid();
        if (!TextUtils.isEmpty(tpl_id)) {
            assetPresenter.getAddAssetCustom(tpl_id);
            customName.setText(addAssetList.getDefault_tplname());
            try {
                jsonObject.put("tpl_id", tpl_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (addAssetList.getList() != null && addAssetList.getList().size() > 0) {
            customList = addAssetList.getList();
        } else {
            customList = new ArrayList<>();
        }
        List<AddAsset> headAssetList = addAssetList.getHeader_part();
        List<AddAsset> footAssetList = addAssetList.getFooter_part();
        for (int i = 0; i < headAssetList.size(); i++) {
            addView(headAssetList.get(i), 1);
        }
        for (int i = 0; i < footAssetList.size(); i++) {
            addView(footAssetList.get(i), 2);
        }
    }

    @Override
    public void initUsers(List<User> user) {
        List<WheelBean> data = new ArrayList<>();
        for (int i = 0; i < user.size(); i++) {
            WheelBean bean = new WheelBean();
            bean.setId(user.get(i).getId());
            bean.setName(user.get(i).getUser_name());
            data.add(bean);
        }
        if (data.size() > 0) {
            userPicker.setData(data);
        }
        userPicker.setOnSelectListener(this);
    }

    @Override
    public void showAddAssetCustom(List<AddAssetCustom> assetCustom) {
        customLayout.removeAllViews();
        for (int i = 0; i < assetCustom.size(); i++) {
            addCustomView(assetCustom.get(i));
        }
    }

    @Override
    public void showAssetCode(String assetCode) {
        assetCodeView.setText(assetCode);
        try {
            object.put("asset_code", assetCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAsset() {
        showAddAssetSuccessDialog();
    }

    @Override
    public void contractExpire() {
        showContractExpireDialog();
    }

    @Override
    public void initArea(List<Area> areas) {
        List<WheelBean> data = new ArrayList<>();
        for (int i = 0; i < areas.size(); i++) {
            WheelBean bean = new WheelBean();
            bean.setId(areas.get(i).getArea_id());
            bean.setName(areas.get(i).getArea_name());
            data.add(bean);
        }
        if (data.size() > 0) {
            areaPickerLinearLayout.setData(data);
        }
        areaPickerLinearLayout.setOnSelectListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                onBackPressed();
                break;
            case R.id.tv_title_right:
                try {
                    jsonObject.put("asset_data", object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assetPresenter.getAddAsset(jsonObject.toString());
                break;
            case R.id.customClickLayout:
                hideSoftInput();
                List<CustomAttrWheelBean> data = new ArrayList<>();
                for (int i = 0; i < customList.size(); i++) {
                    CustomAttrWheelBean bean = new CustomAttrWheelBean();
                    bean.setId(customList.get(i).getTpl_id());
                    bean.setName(customList.get(i).getTpl_name());
                    data.add(bean);
                }
                if (data.size() > 0) {
                    customAttrPicker.setData(data);
                    customAttrPicker.setVisibility(View.VISIBLE);
                    customAttrPicker.setOnSelectListener(this);
                }

                break;
            case R.id.otherClickLayout:
                isHideLayout = !isHideLayout;
                isRetractImg.setBackgroundResource(isHideLayout ? R.drawable.ic_lower : R.drawable.ic_upper);
                findViewById(R.id.spaceView).setVisibility(isHideLayout ? View.VISIBLE : View.GONE);
                if (isHideLayout) {
                    otherLayout.setVisibility(View.GONE);
                } else {
                    otherLayout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public String getFormatTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void getTime(final TextView tv, final AddAsset asset, final ImageView arrowImg, final ImageView clearImg) {
        TimePickerView pvTime = new TimePickerView.Builder(AddAssetActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调
                String time = getFormatTime(date2);
                arrowImg.setVisibility(View.GONE);
                clearImg.setVisibility(View.VISIBLE);
                tv.setText(time);
                asset.setContent(time);
                try {
                    object.put(asset.getAttr_id(), asset.getContent());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    private void getCustomAttrContent(final TextView textView, final AddAssetCustom asset, final ImageView arrowImg, final ImageView clearImg) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(AddAssetActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String s = asset.getDefault_data().get(options1);
                textView.setText(s);
                asset.setContent(s);
                arrowImg.setVisibility(View.GONE);
                clearImg.setVisibility(View.VISIBLE);
                try {
                    object.put(asset.getAttr_id(), asset.getContent());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
//                        .setTitleText("城市选择")//标题
                .setSubCalSize(16)//确定和取消文字大小
//                        .setTitleSize(20)//标题文字大小
//                        .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                        .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                        .setContentTextSize(18)//滚轮文字大小
//                        .setTextColorCenter(Color.BLUE)//设置选中项的颜色
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
//                        .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                        .setLinkage(false)//设置是否联动，默认true
//                        .setLabels("省", "市", "区")//设置选择的三级单位
//                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .setCyclic(false, false, false)//循环与否
//                        .setSelectOptions(1, 1, 1)  //设置默认选中项
//                        .setOutSideCancelable(false)//点击外部dismiss default true
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvOptions.setPicker(asset.getDefault_data());
        pvOptions.show();
    }

    private void getEditContent(EditText editText, final AddAsset asset, final Button clearButton) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.GONE);
                }
                asset.setContent(s.toString());
                try {
                    object.put(asset.getAttr_id(), asset.getContent());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCustomEditContent(EditText editText, final AddAssetCustom asset, final Button clearButton) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    clearButton.setVisibility(View.VISIBLE);
                } else {
                    clearButton.setVisibility(View.GONE);
                }
                asset.setContent(s.toString());
                try {
                    object.put(asset.getAttr_id(), asset.getContent());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCustomTime(final TextView tv, final AddAssetCustom asset, final ImageView arrowImg, final ImageView clearImg) {
        TimePickerView pvTime = new TimePickerView.Builder(AddAssetActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date2, View v) {//选中事件回调
                String time = getFormatTime(date2);
                tv.setText(time);
                asset.setContent(time);
                arrowImg.setVisibility(View.GONE);
                clearImg.setVisibility(View.VISIBLE);
                try {
                    object.put(asset.getAttr_id(), asset.getContent());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
//                        .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                        .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    @Override
    public void onBackPressed() {
        if (mActionSheet.isVisibility()) {
            mActionSheet.hide();
            return;
        }
        if (userPicker.getVisibility() == View.VISIBLE) {
            userPicker.setVisibility(View.GONE);
            return;
        }
        if (customAttrPicker.getVisibility() == View.VISIBLE) {
            customAttrPicker.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConfirm(WheelBean user) {
        if (user != null) {
            adminView.setText(user.getName());
            userId = user.getId();
            administrator.setContent(userId + "");
            adminClearView.setVisibility(View.VISIBLE);
            adminArrowView.setVisibility(View.GONE);
            try {
                object.put(administrator.getAttr_id(), administrator.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onCustomAttrConfirm(CustomAttrWheelBean customAttrWheelBean) {
        customName.setText(customAttrWheelBean.getName());
        assetPresenter.getAddAssetCustom(customAttrWheelBean.getId());
        try {
            jsonObject.put("tpl_id", customAttrWheelBean.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCustomAttrCancel() {

    }

    @Override
    public void onAreaConfirm(WheelBean wheelBean) {
        if (wheelBean != null) {
            areaNameView.setText(wheelBean.getName());
            assetArea.setContent(wheelBean.getId() + "");
            areaNameClearView.setVisibility(View.VISIBLE);
            areaNameArrowView.setVisibility(View.GONE);
            try {
                object.put(assetArea.getAttr_id(), assetArea.getContent());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAreaCancel() {

    }

    private void showAddAssetSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, false);
        builder.setTitle("温馨提示");
        String content = "入库成功";
        builder.setText(content);
        builder.setUpDate(false);
        builder.setNeutralButtonColor();
        builder.setPositiveButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                assetPresenter.getAssetCode();
                dialog.dismiss();
            }
        }, "继续入库");
        builder.setNeutralButton(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                    mPresenter.getReadEditNotice(userPopupNotice.getList().getId());
                dialog.dismiss();
                onBackPressed();
            }
        }, "返回主页");

        builder.show();
    }

    private GalleryFinal.OnHanlderResultCallback mOnHandlerResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int requestCode, final List<PhotoInfo> resultList) {
            if (resultList != null) {
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws IOException {
//                        String bitmap = imageFactory.compressAndGenImage(imageFactory.getBitmap(resultList.get(0).getPhotoPath()), 1024);
//                        emitter.onNext(bitmap);
//                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {

                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String bitmap) {
//                        if (updateImg == 1) {
//                            mWebView.loadUrl("javascript:uploadImgBase64(" + "'" + bitmap + "'" + ")");
//                        } else if (updateImg == 2) {
//                            mWebView.loadUrl("javascript:uploadImgBase64(" + "'" + imgType + "==========" + index + "==========" + bitmap + "'" + ")");
//                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            ToastUtils.toastMessage(AddAssetActivity.this, errorMsg);
        }
    };
}
