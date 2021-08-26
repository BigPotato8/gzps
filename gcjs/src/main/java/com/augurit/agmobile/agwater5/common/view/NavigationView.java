package com.augurit.agmobile.agwater5.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;

import java.util.LinkedHashMap;

/**
 * 政务版-申报详情-审批详情
 */

public class NavigationView extends LinearLayout {

    private String[] naviArr;
    private LinkedHashMap<Integer, View> viewMap = new LinkedHashMap<>();
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private int stepIndex;//进行到第几步，第一步为0
    private int currentPosition;//选择第几步

    public void setStepIndex(int stepIndex) {
        this.stepIndex = stepIndex;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

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
        currentPosition = position;
        for (int key : viewMap.keySet()) {
            View view = viewMap.get(key);
            TextView tv_dot = view.findViewById(R.id.tv_dot);
            TextView tv_title = view.findViewById(R.id.tv_title);
            View tv_line_left = view.findViewById(R.id.tv_line_left);
            View tv_line_right = view.findViewById(R.id.tv_line_right);

            if (key < stepIndex) {
                tv_dot.setBackgroundResource(R.drawable.view_navigator_gcjs_bg_finish_selector);
                tv_dot.setText("");
            } else {
                tv_dot.setBackgroundResource(R.drawable.view_navigator_gcjs_bg_selector);
            }

            if (key <= stepIndex) {
                tv_title.setSelected(true);
            } else {
                tv_title.setSelected(false);
            }

            if (key <= position) {
                tv_line_left.setSelected(true);
                tv_line_right.setSelected(false);
                if (viewMap.containsKey(key - 1)) {
                    viewMap.get(key - 1).findViewById(R.id.tv_line_right).setSelected(true);
                }
            } else {
                tv_line_left.setSelected(false);
                tv_line_right.setSelected(false);
            }
            if (key == position) {//选中阶段粗体
                tv_dot.setSelected(true);
            } else {
                tv_dot.setSelected(false);
            }


        }
    }

    private void initView(Context context) {
        if (naviArr != null) {
            for (int i = 0; i < naviArr.length; i++) {
                View view = View.inflate(context, R.layout.view_navigation_gcjs_item, null);
                TextView tv_dot = view.findViewById(R.id.tv_dot);
                TextView tv_title = view.findViewById(R.id.tv_title);
                View tv_line_left = view.findViewById(R.id.tv_line_left);
                View tv_line_right = view.findViewById(R.id.tv_line_right);
                if (i == 0) {
                    tv_line_left.setVisibility(GONE);
                    tv_line_right.setVisibility(VISIBLE);
                } else if (i == naviArr.length - 1) {
                    tv_line_left.setVisibility(VISIBLE);
                    tv_line_right.setVisibility(GONE);
                }

                tv_dot.setText("0" + (i + 1));
                tv_title.setText(naviArr[i]);
                int finalI = i;
                tv_dot.setOnClickListener(v -> {
                    if (onItemClickListener != null && currentPosition != finalI) {
                        onItemClickListener.onItemClick(finalI, view);
                    }
                    setCurrentState(finalI);
                });
                tv_title.setOnClickListener(v -> {
                    if (onItemClickListener != null && currentPosition != finalI) {
                        onItemClickListener.onItemClick(finalI, view);
                    }
                    setCurrentState(finalI);
                });

                viewMap.put(i, view);

                view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));

                addView(view);
            }
        }

        setCurrentState(0);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }


}
