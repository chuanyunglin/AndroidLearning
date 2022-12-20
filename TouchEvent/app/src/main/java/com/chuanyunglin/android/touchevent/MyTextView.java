package com.chuanyunglin.android.touchevent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MyTextView extends TextView {
    private static final String TAG = MyTextView.class.getSimpleName();

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        setOnTouchListener(new OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent ev) {
//                Log.w(TAG, "onTouch, ev = " + MotionEvent.actionToString(ev.getAction()));
//                return true;
//            }
//        });
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
