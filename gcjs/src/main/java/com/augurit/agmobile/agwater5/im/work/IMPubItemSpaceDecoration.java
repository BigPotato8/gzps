package com.augurit.agmobile.agwater5.im.work;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.common.im.timchat.ui.adapter
 * @createTime 创建时间 ：2019/8/5
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class IMPubItemSpaceDecoration extends RecyclerView.ItemDecoration {
    private int leftAndRightSpace;
    private int upAndDownSpace;

    public IMPubItemSpaceDecoration(int leftAndRightSpace, int upAndDownSpace, int mRadius) {
        this.leftAndRightSpace = leftAndRightSpace;
        this.upAndDownSpace = upAndDownSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        outRect.left = leftAndRightSpace;
        outRect.right = leftAndRightSpace;
        outRect.top = upAndDownSpace;
        if(position == itemCount-1){
            outRect.bottom = upAndDownSpace;
        }
    }
}
