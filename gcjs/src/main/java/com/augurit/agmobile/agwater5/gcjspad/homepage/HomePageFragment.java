package com.augurit.agmobile.agwater5.gcjspad.homepage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsConstant;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.MainPadFragment;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.EventDetailPadFragment;
import com.augurit.agmobile.agwater5.gcjspad.homepage.adapter.AnnounceListAdapter;
import com.augurit.agmobile.agwater5.gcjspad.homepage.adapter.HandlingListAdapter;
import com.augurit.agmobile.agwater5.gcjspad.widget.PageControlView;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.widget.WEUIButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class HomePageFragment extends BasePadFragment implements View.OnClickListener {

    private EditText tv_search_keyword;
    private ImageView iv_search_keyword;
    private RecyclerView rv_handing_list;//待办列表控件
    private RecyclerView rv_announce_list;//通知公告控件
    private HandlingListAdapter handlingListAdapter;//待办列表适配器
    private AnnounceListAdapter announceListAdapter;//通知公告列表；
    private TextView tv_list_index_tip;//页码提示
    private PageControlView pc_page_control;//分页控件
    private EditText et_jump_index;//跳转指定页面填写框
    private WEUIButton btn_jump_confirm;//跳转确定
    private TextView tvHandingNum;
    private TextView tvAnnounceNum;
    private TextView tvNoAnnounce;
    private TextView tvNoHanding;
    private int curPage = 0;
    private int maxPage = 0;
    private String keyword;
    private ViewGroup cl_dbrw;
    private ViewGroup cl_ybrw;
    private ViewGroup cl_wdbj;
    private ViewGroup cl_bysl;
    private ViewGroup cl_sbyj;
    private ViewGroup cl_sbyq;
    private TextView tv_dbrw_num;
    private TextView tv_ybrw_num;
    private TextView tv_wdbj_num;
    private TextView tv_bysl_num;
    private TextView tv_sbyj_num;
    private TextView tv_sbyq_num;

    public static HomePageFragment newInstance() {
        Bundle args = new Bundle();
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        return view;
    }

    private void initListener() {
//        //监听关键字
        iv_search_keyword.setOnClickListener(v -> {
            String string = tv_search_keyword.getText().toString();
            if (!TextUtils.isEmpty(string)) {
                Log.i("keyword", string);
                keyword = string;
                getHandlingList(1, keyword, true);
                InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                if (manager != null)
                    manager.hideSoftInputFromWindow(iv_search_keyword.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                if (!TextUtils.isEmpty(keyword)) {
                    keyword = "";
                    getHandlingList(1, keyword, true);
                    InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(iv_search_keyword.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        getHandlingList(1, keyword, false);
        getAnnounceList();
//        getDbYbCount();
        getUserType();
    }

    private void getUserType() {
        Observable<String> userType = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_NEW_USER_TYPE, null);
        Observable<String> winPortalCount = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_WIN_PORTAL_COUNT, null);
        Observable<String> orgPortalCount = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_ORG_PORTAL_COUNT, null);
        Disposable subscribe = userType.flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                JSONObject jsonObj = new JSONObject(s);
                boolean isSuccess = false;
                if (jsonObj.has("success")) {
                    isSuccess = jsonObj.getBoolean("success");
                }
                if (isSuccess && jsonObj.has("content")) {
                    JSONObject content = jsonObj.getJSONObject("content");
                    String userType = "window_role";
                    if (content.has("roles")) {
                        JSONArray roles = content.getJSONArray("roles");
                        List<String> types = new ArrayList<>();
                        for (int i = 0; i < roles.length(); i++) {
                            JSONObject jsonObject = roles.getJSONObject(i);
                            if (jsonObject.has("roleCode")) {
                                String roleCode = jsonObject.getString("roleCode");
                                types.add(roleCode);
                            }
                        }
                        if (types.contains("approve_role") && !types.contains("window_role")) {
                            userType = "approve_role";
                        }
                        if ("approve_role".equals(userType)) {
                            return orgPortalCount;
                        }
                    }
                } else {
                    return Observable.empty();
                }
                return winPortalCount;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                ApiResult<CountBean> countBeanApiResult = new Gson().fromJson(s, new TypeToken<ApiResult<CountBean>>() {
                }.getType());
                CountBean data = countBeanApiResult.getData();
                if (countBeanApiResult.getData() != null) {
                    if(!TextUtils.isEmpty(data.getDaiBanRenWu())){
                        tv_dbrw_num.setText(data.getDaiBanRenWu());
                    }

                    if(!TextUtils.isEmpty(data.getYiBanRenWu())){
                        tv_ybrw_num.setText(data.getYiBanRenWu());
                    }

                    if(!TextUtils.isEmpty(data.getPartsCnt())){
                        tv_wdbj_num.setText(data.getPartsCnt());
                    }

                    if(!TextUtils.isEmpty(data.getBuYuShouLiShenBao())){
                        tv_bysl_num.setText(data.getBuYuShouLiShenBao());
                    }

                    if(!TextUtils.isEmpty(data.getShenBaoShiXianYuJing())){
                        tv_sbyj_num.setText(data.getShenBaoShiXianYuJing());
                    }

                    if(!TextUtils.isEmpty(data.getShenBaoShiXianYuQi())){
                        tv_sbyq_num.setText(data.getShenBaoShiXianYuQi());
                    }


                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        compositeDisposable.add(subscribe);
    }

    private void initView(View view) {
        //快捷入口
        cl_dbrw = view.findViewById(R.id.cl_dbrw);
        cl_ybrw = view.findViewById(R.id.cl_ybrw);
        cl_wdbj = view.findViewById(R.id.cl_wdbj);
        cl_bysl = view.findViewById(R.id.cl_bysl);
        cl_sbyj = view.findViewById(R.id.cl_sbyj);
        cl_sbyq = view.findViewById(R.id.cl_sbyq);
        cl_dbrw.setOnClickListener(this);
        cl_ybrw.setOnClickListener(this);
        cl_wdbj.setOnClickListener(this);
        cl_bysl.setOnClickListener(this);
        cl_sbyj.setOnClickListener(this);
        cl_sbyq.setOnClickListener(this);

        tv_dbrw_num = view.findViewById(R.id.tv_dbrw_num);
        tv_ybrw_num = view.findViewById(R.id.tv_ybrw_num);
        tv_wdbj_num = view.findViewById(R.id.tv_wdbj_num);
        tv_bysl_num = view.findViewById(R.id.tv_bysl_num);
        tv_sbyj_num = view.findViewById(R.id.tv_sbyj_num);
        tv_sbyq_num = view.findViewById(R.id.tv_sbyq_num);

        //待办列表关键字
        tv_search_keyword = view.findViewById(R.id.tv_search_keyword);
        iv_search_keyword = view.findViewById(R.id.iv_search_keyword);
        keyword = "";
        //待办列表
        handlingListAdapter = new HandlingListAdapter(R.layout.item_daiban_list_pad);
        rv_handing_list = view.findViewById(R.id.rv_handing_list);
        rv_handing_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_handing_list.setAdapter(handlingListAdapter);
        handlingListAdapter.setOnItemClickListener((adapter, view1, position) -> {
            //跳转至项目详情
            Object item = adapter.getData().get(position);
            EventDetailPadFragment eventDetailPadFragment = EventDetailPadFragment.getInstance((EventListItem.DataBean) item);
            addFragmentOnActivity(eventDetailPadFragment);

        });
        tvHandingNum = view.findViewById(R.id.tv_handing_num);

        //通知公告
        announceListAdapter = new AnnounceListAdapter(R.layout.item_announce_pad);
        rv_announce_list = view.findViewById(R.id.rv_announce_list);
        rv_announce_list.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_announce_list.setAdapter(announceListAdapter);
        tvAnnounceNum = view.findViewById(R.id.tv_announce_num);
        tvNoAnnounce = view.findViewById(R.id.tv_no_announce_data);

        //底部分页导航
        tv_list_index_tip = view.findViewById(R.id.tv_list_index_tip);
        pc_page_control = view.findViewById(R.id.pc_page_control);
        et_jump_index = view.findViewById(R.id.et_jump_index);
        btn_jump_confirm = view.findViewById(R.id.btn_jump_confirm);
        pc_page_control.setPageChangeListener(new PageControlView.OnPageChangeListener() {
            @Override
            public void pageChanged(PageControlView pageControl, int numPerPage) {
                getHandlingList(numPerPage, keyword, false);
            }
        });
        btn_jump_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_jump_index.getText().toString();
                if (TextUtils.isEmpty(number) || Integer.parseInt(number) > maxPage) {
                    ToastUtil.shortToast(getActivity(), "请输入合法的页码");
                    return;
                }
                getHandlingList(Integer.parseInt(number), keyword, false);
            }
        });


    }

    @SuppressLint("CheckResult")
    private void getHandlingList(int pageNum, String keyword, boolean isSearch) {
        if (curPage == pageNum && !isSearch) {
            ToastUtil.shortToast(getActivity(), "当前页面已加载");
            return;
        }
        Map<String, String> param = new HashMap<>();
        param.put("viewCode", GcjsConstant.DB_VIEW_CODE);
        param.put("pageNum", pageNum + "");
        param.put("pageSize", 5 + "");
        param.put("keyword", keyword);
//        param.put("sort", "taskId");
//        param.put("order", "asc");
//        param.put("sort[field]", "applyTime");
//        param.put("sort[sort]", "desc");
        HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_DB_LIST, param)
                .map(new Function<String, ApiResult<List<EventListItem.DataBean>>>() {
                    @Override
                    public ApiResult<List<EventListItem.DataBean>> apply(String s) throws Exception {
                        ApiResult<EventListItem> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<EventListItem>>() {
                        }.getType());

                        EventListItem data = apiResult.getData();
                        pc_page_control.setTotalPage(data.getPages());
                        tv_list_index_tip.setText("第" + data.getStartRow() + "-" + data.getEndRow() + "条/总共" + data.getPages() + "页");
                        ApiResult<List<EventListItem.DataBean>> listBeanApiResult = new ApiResult<>();
                        if (data != null) {
                            listBeanApiResult.setData(data.getList() == null ? new ArrayList() : data.getList());
                        } else {
                            listBeanApiResult.setData(new ArrayList<>());
                        }
                        curPage = pageNum;
                        maxPage = data.getPages();
                        tvHandingNum.setText(data.getTotal() + "");
                        pc_page_control.setSelectView(curPage);
                        return listBeanApiResult;
                    }
                }).subscribe(new Consumer<ApiResult<List<EventListItem.DataBean>>>() {
            @Override
            public void accept(ApiResult<List<EventListItem.DataBean>> listApiResult) throws Exception {
                List<EventListItem.DataBean> data = listApiResult.getData();
                handlingListAdapter.setNewData(data);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    @SuppressLint("CheckResult")
    private void getAnnounceList() {
        Map<String, String> map = new HashMap<>();
        map.put("pageSize", 100000 + "");
        map.put("pageNum", 1 + "");
        map.put("businessFlag", "busiTypeDefault");
        HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.ANNOUNCE_LIST, map)
                .subscribe(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult<Announce> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<Announce>>() {
                        }.getType());
                        Announce data = apiResult.getData();
                        if (data != null) {
                            List<Announce.RowsBean> rows = data.getRows();
                            if (!ListUtil.isEmpty(rows)) {
                                tvAnnounceNum.setText(data.getTotal() + "");
                                showAnnounceList(true);
                                announceListAdapter.setNewData(rows);
                            } else {
                                showAnnounceList(false);
                            }
                        } else {
                            showAnnounceList(false);

                        }
                    } else {
                        showAnnounceList(false);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    showAnnounceList(false);
                });

    }

    private void showAnnounceList(boolean show) {
        if (show) {
            rv_announce_list.setVisibility(View.VISIBLE);
            tvNoAnnounce.setVisibility(View.GONE);
        } else {
            rv_announce_list.setVisibility(View.GONE);
            tvNoAnnounce.setVisibility(View.VISIBLE);
        }
    }

    private void getDbYbCount() {

        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_WIN_PORTAL_COUNT, null)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ApiResult<CountBean> countBeanApiResult = new Gson().fromJson(s, new TypeToken<ApiResult<CountBean>>() {
                        }.getType());
                        CountBean data = countBeanApiResult.getData();
                        if (countBeanApiResult.getData() != null) {
                            tv_dbrw_num.setText(data.getDaiBanRenWu());
                            tv_ybrw_num.setText(data.getYiBanRenWu());
                            tv_wdbj_num.setText(data.getXiangMu());
                            tv_bysl_num.setText(data.getBuYuShouLiShenBao());
                            tv_sbyj_num.setText(data.getShenBaoShiXianYuJing());
                            tv_sbyq_num.setText(data.getShenBaoShiXianYuQi());
                        }
                    }
                }, Throwable::printStackTrace);
        compositeDisposable.add(subscribe);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cl_dbrw:
                ((MainPadFragment) getParentFragment()).JumpToExaminePadFragment(0);
                break;
            case R.id.cl_ybrw:
                ((MainPadFragment) getParentFragment()).JumpToExaminePadFragment(1);
                break;
            case R.id.cl_wdbj:
                ((MainPadFragment) getParentFragment()).JumpToExaminePadFragment(2);
                break;
            case R.id.cl_bysl:
                //不予受理
                ((MainPadFragment) getParentFragment()).JumpToExaminePadFragment(3);
                break;
            case R.id.cl_sbyj:
                ((MainPadFragment) getParentFragment()).JumpToExaminePadFragment(4);
                break;
            case R.id.cl_sbyq:
                ((MainPadFragment) getParentFragment()).JumpToExaminePadFragment(5);
                break;
            default:
                break;
        }
    }
}
