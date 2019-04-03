package com.gengcon.android.fixedassets.common.module.update;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.gengcon.android.fixedassets.R;

/*
 * 文件名：CircleProgressView
 * 描    述：CircleProgressView
 * 作    者：杨兴
 * 时    间：2018.03.15
 * 版    权：武汉精臣智慧标识科技有限公司
 */
public class CircleProgressView extends View {

    private float mStripeWidth;
    private int mCurrentProgress;
    private int mSmallColor;
    private int mBigColor;
    private int mCenterTextSize;
    private float mRadius;
    private float x;
    private float y;
    private int mWidth;
    private int mHeight;
    //要画的弧度
    private int mEndAngle;
    private boolean stop = false;
    private boolean isComplete = false;
    private Context mContext;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        mStripeWidth = typedArray.getDimension(R.styleable.CircleProgressView_stripeWidth, dip2px(context, 30));
        mCurrentProgress = typedArray.getInteger(R.styleable.CircleProgressView_percent, 0);
        mSmallColor = typedArray.getColor(R.styleable.CircleProgressView_smallColor, Color.RED);
        mBigColor = typedArray.getColor(R.styleable.CircleProgressView_bigColor, Color.BLUE);
        mCenterTextSize = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_centerTextSize, 20);
        mRadius = typedArray.getDimensionPixelSize(R.styleable.CircleProgressView_radius, dip2px(context, 100));
        this.mContext = context;
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            mRadius = widthSize / 2;
            x = widthSize / 2;
            y = heightSize / 2;
            mWidth = widthSize;
            mHeight = heightSize;
        }
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            mWidth = (int) (mRadius * 2);
            mHeight = (int) mRadius * 2;
            x = mRadius;
            y = mRadius;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEndAngle = (int) (mCurrentProgress * 3.6);
        //绘制大圆
        Paint bigCircle = new Paint();
        bigCircle.setAntiAlias(true);
        bigCircle.setColor(mBigColor);
        canvas.drawCircle(x, y, mRadius, bigCircle);
        //    绘制线条
        Paint lineCircle = new Paint();
        lineCircle.setAntiAlias(true);
        lineCircle.setColor(Color.parseColor("#e1e1e1"));
        canvas.drawCircle(x, y, mRadius, lineCircle);
        //饼状图
        Paint sectorPaint = new Paint();
        sectorPaint.setColor(mSmallColor);
        sectorPaint.setAntiAlias(true);
        RectF rectF = new RectF(0, 0, mWidth, mHeight);
        canvas.drawArc(rectF, -90, mEndAngle, true, sectorPaint);

        //绘制小圆
        Paint smallCircle = new Paint();
        smallCircle.setAntiAlias(true);
        smallCircle.setColor(mBigColor);
        canvas.drawCircle(x, y, mRadius - mStripeWidth, smallCircle);
        Paint textPaint = new Paint();
        String strText = mCurrentProgress + "%";
        if (isComplete) {
            strText = "安  装";
        }
        textPaint.setTextSize(mCenterTextSize);

        float textLength = textPaint.measureText(strText);
        textPaint.setColor(mSmallColor);
        canvas.drawText(strText, x - textLength / 2, y + px2dip(mContext, mCenterTextSize), textPaint);

    }

    public void setComplete() {
        if (mCurrentProgress == 100) {
            isComplete = true;
            this.postInvalidate();
        }
    }

    public void setPercent(final int percent) {
        stop = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = mCurrentProgress; i <= percent; i++) {
                    if (!stop) {
                        try {
                            Thread.sleep(16);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mCurrentProgress = i;
                        CircleProgressView.this.postInvalidate();
                    }
                }
            }
        }).start();
    }

    public void setCurrentPercent(int percent) {
        mCurrentProgress = percent;
        CircleProgressView.this.postInvalidate();
    }

    public void stop() {
        stop = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = mCurrentProgress; i >= 0; i--) {
                    if (stop) {
                        try {
                            Thread.sleep(8);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mCurrentProgress = i;
                        CircleProgressView.this.postInvalidate();
                    }
                }
            }
        }).start();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                setPercent(100);
//                break;
//            case MotionEvent.ACTION_UP:
//                    stop();
//                break;
//        }
//        return true;
//    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * <p>
     * 说明：px转换为dip
     * </p>
     *
     * @param context
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}