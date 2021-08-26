package com.augurit.agmobile.agwater5.gcjs.common;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.agwater5.gcjspad.CustomFileOpenViewActivity;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.augurit.common.common.util.ProgressDialogUtil;
import com.google.gson.Gson;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * com.augurit.common.common.util
 * Created by sdb on 2019/12/17  14:18.
 * Desc：
 */
public class FilePreviewUtil {


    private FilePreviewUtil() {
    }

    /**
     * 下载多个文件
     * @param ctx
     * @param fileName
     * @param fileType
     * @param fileIds
     */
    public static void downMoreFile(Context ctx, String fileName, String fileType, List<String> fileIds){
        if (ListUtil.isEmpty(fileIds))return;
        Gson gson = new Gson();
        FileBean fileBean = new FileBean();
        fileBean.setName(fileName);
        fileBean.setMimeType(fileType);
        fileBean.setId(gson.toJson(fileIds));
        fileBean.setUploaded(true);
        downMoreFile(ctx, fileBean);
    }
    /**
     * 下载多个文件
     * @param ctx
     * @param fileName
     * @param fileType
     * @param fileId
     */
    public static void downMoreFile(Context ctx, String fileName,String fileType,String fileId){
        if (TextUtils.isEmpty(fileId))return;
        FileBean fileBean = new FileBean();
        fileBean.setName(fileName);
        fileBean.setMimeType(fileType);
        fileBean.setId(fileId);
        fileBean.setUploaded(true);
        downMoreFile(ctx, fileBean);
    }

    public static void downFile(Context ctx, String fileName,String fileType,String fileId){
        FileBean fileBean = new FileBean();
        fileBean.setName(fileName);
        fileBean.setMimeType(fileType);
        fileBean.setId(fileId);
        fileBean.setUploaded(true);
        downFile(ctx, fileBean);
    }

    public static void downFile(Context ctx, FileBean item) {
        if (item == null) {
            return;
        }
        String downloadPath = new FilePathUtil(ctx).getDownloadFilePath();
        if (item.isUploaded()) {
            String dPath = downloadPath + "/" + item.getName();
            File file = new File(dPath);
            if (file.exists() && file.isFile()) {
                //直接打开文件
                previewFile(ctx, item, dPath);
            } else {
                //下载文件，并打开文件
                ProgressDialogUtil.showProgressDialog(ctx, "正在下载附件...", false);
                AuthDownloadManager.getInstance()
//                        .serverUrl(AwUrlManager.qualityServerUrl())
                        .serverUrl(AwUrlManager.serverUrl())
                        .downLoadPath(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 + "/" + item.getId())
                        .savePath(downloadPath)
                        .saveName(item.getName())
                        .Download(new AuthDownloadListener() {
                            @Override
                            public void success() {
                                ProgressDialogUtil.dismissProgressDialog();
                                //直接打开文件
                                previewFile(ctx, item, dPath);
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
            previewFile(ctx, item, path);
        }
    }

    public static void downMoreFile(Context ctx, FileBean item) {
        if (item == null) {
            return;
        }
        String downloadPath = new FilePathUtil(ctx).getDownloadFilePath();
        if (item.isUploaded()) {
            String dPath = downloadPath + "/" + item.getName();
            File file = new File(dPath);
            if (file.exists() && file.isFile()) {
                //直接打开文件
                previewFile(ctx, item, dPath);
            } else {
                //下载文件，并打开文件
                Gson gson = new Gson();
                String toJson = gson.toJson(item.getId().split(","));
                ProgressDialogUtil.showProgressDialog(ctx, "正在下载附件...", false);
                EasyHttp.downLoad(GcjsUrlConstant.URL_FILE_DONMLOAD_4_0 )
                        .baseUrl(AwUrlManager.serverUrl())
                        .params("detailIds",toJson)
                        .savePath(downloadPath)
                        .saveName(item.getName())
                        .execute(new DownloadProgressCallBack<File>() {

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onError(ApiException e) {
                                //跳出对话框，提示无证书
                                ProgressDialogUtil.dismissProgressDialog();
                                ToastUtil.shortToast(ctx, "文件下载失败");
                            }

                            @Override
                            public void update(long bytesRead, long contentLength, boolean done) {

                            }

                            @Override
                            public void onComplete(String path) {
                                ProgressDialogUtil.dismissProgressDialog();
                                //直接打开文件
                                previewFile(ctx, item, dPath);
                            }
                        });

            }
        } else {
            String path = item.getPath();
            previewFile(ctx, item, path);
        }
    }

    private static void previewFile(Context ctx, FileBean item, String dPath) {
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

        CustomFileOpenViewActivity.getIntent(ctx, dPath, tempPath, item.getName());
    }

}
