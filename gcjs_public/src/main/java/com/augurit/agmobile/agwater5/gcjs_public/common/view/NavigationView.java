package com.augurit.agmobile.agwater5.gcjs_public.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.augurit.agmobile.agwater5.R;
import java.util.LinkedHashMap;

/**
 * com.augurit.agmobile.agwater5.common.view
 * Created by sdb on 2019/5/13  9:56.
 * Desc：
 */

public class NavigationView extends LinearLayout {

    private String[] naviArr;
    private LinkedHashMap<Integer, View> viewMap = new LinkedHashMap<>();
    private Context mContext;

    public NavigationView(Context context) {
        super(context);
        mContext = context;
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }


    public void setNaviTextArrs(String[] naviArr) {
        this.naviArr = naviArr;
        initView(mContext);
    }

    public void setCurrentState(int position) {
        for (int key : viewMap.keySet()) {
            View view = viewMap.get(key);
            TextView tv_dot = view.findViewById(R.id.tv_dot);
            View tv_line_left = view.findViewById(R.id.tv_line_left);
            View tv_line_right = view.findViewById(R.id.tv_line_right);
            if (key <= position) {
                tv_dot.setSelected(true);
                tv_line_left.setSelected(true);
                tv_line_right.setSelected(false);
                if (viewMap.containsKey(key - 1)) {
                    viewMap.get(key - 1).findViewById(R.id.tv_line_right).setSelected(true);
                }
            } else {
                tv_line_left.setSelected(false);
                tv_dot.setSelected(false);
                tv_line_right.setSelected(false);
            }

        }
    }

    private void initView(Context context) {
        if (naviArr != null) {
            for (int i = 0; i < naviArr.length; i++) {
                View view = View.inflate(context, R.layout.view_navigation_gcjs_public_item, null);
                TextView tv_dot = view.findViewById(R.id.tv_dot);
                TextView tv_title = view.findViewById(R.id.tv_title);
                View tv_line_left = view.findViewById(R.id.tv_line_left);
                View tv_line_right = view.findViewById(R.id.tv_line_right);
                if (i == 0) {
                    tv_line_left.setVisibility(GONE);
                } else if (i == naviArr.length - 1) {
                    tv_line_right.setVisibility(GONE);
                }

                tv_dot.setText("0" + (i + 1));
                tv_title.setText(naviArr[i]);
                viewMap.put(i, view);

                view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));

                addView(view);
            }
        }

        setCurrentState(0);
    }


}
