package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.multi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.augurit.agmobile.agwater5.R;

import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MultiMaterialBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectReportBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.StateBean;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs_public.common.view.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.UploadProjectListAdapter.PROJECT;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class MultiUploadEventActivity extends AppCompatActivity {



    private Fragment mFragment;
    private TitleBar mTitleBar;
    private MultiUploadEventFirstFragment mMultiUploadEventFirstFragment;
    private MultiUploadEventSecondFragment mMultiUploadEventSecondFragment;
    private MultiUploadEventThirdFragment mMultiUploadEventThirdFragment;
    private NavigationView mNavigationView;

    public String stageId;//当前所选中的阶段id
    public List<String> stateIds;//情形id列表
    public List<EventItemBean> eventItemBeans;//事项列表
    public ProjectReportBean projectReportBean;//
    public Map<String,String> formValues;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcjs_public_uploadevent_activity);

        projectReportBean = (ProjectReportBean) getIntent().getSerializableExtra(PROJECT);

        mTitleBar = findViewById(R.id.titleBar);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNaviTextArrs(new String[]{"补全信息", "选择情形", "材料清单"});

        mMultiUploadEventFirstFragment = new MultiUploadEventFirstFragment();
        mMultiUploadEventSecondFragment = new MultiUploadEventSecondFragment();
        mMultiUploadEventThirdFragment = new MultiUploadEventThirdFragment();

        replaceFragment(mMultiUploadEventFirstFragment);

        View btn_pre = findViewById(R.id.btn_pre);
        View btn_next = findViewById(R.id.btn_next);
        View btn_upload = findViewById(R.id.btn_upload);
        btn_pre.setVisibility(View.GONE);
        findViewById(R.id.btn_pre).setOnClickListener(v -> {
            if (mFragment instanceof MultiUploadEventSecondFragment) {
                replaceFragment(mMultiUploadEventFirstFragment);
                btn_pre.setVisibility(View.GONE);
                btn_upload.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
            } else if (mFragment instanceof MultiUploadEventThirdFragment) {
                replaceFragment(mMultiUploadEventSecondFragment);
                btn_pre.setVisibility(View.VISIBLE);
                btn_upload.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(v -> {
            if (mFragment instanceof MultiUploadEventSecondFragment&& ((MultiUploadEventSecondFragment) mFragment).validateForm()) {

                replaceFragment(mMultiUploadEventThirdFragment);
                btn_upload.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.GONE);
                btn_pre.setVisibility(View.VISIBLE);

            } else if (mFragment instanceof MultiUploadEventFirstFragment && ((MultiUploadEventFirstFragment) mFragment).validateForm()) {
                replaceFragment(mMultiUploadEventSecondFragment);
                btn_upload.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
                btn_pre.setVisibility(View.VISIBLE);
            }
        });


        mTitleBar.setBackListener(v -> backPress());

        btn_upload.setOnClickListener(v -> {

            final ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("正在提交");
            pd.show();

            Gson gson = new Gson();
            HashMap<String, Object> params = new HashMap<>();
            StringBuilder eventSb = new StringBuilder();
            if (!ListUtil.isEmpty(eventItemBeans)) {
                for (EventItemBean eib : eventItemBeans) {
                    eventSb.append(eib.getItemVerId() + ",");
                }
            }
            eventSb.deleteCharAt(eventSb.lastIndexOf(","));

            List<StateBean> stateList = new ArrayList<>();
            for (DictItem stateS : mMultiUploadEventSecondFragment.getSelectState()){
                StateBean sb= new StateBean();
                sb.setStateId(stateS.getValue());
                sb.setParentStateId(stateS.getParentTypeCode());
                stateList.add(sb);
            }
            String stateJson = gson.toJson(stateList, new TypeToken<List<StateBean>>() {
            }.getType());

            StringBuilder orgSb = new StringBuilder();
            List<String> strings = new ArrayList<>();
            if (!ListUtil.isEmpty(projectReportBean.getAeaUnitInfoList())){
                for (ProjectReportBean.AeaUnitInfoListBean aib : projectReportBean.getAeaUnitInfoList()){
                    strings.add(aib.getUnitInfoId());
                }
                String s = gson.toJson(strings);
                orgSb.append(s);
            }

            if("0".equals(formValues.get("receive_mode"))) {
                params.put("addresseeAddr", formValues.get("receive_addr"));//详细地址
//            params.put("addresseeCity", "");
//            params.put("addresseeCounty", "");
//            params.put("addresseeIdcard", "");
                params.put("addresseeName", formValues.get("receive_name"));
                params.put("addresseePhone", formValues.get("receive_phone"));
            }else{
                params.put("addresseeAddr", formValues.get("draw_card_num"));//身份证
                params.put("addresseeName", formValues.get("draw_name"));
                params.put("addresseePhone", formValues.get("draw_phone"));
            }
//            params.put("addresseeProvince", "");
            params.put("applyLinkmanId", "");
//            params.put("applySubject", "1");//申报主体：1 单位，0 个人
//            params.put("applyinstId", "");
            params.put("applyinstSource", "net");//申报来源, net表示网上申报，win表示窗口申报
            params.put("bindItemVerIds", eventSb.toString());//事项版本id， 多个用逗号隔开
            params.put("branchOrgMap", "");//分局承办组织map[{itemVerId=2, branchOrg=1}, {itemVerId=11, branchOrg=22}]
            params.put("chooseId", stateJson );//选择的情形id, json字符串数组["1", "2", "3"]
            params.put("comments", "");//审批意见
//            params.put("createTime", projectReportBean.getProjectCreateDate());//createTime
//            params.put("creater", "");
            params.put("destActId", "bumenshenpi");//下一环节id, bumenshenpi
//            params.put("expressNum", "");
            params.put("handleUnitProj", "");//经办单位
//            params.put("issueTime", "");
            params.put("linkmanInfoId", formValues.get("applicator"));//联系人id
            params.put("linkmanMobilePhone", formValues.get("linkmanMobilePhone"));//联系人手机号
            params.put("matinstsIds", "");//材料实例id, 可能多个
//            params.put("orderId", "");
            params.put("projInfoId", projectReportBean.getProjInfoId());//项目id
//            params.put("projUnitIds", orgSb.toString());//企业单位id，可能多个
            params.put("projUnitIds", strings);//企业单位id，可能多个

            params.put("receiveMode", formValues.get("receive_mode"));//领取模式
            params.put("smsType", formValues.get("receive_type"));//取件方式
//            params.put("senderAddr", "");
//            params.put("senderCity", "");
//            params.put("senderCounty", "");
//            params.put("senderName", "");
//            params.put("senderPhone", "");
//            params.put("senderProvince", "");
            params.put("stageId", stageId);//阶段id
            params.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
            params.put("createStageItemSign", "0");//后台无此参数，但需传

            ArrayList<MultiMaterialBean> datas = mMultiUploadEventThirdFragment.getDatas();//选择的文件数据

            HttpUtil.getInstance(AwUrlManager.serverUrl()).postJsonBodyByObject(GcjsPuUrlConstant.POST_MULTI_UPLOAD, params)
                    .map(s -> {
                        Gson g = new Gson();
                        ApiResult apiResult = g.fromJson(s, ApiResult.class);
                        return apiResult;
                    })
                    .subscribe(s -> {
                        pd.dismiss();
                        if (s.isSuccess()){
                            ToastUtil.longToast(MultiUploadEventActivity.this,"成功发起申报");
                            finish();
                        }else{
                            ToastUtil.longToast(MultiUploadEventActivity.this,"发起申报失败");
                        }
                    }, Throwable::printStackTrace);
        });

    }


    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress() {
        View btn_pre = findViewById(R.id.btn_pre);
        View btn_next = findViewById(R.id.btn_next);
        View btn_upload = findViewById(R.id.btn_upload);
        if (mFragment instanceof MultiUploadEventSecondFragment) {
            replaceFragment(mMultiUploadEventFirstFragment);
            btn_upload.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
            btn_pre.setVisibility(View.GONE);
        } else if (mFragment instanceof MultiUploadEventThirdFragment) {
            replaceFragment(mMultiUploadEventSecondFragment);
            btn_upload.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
            btn_pre.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(
                android.R.anim.fade_in, R.anim.fade_out);

        if (!fragment.isAdded() && mFragment == null) {    // 先判断是否被add过
            transaction.add(R.id.fl_container, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else if (!fragment.isAdded() && mFragment != null) {
            transaction.hide(mFragment).add(R.id.fl_container, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else if (fragment.isAdded() && mFragment != null) {
            transaction.hide(mFragment).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
        } else {
            transaction.show(fragment).commit(); // 显示下一个
        }

        mFragment = fragment;

        if (mFragment instanceof MultiUploadEventThirdFragment) {
            mTitleBar.setTitleText("材料清单");
            mNavigationView.setCurrentState(2);
        } else if (mFragment instanceof MultiUploadEventSecondFragment) {
            mTitleBar.setTitleText("选择情形");
            mNavigationView.setCurrentState(1);
        } else {
            mTitleBar.setTitleText("补全信息");
            mNavigationView.setCurrentState(0);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MultiUploadEventThirdFragment.CLFJ_REQUEST) {
            if (resultCode == RESULT_OK) {//获取文件上传
                mMultiUploadEventThirdFragment.onActivityResult(requestCode,resultCode,data);
            }
        }
    }



}
