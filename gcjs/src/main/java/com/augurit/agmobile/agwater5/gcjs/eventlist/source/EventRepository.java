package com.augurit.agmobile.agwater5.gcjs.eventlist.source;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;

import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AcceptReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ApproveNotThroughBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ApproveThroughBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.Attachment;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClbzDetailBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClfjBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.CorrectReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ElementButton;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventClbzItemBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventSituationBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialCorrectInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialListBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.NotAcceptReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PersonSelectBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfTypeBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsMaterialBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.TscxBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzTypeBean;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.ApproveStateBean;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.SmsInfo;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.common.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.GetRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * 数据仓库实现
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.eventlist.source
 * @createTime 创建时间 ：2018/8/30
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class EventRepository {


    /**
     * 获取办理意见
     */
    @Deprecated
    public Observable<ApiResult<List<com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess>>> getAdviceList(String processInstanceId) {
        GetRequest postRequest = EasyHttp.get(GcjsUrlConstant.GET_EVENT_ADVICE_INFO + "/" + processInstanceId)
                .baseUrl(AwUrlManager.serverUrl());

        return postRequest.execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(s -> {
                    ApiResult<List<com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess>> result =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess>>>() {
                            }.getType());
                    return result;
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取办理意见
     */
    public Observable<ApiResult<List<com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess>>> getAdviceList4_0(String applyinstId, String processInstanceId) {
        Map<String, String> params = new HashMap<>();
        params.put("applyinstId", applyinstId);
        params.put("processInstanceId", processInstanceId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_ADVICE_LIST_4_0, params)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<List<com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess>> result =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess>>>() {
                            }.getType());
                    return result;
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取基本信息-项目申报
     */
    @Deprecated
    public Observable<ApiResult<EventBean>> getBaseInfo(String id, String isItemSeek, String busRecordId, String taskId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstCode", id);
        param.put("isItemSeek", isItemSeek);
        param.put("busRecordId", busRecordId);
        param.put("taskId", taskId);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.GET_EVENT_INFO, param)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<EventBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<EventBean>>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 获取基本信息-项目申报，已办项目查询
     */
    public Observable<ApiResult<EventInfoBean>> getBaseInfo(String applyinstId, String taskId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("taskId", taskId);
        param.put("isItemSeek", "false");
        Map<String, String> headerParam = new HashMap<>();
        headerParam.put("Authorization", LoginManager.getInstance().getCurrentUser().getAuthentication().generateHeader());
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_INFO_4_0, param, headerParam)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<EventInfoBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<EventInfoBean>>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 获取受理回执信息
     */
    public Observable<ApiResult<AcceptReceiptBean>> getReceiptInfo(String applyinstId, String taskId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("taskId", taskId);
//        param.put("isItemSeek", "false");
        Map<String, String> headerParam = new HashMap<>();
        headerParam.put("Authorization", LoginManager.getInstance().getCurrentUser().getAuthentication().generateHeader());
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_ACCEPT_RECEIPT_INFO, param, headerParam)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<AcceptReceiptBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<AcceptReceiptBean>>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 获取不予受理回执信息
     */
    public Observable<ApiResult<NotAcceptReceiptBean>> getNotReceiptInfo(String applyinstId, String taskId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("taskId", taskId);
//        param.put("isItemSeek", "false");
        Map<String, String> headerParam = new HashMap<>();
        headerParam.put("Authorization", LoginManager.getInstance().getCurrentUser().getAuthentication().generateHeader());
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_NOT_ACCEPT_RECEIPT_INFO, param, headerParam)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<NotAcceptReceiptBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<NotAcceptReceiptBean>>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 获取审批决定通过回执信息
     */
    public Observable<ApiResult<ApproveThroughBean>> getApproveThroughInfo(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
//        param.put("isItemSeek", "false");
        Map<String, String> headerParam = new HashMap<>();
        headerParam.put("Authorization", LoginManager.getInstance().getCurrentUser().getAuthentication().generateHeader());
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_APPROVE_THROUGH_INFO, param, headerParam)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<ApproveThroughBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<ApproveThroughBean>>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 获取审批决定不通过回执信息
     */
    public Observable<ApiResult<ApproveNotThroughBean>> getNotApproveInfo(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
//        param.put("isItemSeek", "false");
        Map<String, String> headerParam = new HashMap<>();
        headerParam.put("Authorization", LoginManager.getInstance().getCurrentUser().getAuthentication().generateHeader());
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_APPROVE_NOT_THROUGH_INFO, param, headerParam)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<ApproveNotThroughBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<ApproveNotThroughBean>>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 获取补正回执信息
     */
    public Observable<ApiResult<CorrectReceiptBean>> getCorrectionReceiptInfo(String finalStr, String applyinstId, String iteminstId, String correctDueDays, Object correctId, String isMulti) {
        Map<String, String> param = new HashMap<>();
        param.put("matCorrectDtosJson",finalStr);
        param.put("applyinstId",applyinstId);
        param.put("iteminstId",iteminstId);
        param.put("correctDueDays",correctDueDays);
        param.put("correctId",correctId.toString());
        param.put("isMulti",isMulti);
//        param.put("correctinstDtoJson",value);
//        Map<String, String> headerParam = new HashMap<>();
//        headerParam.put("Authorization", LoginManager.getInstance().getCurrentUser().getAuthentication().generateHeader());
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).postJsonBody1(GcjsUrlConstant.GET_CORRECT_RECEIPT_INFO, param)
//                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<CorrectReceiptBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<CorrectReceiptBean>>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 补正回执保存
     */
    public Observable<ApiResult> postCorrectReceipt(Map<String, String> param) {
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).postJsonBody(GcjsUrlConstant.POST_CORRECT_RECEIPT, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 删除补正回执
     */
    public Observable<ApiResult> deleteCorrectReceipt(Map<String, String> param) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.DELETE_CORRECT_RECEIPT, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 获取sms信息
     *
     * @param applyinstId
     * @return
     */
    public Observable<ApiResult<SmsInfo>> getSmsInfo(String applyinstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_SMS_INFO, param)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<SmsInfo> data = new Gson().fromJson(s, new TypeToken<ApiResult<SmsInfo>>() {
                    }.getType());
                    return data;
                });
    }


    public Observable<ApiResult<ApproveStateBean>> getApproveStateBean(String applyinstId, String taskId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("taskId", taskId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_STATE_INFO, param)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<ApproveStateBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<ApproveStateBean>>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 获取基本信息情形 并联
     */
    public Observable<ApiResult<List<EventSituationBean>>> getEventSituationA(String applyinstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_SITUATIONA, param)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<List<EventSituationBean>> data = new Gson().fromJson(s, new TypeToken<ApiResult<List<EventSituationBean>>>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 获取基本信息情形 单项
     */
    public Observable<ApiResult<List<EventSituationBean>>> getEventSituationB(String applyinstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_SITUATIONB, param)
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<List<EventSituationBean>> data = new Gson().fromJson(s, new TypeToken<ApiResult<List<EventSituationBean>>>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 获取基本信息-事项列表xxxx
     */
    @Deprecated
    public Observable<ApiResult<List<EventListBean>>> getEvevtList(String id, String isItemSeek, String busRecordId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", id);
        param.put("isItemSeek", isItemSeek);
        param.put("busRecordId", busRecordId);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_ISMT_INFO, param)
                .map(s -> {
                    ApiResult<List<EventListBean>> data = new Gson().fromJson(s, new TypeToken<ApiResult<List<EventListBean>>>() {
                    }.getType());
                    return data;
                });
    }


    /**
     * 获取基本信息-材料列表-并联
     */
    @Deprecated
    public Observable<ApiResult<List<MaterialBean>>> getMaterialListA(String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("iteminstId", iteminstId);
        param.put("typeSign", "2");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_PARMATINS_INFOA, param)
                .map(s -> {
                    ApiResult<List<MaterialBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<MaterialBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取基本信息-材料列表-单项
     */
    @Deprecated
    public Observable<ApiResult<List<MaterialBean>>> getMaterialListB(String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("iteminstId", iteminstId);
        param.put("typeSign", "1");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_PARMATINS_INFOB, param)
                .map(s -> {
                    ApiResult<List<MaterialBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<MaterialBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取根据材料id获取附件列表xxxx
     */
    @Deprecated
    public Observable<ApiResult<List<Attachment>>> getMaterialFileList(String id) {
        Map<String, String> param = new HashMap<>();
        param.put("matinstId", id);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_PARMATINS_FILE_INFO, param)
                .map(s -> {
                    ApiResult<List<Attachment>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<Attachment>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取材料附件列表
     *
     * @return
     */
    public Observable<ApiResult<String>> getEventItemId4_0(String taskId) {
        Map<String, String> param = new HashMap<>();
        param.put("taskId", taskId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_ITEM_ID_4_0, param)
                .map(s -> {
                    ApiResult<String> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<String>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取材料附件列表
     *
     * @return
     */
    public Observable<ApiResult<List<MaterialListBean>>> getMaterialFileList4_0(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
//        param.put("applyinstId", "63fe5a56-d765-4a35-ab4e-b50991576c60");//测试
        param.put("iteminstId", iteminstId);
//        param.put("iteminstId", "50c03c1b-dc2b-44c5-81b3-b043afc47d63");
        param.put("isSeries", "1");
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_MATERIAL_LIST, param)
                .map(s -> {
                    ApiResult<List<MaterialListBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<MaterialListBean>>>() {
                            }.getType());
                    return data;
                });
    }
    /**
     * 获取材料附件列表
     *
     * @return
     */
    public Observable<ApiResult<List<ResultGoodsMaterialBean>>> getResultGoodsMaterialFileList(String applyinstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_RESULT_MATERIAL_LIST, param)
                .map(s -> {
                    ApiResult<List<ResultGoodsMaterialBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<ResultGoodsMaterialBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 上传材料附件列表的材料
     *
     * @return
     */
    public Observable<ApiResult> uploadMaterialFiles4_0(Map<String, String> params, Map<String, List<FileBean>> filesMap) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).postWithFileBySameName(GcjsUrlConstant.UPLOAD_EVENT_MATERIAL_FILE, params, filesMap)
                .map(s -> {
                    ApiResult data =
                            new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取材料附件item相关的材料库
     *
     * @return
     */
    public Observable<ApiResult<List<Attachment>>> getMaterialStore(String applyinstId, String matId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("matId", matId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.GET_EVENT_MATERIAL_STORE, param)
                .map(s -> {
                    ApiResult<List<Attachment>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<Attachment>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取批文批复-并联
     */
    @Deprecated
    public Observable<ApiResult<List<PwpfBean>>> getPwpfListA(String id) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", id);
        param.put("isApprover", "1");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_PWPF_INFOA, param)
                .map(s -> {
                    ApiResult<List<PwpfBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<PwpfBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取批文批复-单项
     */
    @Deprecated
    public Observable<ApiResult<List<PwpfBean.ItemMatinstBean>>> getPwpfListB(String id) {
        Map<String, String> param = new HashMap<>();
        param.put("iteminstId", id);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_PWPF_INFOB, param)
                .map(s -> {
                    ApiResult<List<PwpfBean.ItemMatinstBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<PwpfBean.ItemMatinstBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取批文批复列表4.0
     */
    public Observable<ApiResult<List<PwpfBean>>> getPwpfList(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_PWPF_LIST, param)
                .map(s -> {
                    ApiResult<List<PwpfBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<PwpfBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取批文批复的类型4.0
     */
    public Observable<ApiResult<List<PwpfTypeBean>>> getPwpfTypeList(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_PWPF_TYPE, param)
                .map(s -> {
                    ApiResult<List<PwpfTypeBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<PwpfTypeBean>>>() {
                            }.getType());
                    return data;
                }).onErrorResumeNext(throwable -> null);
    }

    /**
     * 获取证件证照列表4.0
     */
    public Observable<ApiResult<List<ZjzzBean>>> getZjzzList(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_ZJZZ_LIST, param)
                .map(s -> {
                    ApiResult<List<ZjzzBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<ZjzzBean>>>() {
                            }.getType());
                    return data;
                }).onErrorResumeNext(throwable -> null);
    }

    /**
     * 获取证件证照的类型4.0
     */
    public Observable<ApiResult<ZjzzTypeBean>> getZjzzTypeList(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_ZJZZ_TYPE, param)
                .map(s -> {
                    ApiResult<ZjzzTypeBean> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<ZjzzTypeBean>>() {
                            }.getType());
                    return data;
                }).onErrorResumeNext(throwable -> null);
    }

    /**
     * 获取根据批文批复id获取附件列表xxxx
     */
    public Observable<ApiResult<List<Attachment>>> getPwpfFileList(String id) {
        Map<String, String> param = new HashMap<>();
//        param.put("matinstId", id);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_EVENT_PWPF_FILE_LIST + "/" + id, param)
                .map(s -> {
                    ApiResult<List<Attachment>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<Attachment>>>() {
                            }.getType());
                    return data;
                }).onErrorResumeNext(throwable -> null);
    }

    /**
     * 获取申报材料列表
     */
    public Observable<ApiResult<List<ClfjBean.FilesBean>>> getClfjList(String dirId, String isSeries, String id) {
        Map<String, String> param = new HashMap<>();
        param.put("attType", "");
        param.put("dirId", dirId);//sbcl_2018_augurit
        param.put("isSeries", isSeries);//是否单项申报0并联1单项
        param.put("recordIds", id);//申报实例id
        param.put("tableName", "AEA_HI_APPLYINST");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.CLFJ_LIST, param)
                .map(s -> {
                    ApiResult<ClfjBean> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<ClfjBean>>() {
                            }.getType());
                    ApiResult<List<ClfjBean.FilesBean>> apiResult = new ApiResult<>();
                    apiResult.setCode(200);
                    List<ClfjBean.FilesBean> list = new ArrayList<>();
                    list.addAll(data.getData().getFiles());
                    apiResult.setData(list);
                    return apiResult;
                });
    }

    /**
     * 获取申报材料列表
     */
    public Observable<ApiResult<ClfjBean>> getClfjAllList(String dirId, String isSeries, String id) {
        Map<String, String> param = new HashMap<>();
        param.put("attType", "");
        param.put("dirId", dirId);//sbcl_2018_augurit
        param.put("isSeries", isSeries);//是否单项申报0并联1单项
        param.put("recordIds", id);//申报实例id
        param.put("tableName", "AEA_HI_APPLYINST");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.CLFJ_LIST, param)
                .map(s -> {
                    ApiResult<ClfjBean> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<ClfjBean>>() {
                            }.getType());

                    return data;
                });
    }

    /**
     * 获取项目项目的材料补正详情信息
     */
    public Observable<ApiResult<List<ClbzDetailBean>>> getClbzDetail(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.CLBZ_DETAIL_INFO, param)
                .map(s -> {
                    ApiResult<List<ClbzDetailBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<ClbzDetailBean>>>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取项目的特殊程序详情信息
     */
    public Observable<ApiResult<List<TscxBean>>> getTscxDetail(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.TSCX_DETAIL_INFO, param)
                .map(s -> {
                    ApiResult<List<TscxBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<TscxBean>>>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取材料补正列表
     */
    public Observable<ApiResult<EventClbzItemBean>> getMaterials(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.CLBZ_GET_MATERIALS, param)
                .map(s -> {
                    ApiResult<EventClbzItemBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<EventClbzItemBean>>() {
                            }.getType());

                    return data;
                });
    }

    /**
     * 发起材料补正
     */
    public Observable<ApiResult> postMaterialCorrection(Map<String, String> param) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.CLBZ_START_FLOW, param)
                .map(s -> {
                    ApiResult data =
                            new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                    return data;
                });
    }
//    public Observable<ApiResult<List<MaterialCorrectInfoBean>>> postMaterialCorrection(Map<String, String> param) {
//
//        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.CLBZ_START_FLOW, param)
//                .map(s -> {
//                    ApiResult<List<MaterialCorrectInfoBean>> data =
//                            new Gson().fromJson(s, new TypeToken<ApiResult<List<MaterialCorrectInfoBean>>>() {
//                            }.getType());
//                    return data;
//                });
//    }
    /**
     * 发起受理回执保存
     */
    public Observable<ApiResult> postAcceptReceipt(Map<String, String> param) {

//        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.POST_ACCEPT_RECEIPT, param)
//                .map(s -> {
//                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
//                            }.getType());
//                    return data;
//                });
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).postJsonBody(GcjsUrlConstant.POST_ACCEPT_RECEIPT, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                    return data;
                });
    }
    public Observable<ApiResult> postNotAcceptReceipt(Map<String, String> param) {
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).postJsonBody(GcjsUrlConstant.POST_NOT_ACCEPT_RECEIPT, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }


    //保存审批决定通过GET_APPROVE_THROUGH_SAVE_INFO
    public Observable<ApiResult> postApproveThroughInfo(Map<String, String> param) {
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).postJsonBody(GcjsUrlConstant.GET_APPROVE_THROUGH_SAVE_INFO, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }

    //保存审批决定不通过POST_SAVE_NOT_ACCEPT_RECEIPT_INFO
    public Observable<ApiResult> postNotApproveThroughInfo(Map<String, String> param) {
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).postJsonBody(GcjsUrlConstant.POST_SAVE_NOT_ACCEPT_RECEIPT_INFO, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }


    /**
     * 删除受理回执
     */
    public Observable<ApiResult> deleteAcceptReceipt(Map<String, String> param) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.DELETE_ACCEPT_RECEIPT, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 删除不予受理回执
     */
    public Observable<ApiResult> deleteNotAcceptReceipt(Map<String, String> param) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.DELETE_NOT_ACCEPT_RECEIPT, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 删除不予受理回执
     */
    public Observable<ApiResult> deleteApprove(Map<String, String> param) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.DELETE_APPROVE, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }
    /**
     * 删除不予受理回执保存
     */
    public Observable<ApiResult> deleteNotApprove(Map<String, String> param) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.DELETE_NOT_APPROVE, param)
                .map(s -> {
                    ApiResult data = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    return data;
                });
    }

    /**
     * 发起材料补正
     */
    public Observable<ApiResult<List<ResultGoodsBean>>> getResultGoods(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_RESULT_GOODS, param)
                .map(s -> {
                    ApiResult<List<ResultGoodsBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<ResultGoodsBean>>>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取其它结果物列表
     */
    public Observable<ApiResult<List<PwpfBean>>> getOtherGoodsList(String applyinstId, String iteminstId) {
        Map<String, String> param = new HashMap<>();
        param.put("applyinstId", applyinstId);
        param.put("iteminstId", iteminstId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_OTHER_GOODS, param)
                .map(s -> {
                    ApiResult<List<PwpfBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<PwpfBean>>>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取审批按钮
     * version=13&privMode=act-view&viewId=9aea00ed-ee86-4d7d-ba6b-ebbe89705b86&actId=task1570601068750&appFlowdefId=e14046ed-1475-4d29-b1af-20075c61e0a1
     */
    public Observable<ApiResult<ElementButton>> getAuthorizeElementPlus(Map<String, String> params) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_ELEMENT_BUTTON_4_2, params)
                .map(s -> {
                    ApiResult<ElementButton> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<ElementButton>>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 添加审批意见
     */
    public Observable<ApiResult> addTaskComment(String message, String taskId, String processInstanceId) {
        Map<String, String> params = new HashMap<>();
        params.put("message", message);
        params.put("comment", message);
        params.put("taskId", taskId);
        params.put("processInstanceId", processInstanceId);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.ADD_TASK_COMMENT_4_2, params)
                .map(s -> {
                    ApiResult data =
                            new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 窗口流程：流程审批并更改申请状态和事项状态按钮方法（使用于既要推动流程流转，也需要修改申报状态和事项状态时使用）
     */
    public Observable<ApiResult> sendAndChange(Map<String, String> params) {

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.SEND_AND_CHANGE, params)
                .map(s -> {
                    ApiResult data =
                            new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取组织列表
     */
    public Observable<ApiResult<List<PersonSelectBean>>> getOrgTree(Map<String, String> params) {
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_ORG_TREE, params)
                .map(s -> {
                    List<PersonSelectBean> data =
                            new Gson().fromJson(s, new TypeToken<List<PersonSelectBean>>() {
                            }.getType());
                    ApiResult<List<PersonSelectBean>> apiResult = new ApiResult<>();
                    if (params.size()<=1){//跟组织
                        List<PersonSelectBean> dataNew = new ArrayList<>();
                        for (PersonSelectBean datum : data) {
                            if (datum.getNocheck()==null) {
                                dataNew.add(datum);
                            }
                        }
                        apiResult.setData(dataNew);
                    }else {
                        apiResult.setData(data);
                    }
                    apiResult.setSuccess(!ListUtil.isEmpty(data));

                    return apiResult;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 部门审批，转办
     * "nextTaskManVal": "",
     * "comment": "阿萨德",
     * "loginName": "huangjw",
     * "showText": "黄建文（huangjw）",
     * "taskId": "5ea45da8-0cce-48d5-8455-ed27297590df"
     */
    public Observable<ApiResult> sendOnTask(String comment, String loginName, String showText, String taskId) {
        Map<String, String> params = new HashMap<>();
        params.put("nextTaskManVal", "");
        params.put("comment", comment);
        params.put("loginName", loginName);
        params.put("showText", showText);
        params.put("taskId", taskId);

        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.SEND_ON_TASK, params)
                .map(s -> {
                    ApiResult data =
                            new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
