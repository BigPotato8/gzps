package com.augurit.common.common.util;

import android.text.TextUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * com.augurit.agmobile.agwater5.common.utils
 * Created by sdb on 2019/4/15  15:58.
 * Desc：
 */

public class StringUtils {

    public static boolean isJson(String jsonStr) {
        if (TextUtils.isEmpty(jsonStr)) {
            return false;
        }
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(jsonStr);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }
}
