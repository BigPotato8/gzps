package com.augurit.agmobile.agwater5.gcjs;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.patroldynamic.source
 * @createTime 创建时间 ：2018-09-07
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface GcjsContract {
    interface View {

        void showLoading();

        void hideLoading();

//        Context getContext();
    }

    interface Presenter {
        void loadApproveNews();

        void loadPshPatrolDynamicNews();

        void loadPatrolEventNums();

        void destroyDisposable();
    }
}
