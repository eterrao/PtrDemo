package com.welove.welove520.ptrlib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Raomengyang on 17-8-15.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */

public class PTRLayout extends ViewGroup {

    private View mContent;
    private View mHeaderView;
    private PtrIndicator mPtrIndicator;
    private int mHeaderId;
    private int mContainerId;

    public PTRLayout(Context context) {
        super(context);
        init(context);
    }

    public PTRLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PTRLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mPtrIndicator = new PtrIndicator();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren(l, t, r, b);
    }

    private void layoutChildren(int l, int t, int r, int b) {
        int offset = mPtrIndicator.getCurrentPosY();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
//
//        if (mHeaderView != null) {
//            MarginLayoutParams lp = (MarginLayoutParams) mHeaderView.getLayoutParams();
//            final int left = paddingLeft + lp.leftMargin;
//            // enhance readability(header is layout above screen when first init)
//            final int top = -(mHeaderHeight - paddingTop - lp.topMargin - offset);
//            final int right = left + mHeaderView.getMeasuredWidth();
//            final int bottom = top + mHeaderView.getMeasuredHeight();
//            mHeaderView.layout(left, top, right, bottom);
//            if (isDebug()) {
//                PtrCLog.d(LOG_TAG, "onLayout header: %s %s %s %s", left, top, right, bottom);
//            }
//        }
        if (mContent != null) {
//            if (isPinContent()) {
//                offset = 0;
//            }
            MarginLayoutParams lp = (MarginLayoutParams) mContent.getLayoutParams();
            final int left = paddingLeft + lp.leftMargin;
            final int top = paddingTop + lp.topMargin + offset;
            final int right = left + mContent.getMeasuredWidth();
            final int bottom = top + mContent.getMeasuredHeight();
//            if (isDebug()) {
//                PtrCLog.d(LOG_TAG, "onLayout content: %s %s %s %s", left, top, right, bottom);
//            }
            mContent.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onFinishInflate() {
        final int childCount = getChildCount();
        if (childCount > 2) {
            throw new IllegalStateException("PtrFrameLayout can only contains 2 children");
        } else if (childCount == 2) {
            if (mHeaderId != 0 && mHeaderView == null) {
                mHeaderView = findViewById(mHeaderId);
            }
            if (mContainerId != 0 && mContent == null) {
                mContent = findViewById(mContainerId);
            }

            // not specify header or content
            if (mContent == null || mHeaderView == null) {

                View child1 = getChildAt(0);
                View child2 = getChildAt(1);
                if (child1 instanceof PtrUIHandler) {
                    mHeaderView = child1;
                    mContent = child2;
                } else if (child2 instanceof PtrUIHandler) {
                    mHeaderView = child2;
                    mContent = child1;
                } else {
                    // both are not specified
                    if (mContent == null && mHeaderView == null) {
                        mHeaderView = child1;
                        mContent = child2;
                    }
                    // only one is specified
                    else {
                        if (mHeaderView == null) {
                            mHeaderView = mContent == child1 ? child2 : child1;
                        } else {
                            mContent = mHeaderView == child1 ? child2 : child1;
                        }
                    }
                }
            }
        } else if (childCount == 1) {
            mContent = getChildAt(0);
        } else {
            TextView errorView = new TextView(getContext());
            errorView.setClickable(true);
            errorView.setTextColor(0xffff6600);
            errorView.setGravity(Gravity.CENTER);
            errorView.setTextSize(20);
            errorView.setText("The content view in PtrFrameLayout is empty. Do you forget to specify its id in xml layout file?");
            mContent = errorView;
            addView(mContent);
        }
        if (mHeaderView != null) {
            mHeaderView.bringToFront();
        }
        super.onFinishInflate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || mContent == null || mHeaderView == null) {
            return dispatchTouchEventSuper(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return dispatchTouchEventSuper(ev);
            case MotionEvent.ACTION_DOWN:
                dispatchTouchEventSuper(ev);
                return true;
            case MotionEvent.ACTION_MOVE:
                mPtrIndicator.onMove(ev.getX(), ev.getY());
                float offsetX = mPtrIndicator.getOffsetX();
                float offsetY = mPtrIndicator.getOffsetY();
                movePosition((int) offsetY);
                break;
        }


        return dispatchTouchEventSuper(ev);
    }

    private void movePosition(int offsetY) {
        if (offsetY < 0) {
            return;
        }
        mContent.offsetTopAndBottom(offsetY);
    }

    private boolean dispatchTouchEventSuper(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
