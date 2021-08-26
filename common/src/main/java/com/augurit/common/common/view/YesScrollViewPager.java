package com.augurit.common.common.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * 地图不可左右滑动的ViewPager
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.view
 * @createTime 创建时间 ：2018/08/23
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/08/23
 * @modifyMemo 修改备注：
 */

public class YesScrollViewPager extends ViewPager {

    public YesScrollViewPager(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public YesScrollViewPager(Context context) {
        super(context);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v.getClass().getName().equals("com.baidu.mapapi.map.MapView")
                || v.getClass().getName().equals("com.esri.android.map.MapView")){
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
