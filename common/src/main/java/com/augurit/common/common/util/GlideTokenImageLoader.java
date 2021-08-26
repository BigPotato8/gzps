package com.augurit.common.common.util;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.view.imagepicker.loader.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.io.File;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.login
 * @createTime 创建时间 ：2019-06-24
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class GlideTokenImageLoader implements ImageLoader {

    private static GlideTokenImageLoader instance;

    public static GlideTokenImageLoader getInstance() {
        if (instance == null) {
            synchronized (GlideTokenImageLoader.class) {
                if (instance == null) {
                    instance = new GlideTokenImageLoader();
                }
            }
        }
        return instance;
    }
    public void displayImage(Activity activity, String path, ImageView imageView) {
        displayImage(activity,path,imageView,0,0);
    }
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        if(TextUtils.isEmpty(path)) return;
        LazyHeaders headers = new LazyHeaders.Builder()
                .addHeader("Authorization",
                        LoginManager.getInstance().
                                getCurrentUser().
                                getAuthentication().
                                generateHeader())
                .build();
        if (path.contains("http")) {
            GlideUrl glideUrl = new GlideUrl(path, headers);
            Glide.with(activity)                             //配置上下文
//                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .load(glideUrl)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(com.augurit.agmobile.common.view.R.drawable.ic_default_image)           //设置错误图片
                    .placeholder(com.augurit.agmobile.common.view.R.drawable.ic_default_image)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(imageView);
        } else {
            Glide.with(activity)                             //配置上下文
                    .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(com.augurit.agmobile.common.view.R.drawable.ic_default_image)           //设置错误图片
                    .placeholder(com.augurit.agmobile.common.view.R.drawable.ic_default_image)     //设置占位图片
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(imageView);
        }
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        if(TextUtils.isEmpty(path)) return;
        LazyHeaders headers = new LazyHeaders.Builder()
                .addHeader("Authorization",
                        LoginManager.getInstance().
                                getCurrentUser().
                                getAuthentication().
                                generateHeader())
                .build();
        if (path.contains("http")) {
            GlideUrl glideUrl = new GlideUrl(path, headers);
            Glide.with(activity)                             //配置上下文
//                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .load(glideUrl)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(imageView);
        } else {
            Glide.with(activity)                             //配置上下文
                    .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
//                    .load(glideUrl)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                    .into(imageView);
        }

    }

    @Override
    public void clearMemoryCache() {
    }

}
