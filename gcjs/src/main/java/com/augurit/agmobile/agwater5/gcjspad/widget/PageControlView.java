package com.augurit.agmobile.agwater5.gcjspad.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.lib.ui.DisplayUtils;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.widget
 * @createTime 创建时间 ：2020/12/1
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class PageControlView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private int maxPage = 1;//最大页
    private int curPage = 1;//当前页
    private int buttonCount = 5;//按钮数
    private int lastButton = 0;//最后一个按钮对应的页码
    private int firstButton = 0;//第一个按钮对应的页码
    private int totalCount; //总页数
    public TextView upPage;//上一页
    public TextView downPage;//下一页

    public PageControlView(Context context) {
        this(context, null);
    }

    public PageControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.setBackgroundResource(R.color.white);
        this.setGravity(Gravity.RIGHT);
        initPageComposite();
    }

    private OnPageChangeListener mPageChangeListener;

    public interface OnPageChangeListener {
        void pageChanged(PageControlView pageControl,int numPerPage);
    }

    /**
     * @param tCount 总页数
     */
    public void setTotalPage(int tCount) {
        totalCount =  tCount;
        initPageComposite();
    }

    /**
     * 创建view
     * @return
     */
    private TextView createView() {
        TextView page = new TextView(context);
        page.setBackgroundResource(R.drawable.selector_btn_round5_unselected);
        page.setPadding(DisplayUtils.dip2px(getContext(),10), 0, DisplayUtils.dip2px(getContext(), 10), 0);
        page.setGravity(Gravity.CENTER);
        LayoutParams layoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, DisplayUtils.dip2px(getContext(),30));
        page.setMinWidth(DisplayUtils.dip2px(getContext(), 30));
        layoutParam.setMargins(0, 0, DisplayUtils.dip2px(getContext(),5), 0);
        page.setLayoutParams(layoutParam);
        return page;
    }

    /**
     * 创建中间页码view
     * @param page
     */
    private void createView(int page) {
        TextView countPage = createView();
        countPage.setText(page + "");
        countPage.setTag(page);
        countPage.setOnClickListener(this);
        this.addView(countPage);
    }
    /**
     * 创建上一页view
     */
    private void createPrevious() {
        upPage = createView();
        upPage.setText(getResources().getString(R.string.gcjs_page_uppage));
        upPage.setOnClickListener(this);
        this.addView(upPage, 0);
    }
    /**
     * 创建下一页view
     */
    private void createNext() {
        downPage = createView();
        downPage.setText(getResources().getString(R.string.gcjs_page_downpage));
        downPage.setOnClickListener(this);
        this.addView(downPage);
    }
    /**
     * n个页 创建第一个view
     */
    private void first() {
        TextView first = createView();
        first.setText("1");
        first.setOnClickListener(this);
        this.addView(first);
    }

    /**
     * n个页 创建末页view
     */
    private void last() {
        TextView first = createView();
        first.setText(totalCount+"");
        first.setOnClickListener(this);
        this.addView(first);
    }
    /**
     * n个页 创建...
     */
    private void middleView() {
        TextView first = new TextView(context);
        first.setText("...");
        first.setPadding(DisplayUtils.dip2px(getContext(),10), 0, DisplayUtils.dip2px(getContext(),10), 0);
        first.setGravity(Gravity.CENTER);
        first.setOnClickListener(this);
        this.addView(first);
    }


    public void initPageComposite() {
        int temp = maxPage;
        maxPage = totalCount % 1 == 0 ? totalCount / 1 : totalCount / 1 + 1;

        if (temp != maxPage || curPage >= 1) {
            createAllView();
        }
        if (maxPage == 0) {
            removeAllViews();
            return;
        }
        setSelectView(curPage);
        setActionStatus(curPage);
    }

    /**
     * 当前页码为1的时候上一页不可点击
     * 当前页码为末页的时候下一页不可点击
     * @param curPage
     */
    private void setActionStatus(int curPage) {
        if (curPage > 1) {
            upPage.setEnabled(true);
        }else {
            upPage.setEnabled(false);
        }
        if(totalCount > curPage){
            downPage.setEnabled(true);
        }else {
            downPage.setEnabled(false);
        }
    }
    private void setBtnGroup() {
        int n = maxPage / buttonCount;
        if (n == 0) {
            //只有一组
            firstButton = 1;
            lastButton = maxPage;
        } else {
            int i = curPage / buttonCount;
            //有n组
            firstButton = i * buttonCount + 1;
            if (firstButton > curPage) {
                firstButton = (i - 1) * buttonCount + 1;
            }
            lastButton = (firstButton - 1) + buttonCount;
            if (lastButton > maxPage && lastButton > 1) {
                lastButton = maxPage;
            }

        }
    }

    private void createAllView() {
        setBtnGroup();
        this.removeAllViews();
        this.setPadding(DisplayUtils.dip2px(getContext(),10), DisplayUtils.dip2px(getContext(),20), DisplayUtils.dip2px(getContext(),10), DisplayUtils.dip2px(getContext(),20));
        createPrevious();
        if((firstButton >= buttonCount ||curPage == buttonCount)&& totalCount > buttonCount){
            firstButton = curPage - 2;
            lastButton = curPage + 2;
        }
        if(firstButton > 1 && totalCount > buttonCount){
            first();
            middleView();
        }
        while (firstButton <= lastButton && firstButton <= totalCount) {
            createView(firstButton);
            firstButton++;
        }
        if(lastButton < totalCount){
            middleView();
            last();
        }
        createNext();
    }

    @Override
    public void onClick(View view) {
        if (mPageChangeListener == null) return;
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            String txt = tv.getText().toString();
            if (txt.equalsIgnoreCase(getResources().getString(R.string.gcjs_page_uppage))) {
                curPage -= 1;
                curPage = curPage >= 1 ? curPage : 1;
                setSelectView(curPage);
                mPageChangeListener.pageChanged(this, curPage);
                initPageComposite();
                return;
            }
            if (txt.equalsIgnoreCase(getResources().getString(R.string.gcjs_page_downpage))) {
                curPage += 1;
                curPage = curPage <= maxPage ? curPage : maxPage;
                setSelectView(curPage);
                mPageChangeListener.pageChanged(this,curPage);
                initPageComposite();
                return;
            }
            if(txt.equalsIgnoreCase(String.valueOf(1))){
                curPage = 1;
                setSelectView(curPage);
                mPageChangeListener.pageChanged(this,curPage);
                initPageComposite();
                return;
            }
            if(txt.equalsIgnoreCase(String.valueOf(totalCount))){
                curPage = totalCount;
                setSelectView(curPage);
                mPageChangeListener.pageChanged(this,curPage);
                initPageComposite();
                return;
            }
        }
        if (view.getTag() != null) {
            Object tag = view.getTag();
            curPage = Integer.parseInt(tag.toString());
            mPageChangeListener.pageChanged(this,curPage);
            setSelectView(curPage);
            initPageComposite();
        }
    }

    public void setSelectView(int n) {
        for (int i = 0; i < this.getChildCount(); i++) {
            View v = this.getChildAt(i);
            if (v.getTag() != null) {
                int tag = Integer.parseInt(v.getTag().toString());
                if (tag == n) {
                    v.setSelected(true);
                    v.setBackgroundResource(R.drawable.selector_btn_round5_selected);
                } else {
                    v.setSelected(false);
                    v.setBackgroundResource(R.drawable.selector_btn_round5_unselected);
                }
            }
        }
    }

    /**
     * 设置分页监听事件
     */
    public void setPageChangeListener(OnPageChangeListener pageChangeListener) {
        this.mPageChangeListener = pageChangeListener;
    }

}
