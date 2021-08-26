package com.augurit.agmobile.agwater5.im.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.augurit.agmobile.common.im.common.source.IShareClickUtil;
import com.augurit.agmobile.common.im.common.source.JumpUIManager;
import com.augurit.agmobile.common.im.timchat.model.CustomMessageType;
import com.augurit.agmobile.common.im.timchat.model.message.CustomJsonMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.share
 * @createTime 创建时间 ：2019/7/24
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MyShareClickUtil implements IShareClickUtil {

    @Override
    public void performShareClick(Context context, CustomJsonMessage customJsonMessage) {
        if(customJsonMessage.getMsgType() == CustomMessageType.TYPE_WEB){
            String webSharePath = JumpUIManager.getInstance().getWebSharePath();
            if(!TextUtils.isEmpty(webSharePath)){
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(context,
                        webSharePath));
                intent.putExtra("WEBVIEW_TITLE", customJsonMessage.getTitle());
                intent.putExtra("WEBVIEW_URL_PATH", customJsonMessage.getUrl());
                if(!customJsonMessage.isPreShare()){
                    intent.putExtra("WEBVIEW_SHARE_BUTTON_SHOW",true);
                }
                context.startActivity(intent);
            }
        }else if(customJsonMessage.getMsgType() == CustomMessageType.TYPE_FORM){
            String formSharePath = customJsonMessage.getUrl();
            String dataStr = customJsonMessage.getDataStr();
            try {
                JSONObject jsonObject = new JSONObject(dataStr);
                if(!TextUtils.isEmpty(formSharePath) && !customJsonMessage.isPreShare()){
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(context,
                            formSharePath));
                    intent.putExtra("isSeriesApproval", jsonObject.getString("isSeriesApproval"));
                    intent.putExtra("applyinst", jsonObject.getString("applyinst"));
                    intent.putExtra("iteminst", jsonObject.getString("iteminst"));
                    intent.putExtra("taskId", jsonObject.getString("taskId"));
                    context.startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
