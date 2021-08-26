package com.augurit.common.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.augurit.common.R;
import com.augurit.common.common.model.MenuItem;
import com.augurit.common.common.view.adapter.AutoMenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 自适应菜单控件
 */
public class AutoMenuView extends RelativeLayout {

    protected Context mContext;
    protected RecyclerView rv_menu;
    protected List<MenuItem> mMenuItems;
    protected AutoMenuItemAdapter mAdapter;
    protected int mSpanCount = 2;
    protected int mItem_layout = -1;
    protected float mParting_line_size = 0;
    protected int mOrientation = 0;
    protected int mParting_line_color = R.color.black_eee;

    public AutoMenuView(Context context) {
        this(context,null);
    }

    public AutoMenuView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    @SuppressLint("ResourceAsColor")
    public AutoMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoMenuView);
        mSpanCount = a.getInt(R.styleable.AutoMenuView_span_count, 2);
        mItem_layout = a.getResourceId(R.styleable.AutoMenuView_item_layout, -1);
        mOrientation = a.getResourceId(R.styleable.AutoMenuView_line_orientation, QuickItemDecoration.ALL);
        mParting_line_size = a.getDimension(R.styleable.AutoMenuView_parting_line_size, 0);
        mParting_line_color = a.getColor(R.styleable.AutoMenuView_parting_line_color, getResources().getColor(R.color.black_eee));
        a.recycle();
        initView();
    }

    protected void initView() {
        if(mItem_layout == -1) return;
        LayoutInflater.from(getContext()).inflate(R.layout.common_view_auto_menu, this);
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
        if(mParting_line_size>0){
            rv_menu.addItemDecoration(new QuickItemDecoration(
                    mContext, mOrientation, (int)mParting_line_size, mParting_line_color,mSpanCount));

        }
        mMenuItems = new ArrayList<>();
        mAdapter = new AutoMenuItemAdapter(getContext(), mMenuItems, mItem_layout);
        rv_menu.setAdapter(mAdapter);
    }

    /**
     * 设置菜单项配置
     * @param menuItems 菜单项
     */
    public void setMenuItems(List<MenuItem> menuItems) {
        mMenuItems.clear();
        mMenuItems.addAll(menuItems);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置角标
     * @param position 位置
     * @param number 数字
     */
    public void setBadge(int position, int number) {
        mAdapter.setBadge(position, number);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置角标
     * @param position 位置
     * @param text 文字
     */
    public void setBadge(int position, String text) {
        mAdapter.setBadge(position, text);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置行数
     * @param spanCount
     */
    public void setSpanCount(int spanCount) {
        rv_menu.setLayoutManager(new GridLayoutManager(getContext(), mSpanCount = spanCount));
    }

    /**
     * 设置背景颜色
     * @param color 颜色
     */
    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        rv_menu.setBackgroundColor(color);
    }

    /**
     * 设置图标文字颜色
     * @param color 颜色
     */
    public void setIconTextColor(int color) {
        mAdapter.setIconTextColor(color);
    }


    /**
     * 设置菜单项点击监听
     * @param listener 监听
     */
    public void setOnItemClickListener(AutoMenuItemAdapter.OnItemClickListener listener){
        mAdapter.setOnItemClickListener(listener);
    }
}
