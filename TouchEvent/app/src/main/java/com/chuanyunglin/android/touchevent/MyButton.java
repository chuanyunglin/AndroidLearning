package com.chuanyunglin.android.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyButton extends Button {
    private static final String TAG = MyButton.class.getSimpleName();

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // setClickable(false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.w(TAG, "onTouchEvent1, ev = " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.onTouchEvent(ev);
        Log.w(TAG, "onTouchEvent2, ev = " + MotionEvent.actionToString(ev.getAction())
                + ", result = " + result);
        return result;
    }
}
