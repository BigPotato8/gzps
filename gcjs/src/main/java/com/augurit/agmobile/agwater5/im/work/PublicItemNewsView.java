package com.augurit.agmobile.agwater5.im.work;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.im.work.model.PubItem;
import com.augurit.agmobile.agwater5.im.work.model.PubItemChild;
import com.augurit.agmobile.common.im.imsdk.ui.CircleImageView;

import com.augurit.common.common.util.TimeUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.common.im.timchat.ui.customview
 * @createTime 创建时间 ：2019/8/1
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class PublicItemNewsView extends RecyclerView implements BaseQuickAdapter.OnItemClickListener {
    private Context mContext;
    private PublicItemNewsAdapter mAdapter;
    private LayoutInflater mLayoutInflater;

    public PublicItemNewsView(Context context) {
        this(context, null);
    }

    public PublicItemNewsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublicItemNewsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    private View getHeaderView(OnClickListener listener, PubItem pubItem, PubItemChild pubItemChild, boolean showHeaderTitle) {
        View view = mLayoutInflater.inflate(R.layout.item_view_news_header, (ViewGroup) getParent(), false);
        LinearLayout llHeaderTitle = view.findViewById(R.id.ll_header_title);
        ImageView background = view.findViewById(R.id.iv_background);
        TextView title = view.findViewById(R.id.tv_title);
        title.setText(pubItemChild.getTitle());
        Glide.with(mContext)
                .load(R.drawable.banner3)
                .into(background);
        CircleImageView imageView = llHeaderTitle.findViewById(R.id.iv_icon_title);
        TextView tvMainTitle = llHeaderTitle.findViewById(R.id.tv_main_title);
        TextView tvMainTime = llHeaderTitle.findViewById(R.id.tv_main_time);
        Glide.with(mContext)
                .load(pubItem.getIconUrl())
                .into(imageView);
        tvMainTitle.setText(pubItem.getName());

        String yymmdd = TimeUtil.getStringTimeYYMMDD(new Date(pubItem.getTime()));
        String nowYYmmdd = TimeUtil.getStringTimeYYMMDD(new Date(System.currentTimeMillis()));
        if (yymmdd.equals(nowYYmmdd)) {
            tvMainTime.setText(TimeUtil.getStringTimeHm(new Date(pubItem.getTime())));
        } else {
            tvMainTime.setText(TimeUtil.getStringTimeYMDHm(new Date(pubItem.getTime())));
        }
        //tvMainTime.setText(pubItem.getTime() + "");
        if (showHeaderTitle) {
            llHeaderTitle.setVisibility(VISIBLE);
        } else {
            llHeaderTitle.setVisibility(GONE);
        }
        view.setOnClickListener(listener);
        return view;
    }

    public void setPublicItem(PubItem pubItem, boolean showHeaderTitle) {
        mAdapter = new PublicItemNewsAdapter(R.layout.item_news_view);
        setLayoutManager(new LinearLayoutManager(mContext));
        ArrayList<PubItemChild> publicItemDates = pubItem.getChild();
        ArrayList<PubItemChild> subItemChildren = new ArrayList<>();
        for (PubItemChild pubItemChild : publicItemDates) {
            if (pubItemChild.isAffiliate()) {
                subItemChildren.add(pubItemChild);
            }
            if (pubItemChild.isHeader()) {
                mAdapter.addHeaderView(getHeaderView(v -> {
                    if (onHeaderClickListener != null) {
                        onHeaderClickListener.OnHeaderClick(pubItemChild);
                    }
                }, pubItem, pubItemChild, showHeaderTitle));
            }
        }
        setAdapter(mAdapter);
        mAdapter.setNewData(subItemChildren);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (onChildItemClickListener != null) {
            onChildItemClickListener.OnChildItemClick((PubItemChild) adapter.getItem(position));
        }
    }

    public interface OnHeaderClickListener {
        void OnHeaderClick(PubItemChild pubItemChild);
    }

    public interface OnChildItemClickListener {
        void OnChildItemClick(PubItemChild pubItemChild);
    }

    public OnHeaderClickListener onHeaderClickListener;

    public OnChildItemClickListener onChildItemClickListener;

    public void setOnHeaderClickListener(OnHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }

    public void setOnChildItemClickListener(OnChildItemClickListener onChildItemClickListener) {
        this.onChildItemClickListener = onChildItemClickListener;
    }

}
