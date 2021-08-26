package com.augurit.common.im.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.augurit.common.R;

import java.util.Locale;

import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * @author 创建人 ：taoerxiang
 * @version zs.0
 * @package 包名 ：com.augurit.agmobile.gzps.communication
 * @createTime 创建时间 ：2017-11-04
 * @modifyBy 修改人 ：taoerxiang
 * @modifyTime 修改时间 ：2017-11-04
 * @modifyMemo 修改备注：
 */

public class ConversationActivity extends FragmentActivity {
    private String title;
    private Conversation.ConversationType mConversationType;
    private Button back_left;
    private Button btn_right;
    private TextView title_tv;
    private Intent intent;

    private String targetId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        intent = getIntent();
        initView();
        initData();


    }

    private void initData() {
        targetId = intent.getData().getQueryParameter("targetId");
        title = intent.getData().getQueryParameter("title");
        setActionBarTitle(mConversationType, targetId);
    }

    private void initView() {

        back_left = findViewById(R.id.left_btn);
        btn_right = findViewById(R.id.right_btn);
        title_tv = findViewById(R.id.title_tv);
        mConversationType = Conversation.ConversationType.valueOf(intent.getData()
                .getLastPathSegment().toUpperCase(Locale.US));
        if (mConversationType.equals(Conversation.ConversationType.GROUP)) {
            btn_right.setBackground(getResources().getDrawable(R.drawable.group_icon));
        } else if (mConversationType.equals(Conversation.ConversationType.PRIVATE) | mConversationType.equals(Conversation.ConversationType.PUBLIC_SERVICE) | mConversationType.equals(Conversation.ConversationType.DISCUSSION)) {
            btn_right.setBackground(null);
        }
//        btn_right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ConversationActivity.this, GroupDetailActivity.class);
//                intent.putExtra("groupId", targetId);
//                intent.putExtra("groupName", title);
//                startActivity(intent);
//            }
//        });
    }

    /**
     * 设置会话页面 Title
     *
     * @param conversationType 会话类型
     * @param targetId         目标 Id
     */
    private void setActionBarTitle(Conversation.ConversationType conversationType, String targetId) {

        title_tv.setText(title);


    }

    /**
     * 设置私聊界面 ActionBar
     */
    private void setPrivateActionBar(String targetId) {
        if (!TextUtils.isEmpty(title)) {
            if (title.equals("null")) {
                if (!TextUtils.isEmpty(targetId)) {
                    UserInfo userInfo = RongUserInfoManager.getInstance().getUserInfo(targetId);
                    if (userInfo != null) {
                        setTitle(userInfo.getName());
                    }
                }
            } else {
                title_tv.setText(title);
            }

        } else {
            title_tv.setText(targetId);
        }
    }

    public void conversationBack(View view) {
        finish();
    }
}
