package com.augurit.agmobile.agwater5.common.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.model.MenuItem;
import com.augurit.agmobile.agwater5.common.view.adapter.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 快捷菜单View
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.view
 * @createTime 创建时间 ：2018/8/20
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/20
 * @modifyMemo 修改备注：
 */
public class ShortcutMenuView extends RelativeLayout {

    protected RecyclerView rv_menu;
    protected List<MenuItem> mMenuItems;
    protected MenuItemAdapter mAdapter;
    //菜单项数量
    protected int mSpanCount = 3;

    public ShortcutMenuView(Context context) {
        super(context);
        initView();
    }

    public ShortcutMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ShortcutMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.common_view_shortcut_menu, this);
        rv_menu = findViewById(R.id.rv_menu);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), mSpanCount) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_menu.setLayoutManager(layoutManager);
        mMenuItems = new ArrayList<>();
        mAdapter = new MenuItemAdapter(getContext(), mMenuItems, R.layout.common_view_shortcut_menu_item);
        rv_menu.setAdapter(mAdapter);
    }

    /**
     * 设置菜单项配置
     *
     * @param menuItems 菜单项
     */
    public void setMenuItems(List<MenuItem> menuItems) {
        mMenuItems.clear();
        mMenuItems.addAll(menuItems);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置角标
     *
     * @param position 位置
     * @param number   数字
     */
    public void setBadge(int position, int number) {
        mAdapter.setBadge(position, number);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置角标
     *
     * @param position 位置
     * @param text     文字
     */
    public void setBadge(int position, String text) {
        mAdapter.setBadge(position, text);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置行数
     *
     * @param spanCount
     */
    public void setSpanCount(int spanCount) {
        rv_menu.setLayoutManager(new GridLayoutManager(getContext(), mSpanCount = spanCount));
    }


    /**
     * 设置菜单项点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MenuItemAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

}
