package com.augurit.agmobile.agwater5.gcjs.publicaffair;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.lidong.pdf.PDFView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * com.augurit.agmobile.gzws.cuberun
 * Created by sdb on 2018/8/13  9:30.
 * Desc：
 */

public class WatchImageOrPdfActivity extends AppCompatActivity {
    public static final String FILES_PATH = "FILES_PATH";
    private ProgressBar progressBar;
    private ViewPager mViewPager;
    List<String> paths;

    public static Intent newIntent(Context ctx, ArrayList<String> paths) {
        Intent i = new Intent(ctx, WatchImageOrPdfActivity.class);
        i.putStringArrayListExtra(FILES_PATH, paths);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nw_cube_preview_photo_fragment);
        paths = getIntent() != null ? (List<String>) getIntent().getSerializableExtra(FILES_PATH) : null;
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        progressBar = (ProgressBar) findViewById(R.id.loading);

        MyViewPagerAdpapter myViewPagerAdpapter = new MyViewPagerAdpapter(this, paths);
        mViewPager.setAdapter(myViewPagerAdpapter);
        mViewPager.setOffscreenPageLimit(5);
    }


    private class MyViewPagerAdpapter extends PagerAdapter {
        Context mContext;
        List<String> mData;

        public MyViewPagerAdpapter(Context context, List<String> data) {
            mContext = context;
            mData = data;
        }

        public void updateData(List<String> data) {
            if (mData == null) {
                mData = new ArrayList<>();
            }
            mData.clear();
            if (data != null) {
                mData.addAll(data);
            }
            notifyDataSetChanged();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.cube_run_viewpager_item, null);
            SubsamplingScaleImageView iv_run = (SubsamplingScaleImageView) view.findViewById(R.id.iv_run);
            PDFView pdfView = (PDFView) view.findViewById(R.id.pdfView);
            String mPath = mData.get(position);
            if (TextUtils.isEmpty(mPath) || !mPath.contains(".pdf")) {
                //final PhotoViewAttacher mAttacher = new PhotoViewAttacher(iv_image);
                iv_run.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                //http://shenbao.dg.gov.cn/dgsyth/serviceimplement/indexFile/20180828_img.png
                //http://article.fd.zol-img.com.cn/t_s640x2000/g5/M00/05/09/ChMkJ1cEe6uICXBMAAM7sl7kvaUAAPU_gKS9z0AAzvK908.jpg
                //String testUrl = "http://article.fd.zol-img.com.cn/t_s640x2000/g5/M00/05/09/ChMkJ1cEe6uICXBMAAM7sl7kvaUAAPU_gKS9z0AAzvK908.jpg";
//                Intent i = new Intent(WatchImageOrPdfActivity.this,WebViewActivity.class);
//                i.putExtra(WebViewConstant.WEBVIEW_URL_PATH,mPath);
//                startActivity(i);
//                finish();

                //直接下载文件预览
//                mPath =testUrl;
//                String savePath = new FilePathUtil(getBaseContext()).getDownloadFilePath();
//                String saveName = mPath.substring(mPath.lastIndexOf("/") + 1);
//                File file = new File(savePath);
//                if (!file.exists()){
//                    file.mkdirs();
//                }else{
//                    file.delete();
//                    file.mkdirs();
//                }
//                AuthDownloadManager.getInstance()
//                        .downLoadPath(mPath)
//                        .savePath(savePath)
//                        .saveName(saveName)
//                        .Download(new AuthDownloadListener() {
//                            @Override
//                            public void success() {
//                                //直接打开文件
//                                iv_run.setImage(ImageSource.uri(Uri.fromFile(new File(savePath,saveName))), new ImageViewState(1.0F, new PointF(0, 0), 0));
//                                progressBar.setVisibility(View.GONE);
//                            }
//
//                            @Override
//                            public void failed() {
//                                //failed
//                                Log.e("downloadOnly","onLoadFailed");
//                            }
//                        });


                //glide只下载
                Glide.with(WatchImageOrPdfActivity.this).load(mPath).downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
                        iv_run.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(1.0F, new PointF(0, 0), 0));
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        Log.e("downloadOnly", "onLoadFailed", e);
                    }
                });
                //glide在线查看,大图模糊
//                Glide.with(WatchImageOrPdfActivity.this).load(mPath).diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(new GlideDrawableImageViewTarget(iv_image) {
//
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                    }
//
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                        super.onResourceReady(resource, animation);
//                        mAttacher.update();
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });

//                mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//
//                    @Override
//                    public void onPhotoTap(View arg0, float arg1, float arg2) {
//                        onBackPressed();
//                    }
//
//                    @Override
//                    public void onOutsidePhotoTap() {
//
//                    }
//                });
            } else {
                iv_run.setVisibility(View.GONE);
//                pdfView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.VISIBLE);
//
//                pdfView.fileFromLocalStorage(null, new OnLoadCompleteListener() {
//                    @Override
//                    public void loadComplete(int i) {
//                        progressBar.setVisibility(View.GONE);
//                    }
//                }, null, mPath, mPath.substring(mPath.lastIndexOf("/") + 1, mPath.length()));
//                pdfView.setSwipeVertical(false);

                downFile(mPath, mContext);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }


    public void downFile(String mPath, Context ctx) {
        String fileName = mPath.substring(mPath.lastIndexOf("/") + 1, mPath.length());
        String downloadPath = new FilePathUtil(ctx).getDownloadFilePath();
        String dPath = downloadPath + "/" + fileName;
        File file = new File(dPath);
        if (file.exists() && file.isFile()) {
            //直接打开文件
            previewFile(fileName, dPath, ctx);
        } else {
            //下载文件，并打开文件
            ProgressDialogUtil.showProgressDialog(ctx, "正在下载...", false);
            AuthDownloadManager.getInstance()
                    .serverUrl(AwUrlManager.getGcjsUrl())
                    .downLoadPath(mPath)
                    .savePath(downloadPath)
                    .saveName(fileName)
                    .Download(new AuthDownloadListener() {
                        @Override
                        public void success() {
                            ProgressDialogUtil.dismissProgressDialog();
                            //直接打开文件
                            previewFile(fileName, dPath, ctx);
                        }

                        @Override
                        public void failed() {
                            //跳出对话框，提示无证书
                            ProgressDialogUtil.dismissProgressDialog();
                            ToastUtil.shortToast(ctx, "文件下载失败");
                        }
                    });
        }
    }


    private void previewFile(String name, String dPath, Context ctx) {
        String tempPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        //如果是图片
//        if (dPath.toLowerCase().endsWith(".png") || dPath.toLowerCase().endsWith(".jpeg") || dPath.toLowerCase().endsWith(".jpg")) {
//            ArrayList<String> imagePaths = new ArrayList<>();
//            imagePaths.add(dPath);
//            //Intent intent = ImageViewActivity.newIntent(AccessoryActivity.this, 0, imagePaths);
//            Intent intent = WatchImageOrPdfActivity.newIntent(ctx, imagePaths);
//            ctx.startActivity(intent);
//            return;
//        }

        if (name.contains("policy")) {
            name = "政策文件";
        } else if (name.contains("process")) {
            name = "改革流程图";
        }
        FileOpenViewActivity.getIntent(ctx, dPath, tempPath, name);
        finish();
    }


//    private void initPic() {
//        if(TextUtils.isEmpty(mPDFUrl)) {
//            progressBar.setVisibility(View.VISIBLE);
//            mImageView.setVisibility(View.VISIBLE);
//            mPDFView.setVisibility(View.GONE);
//            Glide.with(this).load(mImageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).fitCenter().into(new GlideDrawableImageViewTarget(mImageView) {
//
//                @Override
//                public void onStart() {
//                    super.onStart();
//                }
//
//                @Override
//                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                    super.onResourceReady(resource, animation);
//                    mAttacher.update();
//                    progressBar.setVisibility(View.GONE);
//                }
//            });
//        }else{
//            mImageView.setVisibility(View.GONE);
//            mPDFView.setVisibility(View.VISIBLE);
//            mPDFView.fileFromLocalStorage(null,
//                    null, null, mPDFUrl, mPDFName);
//        }
//    }


}
