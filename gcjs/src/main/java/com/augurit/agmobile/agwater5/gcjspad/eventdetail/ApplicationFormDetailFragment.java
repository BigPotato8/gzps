package com.augurit.agmobile.agwater5.gcjspad.eventdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.dict.GcjsDictRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter.ApplyUnitsAdapter;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.ApproveStateBean;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.SmsInfo;
import com.augurit.agmobile.agwater5.gcjspad.widget.PadEditText;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.util.ProgressDialogUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail
 * @createTime 创建时间 ：2020/12/4
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ApplicationFormDetailFragment extends BasePadFragment {
    //todo
    // http://106.53.200.230:8083/aplanmis-front/rest/approve/type/and/state?applyinstId=95465022-4a3a-4ed6-83d8-dc1c14eeb051&taskId=00fb9e98-3855-48d7-8633-14783619eee6&time=1607333022132


    private TextView tv_apply_form_title;//申请表名
    private PadEditText pe_local_code;//项目代码
    private PadEditText pe_central_code;//工程代码
    private PadEditText pe_project_name;//项目/工程名称
    private PadEditText pe_project_theme;//所属主题
    private PadEditText pe_project_type;//立项类型
    private PadEditText pe_project_addr;//建设地点
    private PadEditText pe_project_regionalism;//行政区划
    private PadEditText pe_project_financialSource;//资金来源
    private PadEditText pe_project_isForeign;//是否外资
    private PadEditText pe_project_investType;//投资类型
    private PadEditText pe_project_landSource;//土地来源
    private PadEditText pe_project_isAreaEstimate;//是否完成区域评估
    private PadEditText pe_project_isDesignSolution;//是否带设计方案
    private PadEditText pe_project_projNature;//建设性质
    private PadEditText pe_project_projCategory;//工程分类
    private PadEditText pe_project_nstartTime;//拟开工时间
    private PadEditText pe_project_endTime;//拟建成日期
    private PadEditText pe_project_investSum;//总投资（万元）
    private PadEditText pe_project_projScope;//单体跨度（m）
    private PadEditText pe_project_BuildingArea;//建筑面积（㎡）
    private PadEditText pe_project_LandArea;//用地面积（㎡）
    private PadEditText pe_project_projLevel;//重点项目
    private PadEditText pe_project_theIndustry;//国标行业
    private PadEditText pe_project_gbCodeYear;//国标行业代码发布年代
    private PadEditText pe_project_scaleContent;//建设规模及内容
    private PadEditText pe_project_receiveMode;//纸质结果物签收方式
    private PadEditText pe_project_receiveType;//领取方式
    private PadEditText pe_project_addresseeName;//收件人姓名
    private PadEditText pe_project_addresseePhone;//收件人手机号码
    private PadEditText pe_project_addresseeIdcardType;//收件人证件类型
    private PadEditText pe_project_addresseeIdcard;//收件人证件号
    private RecyclerView rv_apply_units;

    private String applyinstId;
    private String taskId;

    private EventRepository mEventRepository;
    private ApplyUnitsAdapter applyUnitsAdapter;
    private GcjsDictRepository mGcjsDictRepository;

    public static ApplicationFormDetailFragment newInstance(String applyinstId, String taskId) {
        Bundle args = new Bundle();
        ApplicationFormDetailFragment fragment = new ApplicationFormDetailFragment();
        args.putString("applyinstId", applyinstId);
        args.putString("taskId", taskId);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_form_detail, container, false);
        mEventRepository = new EventRepository();
        mGcjsDictRepository = new GcjsDictRepository();
        applyUnitsAdapter = new ApplyUnitsAdapter(R.layout.item_list_units_layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initArgument();
//        getDetails();
    }

    private void initView(View view) {
        tv_apply_form_title = view.findViewById(R.id.tv_apply_form_title);
        pe_local_code = view.findViewById(R.id.pe_local_code);
        pe_central_code = view.findViewById(R.id.pe_central_code);
        pe_project_name = view.findViewById(R.id.pe_project_name);
        pe_project_theme = view.findViewById(R.id.pe_project_theme);
        pe_project_type = view.findViewById(R.id.pe_project_type);
        pe_project_addr = view.findViewById(R.id.pe_project_addr);
        pe_project_regionalism = view.findViewById(R.id.pe_project_regionalism);
        pe_project_financialSource = view.findViewById(R.id.pe_project_financialSource);
        pe_project_isForeign = view.findViewById(R.id.pe_project_isForeign);
        pe_project_investType = view.findViewById(R.id.pe_project_investType);
        pe_project_landSource = view.findViewById(R.id.pe_project_landSource);
        pe_project_isAreaEstimate = view.findViewById(R.id.pe_project_isAreaEstimate);
        pe_project_isDesignSolution = view.findViewById(R.id.pe_project_isDesignSolution);
        pe_project_projNature = view.findViewById(R.id.pe_project_projNature);
        pe_project_projCategory = view.findViewById(R.id.pe_project_projCategory);
        pe_project_nstartTime = view.findViewById(R.id.pe_project_nstartTime);
        pe_project_endTime = view.findViewById(R.id.pe_project_endTime);
        pe_project_investSum = view.findViewById(R.id.pe_project_investSum);
        pe_project_projScope = view.findViewById(R.id.pe_project_projScope);
        pe_project_BuildingArea = view.findViewById(R.id.pe_project_BuildingArea);
        pe_project_LandArea = view.findViewById(R.id.pe_project_LandArea);
        pe_project_projLevel = view.findViewById(R.id.pe_project_projLevel);
        pe_project_theIndustry = view.findViewById(R.id.pe_project_theIndustry);
        pe_project_gbCodeYear = view.findViewById(R.id.pe_project_gbCodeYear);
        pe_project_scaleContent = view.findViewById(R.id.pe_project_scaleContent);
        pe_project_receiveMode = view.findViewById(R.id.pe_project_receiveMode);
        pe_project_receiveType = view.findViewById(R.id.pe_project_receiveType);
        pe_project_addresseeName = view.findViewById(R.id.pe_project_addresseeName);
        pe_project_addresseePhone = view.findViewById(R.id.pe_project_addresseePhone);
        pe_project_addresseeIdcardType = view.findViewById(R.id.pe_project_addresseeIdcardType);
        pe_project_addresseeIdcard = view.findViewById(R.id.pe_project_addresseeIdcard);
        rv_apply_units = view.findViewById(R.id.rv_apply_units);
        rv_apply_units.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_apply_units.setAdapter(applyUnitsAdapter);
        //解决嵌套滑动卡顿问题
        rv_apply_units.setHasFixedSize(true);
        rv_apply_units.setNestedScrollingEnabled(false);
    }


    private void initArgument() {
        applyinstId = getArguments().getString("applyinstId", "");
        taskId = getArguments().getString("taskId", "");
    }




    public void showDetail(SmsInfo smsInfo, EventInfoBean eventInfoBean, ApproveStateBean stateBean) {
        List<EventInfoBean.ProjInfoListBean> projInfoList = eventInfoBean.getProjInfoList();
        List<EventInfoBean.UnitInfoVoListBean> unitInfoVoList = eventInfoBean.getUnitInfoVoList();
        EventInfoBean.ApplyInfoVoBean applyInfoVo = eventInfoBean.getApplyInfoVo();

        //标题
        String applyFormTitle = "申请表";
        if(stateBean!=null && stateBean.getStageOrItemName()!=null){
            tv_apply_form_title.setText(stateBean.getStageOrItemName() + applyFormTitle);
        }else {
            tv_apply_form_title.setText(applyFormTitle);
        }
        //项目/工程信息
        EventInfoBean.ProjInfoListBean projInfoBean = null;
        if (!ListUtil.isEmpty(projInfoList)) {
            projInfoBean = projInfoList.get(0);
        }
        pe_local_code.setValue(projInfoBean == null ? "" : projInfoBean.getLocalCode());
        pe_central_code.setValue(projInfoBean == null ? "" : projInfoBean.getGcbm());
        pe_project_name.setValue(projInfoBean == null ? "" : projInfoBean.getProjName());
        pe_project_theme.setValue(projInfoBean == null ? "" : projInfoBean.getThemeName());
        pe_project_type.setValue(projInfoBean == null ? "" : projInfoBean.getProjType());
        pe_project_addr.setValue(projInfoBean == null ? "" : projInfoBean.getProjAddr());
        pe_project_regionalism.setValue(projInfoBean == null ? "" : projInfoBean.getRegionalism());
        pe_project_financialSource.setValue(projInfoBean == null ? "" : projInfoBean.getFinancialSource());
        pe_project_isForeign.setValue(projInfoBean == null ? "" : projInfoBean.getIsForeign());
        pe_project_investType.setValue(projInfoBean == null ? "" : projInfoBean.getInvestType());
        pe_project_landSource.setValue(projInfoBean == null ? "" : projInfoBean.getLandSource());
        pe_project_isAreaEstimate.setValue(projInfoBean == null ? "" : projInfoBean.getIsAreaEstimate());
        pe_project_isDesignSolution.setValue(projInfoBean == null ? "" : projInfoBean.getIsDesignSolution());
        pe_project_projNature.setValue(projInfoBean == null ? "" : projInfoBean.getProjNature());
        pe_project_projCategory.setValue(projInfoBean == null ? "" : projInfoBean.getProjCategory());
        pe_project_nstartTime.setValue(projInfoBean == null ? "" : projInfoBean.getNstartTime());
        pe_project_endTime.setValue(projInfoBean == null ? "" : projInfoBean.getEndTime());
        pe_project_investSum.setValue(projInfoBean == null ? "" : projInfoBean.getInvestSum());
        pe_project_projScope.setValue(projInfoBean == null ? "" : projInfoBean.getMonomerSpan());
        pe_project_BuildingArea.setValue(projInfoBean == null ? "" : projInfoBean.getBuildAreaSum());
        pe_project_LandArea.setValue(projInfoBean == null ? "" : projInfoBean.getXmYdmj());
        pe_project_projLevel.setValue(projInfoBean == null ? "" : projInfoBean.getProjLevel());
        pe_project_theIndustry.setValue(projInfoBean == null ? "" : projInfoBean.getTheIndustry());
        pe_project_gbCodeYear.setValue(projInfoBean == null ? "" : projInfoBean.getGbCodeYear());
        pe_project_scaleContent.setValue(projInfoBean == null ? "" : projInfoBean.getScaleContent());

        //申报主体
        applyUnitsAdapter.setNewData(unitInfoVoList);


        //结果物签收方式
        String receiveMode = "网上签收";
        if (smsInfo != null && TextUtils.equals("0", smsInfo.getReceiveMode())) {
            receiveMode = "邮政快递";
        } else if (smsInfo != null && TextUtils.equals("1", smsInfo.getReceiveMode())) {
            receiveMode = "窗口取证";
        }
        pe_project_receiveMode.setValue(receiveMode);

        String receiveType = "一次领取";
        if (smsInfo != null && TextUtils.equals("0", smsInfo.getReceiveType())) {
            receiveType = "分别领取";
        }
        pe_project_receiveType.setValue(receiveType);
        pe_project_addresseeName.setValue(smsInfo == null ? "" : smsInfo.getAddresseeName());
        pe_project_addresseePhone.setValue(smsInfo == null ? "" : smsInfo.getAddresseePhone());

        String idCardType = "居民身份证";
        List<? extends IDictItem> idCardTypes = applyUnitsAdapter.getDictItemsOrTreeItems("IDCARD_TYPE");
        if(!ListUtil.isEmpty(idCardTypes)){
            for (IDictItem item : idCardTypes) {
                if (smsInfo != null && TextUtils.equals(item.getValue(), smsInfo.getAddresseeIdcardType())) {
                    idCardType = item.getLabel();
                    break;
                }
            }
        }
        pe_project_addresseeIdcardType.setValue(idCardType);
        pe_project_addresseeIdcard.setValue(smsInfo == null ? "" : smsInfo.getAddresseeIdcard());
    }


}
