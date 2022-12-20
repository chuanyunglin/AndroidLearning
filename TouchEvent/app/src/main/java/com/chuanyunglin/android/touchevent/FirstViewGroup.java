package com.chuanyunglin.android.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class FirstViewGroup extends LinearLayout {
    private static final String TAG = FirstViewGroup.class.getSimpleName();

    public FirstViewGroup(Context context) {
        super(context);
    }

    public FirstViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FirstViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //setClickable(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.w(TAG, "dispatchTouchEvent, ev = " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.dispatchTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.w(TAG, "onTouchEvent, ev = " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.onTouchEvent(ev);
        return result;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.w(TAG, "onInterceptTouchEvent, ev = " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.onInterceptTouchEvent(ev);
        return result;
    }
}
