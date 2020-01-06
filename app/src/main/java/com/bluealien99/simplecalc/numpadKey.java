package com.bluealien99.simplecalc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class numpadKey extends TextView {

    public numpadKey(Context context) {
        super(context);
    }

    public numpadKey(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public numpadKey(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
