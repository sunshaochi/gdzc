package com.example.print.app.new_print.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.print.app.new_print.Action;
import com.example.print.app.new_print.ExcelChangeManager;
import com.example.print.app.new_print.flow.FlowItemView;
import com.example.print.app.new_print.image.ImageItemView;
import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.new_print.module.PrintModule;
import com.example.print.app.new_print.new_table.NewTableView;
import com.example.print.app.new_print.shape.Rect.RoundRectView;
import com.example.print.app.new_print.shape.line.LineItemView;
import com.example.print.app.new_print.text.TextItemView;
import com.example.print.app.new_print.time.DateItemView;
import com.example.print.app.util.ViewCacheUtil;
import com.project.skn.x.util.BitmapUtils;
import com.project.skn.x.util.ToastInstance;

import java.util.ArrayList;
import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * 此类为画板类
 */
public class PrintView extends ConstraintLayout {

    private RelativeLayout printLayer;
    /**
     * 背景层
     */
    private ImageView backgroundLayer;
    /**
     * 第一层
     */
    private RelativeLayout firstLayer;
    /**
     * 第二层
     */
    private RelativeLayout secondLayer;
    /**
     * 第三层
     */
    private RelativeLayout thirdLayer;
    /**
     * 第四层
     */
    private RelativeLayout fourthLayer;
    /**
     * 第五层
     */
    private RelativeLayout fifthLayer;
    /**
     * 覆盖层
     */
    private ImageView coverLayer;
    private ArrayList<BaseItemView> itemViewList = new ArrayList<>();

    public ArrayList<BaseItemView> getItemViewList() {
        return itemViewList;
    }

    public RelativeLayout getPrintLayer() {
        return printLayer;
    }

    public PrintView(Context context) {
        this(context, null);
    }

    public PrintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isMoreSelected) {
                    SelectedViewManager.getDefault().postChanger(false);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 初始化
     */
    public void init() {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        backgroundLayer = new ImageView(getContext());
        backgroundLayer.setLayoutParams(layoutParams);
        backgroundLayer.setScaleType(ImageView.ScaleType.FIT_XY);
        backgroundLayer.setBackgroundColor(Color.WHITE);
        addView(backgroundLayer);
        printLayer = new RelativeLayout(getContext());
        printLayer.setLayoutParams(layoutParams);
        addView(printLayer);
        firstLayer = new RelativeLayout(getContext());
        firstLayer.setLayoutParams(layoutParams);
        printLayer.addView(firstLayer);
        secondLayer = new RelativeLayout(getContext());
        secondLayer.setLayoutParams(layoutParams);
        printLayer.addView(secondLayer);
        thirdLayer = new RelativeLayout(getContext());
        thirdLayer.setLayoutParams(layoutParams);
        printLayer.addView(thirdLayer);
        fourthLayer = new RelativeLayout(getContext());
        fourthLayer.setLayoutParams(layoutParams);
        printLayer.addView(fourthLayer);
        fifthLayer = new RelativeLayout(getContext());
        fifthLayer.setLayoutParams(layoutParams);
        printLayer.addView(fifthLayer);
        coverLayer = new ImageView(getContext());
        coverLayer.setLayoutParams(layoutParams);
        coverLayer.setVisibility(GONE);
        addView(coverLayer);
        coverLayer.setOnClickListener(v -> ToastInstance.Companion.getINSTANCE().showToast("打印期间，请勿操作模板"));
    }

    private float templateWidth;
    private float templateHeight;

    /**
     * 设置模板宽高
     *
     * @param templateWidth
     * @param templateHeight
     */
    public void setSize(float templateWidth, float templateHeight) {
        this.templateWidth = templateWidth;
        this.templateHeight = templateHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) (width * templateHeight / templateWidth);
        int heightMeasureSpecs = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpecs);
    }

    /**
     * 添加覆盖层
     */
    public void showCoverLayer() {
        Bitmap magicDrawingCache = ViewCacheUtil.getBitmap(this);
        if (magicDrawingCache == null) return;
        coverLayer.setVisibility(VISIBLE);
        coverLayer.setImageBitmap(magicDrawingCache);
    }

    /**
     * 移除覆盖层
     */
    public void dismissCoverLayer() {
        coverLayer.setVisibility(GONE);
        coverLayer.setImageBitmap(null);
    }

    /**
     * 将元素加入层
     */
    public <T extends BaseItemView> void addItemView(T iBaseItem) {
        if (iBaseItem == null) return;
        iBaseItem.setMoreSelected(isMoreSelected);
        //注册excel
        if (iBaseItem instanceof ExcelChangeManager.IExcelDataChangeListener) {
            if (iBaseItem instanceof FlowItemView || iBaseItem instanceof DateItemView) {
            } else {
                ExcelChangeManager.getManager().registerChange((ExcelChangeManager.IExcelDataChangeListener) iBaseItem);
            }
        }
        //注册selected
        SelectedViewManager.getDefault().register(iBaseItem);
        if (iBaseItem instanceof IBackgroundLayer.FirstLayer) {
            firstLayer.addView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.SecondLayer) {
            secondLayer.addView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.ThirdLayer) {
            thirdLayer.addView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.FourthLayer) {
            fourthLayer.addView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.FifthLayer) {
            fifthLayer.addView(iBaseItem);
        } else {
            fifthLayer.addView(iBaseItem);
        }
        if (!isMoreSelected) {
            SelectedViewManager.getDefault().postChanger(iBaseItem, false);
        }
        itemViewList.add(iBaseItem);
    }


    public <T extends BaseItemView> void removeAllItemView() {
        for (BaseItemView iBaseItem : itemViewList) {
            if (iBaseItem instanceof ExcelChangeManager.IExcelDataChangeListener) {
                ExcelChangeManager.getManager().unregisterChange((ExcelChangeManager.IExcelDataChangeListener) iBaseItem);
            }
            SelectedViewManager.getDefault().unRegister(iBaseItem);
            if (iBaseItem instanceof IBackgroundLayer.FirstLayer) {
                firstLayer.removeView(iBaseItem);
            } else if (iBaseItem instanceof IBackgroundLayer.SecondLayer) {
                secondLayer.removeView(iBaseItem);
            } else if (iBaseItem instanceof IBackgroundLayer.ThirdLayer) {
                thirdLayer.removeView(iBaseItem);
            } else if (iBaseItem instanceof IBackgroundLayer.FourthLayer) {
                fourthLayer.removeView(iBaseItem);
            } else if (iBaseItem instanceof IBackgroundLayer.FifthLayer) {
                fifthLayer.removeView(iBaseItem);
            } else {
                fifthLayer.removeView(iBaseItem);
            }
        }
        itemViewList.clear();
    }

    /**
     * 将元素从层中删除
     */
    public <T extends BaseItemView> void removeItemView(T iBaseItem) {
        if (iBaseItem instanceof ExcelChangeManager.IExcelDataChangeListener) {
            ExcelChangeManager.getManager().unregisterChange((ExcelChangeManager.IExcelDataChangeListener) iBaseItem);
        }
        SelectedViewManager.getDefault().unRegister(iBaseItem);
        if (iBaseItem instanceof IBackgroundLayer.FirstLayer) {
            firstLayer.removeView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.SecondLayer) {
            secondLayer.removeView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.ThirdLayer) {
            thirdLayer.removeView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.FourthLayer) {
            fourthLayer.removeView(iBaseItem);
        } else if (iBaseItem instanceof IBackgroundLayer.FifthLayer) {
            fifthLayer.removeView(iBaseItem);
        } else {
            fifthLayer.removeView(iBaseItem);
        }
        itemViewList.remove(iBaseItem);
    }


    /**
     * 获取所有的选中元素
     */
    public List<BaseItemView> getSelectedItemView() {
        List<BaseItemView> selectedItemView = new ArrayList<>();
        for (BaseItemView itemView : itemViewList) {
            if (itemView.isSelected()) {
                selectedItemView.add(itemView);
            }
        }
        return selectedItemView;
    }

    /**
     * 删除
     */
    public void delete() {
        List<BaseItemView> selectedItemView = getSelectedItemView();
        for (BaseItemView itemView : selectedItemView) {
            itemView.delete();
            removeItemView(itemView);

        }
    }

    /**
     * 缩小
     */
    public void narrow() {
        List<BaseItemView> selectedItemView = getSelectedItemView();
        for (BaseItemView itemView : selectedItemView) {
            itemView.narrow();
        }
    }

    /**
     * 放大
     */
    public void amplification() {
        List<BaseItemView> selectedItemView = getSelectedItemView();
        for (BaseItemView itemView : selectedItemView) {
            itemView.amplification();
        }
    }


    /**
     * 旋转
     */
    public void rota() {
        List<BaseItemView> selectedItemView = getSelectedItemView();
        for (BaseItemView itemView : selectedItemView) {
            itemView.rota();
        }
    }

    public boolean isMoreSelected() {
        return isMoreSelected;
    }

    private boolean isMoreSelected;

    /**
     * 多选/单选
     */
    public void setMoreSelected(boolean isMoreSelected) {
        this.isMoreSelected = isMoreSelected;
        for (BaseItemView itemView : itemViewList) {
            itemView.setMoreSelected(isMoreSelected);
        }
    }

    /**
     * 撤销/恢复
     */
    private List<Action> actions = new ArrayList<>();


    /**
     * 添加
     */
    public void addAction(Action action) {
        actions.add(0, action);
    }

    /**
     * 撤销
     */
    public void undo() {
        if (itemViewList.size() == 0) return;
        BaseItemView itemView = itemViewList.get(itemViewList.size() - 1);
        removeItemView(itemView);
        addAction(new Action(itemView, itemView.toBean()));
    }

    /**
     * 恢复
     */
    public void restore() {
        if (actions.size() == 0) return;
        Action action = actions.get(0);
        addItemView(action.getBaseItemView());
        actions.remove(0);
    }

    /**
     * 复制
     */
    public void copy(OnItemCreated onItemCreated) {
        List<BaseItemView> selectedItemView = getSelectedItemView();
        for (BaseItemView itemView : selectedItemView) {
            BaseItemView copy = itemView.copy();
            onItemCreated.onCreated(copy);
            addItemView(copy);
        }
    }

    /**
     * 锁定
     */
    public void lock() {
        List<BaseItemView> selectedItemView = getSelectedItemView();
        for (BaseItemView itemView : selectedItemView) {
            itemView.lock();
        }
    }

    /**
     * 位移
     */
    public void displacement(IBaseItem.Orientation orientation) {
        List<BaseItemView> selectedItemView = getSelectedItemView();
        for (BaseItemView itemView : selectedItemView) {
            itemView.displacement(orientation);
        }
    }

    /**
     * 右边对齐(局部)
     */
    public void setPartRight() {
        if (getSelectedItemView().size() < 2) {
            return;
        }
        BaseItemView rightItemView = getRightItemView();
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = rightItemView.getRight() - itemView.getWidth();
            if (layoutParams.leftMargin + itemView.getWidth() > getWidth()) {
                layoutParams.leftMargin = getWidth() - itemView.getWidth();
            }
            itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 右边对齐
     */
    public void setRight() {
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = (getWidth() - itemView.getWidth());
            itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 左边对齐(局部)
     */
    public void setPartLeft() {
        if (getSelectedItemView().size() < 2) {
            return;
        }
        BaseItemView leftItemView = getLeftItemView();
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = leftItemView.getLeft();
            itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 左边对齐
     */
    public void setLeft() {
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = 0;
            itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 垂直居中(局部)
     */
    public void setPartVerticalCenter() {
        if (getSelectedItemView().size() < 3) {
            return;
        }
        BaseItemView topItemView = getTopItemView();
        BaseItemView bottomItemView = getBottomItemView();
        int height = Math.max(0, bottomItemView.getBottom() - topItemView.getTop());
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.topMargin = topItemView.getTop() + height / 2 - itemView.getHeight() / 2;
            itemView.setLayoutParams(layoutParams);
        }
    }


    /**
     * 水平居中(局部)
     */
    public void setPartHorizontalCenter() {
        if (getSelectedItemView().size() < 3) {
            return;
        }
        BaseItemView leftItemView = getLeftItemView();
        BaseItemView rightItemView = getRightItemView();
        int width = Math.max(0, rightItemView.getRight() - leftItemView.getLeft());
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = leftItemView.getLeft() + width / 2 - itemView.getWidth() / 2;
            itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 垂直等距(局部)
     */
    public void setPartVerticalIsometric() {
        setBackgroundColor(Color.RED);
        if (getSelectedItemView().size() < 3) {
            return;
        }
        int sum = 0;
        BaseItemView topItemView = getTopItemView();
        BaseItemView bottomItemView = getBottomItemView();
        for (BaseItemView itemView : getSelectedItemView()) {
            if (itemView == topItemView || itemView == bottomItemView) {
                continue;
            }
            sum += itemView.getHeight();
        }
        int height = bottomItemView.getTop() - topItemView.getBottom();
        int hOffset = (height - sum) / (itemViewList.size() - 2 + 1);
        int index = 1;
        int lastViewHeight = 0;
        for (BaseItemView itemView : getSelectedItemView()) {
            if (itemView == topItemView || itemView == bottomItemView) {
                continue;
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.topMargin = topItemView.getBottom() + hOffset * index + lastViewHeight;
            if (layoutParams.topMargin < 0) {
                layoutParams.topMargin = 0;
            } else if (layoutParams.topMargin + itemView.getHeight() > getHeight()) {
                layoutParams.topMargin = getHeight() - itemView.getHeight();
            }
            itemView.setLayoutParams(layoutParams);
            index++;
            lastViewHeight += itemView.getHeight();
        }
    }

    /**
     * 水平等距(局部)
     */
    public void setPartHorizontalIsometric() {
        if (getSelectedItemView().size() < 3) {
            return;
        }
        int sum = 0;
        BaseItemView leftItemView = getLeftItemView();
        BaseItemView rightItemView = getRightItemView();
        for (BaseItemView itemView : getSelectedItemView()) {
            if (itemView == leftItemView || itemView == rightItemView) {
                continue;
            }
            sum += itemView.getWidth();
        }
        int width = rightItemView.getLeft() - leftItemView.getRight();
        int hOffset = (width - sum) / (itemViewList.size() - 2 + 1);
        int index = 1;
        int lastViewWidth = 0;
        for (BaseItemView itemView : getSelectedItemView()) {
            if (itemView == leftItemView || itemView == rightItemView) {
                continue;
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = leftItemView.getRight() + hOffset * index + lastViewWidth;
            if (layoutParams.leftMargin < 0) {
                layoutParams.leftMargin = 0;
            } else if (layoutParams.leftMargin + itemView.getWidth() > getWidth()) {
                layoutParams.leftMargin = getWidth() - itemView.getWidth();
            }
            itemView.setLayoutParams(layoutParams);
            index++;
            lastViewWidth += itemView.getWidth();
        }
    }

    /**
     * 得到最左边的view
     */
    private BaseItemView getLeftItemView() {
        Integer left = null;
        BaseItemView baseItemView = null;
        for (BaseItemView itemView : getSelectedItemView()) {
            if (left == null) {
                left = itemView.getLeft();
                baseItemView = itemView;
            }
            if (itemView.getLeft() < left) {
                left = itemView.getLeft();
                baseItemView = itemView;
            }
        }
        return baseItemView;
    }

    /**
     * 得到最上边的view
     */
    private BaseItemView getTopItemView() {
        Integer top = null;
        BaseItemView baseItemView = null;
        for (BaseItemView itemView : getSelectedItemView()) {
            if (top == null) {
                top = itemView.getTop();
                baseItemView = itemView;
            }
            if (itemView.getTop() < top) {
                top = itemView.getTop();
                baseItemView = itemView;
            }
        }
        return baseItemView;
    }

    /**
     * 得到最右边的view
     */
    private BaseItemView getRightItemView() {
        Integer left = null;
        BaseItemView baseItemView = null;
        for (BaseItemView itemView : getSelectedItemView()) {
            if (left == null) {
                left = itemView.getRight();
                baseItemView = itemView;
            }
            if (itemView.getRight() > left) {
                left = itemView.getRight();
                baseItemView = itemView;
            }
        }
        return baseItemView;
    }

    /**
     * 得到最下边的view
     */
    private BaseItemView getBottomItemView() {
        Integer top = null;
        BaseItemView baseItemView = null;
        for (BaseItemView itemView : getSelectedItemView()) {
            if (top == null) {
                top = itemView.getBottom();
                baseItemView = itemView;
            }
            if (itemView.getBottom() > top) {
                top = itemView.getBottom();
                baseItemView = itemView;
            }
        }
        return baseItemView;
    }

    /**
     * 垂直居中
     */
    public void setVerticalCenter() {
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.topMargin = getHeight() / 2 - itemView.getHeight() / 2;
            itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 垂直等距
     */
    public void setVerticalIsometric() {
        int sum = 0;
        for (BaseItemView itemView : getSelectedItemView()) {
            sum += itemView.getHeight();
        }
        int restHeight = Math.max(0, getHeight() - sum);
        int hOffset = restHeight / (itemViewList.size() + 1);
        int index = 1;
        int lastViewHeight = 0;
        for (BaseItemView itemView : getItemViewList()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.topMargin = hOffset * index + lastViewHeight;
            if (layoutParams.topMargin < 0) {
                layoutParams.topMargin = 0;
            } else if (layoutParams.topMargin + itemView.getHeight() > getHeight()) {
                layoutParams.topMargin = getHeight() - itemView.getHeight();
            }
            itemView.setLayoutParams(layoutParams);
            index++;
            lastViewHeight += itemView.getHeight();
        }
    }

    /**
     * 水平居中
     */
    public void setHorizontalCenter() {
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = getWidth() / 2 - itemView.getWidth() / 2;
            itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 水平等距
     */
    public void setHorizontalIsometric() {
        int sum = 0;
        for (BaseItemView itemView : getSelectedItemView()) {
            sum += itemView.getWidth();
        }
        int restWidth = Math.max(0, getWidth() - sum);
        int hOffset = restWidth / (itemViewList.size() + 1);
        int index = 1;
        int lastViewWidth = 0;
        for (BaseItemView itemView : getSelectedItemView()) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemView.getLayoutParams();
            layoutParams.leftMargin = hOffset * index + lastViewWidth;
            if (layoutParams.leftMargin < 0) {
                layoutParams.leftMargin = 0;
            } else if (layoutParams.leftMargin + itemView.getWidth() > getWidth()) {
                layoutParams.leftMargin = getWidth() - itemView.getWidth();
            }
            itemView.setLayoutParams(layoutParams);
            index++;
            lastViewWidth += itemView.getWidth();
        }
    }

    /**
     * 生成模板json
     */
    public PrintModule toBean(PrintModule printModule) {
        PrintModule.ActionModelBean actionModelBean = new PrintModule.ActionModelBean();
        List<BaseItemModule> baseItemModuleList = new ArrayList<>();
        for (BaseItemView baseItemView : itemViewList) {
            try {
                baseItemModuleList.add(baseItemView.toBean());
            } catch (Exception e) {
                ToastInstance.Companion.getINSTANCE().showToast("元素还未加载完成，请稍后再试");
            }
        }
        actionModelBean.setModels(baseItemModuleList);
        printModule.setBackground(getBackgroundPath());
        printModule.setActionModel(actionModelBean);
        return printModule;

    }

    private boolean isNetworkPath(String path) {
        path = path.toLowerCase();
        boolean start = path.startsWith("http") || path.startsWith("https");
        boolean end = path.endsWith("jpg") || path.endsWith("png") || path.endsWith("jpeg");
        return start && end;
    }

    private boolean isLocalPath(String path) {
        path = path.toLowerCase();
        boolean start = !path.startsWith("http") && !path.startsWith("https");
        boolean end = path.endsWith("jpg") || path.endsWith("png") || path.endsWith("jpeg");
        return start && end;
    }

    private String backgroundPath;

    /**
     * 获取背景上传地址
     */
    public String getBackgroundPath() {
        if (!isShowBackground) {
            return "";
        }
        if (TextUtils.isEmpty(backgroundPath)) {
            return "";
        }
        if (isNetworkPath(backgroundPath)) {
            return backgroundPath;
        } else if (isLocalPath(backgroundPath)) {
            Drawable drawable = backgroundLayer.getDrawable();
            Bitmap bitmap = BitmapUtils.drawable2bitmap(drawable);
            int byteCount;
            byteCount = BitmapUtils.getSize(bitmap);
            while (byteCount / 1024 > 500) {
                bitmap = BitmapUtils.compressMatrix(bitmap, 0.5f);
                byteCount = BitmapUtils.getSize(bitmap);
            }
            return BitmapUtils.bitmap2Base64(bitmap);
        } else {
            return backgroundPath;
        }
    }

    /**
     * 是否显示背景
     */
    public boolean isShowBackground;


    /**
     * 设置背景
     */
    public void setBackgroundPath(boolean showBackground, String backgroundPath) {
        isShowBackground = showBackground;
        this.backgroundPath = backgroundPath;
        if (!isShowBackground) {
            backgroundLayer.setImageDrawable(new ColorDrawable(Color.WHITE));
            return;
        } else if (TextUtils.isEmpty(backgroundPath)) {
            backgroundLayer.setImageDrawable(new ColorDrawable(Color.WHITE));
        }
        if (isNetworkPath(backgroundPath)) {
            BitmapUtils.url2Bitmap(backgroundPath, bitmap -> {
                backgroundLayer.setImageBitmap(bitmap);
                return null;
            });
        } else if (isLocalPath(backgroundPath)) {
            backgroundLayer.setImageBitmap(BitmapFactory.decodeFile(backgroundPath));
        }

    }

    /**
     * copy 或者fromBean  生成元素的时候 每生成一个元素都会回调一次
     */
    public interface OnItemCreated {
        void onCreated(BaseItemView baseItemView);
    }

    /**
     * 生成元素
     */
    public void fromBean(PrintModule printModule, OnItemCreated onItemCreated) {
        setBackgroundPath(printModule.isShowBackground(), printModule.getBackground());
        List<BaseItemModule> models = printModule.getActionModel().getModels();
        if (models.size() > 0) {
            for (BaseItemModule model : models) {
                if (model != null) {
                    if (model.getTagType() == 1) {
                        TextItemView iBaseItem = new TextItemView(getContext()).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 2) {
                        ImageItemView iBaseItem = new ImageItemView(getContext(), null, "123", 2).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 3) {
                        ImageItemView iBaseItem = new ImageItemView(getContext(), null, "123", 1).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 4) {
                        ImageItemView iBaseItem = new ImageItemView(getContext(), null, "123", 3).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 7 || model.getTagType() == 6) {
                        LineItemView iBaseItem = new LineItemView(getContext()).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 8) {
                        DateItemView iBaseItem = new DateItemView(getContext()).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 9) {
                        FlowItemView iBaseItem = new FlowItemView(getContext()).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 10 || model.getTagType() == 11 || model.getTagType() == 12 || model.getTagType() == 13) {
                        RoundRectView iBaseItem = new RoundRectView(getContext()).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                    if (model.getTagType() == 15) {
                        NewTableView iBaseItem = new NewTableView(getContext()).fromBean(model);
                        onItemCreated.onCreated(iBaseItem);
                        addItemView(iBaseItem);
                    }
                }
            }
        }
    }
}
