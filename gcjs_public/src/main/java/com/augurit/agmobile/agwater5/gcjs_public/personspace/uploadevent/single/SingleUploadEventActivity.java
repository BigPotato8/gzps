package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single;

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
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single.model.Situation;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.common.common.util.StringUtils;
import com.augurit.agmobile.agwater5.gcjs_public.common.view.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.eventlist.EventListFragment.EVENT_ITEM;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class SingleUploadEventActivity extends AppCompatActivity {


    private Fragment mFragment;
    private TitleBar mTitleBar;
    private SingleUploadEventFirstFragment mMultiUploadEventFirstFragment;
    private SingleUploadEventSecondFragment mMultiUploadEventSecondFragment;
    private SingleUploadEventThirdFragment mMultiUploadEventThirdFragment;
    private NavigationView mNavigationView;
    private EventItemBean mEventItemBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcjs_public_uploadevent_activity);
        mEventItemBean = (EventItemBean) getIntent().getSerializableExtra(EVENT_ITEM);

        mTitleBar = findViewById(R.id.titleBar);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNaviTextArrs(new String[]{"补全信息", "选择情形", "材料清单"});

        mMultiUploadEventFirstFragment = new SingleUploadEventFirstFragment();
        mMultiUploadEventSecondFragment = new SingleUploadEventSecondFragment();
        mMultiUploadEventThirdFragment = new SingleUploadEventThirdFragment();

        replaceFragment(mMultiUploadEventFirstFragment);

        View btn_pre = findViewById(R.id.btn_pre);
        View btn_next = findViewById(R.id.btn_next);
        View btn_upload = findViewById(R.id.btn_upload);
        btn_pre.setVisibility(View.GONE);
        findViewById(R.id.btn_pre).setOnClickListener(v -> {
            if (mFragment instanceof SingleUploadEventSecondFragment) {
                replaceFragment(mMultiUploadEventFirstFragment);
                btn_pre.setVisibility(View.GONE);
                btn_upload.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
            } else if (mFragment instanceof SingleUploadEventThirdFragment) {
                replaceFragment(mMultiUploadEventSecondFragment);
                btn_pre.setVisibility(View.VISIBLE);
                btn_upload.setVisibility(View.GONE);
                btn_next.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.btn_next).setOnClickListener(v -> {
            if (mFragment instanceof SingleUploadEventSecondFragment&& ((SingleUploadEventSecondFragment) mFragment).validateForm()) {

                List<Situation.AeaItemMatsBean> aeaItemMatsBeans
                        = ((SingleUploadEventSecondFragment) mFragment).postMaterialList();
                replaceFragment(mMultiUploadEventThirdFragment);
                btn_upload.setVisibility(View.VISIBLE);
                btn_next.setVisibility(View.GONE);
                btn_pre.setVisibility(View.VISIBLE);

                mMultiUploadEventThirdFragment.showDatas(aeaItemMatsBeans);

            } else if (mFragment instanceof SingleUploadEventFirstFragment && ((SingleUploadEventFirstFragment) mFragment).validateForm()) {
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

            HashMap<String, String> params = new HashMap<>();
            params.put("applyinstSource", "net");
            params.put("destActId", "wangshangshenbao");
            params.put("itemVerId", mEventItemBean.getItemVerId());
            params.put("matinstsIds", "");
            params.put("netApplyVo", "");
            params.put("projInfoId", mMultiUploadEventFirstFragment.getProjectInfoId());
            params.put("stateList", mMultiUploadEventSecondFragment.getStageList());
            params.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
            HttpUtil.getInstance(AwUrlManager.serverUrl()).postJsonBody(GcjsPuUrlConstant.SINGLE_EVENT_UPLOAD, params)
                    .subscribe(s -> {
                        if (StringUtils.isJson(s)) {
                            ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                            }.getType());
                            if (apiResult.isSuccess()) {
                                ToastUtil.shortToast(SingleUploadEventActivity.this, "申报成功");
                                finish();
                            }
                        }

                        pd.dismiss();
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
        if (mFragment instanceof SingleUploadEventSecondFragment) {
            btn_upload.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
            btn_pre.setVisibility(View.GONE);
            replaceFragment(mMultiUploadEventFirstFragment);
        } else if (mFragment instanceof SingleUploadEventThirdFragment) {
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

        if (mFragment instanceof SingleUploadEventSecondFragment) {
            mTitleBar.setTitleText("材料清单");
            mNavigationView.setCurrentState(1);
        } else if (mFragment instanceof SingleUploadEventThirdFragment) {
            mTitleBar.setTitleText("选择情形");
            mNavigationView.setCurrentState(2);
        } else {
            mTitleBar.setTitleText("补全信息");
            mNavigationView.setCurrentState(0);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SingleUploadEventThirdFragment.CLFJ_REQUEST) {
            if (resultCode == RESULT_OK) {//获取文件上传
                mMultiUploadEventThirdFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

}

