package com.bluealien99.simplecalc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class delKey extends TextView {

    public delKey(Context context) {
        super(context);
    }

    public delKey(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public delKey(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec);
    }
}
