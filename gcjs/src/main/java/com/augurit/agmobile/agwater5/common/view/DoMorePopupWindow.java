package com.augurit.agmobile.agwater5.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.view.videopicker.utils.ScreenUtils;

import java.util.List;

public class DoMorePopupWindow extends PopupWindow {

    private final View bindView;
    RecyclerView rv_popup;
    private DoMorePopupAdapter adapter;

    public DoMorePopupWindow(Context context, View bindView) {
        super(context);
        this.bindView = bindView;
        //todo 获取屏幕宽度，app版屏幕宽度2/5，pad版固定240dp
        int screenWidth = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽
        if (isPad(context)) {
            setWidth(ScreenUtils.dp2px(context, 240));
        } else {
            setWidth(screenWidth * 2 / 5);
        }
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popup_operator,
                null, false);
        setContentView(contentView);
        initView(context, contentView);
    }

    private void initView(Context context, View contentView) {
        rv_popup = contentView.findViewById(R.id.rv_popup);
        adapter = new DoMorePopupAdapter(context);
        rv_popup.setAdapter(adapter);
        rv_popup.setLayoutManager(new LinearLayoutManager(context));
    }

    public void setButtons(List<String> list, DoMorePopupAdapter.ItemClickListener listener) {
//        操作按钮列表
        adapter.setList(list);
        adapter.setItemClickListener(listener);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void show() {
        showAsDropDown(bindView);
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
