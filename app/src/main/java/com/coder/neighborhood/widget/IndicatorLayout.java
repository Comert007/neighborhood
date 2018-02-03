package com.coder.neighborhood.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.TextView;

import com.coder.neighborhood.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class IndicatorLayout extends LinearLayout implements ViewPager.OnPageChangeListener {

    /**
     * 绘制时用的画笔
     */
    private Paint mPaint;
    /**
     * 默认颜色
     */
    private int mCursorColor;
    /**
     * 滚动的指示器的矩形范围
     */
    private Rect mRect = new Rect();
    /**
     * 滚动游标的绘制范围
     */
    private int mL, mR, mT, mB;
    /**
     * 最多可见的游标数
     */
    private int mVisiableTabCount = 0;
    /**
     * 游标高度
     */
    private int mCursorHeight = 6;
    /**
     * tab的宽度
     */
    private int mTabWidth;
    /**
     *
     */
    private int mOffset = 0;
    /**
     * 选中和非选中状态的标签文字颜色
     */
    private int mTabColorNormal;

    private int mTabColorLight;
    /**
     * 水平滚动的距离
     */
    private float mTranslationX;

    /**
     * 判定X 的TouchSlop
     */
    private float mAssessX;

    private float mLastX;

    /**
     * 来处理自定义的点击事件
     */
    private float mMotionX;

    private float mMotionY;

    private long mClickTime;

    Rect mHitRect = new Rect();

    private boolean isDragging;
    /**
     * 最小移动距离
     */
    private int mTouchSlop;
    /**
     * 速度检测
     */
    private VelocityTracker mVelocityTracker;

    private int mMaximumVelocity;

    private int mMinmumVelocity;

    private OverScroller mScroll;

    /**
     * 需要监听ViewPager动作来跟新游标
     */
    private ViewPager mViewPager;

    public OnIndicatorChangeListener mIndicatorListener;

    /**
     * ViewPager变化监听
     */
    public interface OnIndicatorChangeListener {
        void onChanged(int index);
    }

    private int INDICATOR_STATE = STATE_NORMAL;

    public static final int STATE_NORMAL = 1;

    public static final int STATE_ENLARGE = 2;

    public IndicatorLayout(Context context) {
        this(context, null);
    }

    public IndicatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(mCursorColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //
        mScroll = new OverScroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinmumVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        mTabColorNormal = array.getColor(R.styleable.Indicator_tab_color_normal, Color.BLACK);
        mTabColorLight = array.getColor(R.styleable.Indicator_tab_color_light, Color.WHITE);
        mCursorColor = array.getColor(R.styleable.Indicator_cursor_color, Color.BLACK);
        mCursorHeight = array.getDimensionPixelSize(R.styleable.Indicator_cursor_height, mCursorHeight);
        mOffset = array.getDimensionPixelSize(R.styleable.Indicator_cursor_offset, mOffset);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        if (count == 0) {
            return;
        }
        mTabWidth = getMeasuredWidth() / mVisiableTabCount;
        for (int i = 0; i < count; i++) {
            TextView view = (TextView) getChildAt(i);
            LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.weight = 0;
            params.width = mTabWidth;
            params.height = getHeight() - mCursorHeight;
            view.setLayoutParams(params);
        }

        mL = 0 + mOffset;
        mT = getHeight() - mCursorHeight;
        mR = mTabWidth - mOffset;
        mB = getHeight();
        mRect = new Rect(mL, mT, mR, mB);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, 0);
        canvas.drawRect(mRect, mPaint);
        canvas.restore();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        initVelocityTracker(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mAssessX = event.getX();
                mLastX = event.getX();

                if (!mScroll.isFinished()) {
                    mScroll.abortAnimation();
                }

                mMotionX = event.getX();
                mMotionY = event.getY();
                mClickTime = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_MOVE:
                float newX = event.getX();
                float diffX = newX - mAssessX;

                if (!isDragging && Math.abs(diffX) > mTouchSlop) {
                    isDragging = true;
                }
                float diff = newX - mLastX;
                if (isDragging) {
                    scrollBy(-(int) diff, 0);
                    mAssessX = newX;
                }
                mLastX = newX;
                break;
            case MotionEvent.ACTION_UP:
                isDragging = false;
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                float velocity = mVelocityTracker.getXVelocity();
                if (Math.abs(velocity) > mMinmumVelocity) {
                    if (velocity > 0) {
                        flingToLeft(velocity);
                    } else if (velocity < 0) {
                        flingToRight(-velocity);
                    }
                }

                float upX = event.getX();
                float upY = event.getY();
                long tptime = System.currentTimeMillis();

                if (Math.abs(upX - mMotionX) < 10 && Math.abs(upY - mMotionY) < 10 && (tptime - mClickTime) < 400) {
                    int index = getViewIndex(event);
                    if (index >= 0) {
                        int i = (Integer) getChildAt(index).getTag();
                        mIndicatorListener.onChanged(i);
                    }
                }
                recyleVelcityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                isDragging = false;
                recyleVelcityTracker();
                if (!mScroll.isFinished()) {
                    mScroll.abortAnimation();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void scroll(int position, float offset) {
        mTranslationX = getWidth() / mVisiableTabCount * (position + offset);
        /**
         * 当tab数大于可见数目的时候，整个容器滚动
         */
        if (getChildCount() > mVisiableTabCount && offset > 0 && (position >= mVisiableTabCount - 2)) {
            if (mVisiableTabCount != 1) {
                /**
                 * 一屏的数量输mVisiableTabCount
                 * 当滚动到倒数第二个是,Indicator不再滚动
                 */
                if (position < getChildCount() - 2) {
                    scrollTo((position - (mVisiableTabCount - 2)) * mTabWidth + (int) (offset * mTabWidth), 0);
                } else {
                    /**
                     * 不在发生滚动,让其停在倒数第mVisiableTabCount的位置,能够显示剩余的tab即可
                     */
                    scrollTo((getChildCount() - 2 - (mVisiableTabCount - 2)) * mTabWidth, 0);
                }
            } else {
                scrollTo(position * mTabWidth + (int) (offset * mTabWidth), 0);
            }
        }
        invalidate();
    }

    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
    }

    public void setTabs(String[] tabs) {
        if (mVisiableTabCount < 1) {
            throw new IllegalArgumentException("indicator tab count must > 1");
        }
        removeAllViews();
        for (String t : tabs) {
            createChild(t);
        }
        setTabLight(0);
    }

    /**
     * 使用的时候这个方法为必须调用的方法，并且在@link{com.tuxing.app.view.IndicatorLayout#setTabs}之前进行调用
     *
     * @param count 可见的tab数目，必须>1
     */
    public void setVisiableTabCount(int count) {
        mVisiableTabCount = count;
    }


    public void setTabLight(int arg0) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TextView view = (TextView) getChildAt(i);
            if (i == arg0) {
                view.setTextColor(mTabColorLight);
                if (INDICATOR_STATE == STATE_ENLARGE) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                }

            } else {
                view.setTextColor(mTabColorNormal);
                if (INDICATOR_STATE == STATE_ENLARGE) {
                    view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                }
            }
        }
    }

    /**
     * 创建子View
     *
     * @param text
     */
    private void createChild(String text) {
        TextView view = new TextView(getContext());
        LayoutParams params = new LayoutParams(getScreenWidth() / mVisiableTabCount, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        view.setText(text);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(mTabColorNormal);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        //view.setOnClickListener(this);
        view.setTag(getChildCount());
        addView(view, params);
    }

    public int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;

    }

    public void setOnIndicatorChangeListener(OnIndicatorChangeListener listener) {
        mIndicatorListener = listener;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        scroll(arg0, arg1);
    }

    @Override
    public void onPageSelected(int arg0) {
        setTabLight(arg0);
        if (mIndicatorListener != null) {
            mIndicatorListener.onChanged(arg0);
        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (x >= 0 && x <= mTabWidth * (getChildCount() - mVisiableTabCount)) {
            super.scrollTo(x, y);
        }
    }

    private void flingToRight(float v) {
        mScroll.fling(getScrollX(), 0, (int) v, 0, 0, mTabWidth * (getChildCount() - mVisiableTabCount), 0, 0);
        invalidate();
    }

    private void flingToLeft(float v) {
        mScroll.fling(getScrollX(), 0, (int) -v, 0, 0, mTabWidth * (getChildCount() - mVisiableTabCount), 0, 0);
        invalidate();
    }

    @Override
    public void computeScroll() {

        if (mScroll.computeScrollOffset()) {
            scrollTo(mScroll.getCurrX(), 0);
            invalidate();
        }
    }

    private int getViewIndex(MotionEvent event) {
        int x = (int) event.getX() + getScrollX();
        int y = (int) event.getY();
        for (int i = 0; i < getChildCount(); i++) {
            if (getViewByEvent(x, y, getChildAt(i))) {
                return i;
            }
        }
        return -1;
    }

    private boolean getViewByEvent(int x, int y, View v) {
        v.getHitRect(mHitRect);
        return mHitRect.contains(x, y);
    }

    public void setState(int state) {
        INDICATOR_STATE = state;
    }

    /**
     * 初始化
     *
     * @param event
     */
    private void initVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收
     */
    private void recyleVelcityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}