package com.augurit.agmobile.agwater5.gcjspad.mine.notice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.common.common.util.TimeUtil;

import java.util.Date;

public class NoticeDetailFragment extends BasePadFragment {

    Announce.RowsBean rowsBean;

    public static NoticeDetailFragment getInstance(Announce.RowsBean rowsBean){
        NoticeDetailFragment fragment = new NoticeDetailFragment();
        fragment.rowsBean = rowsBean;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_detail_pad,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.iv_back).setOnClickListener(v ->{
            removeFragment(this);
        });
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_author = view.findViewById(R.id.tv_author);
        TextView tv_date = view.findViewById(R.id.tv_date);
        TextView tv_content = view.findViewById(R.id.tv_content);

        if (rowsBean != null) {
            tv_title.setText(rowsBean.getContentTitle());
            tv_author.setText(rowsBean.getContentAuthor());
            tv_date.setText(TimeUtil.getStringTimeYMDFromDate(new Date(rowsBean.getPublishTime())));
            if (TextUtils.isEmpty(rowsBean.getContentText())) {
                tv_content.setText("暂无内容");
            } else {
                tv_content.setText(Html.fromHtml(rowsBean.getContentText()));
                tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
            }
        }
    }
}
