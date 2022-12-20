package com.chuanyunglin.android.touchevent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}