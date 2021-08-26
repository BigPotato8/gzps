package com.augurit.agmobile.agwater5.gcjs_public;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.GcjsVisitorLoginActivity;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.announce.AnnounceActivity;
import com.augurit.agmobile.agwater5.gcjs_public.bszn.BszntActivity;
import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.common.WatchImageOrPdfActivity;
import com.augurit.agmobile.agwater5.gcjs_public.common.webview.WebViewActivity;
import com.augurit.agmobile.agwater5.gcjs_public.common.webview.WebViewConstant;
import com.augurit.agmobile.agwater5.gcjs_public.login.LoginConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.approveinfo.ApproveInfoActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.eventuploaded.EventUploadedActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.mymaterial.MyMaterialActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.myproject.MyProjectActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.view.ProjectProcessActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.prolist.ProjectListActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.StatsBean;
import com.augurit.agmobile.agwater5.gcjs_public.serach.SearchActivity;
import com.augurit.agmobile.agwater5.gcjs_public.zcfg.ZcfgActivity;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.sp.SharedPreferencesUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.lib.qrcode.QrCoder;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.common.common.model.MenuItem;
import com.augurit.common.common.view.HeaderMenuView;
import com.augurit.common.common.view.ShortcutMenuView;
import com.augurit.common.common.view.adapter.MenuItemAdapter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;
import static com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity.EXTRA_TITLE;

/**
 * 农污巡检首页Fragment
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.sewage
 * @createTime 创建时间 ：2018/8/24
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/24
 * @modifyMemo 修改备注：
 */
public class GcjsPublicFragment extends Fragment implements GcjsPublicContract.View {
    protected View mView, tv_empty, tv_empty1;
    private LinearLayout loading_layout, loading_layout1;
    protected HeaderMenuView header_menu;
    protected ShortcutMenuView shortcut_menu, shortcut_menu_column;
    private Disposable mDynamicScrollDisposable;
    private String mRawFileUrl, mBusinessGuideUrl, mReformFlowUrl, mInformPromiseUrl;
    private SharedPreferencesUtil mPreferencesUtil;
    private CompositeDisposable compositeDisposable;
    private ListView mListView, mListViewCheck;
    GcjsPublicPresenter mPresenter;
    ConvenientBanner mBanner;
    private TextView mValue0, mValue1, mValue2, mValue3,tvMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.gcjs_public_fragment_main, container, false);
        initView();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        mPreferencesUtil = new SharedPreferencesUtil(getActivity());
        mPresenter = new GcjsPublicPresenter(this);

        //读写权限申请
        PermissionUtil.instance().with(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .requestCode(101)
                .check(permissions -> {
                });
        //要设置 AwUrlManager.setServerUrl 和  EasyHttp.getInstance().setBaseUrl 避免找不到baseurl
        if (LoginManager.getInstance().getSettings() == null) {
            LoginSettings settings = new LoginSettings(LoginConstant.SERVER_URL,
                    LoginConstant.SERVER_URL, false);
            LoginManager.getInstance().saveSettings(settings);
            AwUrlManager.setServerUrl(settings.getServerUrl());
            EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());
        } else {
            LoginSettings settings = LoginManager.getInstance().getSettings();
            AwUrlManager.setServerUrl(settings.getServerUrl());
            EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());
        }

        loadMenus();

        //获取网厅功能url
        getMallUrl();


        //获取办公统计
        getStatistical();


        //加载审批信息
        mPresenter.loadApproveNews();

    }


    protected void initView() {
//        GCJSDictRemoteDataSource.getAllDict().subscribe(booleans -> {
//
//        }, Throwable::printStackTrace);
        mValue0 = mView.findViewById(R.id.tv_value_0);
        mValue1 = mView.findViewById(R.id.tv_value_1);
        mValue2 = mView.findViewById(R.id.tv_value_2);
        mValue3 = mView.findViewById(R.id.tv_value_3);
        tvMore = mView.findViewById(R.id.tv_more);
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ApproveInfoActivity.class);
                intent.putExtra(EXTRA_TITLE, "审批列表");
                startActivity(intent);
            }
        });

        header_menu = findViewById(R.id.header_menu);
        shortcut_menu = findViewById(R.id.shortcut_menu);
        shortcut_menu_column = findViewById(R.id.shortcut_menu_column);
        //通知公告
        mListView = findViewById(R.id.dynamic_patrol_lv);
        tv_empty = findViewById(R.id.tv_empty);
        loading_layout = findViewById(R.id.loading_layout);

        mBanner = findViewById(R.id.convenientBanner);
        findViewById(R.id.rl_search).setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));

        findViewById(R.id.btn_scan).setOnClickListener(v -> {
            PermissionUtil.instance()
                    .with(this)
                    .permissions(Manifest.permission.CAMERA)
                    .check(permissions -> QrCoder.openScan(getActivity(), REQ_SCAN));
        });


        findViewById(R.id.btn_more).setOnClickListener(v -> {
            User mCurrentUser = LoginManager.getInstance().getCurrentUser();
            if (mCurrentUser == null || TextUtils.isEmpty(mCurrentUser.getLoginPwd())) {
                startActivityForResult(new Intent(getActivity(), GcjsVisitorLoginActivity.class), 111);
            }
        });

        LoginSettings settings = LoginManager.getInstance().getSettings();
        if (settings != null && settings.isAutoLogin()) {
            User mCurrentUser = LoginManager.getInstance().getCurrentUser();
            TextView tv_login = findViewById(R.id.tv_login);
            tv_login.setText(mCurrentUser != null ? mCurrentUser.getUserName() : "登录");
        }

        initBanner();
    }


    private void getStatistical() {
        //获取政策文件
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_STATS, null)
                .subscribe(s -> {
                            StatsBean bean = new Gson().fromJson(s, new TypeToken<StatsBean>(){}.getType());
                            if(bean !=null &&  bean.isSuccess()){
                                mValue0.setText(bean.getContent().getNowMonthCount()+"");
                                mValue1.setText(bean.getContent().getNowMonthComplete()+"");
                                mValue2.setText(bean.getContent().getAllComplete()+"");
                                mValue3.setText(bean.getContent().getAllCount()+"");
                            }

                }, Throwable::printStackTrace);
        compositeDisposable.add(subscribe);
    }


    private void getMallUrl() {

        //获取政策文件
//        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_RAW_FILE, null)
//                .subscribe(s -> {
//                    MallUrlBean mallUrlBean = new Gson().fromJson(s, new TypeToken<MallUrlBean>() {
//                    }.getType());
//                    if (mallUrlBean != null && mallUrlBean.isSuccess()) {
        mRawFileUrl = AwUrlManager.serverUrl() + GcjsUrlConstant.GET_RAW_FILE;
        mPreferencesUtil.setString(GcjsUrlConstant.GET_RAW_FILE, mRawFileUrl);

        List<MenuItem> shortcutMenus1 = getShortcutMenus();
        shortcut_menu.setMenuItems(shortcutMenus1);
//                    }

//                }, Throwable::printStackTrace);
        //获取办事指南
//        Disposable subscribe1 = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_BUSINESSGUIDE, null)
//                .subscribe(s -> {
//                    MallUrlBean mallUrlBean = new Gson().fromJson(s, new TypeToken<MallUrlBean>() {
//                    }.getType());
//                    if (mallUrlBean != null && mallUrlBean.isSuccess()) {
        mBusinessGuideUrl = AwUrlManager.serverUrl() + GcjsUrlConstant.GET_BUSINESSGUIDE;
        mPreferencesUtil.setString(GcjsUrlConstant.GET_BUSINESSGUIDE, mBusinessGuideUrl);

        List<MenuItem> shortcutMenus2 = getShortcutMenus();
        shortcut_menu.setMenuItems(shortcutMenus2);
//                    }
//                }, Throwable::printStackTrace);
        //获取改革流程图
//        Disposable subscribe2 = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_REFORMFLOW, null)
//                .subscribe(s -> {
//                    MallUrlBean mallUrlBean = new Gson().fromJson(s, new TypeToken<MallUrlBean>() {
//                    }.getType());
//                    if (mallUrlBean != null && mallUrlBean.isSuccess()) {
        mReformFlowUrl = AwUrlManager.serverUrl() + GcjsUrlConstant.GET_REFORMFLOW;
        mPreferencesUtil.setString(GcjsUrlConstant.GET_REFORMFLOW, mReformFlowUrl);

        List<MenuItem> shortcutMenus = getShortcutMenus();
        shortcut_menu.setMenuItems(shortcutMenus);
//                    }
//                }, Throwable::printStackTrace);
//        //获取告知承诺
//        Disposable subscribe3 = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_INFORMPROMISE, null)
//                .subscribe(s -> {
//                    MallUrlBean mallUrlBean = new Gson().fromJson(s, new TypeToken<MallUrlBean>() {
//                    }.getType());
//                    if (mallUrlBean != null && mallUrlBean.isSuccess()) {
//                        mInformPromiseUrl = mallUrlBean.getMessage();
//                        mPreferencesUtil.setString(GcjsUrlConstant.GET_INFORMPROMISE, mInformPromiseUrl);
//
//
//                    }
//                }, Throwable::printStackTrace);

//        compositeDisposable.add(subscribe);
//        compositeDisposable.add(subscribe1);
//        compositeDisposable.add(subscribe2);
//        compositeDisposable.add(subscribe3);

    }

    public static final int REQ_SCAN = 123;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_SCAN) {
            if (resultCode == RESULT_OK) {
                String result = QrCoder.getScanResult(data);
                Log.e("ScanResult", result);
            }
        } else if (resultCode == GcjsVisitorLoginActivity.LOGIN_OK) {
            User mCurrentUser = LoginManager.getInstance().getCurrentUser();
            TextView tv_login = findViewById(R.id.tv_login);
            tv_login.setText(mCurrentUser != null ? mCurrentUser.getUserName() : "");
        }
    }


    //banner初始化
    private void initBanner() {
        List<Integer> localImages = new ArrayList<>();
        localImages.add(R.mipmap.banner1);
        localImages.add(R.mipmap.banner2);
        localImages.add(R.mipmap.banner3);
        mBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public LocalImageHolderView createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_gcjspub_banner;
                    }
                }, localImages)
                .startTurning(5000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_indicator_normal, R.mipmap.ic_indicator_select})
//                .setOnItemClickListener(this);
        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
        ;

//        convenientBanner.setManualPageable(false);//设置不能手动影响

        mBanner.setOnItemClickListener(position -> Log.e("mBanner", "点击了第" + position + "个"));
    }


    protected <T extends View> T findViewById(@IdRes int resId) {
        return mView.findViewById(resId);
    }

    protected void loadMenus() {
//        List<MenuItem> headerMenus = GcjsMenuConfig.getHeaderMenus(getContext());
//        header_menu.setMenuItems(headerMenus);
        List<MenuItem> shortcutMenus = getShortcutMenus();
        List<MenuItem> shortcutMenus1 = getShortcutMenus1();
        shortcut_menu.setMenuItems(shortcutMenus);
        shortcut_menu.setSpanCount(5);
        shortcut_menu_column.setMenuItems(shortcutMenus1);

        shortcut_menu_column.setOnItemClickListener(new MenuItemAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuItem item) {
                User mCurrentUser = LoginManager.getInstance().getCurrentUser();
//                if (position == 0) {  //我要申报按钮
//                    //当本地user不是空而且密码不是空 表示已经登录，否则跳到登录页面
//                    if (mCurrentUser == null || TextUtils.isEmpty(mCurrentUser.getLoginPwd())) {
//                        Intent intent = GcjsVisitorLoginActivity.getIntent(getActivity(), UploadProjectListActivity.class);
//                        intent.putExtra(GcjsVisitorLoginActivity.JUMP_TITLE, "我要申报");
//                        getActivity().startActivity(intent);
//                        return true;
//                    } else {//直接进入GcjsNewPublicActivity 工程建设主页
//                        return false;
//                    }
//                } else

                if (position == 100) {  //已申报按钮
                    //当本地user不是空而且密码不是空 表示已经登录，否则跳到登录页面
                    if (mCurrentUser == null || TextUtils.isEmpty(mCurrentUser.getLoginPwd())) {
                        Intent intent = GcjsVisitorLoginActivity.getIntent(getActivity(), EventUploadedActivity.class);
                        intent.putExtra(GcjsVisitorLoginActivity.JUMP_TITLE, "申报列表");
                        getActivity().startActivity(intent);
                        return true;
                    } else {//直接进入GcjsNewPublicActivity 工程建设主页
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public List<MenuItem> getShortcutMenus() {

        ArrayList<MenuItem> menus = new ArrayList<>();

//        menus.add(new MenuItem(getString(R.string.menu_upload), R.mipmap.ic_gcjs_upload, UploadProjectListActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, getString(R.string.menu_upload)));

//        menus.add(new MenuItem(getString(R.string.menu_uploaded), R.mipmap.ic_gcjs_uploaded, EventUploadedActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, getString(R.string.menu_uploaded)));

//        menus.add(new MenuItem(getString(R.string.menu_meaterial_complete), R.mipmap.ic_meaterial_complete, UploadProjectListActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, getString(R.string.menu_meaterial_complete)));
//
//        menus.add(new MenuItem(getString(R.string.menu_service_consult), R.mipmap.home_report, ConsultActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, getString(R.string.menu_service_consult)));

        if (mRawFileUrl == null) {
            mRawFileUrl = mPreferencesUtil.getString(GcjsUrlConstant.GET_RAW_FILE, "");
        }
        if (mRawFileUrl.toLowerCase().contains(".pdf") || mRawFileUrl.toLowerCase().contains(".png") ||
                mRawFileUrl.toLowerCase().contains(".jpg") || mRawFileUrl.toLowerCase().contains(".jpeg")) {
            ArrayList<String> rawFiles = new ArrayList<>();
            rawFiles.add(mRawFileUrl);

            menus.add(new MenuItem(getString(R.string.menu_rawFile), R.mipmap.rawfile, WatchImageOrPdfActivity.class)
                    .addParam(WatchImageOrPdfActivity.FILES_PATH, rawFiles));
        } else {
            menus.add(new MenuItem(getString(R.string.menu_rawFile), R.mipmap.rawfile, WebViewActivity.class)
                    .addParam(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_rawFile))
                    .addParam(WebViewConstant.WEBVIEW_URL_PATH, mRawFileUrl));
        }


        menus.add(new MenuItem(getString(R.string.menu_fileIndicate), R.mipmap.fileindicate, WebViewActivity.class)
                .addParam(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_fileIndicate))
                .addParam(WebViewConstant.WEBVIEW_URL_PATH, mBusinessGuideUrl == null
                        ? mPreferencesUtil.getString(GcjsUrlConstant.GET_BUSINESSGUIDE, "") : mBusinessGuideUrl));

        if (mReformFlowUrl == null) {
            mReformFlowUrl = mPreferencesUtil.getString(GcjsUrlConstant.GET_REFORMFLOW, "");
        }
        if (mReformFlowUrl.toLowerCase().contains(".pdf") || mReformFlowUrl.toLowerCase().contains(".png") ||
                mReformFlowUrl.toLowerCase().contains(".jpg") || mReformFlowUrl.toLowerCase().contains(".jpeg")) {
            ArrayList<String> reformtreeFiles = new ArrayList<>();
            reformtreeFiles.add(mReformFlowUrl);

            menus.add(new MenuItem(getString(R.string.menu_reformTree), R.mipmap.reformtree, WatchImageOrPdfActivity.class)
                    .addParam(WatchImageOrPdfActivity.FILES_PATH, reformtreeFiles));
        } else {
            menus.add(new MenuItem(getString(R.string.menu_reformTree), R.mipmap.reformtree, WebViewActivity.class)
                    .addParam(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_reformTree))
                    .addParam(WebViewConstant.WEBVIEW_URL_PATH, mReformFlowUrl));
        }

        menus.add(new MenuItem(getString(R.string.menu_informPromise), R.mipmap.informpromise, ZcfgActivity.class)
                .addParam(EXTRA_TITLE, getString(R.string.menu_informPromise)));


        menus.add(new MenuItem("审批情况", R.mipmap.fileindicate, AnnounceActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, "审批情况"));

        return menus;
    }

    public List<MenuItem> getShortcutMenus1() {

        ArrayList<MenuItem> menus = new ArrayList<>();
        menus.add(new MenuItem("已申报项目", R.mipmap.informpromise, EventUploadedActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, "申报列表"));

        menus.add(new MenuItem("项目进度", R.mipmap.ic_gcjs_uploaded, ProjectProcessActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, "项目进度"));

        menus.add(new MenuItem("我的办件", R.mipmap.fileindicate, ProjectListActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, "我的办件"));

        menus.add(new MenuItem("我的材料库", R.mipmap.common_ic_photo, MyMaterialActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, "我的材料库"));


        menus.add(new MenuItem(getString(R.string.menu_fileIndicate), R.mipmap.common_ic_photo, BszntActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, getString(R.string.menu_fileIndicate)));

        menus.add(new MenuItem(getString(R.string.menu_informPromise), R.mipmap.informpromise, ZcfgActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, getString(R.string.menu_informPromise)));

        ArrayList<String> rawFiles = new ArrayList<>();
//        rawFiles.add(AwUrlManager.serverUrl() + GcjsPuUrlConstant.GET_REFORMFLOW);
        rawFiles.add(GcjsPuUrlConstant.GET_REFORMFLOW);
        menus.add(new MenuItem(getString(R.string.menu_reformTree), R.mipmap.reformtree, WatchImageOrPdfActivity.class)
                .addParam(WatchImageOrPdfActivity.FILES_PATH, rawFiles));

        menus.add(new MenuItem("我的项目库", R.mipmap.rawfile, MyProjectActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, "我的项目库"));
//        menus.add(new MenuItem("新增项目", R.mipmap.fileindicate, NewProjectActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, "新增项目"));


//        menus.add(new MenuItem("材料补全", R.mipmap.reformtree, CompleteMaterialActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, "材料补全"));

//        menus.add(new MenuItem("材料补正", R.mipmap.menu_exit, MaterialSupplyActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, "材料补正"));



        return menus;
    }

    @Override
    public void showPatrolDynamicNews(List<ApproveInfoBean> workNewsList) {
        if (ListUtil.isEmpty(workNewsList)) {
            mListView.setVisibility(View.GONE);
            tv_empty.setVisibility(View.VISIBLE);
            return;
        }

        ApproveInfoAdapter mAdapter = new ApproveInfoAdapter(workNewsList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
        });

        startDynamicScroll();
    }

    @Override
    public void showPatrolEventNum(Map<String, Integer> map) {
//        int db = map.get("db");
//        int yb = map.get("yb");
//        header_menu.setBadge(0, 48);
//        header_menu.setBadge(1, yb);
    }

    private void getEvnetNum() {
        HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.GET_DB_COUNT, null)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && !"0".equals(s)) {
                        header_menu.setBadge(0, s);
                    }
                });

        HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.GET_YB_COUNT, null)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && !"0".equals(s)) {
                        header_menu.setBadge(1, s);
                    }
                });

    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onPause() {
        super.onPause();
        stopDynamicScroll();
        if (mBanner != null) {
            mBanner.stopTurning();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopDynamicScroll();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopDynamicScroll();

    }


    @Override
    public void onResume() {
        super.onResume();
        if (mBanner != null) {
            mBanner.startTurning(5000);
        }
        if (getUserVisibleHint()) {
            stopDynamicScroll();
            startDynamicScroll();
            //获取待办已办数量
//            getEvnetNum();
        }
    }


    @Override
    public Context getContext() {
        return getActivity();
    }

    public void startDynamicScroll() {
        if (mListView.getVisibility() == View.VISIBLE) {
            mDynamicScrollDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(aLong -> {
                        mListView.smoothScrollBy(10, 0);
                    }, Throwable::printStackTrace);
        }
    }

    public void stopDynamicScroll() {
        if (mDynamicScrollDisposable != null && !mDynamicScrollDisposable.isDisposed()) {
            mDynamicScrollDisposable.dispose();
        }
    }

    class ApproveInfoAdapter extends BaseAdapter {
        private List<ApproveInfoBean> mWorkNewsList;
        private SimpleDateFormat format;

        public ApproveInfoAdapter(List<ApproveInfoBean> workNewsList) {
            mWorkNewsList = workNewsList;
            format = new SimpleDateFormat("yyyy-MM-dd");
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            String component_type;
            String parent_org_name;
            ApproveInfoAdapter.ViewHolder mHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_worknews, null);
                mHolder = new ApproveInfoAdapter.ViewHolder();
                mHolder.tv_name = convertView.findViewById(R.id.tv_name);
                mHolder.tv_time = convertView.findViewById(R.id.tv_time);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ApproveInfoAdapter.ViewHolder) convertView.getTag();
            }
            ApproveInfoBean aib = mWorkNewsList.get(position % mWorkNewsList.size());
            if (aib != null) {
                mHolder.tv_name.setText(aib.getProjName() + "-" + aib.getIteminstState());

                if (!TextUtils.isEmpty(aib.getStartTime())) {
                    mHolder.tv_time.setText(format.format(new Date(Long.parseLong(aib.getStartTime()))));
                }

            }

            return convertView;
        }

        public class ViewHolder {
            TextView tv_name;
            TextView tv_time;
        }
    }

    public class LocalImageHolderView extends Holder<Integer> {
        private ImageView imageView;

        public LocalImageHolderView(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.iv_post);
        }

        @Override
        public void updateUI(Integer data) {
            imageView.setImageResource(data);
        }
    }
}
