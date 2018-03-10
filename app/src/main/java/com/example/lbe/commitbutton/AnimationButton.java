package com.example.lbe.commitbutton;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by lbe on 18-1-14.
 */

public class AnimationButton extends View implements View.OnClickListener{

    private Paint mPaint;
    //绘制文字
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;
    //圆角矩形
    private RectF mRoundRect;
    //绘制文字的矩形区域
    private RectF mTextRect;
    private int mPaintWidth;
    //两边两个半圆拼接成一个圆需要移动的距离
    private int mTotalMoveDistance;
    //两个半圆之间的距离
    private int mCircleDistance;
    //圆的半径
    private int mRaduis;
    //两个半圆拼接成一个圆的动画
    private ValueAnimator mSemicircleAnimator;

    private static final String BUTTOM_TEXT = "OK";

    private AnimationButtonListener mAnimationButtonListener;

    public interface AnimationButtonListener {
        void OnClick();
    }
    public void setAnimationButtonListener(AnimationButtonListener listener) {
        mAnimationButtonListener = listener;
    }

    public AnimationButton(Context context) {
        super(context);
        init();
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getContext().getResources().getColor(R.color.colorAccent));
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(getContext().getResources().getColor(R.color.colorPrimary));
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(sp2px(20));
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mAnimationButtonListener.OnClick();
    }

    public void startAnimation() {
        mSemicircleAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mPaintWidth = 8;
        mRaduis = mHeight / 2;
        mPaint.setStrokeWidth(mPaintWidth);
        mRoundRect = new RectF(mCircleDistance + mPaintWidth, mPaintWidth, mWidth - mCircleDistance - mPaintWidth, mHeight-mPaintWidth);
        initMoveAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = dp2px(300);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = dp2px(50);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRoundRect(canvas);
        drawText(canvas);
    }

    /**
     * 绘制圆角矩形
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        //最开始 mCircleDistance 为0
        mRoundRect.left = mCircleDistance + mPaintWidth;
        mRoundRect.top = mPaintWidth;
        mRoundRect.right = mWidth - mCircleDistance - mPaintWidth;
        mRoundRect.bottom = mHeight-mPaintWidth;
        int round = mHeight / 2;
        canvas.drawRoundRect(mRoundRect, round, round, mPaint);
    }

    /**
     * 绘制中间文字
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        mTextRect = new RectF();
        mTextRect.left =  mPaintWidth;
        mTextRect.top = mPaintWidth;
        mTextRect.right = mWidth;
        mTextRect.bottom = mHeight;
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        //测量文字的宽度
        float textWidth = mTextPaint.measureText(BUTTOM_TEXT) ;
        //文字绘制到整个布局的中心位置
        int baseline = getHeight() / 2 - fontMetrics.descent + (fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(BUTTOM_TEXT, mTextRect.centerX() - textWidth / 2, baseline, mTextPaint);
    }

    /**
     * 对圆角矩形做动画
     */
    private void initMoveAnimation() {
        mTotalMoveDistance = (mWidth - 2 * mRaduis) / 2;
        mSemicircleAnimator = ValueAnimator.ofInt(0, mTotalMoveDistance);
        mSemicircleAnimator.setDuration(1500);
        mSemicircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleDistance = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    private int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());
    }

}
