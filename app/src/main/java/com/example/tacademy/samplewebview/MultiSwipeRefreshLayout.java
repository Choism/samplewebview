package com.example.tacademy.samplewebview;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tacademy on 2016-08-02.
 */
public class MultiSwipeRefreshLayout extends SwipeRefreshLayout {
    public MultiSwipeRefreshLayout(Context context) {
        super(context);
    }

    public MultiSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (scrollChild != null) {
            return scrollChild.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }

    View scrollChild;
    public void setScrollChild(int resId) {
        scrollChild = findViewById(resId);
    }
}