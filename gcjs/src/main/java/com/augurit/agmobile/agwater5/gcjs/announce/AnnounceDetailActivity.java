package com.augurit.agmobile.agwater5.gcjs.announce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.common.lib.time.TimeUtil;

import java.util.Date;

/**
 * com.augurit.agmobile.agwater5.gcjs.announce
 * Created by sdb on 2019/4/28  10:59.
 * Desc：
 */

public class AnnounceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcjs_announce_detail_activity);


        Announce.RowsBean rowsBean = (Announce.RowsBean) getIntent().getSerializableExtra("data");

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_author = findViewById(R.id.tv_author);
        TextView tv_date = findViewById(R.id.tv_date);
        TextView tv_content = findViewById(R.id.tv_content);

        if (rowsBean != null) {
            tv_title.setText(rowsBean.getContentTitle());
            tv_author.setText(rowsBean.getContentAuthor());
            tv_date.setText(TimeUtil.getStringTimeYMDSFromDate(new Date(rowsBean.getPublishTime())));
            if (TextUtils.isEmpty(rowsBean.getContentText())) {
                tv_content.setText("暂无内容");
            } else {
                tv_content.setText(Html.fromHtml(rowsBean.getContentText()));
            }
        }
    }
}
