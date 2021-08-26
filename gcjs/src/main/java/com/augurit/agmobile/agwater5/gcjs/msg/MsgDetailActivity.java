package com.augurit.agmobile.agwater5.gcjs.msg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.msg.model.MsgBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.time.TimeUtil;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * com.augurit.agmobile.agwater5.gcjs.announce
 * Created by sdb on 2019/4/28  10:59.
 * Desc：
 */

public class MsgDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcjs_announce_detail_activity);


        MsgBean.RowsBean rowsBean = (MsgBean.RowsBean) getIntent().getSerializableExtra("data");

        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_author = findViewById(R.id.tv_author);
        TextView tv_date = findViewById(R.id.tv_date);
        TextView tv_content = findViewById(R.id.tv_content);
        TitleBar title_bar = findViewById(R.id.title_bar);
        title_bar.setTitleText("个人消息");

        if (rowsBean != null) {
            tv_title.setText(rowsBean.getContentTitle());
            tv_author.setText(rowsBean.getCreater());
            tv_date.setText(TimeUtil.getStringTimeYMDSFromDate(new Date(rowsBean.getCreateTime())));
            if (TextUtils.isEmpty(rowsBean.getContentText())) {
                tv_content.setText("暂无内容");
            } else {
                tv_content.setText(Html.fromHtml(rowsBean.getContentText()));
            }
            if ("0".equals(rowsBean.getIsRead())) {
                updateState(rowsBean.getRangeId());
            }
        }
    }

    private void updateState(String contentId) {
        Map<String, String> map = new HashMap<>();
        map.put("rangeId", contentId);
//        HttpUtil.getInstance("http://192.168.30.125:8087/").post(GcjsUrlConstant.UPDATE_MSG_STATE, map)
        HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.UPDATE_MSG_STATE, map)
                .subscribe(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                        }.getType());
                        if (apiResult.isSuccess()) {
                            setResult(RESULT_OK);
                        }
                    }
                });
    }

}
