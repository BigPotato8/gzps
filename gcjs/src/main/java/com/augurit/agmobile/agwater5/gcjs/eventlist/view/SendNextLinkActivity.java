package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.view.sweetdialog.SweetAlertDialog;

import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.Assigneers;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.LinkSendConfig;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.NextLink;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.NextLinkAssigneersAdapter;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.ui.common.OnRecyclerItemClickListener;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.lib.validate.MaxLengthInputFilter;
import com.augurit.agmobile.common.view.combineview.AGImagePicker;
import com.augurit.agmobile.common.view.floatingsearchview.util.adapter.TextWatcherAdapter;
import com.augurit.common.common.util.DialogUtil;
import com.augurit.common.common.view.MyGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.view.RxView;
import com.wyt.searchbox.utils.KeyBoardUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * ?????????????????????
 * <p>
 * Created by liangsh on 2017/11/15.
 */

@Deprecated
public class SendNextLinkActivity extends AppCompatActivity {

    private String taskInstId;

    private AutoBreakViewGroup radio_nextlink;   //???????????????????????????
    private View ll_nextlilnk_org;     //?????????????????????????????????

    private AutoBreakViewGroup radio_nextlink_org;   //???????????????????????????
    private MyGridView gv_assignee;

    private NextLinkAssigneersAdapter assigneersAdapter;
    private Assigneers.Assigneer selAssignee;     //????????????????????????????????????
    private AGImagePicker photo_item;
    private EditText et_content;
    private View btn_cancel;
    private View btn_submit;
    private RadioGroup.LayoutParams params;
    private ProgressDialog pd;


    private ViewGroup ll_nextlilnk_org_Rg_Rm;
    private AutoBreakViewGroup radio_nextlink_org_Rg_Rm;
    private String isSendMessage = "0";//???????????????,1?????????
    private CheckBox cb_is_send_message;
    private CompositeDisposable mCompositeDisposable;
    private NextLink mNextLink;
    private boolean mNeedSelectAssignee = true;
    private String mDestActId;
    private boolean mMultiTask;
    private boolean mUserTask;
    private String mDestActName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_nextlink);
        this.taskInstId = getIntent().getStringExtra("taskInstId");

        mCompositeDisposable = new CompositeDisposable();

        initView();
        initListener();
    }

    private void initView() {

        pd = new ProgressDialog(this);
        pd.setMessage("????????????...");
        pd.setCancelable(false);

        radio_nextlink = (AutoBreakViewGroup) findViewById(R.id.radio_nextlink);
        ll_nextlilnk_org = findViewById(R.id.ll_nextlilnk_org);

        //??????????????????
        radio_nextlink_org = (AutoBreakViewGroup) findViewById(R.id.radio_nextlink_org);
        //?????????????????????
        gv_assignee = (MyGridView) findViewById(R.id.gv_assignee);

        //Rg??????Rm??????????????????????????????
        ll_nextlilnk_org_Rg_Rm = (ViewGroup) findViewById(R.id.ll_nextlilnk_org_Rg_Rm);
        radio_nextlink_org_Rg_Rm = (AutoBreakViewGroup) findViewById(R.id.radio_nextlink_org_Rg_Rm);
        ll_nextlilnk_org_Rg_Rm.setVisibility(View.GONE);
        photo_item = (AGImagePicker) findViewById(R.id.photo_item);
        photo_item.setVisibility(View.GONE);
        et_content = (EditText) findViewById(R.id.textfield_content);

        btn_cancel = findViewById(R.id.btn_cancel);
        btn_submit = findViewById(R.id.btn_submit);


        assigneersAdapter = new NextLinkAssigneersAdapter(this);
        gv_assignee.setAdapter(assigneersAdapter);
        assigneersAdapter.setOnItemClickListener(new OnRecyclerItemClickListener<Assigneers.Assigneer>() {
            @Override
            public void onItemClick(View view, int position, Assigneers.Assigneer selectedData) {
                selAssignee = selectedData;
            }

            @Override
            public void onItemLongClick(View view, int position, Assigneers.Assigneer selectedData) {

            }
        });


        getNextLink();


        cb_is_send_message = findViewById(R.id.cb_is_send_message);
        //cb_is_send_message.setVisibility(View.GONE);//???????????????????????????
    }

    private void initListener() {
        final TextView tv_size = (TextView) findViewById(R.id.tv_size);
        final int maxTotal = 200;
        et_content.setFilters(new InputFilter[]{new MaxLengthInputFilter(maxTotal,
                null, et_content, "??????????????????" + maxTotal + "??????").setDismissErrorDelay(1500)});
        et_content.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                try {
                    String inputText = s.toString();
                    if (TextUtils.isEmpty(inputText)) {
                        tv_size.setText("0/" + maxTotal);
                        return;
                    }
                    tv_size.setText(inputText.getBytes("GB2312").length / 2 + "/" + maxTotal);
                } catch (UnsupportedEncodingException e) {
                    Log.d("SendNextLinkActivity", "initListener:" + e.toString());
                }
            }
        });

        radio_nextlink.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mNextLink != null) {
                    int checkedRadioButtonId = group.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(checkedRadioButtonId);
                    int index = ((RadioGroup) radioButton.getParent()).indexOfChild(radioButton);
                    NextLink.ContentBean contentBean = mNextLink.getContent().get(index);
                    mDestActName = contentBean.getDestActName();
                    mDestActId = contentBean.getDestActId();
                    mMultiTask = contentBean.isMultiTask();
                    mUserTask = contentBean.isUserTask();
                    mNeedSelectAssignee = contentBean.isNeedSelectAssignee();
                    if (contentBean.isNeedSelectAssignee()) {
                        getNextLinkAssigneers(mDestActId);
                    } else {
                        selAssignee = new Assigneers.Assigneer();
                        selAssignee.setUserCode(contentBean.getDefaultSendAssigneesId());
                        selAssignee.setUserName(contentBean.getDefaultSendAssignees());
                        ll_nextlilnk_org.setVisibility(TextUtils.isEmpty(contentBean.getDefaultSendAssigneesId()) ?
                                View.GONE : View.VISIBLE);
                        radio_nextlink_org.setVisibility(View.GONE);
                        ArrayList<Assigneers.Assigneer> assigneerList = new ArrayList<>();
                        assigneerList.add(selAssignee);
                        assigneersAdapter.notifyDatasetChanged(assigneerList, 0);
                    }
                }

            }
        });

        btn_cancel.setOnClickListener(v -> finish());

        Disposable subscribe = RxView.clicks(btn_submit)
                .throttleFirst(2, TimeUnit.SECONDS)   //2???????????????????????????????????????????????????
                .subscribe(o -> {
                    submitEvent();

                });
        mCompositeDisposable.add(subscribe);
    }

    private void submitEvent() {

        KeyBoardUtils.closeKeyboard(this,et_content);

        if (mNeedSelectAssignee && selAssignee == null) {
            ToastUtil.shortToast(this, "??????????????????????????????");
            return;
        }


        LinkSendConfig linkSendConfig = new LinkSendConfig();
        linkSendConfig.setTaskId(taskInstId);
        List<LinkSendConfig.SendConfigsBean> sendConfigs = new ArrayList<>();
        LinkSendConfig.SendConfigsBean sendConfigsBean = new LinkSendConfig.SendConfigsBean();
        sendConfigsBean.setAssignees(selAssignee.getUserName() == null ? "" : selAssignee.getUserName());
        sendConfigsBean.setDestActId(mDestActId);
        sendConfigsBean.setIsEnableMultiTask(mMultiTask);
        sendConfigsBean.setIsUserTask(mUserTask);
        sendConfigs.add(sendConfigsBean);
        linkSendConfig.setSendConfigs(sendConfigs);

        String configStr = new Gson().toJson(linkSendConfig);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("????????????????????????");
        progressDialog.show();

        HttpUtil.getInstance(AwUrlManager.serverUrl()).upJson(GcjsUrlConstant.SEND_TO_NEXT_LINK
                        + "?message=" + et_content.getText().toString()
                , configStr)
                .subscribe(s -> {
                    progressDialog.dismiss();
                    ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    if (apiResult != null && apiResult.isSuccess()) {
                        String nextTip = selAssignee == null ? "" : ",??????????????????????????????" + selAssignee.getUserName();
                        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("????????????")
                                .showCancelButton(false)
                                .setContentText("?????????????????????" + mDestActName + " ??????" + nextTip)
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    setResult(RESULT_OK);
                                    finish();
                                })
                                .show();

                    } else {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("????????????")
                                .showCancelButton(false)
                                .setContentText(apiResult.getMessage())
                                .show();
//                        ToastUtil.shortToast(SendNextLinkActivity.this, "????????????:" + apiResult.getMessage());
                    }

                });
    }

    /**
     * ??????????????????
     */
    private void getNextLink() {
        Map<String, String> map = new HashMap<>();
        map.put("taskId",taskInstId);
        HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_NEXT_LINK_4_0 , map)
                .subscribe(s -> {
                    NextLink nextLink = new Gson().fromJson(s, new TypeToken<NextLink>() {
                    }.getType());
                    if (nextLink != null && nextLink.isSuccess()) {
                        mNextLink = nextLink;
                        if (!ListUtil.isEmpty(nextLink.getContent()) && nextLink.getContent().size() == 1) {
                            List<NextLink.ContentBean> content = nextLink.getContent();
                            NextLink.ContentBean contentBean = content.get(0);
                            mNeedSelectAssignee = contentBean.isNeedSelectAssignee();
                            if (!mNeedSelectAssignee) {
                                selAssignee = new Assigneers.Assigneer();
                                selAssignee.setUserCode(contentBean.getDefaultSendAssigneesId());
                                selAssignee.setUserName(contentBean.getDefaultSendAssignees());
                                mDestActId = contentBean.getDestActId();
                                mMultiTask = contentBean.isMultiTask();
                                mUserTask = contentBean.isUserTask();
                                ArrayList<Assigneers.Assigneer> assigneerList = new ArrayList<>();
                                assigneerList.add(selAssignee);
                                ll_nextlilnk_org.setVisibility(View.VISIBLE);
                                radio_nextlink_org.setVisibility(View.GONE);
                                assigneersAdapter.notifyDatasetChanged(assigneerList, 0);
                            }
                        }

                        setNextLinkView(nextLink);
                    }
                });
    }


    /**
     * ????????????????????????????????????
     *
     * @param destActId
     */
    private void getNextLinkAssigneers(String destActId) {
//        ArrayList<Assigneers.Assigneer> assigneerList = new ArrayList<>();
//        Assigneers.Assigneer assigneers = new Assigneers.Assigneer();
//        Assigneers.Assigneer assigneers1 = new Assigneers.Assigneer();
//        Assigneers.Assigneer assigneers2 = new Assigneers.Assigneer();
//        Assigneers.Assigneer assigneers3 = new Assigneers.Assigneer();
//        Assigneers.Assigneer assigneers4 = new Assigneers.Assigneer();
//        assigneers.setUserName("?????????");
//        assigneers1.setUserName("??????");
//        assigneers2.setUserName("??????");
//        assigneers3.setUserName("??????");
//        assigneers4.setUserName("?????????");
//        assigneers.setUserCode("123");
//        assigneers1.setUserCode("123");
//        assigneers2.setUserCode("123");
//        assigneers3.setUserCode("123");
//        assigneers4.setUserCode("123");
//        assigneerList.add(assigneers);
//        assigneerList.add(assigneers2);
//        assigneerList.add(assigneers1);
//        assigneerList.add(assigneers3);
//        assigneerList.add(assigneers4);
        HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_NEXT_LINK_USER + "/" + taskInstId + "/" + destActId, null)
                .subscribe(s -> {

                    ApiResult<List<User>> result = new Gson().fromJson(s, new TypeToken<ApiResult<List<User>>>() {
                    }.getType());

                    if (result != null && result.isSuccess()) {
                        if (!ListUtil.isEmpty(result.getData())) {
                            List<User> data = result.getData();
                            ll_nextlilnk_org.setVisibility(View.VISIBLE);
                            radio_nextlink_org.setVisibility(View.GONE);
                            for (User user : data) {
                                selAssignee = new Assigneers.Assigneer();
                                selAssignee.setUserCode(user.getUserId());
                                selAssignee.setUserName(user.getUserName());
                                ArrayList<Assigneers.Assigneer> assigneerList = new ArrayList<>();
                                assigneerList.add(selAssignee);
                                assigneersAdapter.notifyDatasetChanged(assigneerList, 0);
                            }
                        }
                    }
                });


    }

    private void setNextLinkView(NextLink nextLink) {
        int screenWidths = getScreenWidths();
        params = new AutoBreakViewGroup.LayoutParams(screenWidths / 3, AutoBreakViewGroup
                .LayoutParams
                .WRAP_CONTENT);
        for (int i = 0; i < nextLink.getContent().size(); i++) {
            RadioButton radioButton = new RadioButton(SendNextLinkActivity.this);
            radioButton.setText(nextLink.getContent().get(i).getDestActName());
            radioButton.setLayoutParams(params);
            radio_nextlink.addView(radioButton);
            if (i == 0) {
                radioButton.setChecked(true);
            }
        }
    }

    private int getScreenWidths() {
        WindowManager manager = SendNextLinkActivity.this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    @Override
    public void onBackPressed() {
        DialogUtil.MessageBox(SendNextLinkActivity.this, "??????", "?????????????????????????????????"
                , (dialog, which) -> finish(), (dialog, which) -> dialog.dismiss());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
        KeyBoardUtils.closeKeyboard(this,et_content);
    }

}

