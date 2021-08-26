package com.augurit.agmobile.agwater5.im;

import android.content.Context;

import com.augurit.agmobile.common.lib.file.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * IM相关常量
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im
 * @createTime 创建时间 ：2019-04-24
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class IMConstant {

    public static final boolean IS_ENABLED = true;
    public static final boolean IS_TEST = false;

    public static Map<String, String> mTestUsers;

    public static void initTestUsers(Context context) {
        mTestUsers = new LinkedHashMap<>();
        String json = FileUtils.readAssetsFile(context, "im_test_users.json");
        ArrayList<User> users = new Gson().fromJson(json, new TypeToken<ArrayList<User>>(){}.getType());
        for (User user : users) {
            mTestUsers.put(user.id, user.userSig);
        }
    }

    public static ArrayList<String> getTestUserIds() {
        if(!IS_TEST){
            return new ArrayList<>();
        }
        return new ArrayList<>(mTestUsers.keySet());
    }

    public static String getTestUserSig(String userId) {
        return mTestUsers.get(userId);
    }

    public static boolean isTestUser(String userId) {
        return mTestUsers.containsKey(userId);
    }

    private static class User {
        String id;
        String name;
        String userSig;
    }

}
