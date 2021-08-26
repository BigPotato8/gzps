package com.augurit.agmobile.agwater5.gcjs.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClfjBean;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.common.Callback2;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @description 附件下载或删除
 * @date: 20190416
 * @author: xieruibin
 */
public class FilePreviewUtil {

    Disposable deleteDisposable;

    public void downFile(FileBean item, Context ctx) {
        if (item == null || ctx == null) {
            return;
        }
        String downloadPath = new FilePathUtil(ctx).getDownloadFilePath();
        if (item.isUploaded()) {
            String dPath = downloadPath + "/" + item.getName();
            File file = new File(dPath);
            if (file.exists() && file.isFile()) {
                //直接打开文件
                previewFile(item, dPath,ctx);
            } else {
                //下载文件，并打开文件
                ProgressDialogUtil.showProgressDialog(ctx, "正在下载附件...", false);
                AuthDownloadManager.getInstance()
                        .serverUrl(AwUrlManager.serverUrl())
                        .downLoadPath(GcjsUrlConstant.URL_FILE_DONMLOAD + "?detailId=" + item.getId())
                        .savePath(downloadPath)
                        .saveName(item.getName())
                        .Download(new AuthDownloadListener() {
                            @Override
                            public void success() {
                                ProgressDialogUtil.dismissProgressDialog();
                                //直接打开文件
                                previewFile(item, dPath,ctx);
                            }

                            @Override
                            public void failed() {
                                //跳出对话框，提示无证书
                                ProgressDialogUtil.dismissProgressDialog();
                                ToastUtil.shortToast(ctx, "文件下载失败");
                            }
                        });
            }
        } else {
            String path = item.getPath();
            previewFile(item, path,ctx);
        }
    }


    private void previewFile(FileBean item, String dPath,Context ctx) {
        String tempPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //如果是图片
        if (dPath.toLowerCase().endsWith(".png") || dPath.toLowerCase().endsWith(".jpeg") || dPath.toLowerCase().endsWith(".jpg")) {
            ArrayList<String> imagePaths = new ArrayList<>();
            imagePaths.add(dPath);
            //Intent intent = ImageViewActivity.newIntent(AccessoryActivity.this, 0, imagePaths);
            Intent intent = WatchImageOrPdfActivity.newIntent(ctx, imagePaths);
            ctx.startActivity(intent);
            return;
        }
//        else if(dPath.toLowerCase().endsWith(".docx") ){
//            try {
//                String mineType = FileUtils.getMimeType(dPath);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                FileUtils.setFlags(intent);
//                Uri uri = FileUtils.getUri(dPath, ctx);
//                intent.setDataAndType(uri, mineType);
//                ctx.startActivity(intent);
//            } catch (Exception e) {
//              ToastUtil.shortToast(ctx, ctx.getString(R.string.validate_check_file_open_fail));
//            }
//
//            return;
//        }
        FileOpenViewActivity.getIntent(ctx, dPath, tempPath, item.getName());
    }


    public void deleteFile(Context ctx, RecyclerView.Adapter mAdapter, int position, ClfjBean.FilesBean data, Callback2<ApiResult> callback2){
        AlertDialog dialog = new AlertDialog.Builder(ctx)
                .setTitle("提示:")
                .setMessage("是否删除")
                .setNegativeButton("取消", (dialog1, which) -> {
                })
                .setPositiveButton("确定", (dialog12, which) -> {
                    ProgressDialogUtil.showProgressDialog(ctx, "正在删除...", false);
                    ProgressDialogUtil.setCancelListener(dialog13 -> {
                        if (deleteDisposable!=null) {
                            deleteDisposable.dispose();
                        }
                    });
                    Map<String, String> map = new HashMap<>();
                    map.put("dirIds", "");
                    map.put("fileIds", data.getDetailId());
                    deleteDisposable = HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.DELETE_PWPF_MATERIAL_ATTACHMENT, map)
                            .onErrorReturnItem("")
                            .subscribe(s -> {
                                ProgressDialogUtil.dismissProgressDialog();
                                if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                                    ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                                    }.getType());
                                    if (apiResult != null && apiResult.isSuccess()) {
                                        ToastUtil.shortToast(ctx, "删除成功");
                                        //mAdapter.notifyItemRemoved(position);//成功移除某一项
                                        callback2.onSuccess(apiResult);
                                    } else {
                                        ToastUtil.shortToast(ctx, "删除失败");
                                        callback2.onFail(new Exception());
                                    }
                                }else{
                                    ToastUtil.shortToast(ctx, "删除失败");
                                    callback2.onFail(new Exception());
                                }
                                dialog12.dismiss();
                            });
                })
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ctx.getResources().getColor(R.color.agmobile_red));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ctx.getResources().getColor(R.color.black));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);

    }


    /**
     * 材料附件上传附件
     */
    public void uploadFileByCl(Context ctx, FileBean fileBean, String recordId, String dirId,Callback2<ApiResult> callback2) {
        if (ctx==null) {
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put("recordId", recordId);
        param.put("pkName", "APPLYINST_ID");
        param.put("tableName", "AEA_HI_APPLYINST");
        param.put("dirId", dirId);

        Map<String, FileBean> files1 = new HashMap<>();
        if (!"0".equals(fileBean.getPath())) {
            files1.put("file", fileBean);
        }

        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("正在提交，请等待");
        progressDialog.show();

        HttpUtil.getInstance(AwUrlManager.serverUrl()).postWithFile(GcjsUrlConstant.UPLOAD_PWPF_MATERIAL_ATTACHMENT, param, files1)
//                .map(new Function<String, ApiResult>() {
//                    @Override
//                    public ApiResult apply(String s) throws Exception {
//                        if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
//                            ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
//                            }.getType());
//                            return apiResult;
//                        }
//                        return null;
//                    }
//                })
                .subscribe(s -> {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                            ApiResult apiResult = new Gson().fromJson(s,ApiResult.class);
                            if (apiResult != null && apiResult.isSuccess()) {
                                ToastUtil.shortToast(ctx, "提交成功");
                                callback2.onSuccess(apiResult);
                            } else {
                                ToastUtil.shortToast(ctx, "提交失败");
                                callback2.onFail(new Exception("提交失败"));
                            }
                        }
                    }
                },throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();

                })
                ;
    }
}
