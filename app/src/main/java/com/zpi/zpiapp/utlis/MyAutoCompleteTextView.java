package com.zpi.zpiapp.utlis;

import android.content.Context;
import android.util.AttributeSet;



public class MyAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {

    public MyAutoCompleteTextView(Context context) {
        super(context);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // Allows you to show options on empty string.
    public boolean enoughToFilter() {
        return true;
    }
    // Override to always send an empty string.
    @Override
    protected void performFiltering(CharSequence text, int keyCode) {
        if( text.length()==0 )
            super.performFiltering("", 0);
        else super.performFiltering(text,keyCode);
    }
}