package com.augurit.common.common.util;

import android.content.Context;
import android.os.Environment;

import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;

import java.io.File;

/**
 * com.augurit.common.common.util
 * Created by sdb on 2019/12/17  14:18.
 * Desc：
 */
public class FilePreviewUtil {

    private final Context mContext;

    public FilePreviewUtil(Context ctx) {
        mContext = ctx;
    }

    public void downFile(FileBean item,String url) {
        if (item == null) {
            return;
        }
        String downloadPath = new FilePathUtil(mContext).getDownloadFilePath();
        if (item.isUploaded()) {
            String dPath = downloadPath + "/" + item.getName();
            File file = new File(dPath);
            if (file.exists() && file.isFile()) {
                //直接打开文件
                previewFile(item, dPath);
            } else {
                //下载文件，并打开文件
                ProgressDialogUtil.showProgressDialog(mContext, "正在下载附件...", false);
                AuthDownloadManager.getInstance()
//                        .serverUrl(AwUrlManager.qualityServerUrl())
                        .downLoadPath(url)
                        .savePath(downloadPath)
                        .saveName(item.getName())
                        .Download(new AuthDownloadListener() {
                            @Override
                            public void success() {
                                ProgressDialogUtil.dismissProgressDialog();
                                //直接打开文件
                                previewFile(item, dPath);
                            }

                            @Override
                            public void failed() {
                                //跳出对话框，提示无证书
                                ProgressDialogUtil.dismissProgressDialog();
                                ToastUtil.shortToast(mContext, "文件下载失败");
                            }
                        });
            }
        } else {
            String path = item.getPath();
            previewFile(item, path);
        }
    }

    private void previewFile(FileBean item, String dPath) {
        String tempPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileOpenViewActivity.getIntent(mContext, dPath, tempPath, item.getName());
    }

}
