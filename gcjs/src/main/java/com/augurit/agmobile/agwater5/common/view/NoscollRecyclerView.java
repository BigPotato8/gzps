package com.augurit.agmobile.agwater5.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 默认展开，嵌套scrollview
 * Created by long on 2017/11/23.
 */

public class NoscollRecyclerView extends RecyclerView {
    public NoscollRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoscollRecyclerView(Context context) {
        super(context);
    }

    public NoscollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}