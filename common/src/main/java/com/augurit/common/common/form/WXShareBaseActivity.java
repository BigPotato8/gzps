package com.augurit.common.common.form;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.common.common.manager.AwUrlManager;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.GetRequest;

import java.net.URLEncoder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.view
 * @createTime 创建时间 ：2018-09-06
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-09-06
 * @modifyMemo 修改备注：
 */

public class WXShareBaseActivity extends AwFormActivity {

    private String url;

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    private String reportId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        title_bar.setMoreButtonText("分享至微信");
        title_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    shareWithWechat();
                }
            }
        });
    }

    private void shareWithWechat() {
        Disposable disposable = AESEncodeSJID(getReportId()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(listApiResult -> {
                    if (listApiResult != null && listApiResult.getCode() == 200) {
                        String s = listApiResult.getData();
                        try {
                            String url = AwUrlManager.testAgSupportUrl()
                                    + "event/eventDetail.html?";
                            String param = "sjid=" + URLEncoder.encode(s);
                            url += param;
                            Intent wechatIntent = new Intent(Intent.ACTION_SEND);
                            wechatIntent.setPackage("com.tencent.mm");
                            wechatIntent.setType("text/plain");
                            wechatIntent.putExtra(Intent.EXTRA_TEXT, url);
                            startActivity(wechatIntent);
                        } catch (Exception e) {
                            ToastUtil.shortToast(WXShareBaseActivity.this, "请确认手机是否已安装微信");
                        }

                    }
                }, throwable -> {
                    ToastUtil.shortToast(WXShareBaseActivity.this, "无法连接网络，请检查网络设置");
                    throwable.printStackTrace();
                });
        compositeDisposable.add(disposable);
    }


    public void setAESEncodeSJIUrl(String url) {
        this.url = url;
    }

    /**
     * 加密问题ID，然后获取问题处理情况、施工日志、管理层意见
     */
    private Observable<ApiResult<String>> AESEncodeSJID(String content) {
        GetRequest getRequest = EasyHttp.get(url == null ? "" : url)
                .baseUrl(AwUrlManager.testAgSupportUrl())
                .params("content", content);


        return getRequest.execute(String.class).subscribeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(s -> {
                    return new ApiResult<String>();
                })
                .subscribeOn(Schedulers.io());
    }
}
