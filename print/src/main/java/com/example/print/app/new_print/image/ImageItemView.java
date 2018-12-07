package com.example.print.app.new_print.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.print.app.R;
import com.example.print.app.new_print.ExcelChangeManager;
import com.example.print.app.new_print.base.BaseItemView;
import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.new_print.module.ImageItemModule;
import com.example.print.app.util.CodeUtil;
import com.example.print.app.util.TranslateUtils;
import com.project.skn.x.util.BitmapUtils;
import com.project.skn.x.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ImageItemView extends BaseItemView implements ImageItem, ExcelChangeManager.IExcelDataChangeListener {
    private Bitmap mBitmap;
    private ImageView imageView;
    private boolean isFirst = true;
    private TextView textView;
    public String text;
    private LayoutParams textLayoutParams;
    private LayoutParams imageLayoutParams;
    private int excelPosition;

    public BarCode barcodeFormat = BarCode.Code128;
    /**
     * 视图是否展示 用来防止重复绘制
     */
    private boolean isShow = false;
    /**
     * excel数据
     */
    private List<String> excelData = new ArrayList<>();
    public int type;//1 条形码 2 二维码 3 图片
    public int typeTextShow = -1;//0 不显示文字  1 显示在上面  2 显示在下面 文字的位置

    public ImageItemView(Context context, Bitmap bitmap, String text, int type) {
        super(context);
        setSelected(true);
        setScan(type != 1);
        this.type = type;
        setDrawBottomBitmap(type == 1);
        imageLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
        textLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
        this.mBitmap = bitmap;
        this.text = text;
        this.type = type;
        imageView = new ImageView(getContext());
        imageView.setLayoutParams(imageLayoutParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(mBitmap);
        textView = new TextView(getContext());
        textView.setId(R.id.imageItemView_text);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#000000"));
        textView.setSingleLine(true);
        textView.setLayoutParams(textLayoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TranslateUtils.getLocationTextSize(1));
        textView.setText(text);
        imageView.setImageBitmap(mBitmap);
        addView(imageView);
        addView(textView);
        setTextShowLocation(typeTextShow);
    }

    private void changeMinWidth() {
        if (type == 2) {
//            setMinWidth();
        } else if (type == 3) {

        }
    }

    public static ImageItemView create(Context context, Bitmap bitmap, String text, int type) {
        ImageItemView textItemView = new ImageItemView(context, bitmap, text, type);

        textItemView.setSelected(true);
        LayoutParams layoutParams = new LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        textItemView.mamazaiaiwoyici(layoutParams);
        return textItemView;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    @Override
    public void amplification() {
        if (type == 1) {
            Integer position = TranslateUtils.getPositionFromLocal(textView.getTextSize());
            Float locationTextSize = TranslateUtils.getLocationTextSize(position + 1);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, locationTextSize);
        } else {
            super.amplification();
        }

    }

    @Override
    public void narrow() {
        if (type == 1) {
            Integer position = TranslateUtils.getPositionFromLocal(textView.getTextSize());
            Float locationTextSize = TranslateUtils.getLocationTextSize(position - 1);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, locationTextSize);
        } else {
            super.narrow();
        }
    }

    private void setTextShowLocation(int typeTextShow) {
        switch (typeTextShow) {
            case -1:
                if (type != 1) {
                    setNoText();
                } else {
                    setBottomText();
                }
                break;
            case 0:
                setNoText();
                break;
            case 1:
                setTopText();
                break;
            case 2:
                setBottomText();
                break;
        }
    }


    @Override
    public ImageItemView copy() {
        ImageItemView copy = new ImageItemView(getContext(), mBitmap, text, type);
        copy.textView.setText(textView.getText());
        copy.textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getTextSize());
        copy.barcodeFormat = barcodeFormat;
        copy.excelData = excelData;
        copy.setTextShowLocation(typeTextShow);
        LayoutParams layoutParams = new LayoutParams(getWidth(), getHeight());
        layoutParams.topMargin = getTop() + 10;
        layoutParams.leftMargin = getLeft() + 10;
        copy.setLayoutParams(layoutParams);
        copy.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                copy.setRotateSum(getRotateSum());
                copy.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        return copy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isFirst) {
            isFirst = false;
            isShow = true;
        }
        super.onDraw(canvas);
    }


    @Override
    public TextView getText() {
        return textView;
    }

    @Override
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void setTopText() {
        if (type != 1) return;
        if (textView == null || imageView == null) return;
        textView.setVisibility(View.VISIBLE);
        textLayoutParams.removeRule(ALIGN_PARENT_BOTTOM);
        textLayoutParams.addRule(ALIGN_PARENT_TOP);
        imageLayoutParams.removeRule(ABOVE);
        imageLayoutParams.addRule(BELOW, textView.getId());
        textView.setLayoutParams(textLayoutParams);
        imageView.setLayoutParams(imageLayoutParams);
        typeTextShow = 1;
    }

    /**
     *
     */
    @Override
    public void setBottomText() {
        if (type != 1) return;
        if (textView == null || imageView == null) return;
        textView.setVisibility(View.VISIBLE);
        textLayoutParams.addRule(ALIGN_PARENT_BOTTOM);
        textLayoutParams.removeRule(ALIGN_PARENT_TOP);
        imageLayoutParams.removeRule(BELOW);
        imageLayoutParams.addRule(ABOVE, textView.getId());
        textView.setLayoutParams(textLayoutParams);
        imageView.setLayoutParams(imageLayoutParams);
        typeTextShow = 2;
    }


    @Override
    public void setNoText() {
        if (textView != null) {
            textView.setVisibility(View.GONE);
        }
        typeTextShow = 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ExcelChangeManager.getManager().unregisterChange(this);
    }

    @Override
    public void setExcelData(List<String> excelData) {
        this.excelData = excelData;
    }

    @Override
    public List<String> getExcelData() {
        return excelData == null ? new ArrayList<>() : excelData;
    }

    @Override
    public boolean hasExcelData() {
        return excelData != null && excelData.size() > 0;
    }

    public void setText(String text) {
        textView.setText(text);
        if (hasExcelData()) {
            if (excelPosition < excelData.size())
                excelData.set(excelPosition, text);
        }
    }

    /**
     * 设置Bitmap并且显示
     *
     * @param text
     */
    private void setBitmapShow(String text) {
        if (type == 1) {
            try {
                mBitmap = CodeUtil.CreateOneDCode(text, barcodeFormat.getBarCode(), 400, 200);
            } catch (Exception e) {
                mBitmap = null;
                ToastUtils.showToast("生成图片失败");
            } finally {
                imageView.setImageBitmap(mBitmap);
            }
        } else if (type == 2) {
            try {
                mBitmap = CodeUtil.createQRImage(text, 400, 400, null);
            } catch (Exception e) {
                mBitmap = null;
                ToastUtils.showToast("生成图片失败");
            } finally {
                imageView.setImageBitmap(mBitmap);
            }
        }
    }


    @Override
    public void onUpdate(int position) {
        if (excelData != null) {
            if (excelData.size() > 0) {
                if (position < excelData.size()) {
                    excelPosition = position;
                    setVisibility(VISIBLE);
                    textView.setText(excelData.get(position));
                    onViewLiveCycleListener.onUpdate((excelData.get(position)));
                    setBitmapShow(getExcelData().get(position));
                } else {
                    setVisibility(GONE);
                }
            }
        }

    }

    /**
     * 2;//二维码  3;//挑衅吗  4;//图片
     *
     * @return
     */
    public BaseItemModule toBean() {
        ImageItemModule itemModule = new ImageItemModule();
        itemModule.setBarcodeHeight(textView.getHeight());
        if (type == 1) {
            itemModule.setTagType(3);
            itemModule.setTextAlignment(1);
        } else if (type == 2) {
            itemModule.setTagType(2);
        } else if (type == 3) {
            itemModule.setTagType(4);
            int byteCount;
            if (mBitmap != null) {
                do {
                    mBitmap = BitmapUtils.compressMatrix(mBitmap, 0.9f);

                    byteCount = BitmapUtils.getSize(mBitmap);
                } while (byteCount / 1024 > 500);
                itemModule.setImageUrl(BitmapUtils.bitmap2Base64(mBitmap));
            }
        }
        itemModule.setBarcodeFormat(barcodeFormat.getCode());
        if (excelData.size() > 0) {
            itemModule.setString(excelData.get(0));
        } else {
            itemModule.setString(text);
        }
        itemModule.setTextSize(TranslateUtils.getServiceTextSize(
                TranslateUtils.getPositionFromLocal(textView.getTextSize())));
        itemModule.setRotate(getRotateSum());
        itemModule.setX(TranslateUtils.getPxByLocalPx(getLeft()));
        itemModule.setY(TranslateUtils.getPxByLocalPx((getTop())));
        itemModule.setHeight(TranslateUtils.getPxByLocalPx(getHeight()));
        itemModule.setWidth(TranslateUtils.getPxByLocalPx(getWidth()));
        itemModule.setRotate(getRotateSum());
        itemModule.setX(TranslateUtils.getPxByLocalPx(getLeft()));
        itemModule.setY(TranslateUtils.getPxByLocalPx((getTop())));
        itemModule.setHeight(TranslateUtils.getPxByLocalPx(getHeight()));
        itemModule.setWidth(TranslateUtils.getPxByLocalPx(getWidth()));
        itemModule.setStringLocation(typeTextShow);
        StringBuilder excelString = new StringBuilder();
        if (excelData != null) {
            for (int i = 0; i < excelData.size(); i++) {
                excelString.append(excelData.get(i));
                if (i < excelData.size() - 1) {
                    excelString.append(":excel:");
                }
            }
        }
        itemModule.setExcelString(excelString.toString());
        itemModule.setRotate(getRotateSum());
        return itemModule;
    }

    @Override
    public ImageItemView fromBean(BaseItemModule itemModule) {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        if (itemModule.getExcelString() != null && itemModule.getExcelString().contains(":excel:")) {
            excelData = Arrays.asList(itemModule.getExcelString().split(":excel:"));
        }
        text = itemModule.getString();
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                TranslateUtils.getPositionFromService(itemModule.getTextSize()) == -1 ?
                        TranslateUtils.getLocationTextSize(1) :
                        TranslateUtils.getLocationTextSize(
                                TranslateUtils.getPositionFromService(itemModule.getTextSize())));
        barcodeFormat = BarCode.Companion.getBarCodeByCode(itemModule.getBarcodeFormat());
        typeTextShow = itemModule.getStringLocation();
        switch (itemModule.getTagType()) {
            case 2://二维码
                type = 2;
                setBitmapShow(text);
                break;
            case 3://一维码
                type = 1;
                setBitmapShow(text);
                break;
            case 4://图片
                type = 3;
                BitmapUtils.url2Bitmap(itemModule.getImageUrl(), bitmap -> {
                    post(() ->
                            {
                                mBitmap = bitmap;
                                imageView.setImageBitmap(bitmap);
                            }

                    );
                    return null;
                });
                break;
        }

        setTextShowLocation(typeTextShow);

        LayoutParams layoutParams = new LayoutParams(
                (int) TranslateUtils.getPxByServicePx(itemModule.getWidth())
                , (int) TranslateUtils.getPxByServicePx(itemModule.getHeight()));
        layoutParams.topMargin = (int) (TranslateUtils.getPxByServicePx(itemModule.getY()));
        layoutParams.leftMargin = (int) (TranslateUtils.getPxByServicePx(itemModule.getX()));
//        LayoutParams layoutParams = new LayoutParams((int) itemModule.getWidth(), (int) itemModule.getHeight());
//        layoutParams.topMargin = (int) (itemModule.getY());
//        layoutParams.leftMargin = (int) (itemModule.getX());
        this.setLayoutParams(layoutParams);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setRotateSum(itemModule.getRotate());
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        return this;
    }
}
