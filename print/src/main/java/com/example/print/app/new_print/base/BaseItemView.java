package com.example.print.app.new_print.base;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.example.print.app.R;
import com.example.print.app.new_print.module.BaseItemModule;
import com.example.print.app.util.TranslateUtils;

import java.lang.reflect.Constructor;
import java.util.UUID;


/**
 * 抽象base基类 实现接口，提供共有实现方法
 */
abstract public class BaseItemView extends RelativeLayout implements IBaseItem {
    /**
     * 是否移动
     */
    private int clickCount = 0;
    /**
     * 是否等比放大
     */
    public String uuid = UUID.randomUUID().toString();
    private static final long TIME_OUT = 300;
    private Point firstPoint;
    /**
     * 允许移动的范围
     */
    private static final int move = 3;
    private boolean isScan = false;
    private boolean isFirstScale = true;
    private float plottingScale = 0;//比例尺 防止拖动的时候丢失精度 宽/高
    private boolean isMove = false;
    private long firstClickTime = 0;
    protected Bitmap verticalBitmap;
    private Rect verticalBitmapRect;
    protected Bitmap horizontalBitmap;
    /**
     * 放大的图标
     */
    private Rect horizontalBitmapRect;
    private Paint dottedLinePaint;
    protected int startWidth;
    protected int startHeight;
    protected int endWidth;
    protected int endHeight;

    private boolean isClickVerticalBitmap;
    private boolean isClickHorizontalBitmap;
    private int lastX;
    private int lastY;

    protected PrintView mParent;
    private int mParentHeight;
    private int mParentWidth;


    private boolean hasSelected = false;

    private boolean isDrawDottedLine = true;
    protected boolean isDrawBitmap = true;
    protected float vOffset;
    protected float hOffset;
    protected boolean isLock;
    private boolean isMoreSelected = false;
    private boolean isDrawRightBitmap = true;
    private boolean isDrawBottomBitmap = true;
    private float minHeight;
    protected float minWidth;
    private int lineWidth = 1;
    private boolean doubleClick;

    public int getRotateSum() {
        return rotateSum;
    }


    public void setRotateSum(int rotateSum) {
        this.rotateSum = rotateSum;
        setRota(rotateSum);
    }


    @Override
    public void delete() {

    }

    private int rotateSum = 0;

    public boolean isScan() {
        return isScan;
    }

    public void setScan(boolean scan) {
        isScan = scan;
    }


    public void setDrawDottedLine(boolean drawDottedLine) {
        isDrawDottedLine = drawDottedLine;
    }


    public void setSelected(boolean selected) {
        hasSelected = selected;
        invalidate();
        if (onViewLiveCycleListener != null) {
            onViewLiveCycleListener.onSelectChange(selected);
        }
    }


    public String getUuid() {
        return uuid;
    }

    public boolean isSelected() {
        return hasSelected;
    }

    public void setMoreSelected(boolean moreSelected) {
        //多选切换到单选，取消焦点事件
        if (this.isMoreSelected && !moreSelected) {
            hasSelected = false;
            invalidate();
        }
        isMoreSelected = moreSelected;
    }


    protected int verticalBitmapHeight = 0;
    protected int verticalBitmapWidth = 0;
    protected int horizontalBitmapHeight = 0;
    protected int horizontalBitmapWidth = 0;

    public BaseItemView(Context context) {
        super(context);
        setWillNotDraw(false);
        verticalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_scale_height);
        verticalBitmapRect = new Rect();
        horizontalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_scale_width);
        horizontalBitmapRect = new Rect();
        setDrawBottomBitmap(true);
        setDrawRightBitmap(true);
        dottedLinePaint = new Paint();
        dottedLinePaint.setAntiAlias(true);
        dottedLinePaint.setStrokeWidth(lineWidth * 2);
        dottedLinePaint.setColor(Color.BLUE);
        dottedLinePaint.setStyle(Paint.Style.STROKE);
        dottedLinePaint.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));
        init();
    }

    public void setDrawBottomBitmap(boolean drawBottomBitmap) {
        isDrawBottomBitmap = drawBottomBitmap;
        if (!isDrawBottomBitmap) {
            verticalBitmapHeight = 0;
            verticalBitmapWidth = 0;
        } else {
            verticalBitmapHeight = verticalBitmap.getHeight();
            verticalBitmapWidth = verticalBitmap.getWidth();
        }
        refreshSize();
    }

    public void setDrawRightBitmap(boolean drawRightBitmap) {
        isDrawRightBitmap = drawRightBitmap;
        if (!isDrawRightBitmap) {
            horizontalBitmapHeight = 0;
            horizontalBitmapWidth = 0;
        } else {
            horizontalBitmapHeight = horizontalBitmap.getHeight();
            horizontalBitmapWidth = horizontalBitmap.getWidth();
        }
        refreshSize();
    }

    private void refreshSize() {
        vOffset = verticalBitmapHeight / 2;
        hOffset = horizontalBitmapWidth / 2;
        startWidth = (int) hOffset;
        startHeight = (int) vOffset;
        minWidth = verticalBitmap.getWidth() / 2 + horizontalBitmap.getWidth();
        minHeight = verticalBitmap.getHeight() + horizontalBitmap.getHeight() / 2;
        setPadding(0, 0, (int) hOffset, (int) vOffset);
    }

    public void setMinWidth(float minWidth) {
        this.minWidth = minWidth;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    @Override
    public void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getLayoutParams().height, MeasureSpec.AT_MOST);
        int newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getLayoutParams().width, MeasureSpec.AT_MOST);
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec);
        setPivotX((getMeasuredWidth() - hOffset) / 2);
        setPivotY((getMeasuredHeight() - vOffset) / 2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mParent = (PrintView) getParent().getParent().getParent();
        mParentHeight = mParent.getHeight();
        mParentWidth = mParent.getWidth();
        endWidth = (int) (w - hOffset);
        endHeight = (int) (h - vOffset);
        //放大
        if (isFirstScale) {
            plottingScale = (float) (getWidth() - hOffset) / (float) (getHeight() - vOffset);
            isFirstScale = false;
        }
    }

    @Override
    public void amplification() {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        if (layoutParams.height > layoutParams.width) {
            layoutParams.height += 5;
            layoutParams.width += Math.round(5 / plottingScale);
        } else {
            layoutParams.width += 5;
            layoutParams.height += Math.round(5 / plottingScale);
        }
        setLayoutParams(layoutParams);
    }

    @Override
    public void narrow() {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        if (minHeight >= layoutParams.height || minWidth >= layoutParams.width) return;
        if (layoutParams.height > layoutParams.width) {
            layoutParams.height -= 5;
            layoutParams.width -= Math.round(5 / plottingScale);
        } else {
            layoutParams.height -= Math.round(5 / plottingScale);
            layoutParams.width -= 5;
        }
        setLayoutParams(layoutParams);
    }

    @Override
    public void rota() {
        rotateSum += 90;
        if (rotateSum == 360) rotateSum = 0;
        setRota(rotateSum);
    }


    private void setRota(int rotateSum) {
        setRotation(rotateSum);
    }

    @Override
    public BaseItemView copy() {
        BaseItemView baseItemView = null;
        try {
            Constructor cons = getClass().getConstructor(Context.class);
            baseItemView = (BaseItemView) cons.newInstance(getContext());
            baseItemView.setSelected(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (baseItemView != null) setCopyParameters(baseItemView);
        return baseItemView;
    }

    private void setCopyParameters(BaseItemView baseItemView) {
        LayoutParams mLayoutParams = new LayoutParams((ViewGroup.MarginLayoutParams) getLayoutParams());
        mLayoutParams.topMargin += 10;
        mLayoutParams.leftMargin += 10;
        baseItemView.setLayoutParams(mLayoutParams);
        baseItemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                baseItemView.setRotateSum(getRotateSum());
                baseItemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    @Override
    public void lock() {
        isLock = !isLock;
    }


    public boolean isLock() {
        return isLock;
    }

    @Override
    public void displacement(Orientation orientation) {
        if (isLock) {
            return;
        }
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        switch (orientation) {
            case top:
                if (getTop() - 5 < 0) {
                    return;
                }
                layoutParams.topMargin += -5;
                break;
            case left:
                if (getLeft() - 5 < 0) {
                    return;
                }
                layoutParams.leftMargin += -5;
                break;
            case right:
                if (getRight() + 5 > mParent.getWidth()) {
                    return;
                }
                layoutParams.leftMargin += 5;
                break;
            case bottom:
                if (getBottom() + 5 > mParent.getHeight()) {
                    return;
                }
                layoutParams.topMargin += 5;
                break;
        }
        setLayoutParams(layoutParams);
    }


    private int getHorizontalOffset(int x, int y) {
        switch (rotateSum / 90) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return -x;
            case 3:
                return -y;
            default:
                return x;
        }
    }

    private int getVerticalOffset(int x, int y) {
        switch (rotateSum / 90) {
            case 0:
                return y;
            case 1:
                return -x;
            case 2:
                return -y;
            case 3:
                return x;
            default:
                return y;
        }
    }

    /**
     * 处理双击事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LayoutParams layoutParams = (LayoutParams) getLayoutParams();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                //记录上次的数据
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                int x = (int) event.getX();
                int y = (int) event.getY();
                isClickVerticalBitmap = verticalBitmapRect.contains(x, y);
                if (isClickVerticalBitmap)
                    Log.w("1234", "下方按钮被点击");
                isClickHorizontalBitmap = horizontalBitmapRect.contains(x, y);
                if (isClickHorizontalBitmap)
                    Log.w("1234", "右方按钮被点击");
                break;
            case MotionEvent.ACTION_MOVE:
                int newX = (int) event.getRawX();
                int newY = (int) event.getRawY();
                int offsetX = newX - this.lastX;
                int offsetY = newY - this.lastY;

                if (Math.abs(offsetX) > move || Math.abs(offsetX) > move) {
                    clickCount = 0;
                    isMove = true;
                }
                if (isLock) {
                    return true;
                }

                if (isDrawBottomBitmap && isClickVerticalBitmap) {
                    if (onMoveListener != null) {
                        onMoveListener.onMove(0, offsetY);
                    } else if (rotateSum == 0 && getBottom() + getVerticalOffset(offsetX, offsetY) <= mParentHeight + vOffset
                            && getHeight() + getVerticalOffset(offsetX, offsetY) >= minHeight || rotateSum != 0
                            && getHeight() + getVerticalOffset(offsetX, offsetY) >= minHeight) {
                        layoutParams.height = getVerticalOffset(offsetX, offsetY) + getHeight();
                        setLayoutParams(layoutParams);
                        isFirstScale = true;
                    }
                } else if (isClickHorizontalBitmap && isDrawRightBitmap) {
                    if (onMoveListener != null) {
                        onMoveListener.onMove(offsetX, 0);
                    } else if (rotateSum == 0 && getRight() + getHorizontalOffset(offsetX, offsetY) <= mParentWidth + hOffset
                            && getWidth() + getHorizontalOffset(offsetX, offsetY) >= minWidth || rotateSum != 0
                            && getWidth() + getHorizontalOffset(offsetX, offsetY) >= minWidth) {
                        layoutParams.width = getHorizontalOffset(offsetX, offsetY) + getWidth();
                        if (isScan) {
                            layoutParams.height = Math.round(getHorizontalOffset(offsetX, offsetY) / plottingScale) + getHeight();
                        } else {
                            isFirstScale = true;
                        }
                        setLayoutParams(layoutParams);
                    }
                } else {
                    //位移
                    if (true) {
                        boolean isCanMoveX = false;
                        boolean isCanMoveY = false;
                        if (rotateSum == 0 && getTop() >= 0 && mParentHeight - getBottom() + vOffset >= 0) {
                            if (getTop() + offsetY < 0) {
                                offsetY = -getTop();
                            }
                            if (getBottom() + offsetY > mParentHeight + vOffset) {
                                offsetY = (int) (mParentHeight - getBottom() + vOffset);
                            }
                        }
                        layoutParams.topMargin += offsetY;
                        if (offsetY != 0) isCanMoveY = true;

                        if (rotateSum == 0 && getLeft() >= 0 && mParentWidth - getRight() + hOffset >= 0) {
                            if (getLeft() + offsetX < 0) {
                                offsetX = -getLeft();
                            }
                            if (getRight() + offsetX > mParentWidth + hOffset) {
                                offsetX = (int) (mParentWidth - getRight() + hOffset);
                            }
                        }
                        layoutParams.leftMargin += offsetX;
                        if (offsetX != 0) isCanMoveX = true;
                        if (isCanMoveX || isCanMoveY) {
                            setLayoutParams(layoutParams);
                        }
                    }
                }
                this.lastX = newX;
                this.lastY = newY;
                break;
            case MotionEvent.ACTION_UP:
                //先取消其他所有元素的选中，在选中自己  否者在锁定按钮图片切换的时候会出现bug
                if (!isMoreSelected) {
                    SelectedViewManager.getDefault().postChanger(this, false);
                }
                doubleClick = false;
                if (hasSelected) {
                    if (!isMove) {
                        clickCount++;
                        if (clickCount == 1) {
                            firstClickTime = System.currentTimeMillis();
                            //多选状态下
                            if (isMoreSelected) {
                                new android.os.Handler().postDelayed(() -> {
                                    if (hasSelected) {
                                        if (!doubleClick) {
                                            setSelected(false);
                                            clickCount = 0;
                                        }
                                    }
                                }, TIME_OUT);
                            }
                            return true;
                            //第二次点击
                        } else if (clickCount == 2) {
                            long secondClickTime = System.currentTimeMillis();
                            if (secondClickTime - firstClickTime <= TIME_OUT) {
                                doubleClick = true;
                                if (onViewLiveCycleListener != null) {
                                    onViewLiveCycleListener.onDoubleClick(isMoreSelected);
                                }
                                clickCount = 0;
                            } else {
                                firstClickTime = System.currentTimeMillis();
                                clickCount = 1;
                            }
                            return true;
                        }
                    }
                } else {
                    setSelected(true);
                }
                isMove = false;
                isClickVerticalBitmap = false;
                isClickHorizontalBitmap = false;
                return true;
        }
        return true;
    }


    private OnMoveListener onMoveListener;


    /**
     * 拖动按钮事件 设置将屏蔽原本事件
     */
    public void setOnMoveListener(OnMoveListener onMoveListener) {
        this.onMoveListener = onMoveListener;
    }

    public interface OnMoveListener {
        void onMove(int offsetX, int offsetY);

    }


    /**
     * 绘制外框和拖拽图标
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        onDrawBold(canvas);
    }

    private boolean isPrint;

    public void setPrint(boolean print) {
        isPrint = print;
    }

    public void onDrawBold(Canvas canvas) {
        Log.e("BaseItemView", "onDraw: ");
        if (verticalBitmap == null) {
            verticalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_scale_height);

        }
        if (horizontalBitmap == null) {
            horizontalBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_scale_width);

        }
        //bitmap所在的矩形
        verticalBitmapRect.left = (getWidth() - horizontalBitmapWidth / 2) / 2 - verticalBitmapWidth / 2;
        verticalBitmapRect.top = getHeight() - verticalBitmapHeight;
        verticalBitmapRect.right = verticalBitmapRect.left + verticalBitmapWidth;
        verticalBitmapRect.bottom = verticalBitmapRect.top + verticalBitmapHeight;

        horizontalBitmapRect.left = getWidth() - horizontalBitmapWidth;
        horizontalBitmapRect.top = (getHeight() - verticalBitmapHeight / 2) / 2 - horizontalBitmapHeight / 2;
        horizontalBitmapRect.right = horizontalBitmapRect.left + horizontalBitmapWidth;
        horizontalBitmapRect.bottom = horizontalBitmapRect.top + horizontalBitmapHeight;
        if (!isPrint && hasSelected && isDrawDottedLine) {
            RectF rectF = new RectF();
            rectF.left = lineWidth;
            rectF.top = lineWidth;
            rectF.right = getWidth() - hOffset - lineWidth;
            rectF.bottom = getHeight() - vOffset - lineWidth;
            canvas.drawRect(rectF, dottedLinePaint);
        }
//        canvas.drawRect(horizontalBitmapRect, dottedLinePaint);
//        canvas.drawRect(verticalBitmapRect, dottedLinePaint);
        if (!isPrint && hasSelected && isDrawBitmap) {
            //垂直拖拽按钮
            if (isDrawBottomBitmap) {
                canvas.drawBitmap(verticalBitmap, verticalBitmapRect.left, verticalBitmapRect.top, null);
            }
            //水平拖拽按钮
            if (isDrawRightBitmap) {
                canvas.drawBitmap(horizontalBitmap, horizontalBitmapRect.left, horizontalBitmapRect.top, null);
            }
        }

    }

    /**
     *
     */
    public void mamazaiaiwoyici(LayoutParams layoutParams) {
        if (isDrawRightBitmap) {
            layoutParams.width += hOffset;
        }
        if (isDrawBottomBitmap) {
            layoutParams.height += vOffset;
        }
        super.setLayoutParams(layoutParams);
    }

    protected OnViewLiveCycleListener onViewLiveCycleListener;

    public void setOnViewLiveCycleListener(OnViewLiveCycleListener onViewLiveCycleListener) {
        this.onViewLiveCycleListener = onViewLiveCycleListener;
    }

    public interface OnViewLiveCycleListener {
        void onAttached();

        void onDetached();

        void onDoubleClick(boolean isMoreSelected);

        void onSelectChange(boolean isSelected);

        void onUpdate(String excel);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (onViewLiveCycleListener != null) {
            onViewLiveCycleListener.onAttached();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (onViewLiveCycleListener != null) {
            onViewLiveCycleListener.onDetached();
        }
    }

    /**
     * to bean
     */
    public BaseItemModule toBean() {
        BaseItemModule itemModule = new BaseItemModule();
        itemModule.setRotate(getRotateSum());
        itemModule.setX(TranslateUtils.getPxByLocalPx(getLeft()));
        itemModule.setY(TranslateUtils.getPxByLocalPx((getTop())));
        itemModule.setHeight(TranslateUtils.getPxByLocalPx(getMeasuredHeight()));
        itemModule.setWidth(TranslateUtils.getPxByLocalPx(getMeasuredWidth()));
        return itemModule;
    }

    /**
     * to bean
     */
    public BaseItemView fromBean(BaseItemModule itemModule) {
        LayoutParams layoutParams = new LayoutParams(
                (int) TranslateUtils.getPxByServicePx(itemModule.getWidth())
                , (int) TranslateUtils.getPxByServicePx(itemModule.getHeight()));
        layoutParams.topMargin = (int) (TranslateUtils.getPxByServicePx(itemModule.getY()));
        layoutParams.leftMargin = (int) (TranslateUtils.getPxByServicePx(itemModule.getX()));
        setLayoutParams(layoutParams);
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
