package com.augurit.common.common.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.augurit.agmobile.common.lib.log.LogUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.DefaultProgressDialogMaker;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogMaker;
import com.augurit.agmobile.common.lib.validate.StringUtil;

/**
 * 等待进度条展示工具类
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.common.lib.ui
 * @createTime 创建时间 ：18/3/30
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：18/3/30
 * @modifyMemo 修改备注：
 */

public class ProgressDialogUtil {


    private static final String TAG = ProgressDialogUtil.class.getSimpleName();

    private static Dialog progressDialog;

    private static int level;//等待框展示层级，只有level=0的时候才能关闭progressDialog

    private static DefaultProgressDialogMaker defaultProgressDialogMaker = new DefaultProgressDialogMaker();//对话框制造者

    private static ProgressDialogMaker dialogMaker;


    public static ProgressDialogMaker getDialogMaker() {

        return dialogMaker;
    }

    /**
     * 设置对话框构建类
     *
     * @param dialogMaker 对话框构建者
     */
    public static void setDialogMaker(ProgressDialogMaker dialogMaker) {

        ProgressDialogUtil.dialogMaker = dialogMaker;
    }

    /**
     * 设置等待对话框提醒内容
     */
    public static void setTipContent(String content) {

        defaultProgressDialogMaker.setContent(content);
    }

    /**
     * 设置自定义的对话框视图
     */
    public static void setCustomView(int customViewLayout) {

        defaultProgressDialogMaker.setCustomViewLayout(customViewLayout);
    }

    /**
     * 设置对话框样式
     */
    public static void setDialogStyle(int dialogStyle) {

        defaultProgressDialogMaker.setStyle(dialogStyle);
    }

    public static synchronized void showProgressDialog(Context context,boolean cancelable) {

        showProgressDialog(context, null,cancelable);
    }


    public static synchronized void showProgressDialog(Context context, String content,boolean cancelable) {

        if (context instanceof Activity) {


            if (progressDialog == null || !context.equals(progressDialog.getOwnerActivity()) || !progressDialog.isShowing()) {

                dismissProgressDialog();

                if (StringUtil.isEmpty(content)) {

                    content = context.getResources().getString(com.augurit.agmobile.common.lib.R.string.progressdialog_please_wait);
                }
                if (dialogMaker != null) {

                    progressDialog = dialogMaker.makeDialog(context);

                } else {

                    defaultProgressDialogMaker.setContent(content);

                    progressDialog = defaultProgressDialogMaker.makeDialog(context);
                }

                progressDialog.setOwnerActivity((Activity) context);

                progressDialog.setCancelable(cancelable);

                progressDialog.setCanceledOnTouchOutside(cancelable);

                progressDialog.show();
            }

            level++;

        }
    }

    public static synchronized void showProgressDialog(Context context, String content,boolean cancelable,boolean outsideCancelable) {

        if (context instanceof Activity) {


            if (progressDialog == null || !context.equals(progressDialog.getOwnerActivity()) || !progressDialog.isShowing()) {

                dismissProgressDialog();

                if (StringUtil.isEmpty(content)) {

                    content = context.getResources().getString(com.augurit.agmobile.common.lib.R.string.progressdialog_please_wait);
                }
                if (dialogMaker != null) {

                    progressDialog = dialogMaker.makeDialog(context);

                } else {

                    defaultProgressDialogMaker.setContent(content);

                    progressDialog = defaultProgressDialogMaker.makeDialog(context);
                }

                progressDialog.setOwnerActivity((Activity) context);

                progressDialog.setCancelable(cancelable);

                progressDialog.setCanceledOnTouchOutside(outsideCancelable);

                progressDialog.show();
            }

            level++;

        }
    }

    /**
     * 消除对话框，不用担心多个对话框同时出现，只有当showProgressDialog的次数和dismissProgressDialog调用次数相同时，ProgressDialog才会消除
     */
    public static synchronized void dismissProgressDialog() {

        level--;

        if (level < 0) {

            level = 0;
        }
        if (progressDialog != null && level == 0 && progressDialog.isShowing() && !progressDialog.getOwnerActivity().isFinishing()) {

            try {
                progressDialog.dismiss();

            } catch (Exception e) {

                LogUtil.e(TAG, e.getMessage());

            } finally {

                progressDialog = null;
            }
        }
    }

    public static boolean isShowing(){
        if (progressDialog ==null) {
            return false;
        }else {
            return progressDialog.isShowing();
        }
    }

    public static void setDismissListener(DialogInterface.OnDismissListener dismissListener){
        progressDialog.setOnDismissListener(dismissListener);
    }

    public static void setCancelListener(DialogInterface.OnCancelListener dismissListener){
        progressDialog.setOnCancelListener(dismissListener);
    }

    public static void removeDismissListener(){
        if (progressDialog != null){
            progressDialog.setOnDismissListener(null);
        }
    }

    /**
     * 一般情况下不要调用此方法，此方法为一次性全部消除所有对话框层级．推荐调用dismissProgressDialog按层级进行消除等待框
     */
    public static void dismissAll() {

        if (progressDialog != null && progressDialog.isShowing() && !progressDialog.getOwnerActivity().isFinishing()) {

            try {

                progressDialog.dismiss();

            } catch (Exception e) {

                LogUtil.e(TAG, e.getMessage());

            } finally {

                /**
                 * 复位
                 */
                level = 0;

                progressDialog = null;
            }

        }
    }
}
