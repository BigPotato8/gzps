package com.augurit.agmobile.agwater5.gcjs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.model.MenuItem;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.common.view.DynamicListView;
import com.augurit.agmobile.agwater5.common.view.HeaderMenuView;
import com.augurit.agmobile.agwater5.common.view.ShortcutMenuView;
import com.augurit.agmobile.agwater5.gcjs.announce.AnnounceDetailActivity;
import com.augurit.agmobile.agwater5.gcjs.announce.AnnounceListActivity;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewActivity;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MallUrlBean;
import com.augurit.agmobile.agwater5.gcjs.home.MainInfoAdapter;
import com.augurit.agmobile.agwater5.gcjs.home.MainNoticeActivity;
import com.augurit.agmobile.agwater5.gcjs.home.model.MainInfoModel;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.agwater5.gcjs.model.CheckIndexBean;
import com.augurit.agmobile.agwater5.gcjs.model.TasksCountBean;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.agwater5.gcjs.serach.SearchActivity;
import com.augurit.agmobile.agwater5.mine.MineActivity;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.sp.SharedPreferencesUtil;
import com.augurit.agmobile.common.lib.time.TimeUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.lib.qrcode.QrCoder;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import q.rorbin.badgeview.QBadgeView;

import static android.app.Activity.RESULT_OK;

/**
 * 首页Fragment
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.sewage
 * @createTime 创建时间 ：2018/8/24
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/24
 * @modifyMemo 修改备注：
 */
public class GcjsFragment extends Fragment implements GcjsContract.View {
    protected View mView, tv_empty, tv_empty1;
    protected HeaderMenuView header_menu;
    protected ShortcutMenuView shortcut_menu;
    private DynamicListView mListView;
    private ListView mListViewCheck;
//    private LinearLayout loading_layout, loading_layout1;
    private Disposable mDynamicScrollDisposable;
    private String mRawFileUrl, mBusinessGuideUrl, mReformFlowUrl, mInformPromiseUrl;
    private SharedPreferencesUtil mPreferencesUtil;
    private TextView mValue0, mValue1, mValue2, mValue3, tv_announce_more;
    private CompositeDisposable compositeDisposable;
    private List<Announce.RowsBean> mRows;
    ConvenientBanner mBanner;
    private ImageView mIvNoticeNum;
    private RelativeLayout mBtnNotice;
    private ImageView mMainInfo;
    private RecyclerView main_info_rv;
    private LinearLayout mLlTitleMeasure;
    private LinearLayout mLlPost;
    private int mHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.gcjs_fragment_main, container, false);
        mValue0 = mView.findViewById(R.id.tv_value_0);
        mValue1 = mView.findViewById(R.id.tv_value_1);
        mValue2 = mView.findViewById(R.id.tv_value_2);
        mValue3 = mView.findViewById(R.id.tv_value_3);
        mIvNoticeNum = mView.findViewById(R.id.iv_notice_num);
        mBtnNotice = mView.findViewById(R.id.btn_notice);
        mMainInfo = mView.findViewById(R.id.iv_main_info);
        main_info_rv = mView.findViewById(R.id.main_info_rv);
        mLlTitleMeasure = mView.findViewById(R.id.ll_title_measure);
        mLlPost = mView.findViewById(R.id.ll_post);
        initView();
        return mView;
    }

    // View宽，高
    public int[] getLocation(View v) {
        int[] loc = new int[4];
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        loc[0] = location[0];
        loc[1] = location[1];
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(w, h);

        loc[2] = v.getMeasuredWidth();
        loc[3] = v.getMeasuredHeight();

        //base = computeWH();
        return loc;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handleViewAndMeasure();
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 处理待办信息界面展示以及获取高度动态渲染
     */
    private void handleViewAndMeasure() {
        // 测量高度
        //48标题+50待办消息标题+50底部导航+20最上边的状态栏 --->单位为dp
        mHeight = 48 + 50 +20 + 50;
        int height = mLlPost.getHeight();
        int height2 = header_menu.getHeight();
        int[] location = getLocation(mLlPost);
        int[] locationMenu = getLocation(header_menu);
        int height1 = px2dip(getContext(),location[3]);
        int locationMenu1 = px2dip(getContext(),locationMenu[3]);
        mHeight = height1 + mHeight +locationMenu1;
        List<MainInfoModel> modelList = new ArrayList<>();
        int screenHeight = px2dip(getContext(),getScreenHeight(getContext())); //返回px
        int realHeight = screenHeight - mHeight;
        int num = realHeight / 45;
        for (int i = 0; i < num; i++) {
            MainInfoModel mainInfoModel = new MainInfoModel("你有一条【大胶带的骄傲的骄傲】项目待办接的近似解答");
            modelList.add(mainInfoModel);
        }
        MainInfoAdapter mainInfoAdapter = new MainInfoAdapter(getContext(),modelList);
        main_info_rv.setAdapter(mainInfoAdapter);
        main_info_rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        //待办信息中的未读信息
        new QBadgeView(getContext())
                .bindTarget(mMainInfo)
                .setBadgeNumber(num)
                .setShowShadow(false);


    }

    /**
     * 获得屏幕高度（不包含底部导航栏）
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics.heightPixels;
    }

    /**
     * 获得屏幕真实高度（包含底部导航栏）
     */
    public static int getScreenRealHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        int mRealSizeHeight;//手机屏幕真实高度
        mRealSizeHeight = outPoint.y;
        return mRealSizeHeight;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        mPreferencesUtil = new SharedPreferencesUtil(getActivity());


        loadMenus();

        //获取网厅功能url
        getMallUrl();

        //获取办公统计
        getStatistical();

//        //获取审核情况
//        getCheckList();

        //通知公告
        getAnnounce();

    }

    private void getAnnounce() {
//        loading_layout.setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();
        map.put("pageSize", 10 + "");
        map.put("pageNum", 1 + "");
        //获取政策文件
//        Disposable subscribe = HttpUtil.getInstance("http://192.168.30.125:8087/").get(GcjsUrlConstant.ANNOUNCE_LIST, map)
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.ANNOUNCE_LIST, map)
                .subscribe(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult<Announce> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<Announce>>() {
                        }.getType());
                        Announce data = apiResult.getData();
                        if (data != null) {
                            mRows = data.getRows();
                            showAnnounce(data.getRows());
                        }
//                        tv_empty.setVisibility(View.GONE);
                    } else {
//                        tv_empty.setVisibility(View.VISIBLE);
                    }
//                    loading_layout.setVisibility(View.GONE);
                }, Throwable::printStackTrace);
        compositeDisposable.add(subscribe);
    }

//    private void getCheckList() {
//        loading_layout1.setVisibility(View.VISIBLE);
//        Map<String,String> map = new HashMap<>();
//        map.put("pageNum",10+"");
//        map.put("pageSize",1+"");
//        //获取政策文件
//        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl())
//                .get(GcjsUrlConstant.CHECK_INDEX_LIST, map)
//                .subscribe(s -> {
//                    if (StringUtils.isJson(s)) {
//                        ApiResult<List<CheckIndexBean>> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<List<CheckIndexBean>>>() {
//                        }.getType());
//                        List<CheckIndexBean> data = apiResult.getData();
//                        showCheckList(data);
//                        loading_layout1.setVisibility(View.GONE);
//                    }
//                });
//        compositeDisposable.add(subscribe);
//    }


    private void getStatistical() {
        //获取政策文件
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_STATISTICALANALYSIS, null)
                .subscribe(s -> {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            String message = jsonObject.getString("message");
                            String replaceJson = message.replace("\\\"", "");
//                            JSONObject resultObject = new JSONObject(replaceJson);
                            JSONObject result = jsonObject.getJSONObject("content");
                            String nowMonthComplete = result.getString("nowMonthComplete");
                            String nowMonthCount = result.getString("nowMonthCount");
                            String allItemInstComplete = result.getString("allComplete");
                            String allItemInstCount = result.getString("allCount");
                            mValue0.setText(nowMonthComplete);
                            mValue1.setText(nowMonthCount);
                            mValue2.setText(allItemInstComplete);
                            mValue3.setText(allItemInstCount);
                        }
                    } catch (Exception e) {
                        ToastUtil.shortToast(GcjsFragment.this.getActivity(), "获取办件统计数据失败");
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
        mRawFileUrl = AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_RAW_FILE;
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
        mBusinessGuideUrl = AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_BUSINESSGUIDE;
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
        mReformFlowUrl = AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_REFORMFLOW;
        mPreferencesUtil.setString(GcjsUrlConstant.GET_REFORMFLOW, mReformFlowUrl);

        List<MenuItem> shortcutMenus = getShortcutMenus();
        shortcut_menu.setMenuItems(shortcutMenus);
//                    }
//                }, Throwable::printStackTrace);
        //获取告知承诺
        Disposable subscribe3 = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_INFORMPROMISE, null)
                .subscribe(s -> {
                    MallUrlBean mallUrlBean = new Gson().fromJson(s, new TypeToken<MallUrlBean>() {
                    }.getType());
                    if (mallUrlBean != null && mallUrlBean.isSuccess()) {
                        mInformPromiseUrl = mallUrlBean.getMessage();
                        mPreferencesUtil.setString(GcjsUrlConstant.GET_INFORMPROMISE, mInformPromiseUrl);


                    }
                }, Throwable::printStackTrace);

//        compositeDisposable.add(subscribe);
//        compositeDisposable.add(subscribe1);
//        compositeDisposable.add(subscribe2);
        compositeDisposable.add(subscribe3);

    }

    public static final int REQ_SCAN = 123;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_SCAN) {
            if (resultCode == RESULT_OK) {
                String result = QrCoder.getScanResult(data);
            }
        }
    }

    protected void initView() {
        header_menu = findViewById(R.id.header_menu);
        shortcut_menu = findViewById(R.id.shortcut_menu);
        //通知公告
        mListView = findViewById(R.id.dynamic_patrol_lv);
//        tv_empty = findViewById(R.id.tv_empty);
//        loading_layout = findViewById(R.id.loading_layout);

        // 标题栏上的图标数字
        new QBadgeView(getContext())
                .bindTarget(mIvNoticeNum)
                .setBadgeNumber(10)
                .setShowShadow(false);

        mBtnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainNoticeActivity.class);
                startActivity(intent);
            }
        });

//        //审核情况
//        mListViewCheck = findViewById(R.id.dynamic_check_lv);
//        tv_empty1 = findViewById(R.id.tv_empty1);
//        loading_layout1 = findViewById(R.id.loading_layout1);

        mBanner = findViewById(R.id.convenientBanner);

        findViewById(R.id.rl_search).setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)));
        findViewById(R.id.btn_scan).setOnClickListener(v -> {
            PermissionUtil.instance()
                    .with(this)
                    .permissions(Manifest.permission.CAMERA)
                    .check(permissions -> QrCoder.openScan(getActivity(), REQ_SCAN));
        });

        findViewById(R.id.btn_more).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MineActivity.class));
        });

        findViewById(R.id.tv_announce_more).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AnnounceListActivity.class));
        });
        initBanner();

    }


    protected <T extends View> T findViewById(@IdRes int resId) {
        return mView.findViewById(resId);
    }

    protected void loadMenus() {
        List<MenuItem> headerMenus = GcjsMenuConfig.getHeaderMenus(getContext());
        header_menu.setMenuItems(headerMenus);
        List<MenuItem> shortcutMenus = getShortcutMenus();
        shortcut_menu.setMenuItems(shortcutMenus);
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
                        return R.layout.item_gcjs_banner;
                    }
                }, localImages)
                .startTurning(3000)
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

    /*@Override
    public void onPause() {
        super.onPause();
        stopDynamicScroll();
        if (mBanner != null) {
            mBanner.stopTurning();
        }
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
    }*/

    public List<MenuItem> getShortcutMenus() {

        ArrayList<MenuItem> menus = new ArrayList<>();

        if (mRawFileUrl == null) {
            mRawFileUrl = mPreferencesUtil.getString(GcjsUrlConstant.GET_RAW_FILE, "");
        }
        if (mRawFileUrl.toLowerCase().contains(".pdf") || mRawFileUrl.toLowerCase().contains(".png") ||
                mRawFileUrl.toLowerCase().contains(".jpg") || mRawFileUrl.toLowerCase().contains(".jpeg")) {
            ArrayList<String> rawFiles = new ArrayList<>();
            rawFiles.add(mRawFileUrl);

            menus.add(new MenuItem(getString(R.string.menu_rawFile), R.mipmap.rawfile, WatchImageOrPdfActivity.class)
                    .addParam(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_rawFile))
                    .addParam(WatchImageOrPdfActivity.FILES_PATH, rawFiles));
//            menus.add(new MenuItem(getString(R.string.menu_rawFile), R.mipmap.rawfile, null));
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
                    .addParam(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_reformTree))
                    .addParam(WatchImageOrPdfActivity.FILES_PATH, reformtreeFiles));
//            menus.add(new MenuItem(getString(R.string.menu_reformTree), R.mipmap.reformtree, null));
        } else {
            menus.add(new MenuItem(getString(R.string.menu_reformTree), R.mipmap.reformtree, WebViewActivity.class)
                    .addParam(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_reformTree))
                    .addParam(WebViewConstant.WEBVIEW_URL_PATH, mReformFlowUrl));
        }

//        menus.add(new MenuItem(getString(R.string.menu_informPromise), R.mipmap.informpromise, WebViewActivity.class)
//                .addParam(WebViewConstant.WEBVIEW_SHARE_BUTTON_SHOW,true)
//                .addParam(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_informPromise))
//                //TODO 这里需要还原
//                .addParam(WebViewConstant.WEBVIEW_URL_PATH, mInformPromiseUrl == null
//                        ? mPreferencesUtil.getString(GcjsUrlConstant.GET_INFORMPROMISE, "") : mInformPromiseUrl));
//                .addParam(WebViewConstant.WEBVIEW_URL_PATH, "http://www.baidu.com"));
        //审批版暂现无政策法规
//        menus.add(new MenuItem(getString(R.string.menu_informPromise), R.mipmap.informpromise, ZczyActivity.class)
//                .addParam(AwFormActivity.EXTRA_TITLE, getString(R.string.menu_informPromise)));
        return menus;
    }



    private void showAnnounce(List<Announce.RowsBean> workNewsList) {
        if (ListUtil.isEmpty(workNewsList)) {
            mListView.setVisibility(View.GONE);
//            tv_empty.setVisibility(View.VISIBLE);
            return;
        }

        AnnounceAdapter mPatrolDynamicAdapter = new AnnounceAdapter(workNewsList);
        mListView.setAdapter(mPatrolDynamicAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(getActivity(), AnnounceDetailActivity.class);
            intent.putExtra("data", mRows != null && mRows.size() > position ? mRows.get(position) : null);
            startActivity(intent);

        });

    }



    private void getEvnetNum() {
        HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_All_COUNT)
                .subscribe(s -> {
                   ApiResult<TasksCountBean>result = new Gson().fromJson(s,new TypeToken<ApiResult<TasksCountBean>>() {}.getType());
                    try {
                        if(result.isSuccess()){
                            TasksCountBean bean = result.getData();
                            header_menu.setBadge(0, "0".equals(bean.getToDoTotal())?"0":bean.getToDoTotal());
                            header_menu.setBadge(1, "0".equals(bean.getOverDueTotal())?"0":bean.getOverDueTotal());
                            header_menu.setBadge(2, "0".equals(bean.getWarningTotal())?"0":bean.getWarningTotal());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        /*HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.GET_DB_COUNT, null)
                .subscribe(s -> {
                    try {
                        int i = Integer.parseInt(s);
                        header_menu.setBadge(0, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.GET_YB_COUNT, null)
                .subscribe(s -> {
                    try {
                        int i = Integer.parseInt(s);
                        header_menu.setBadge(1, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        Map<String, String> param = new HashMap<>();
        param.put("pageNum", 1 + "");
        param.put("pageSize", 1 + "");

        HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.PERSON_MSG_LIST, param)
                .subscribe(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult<MsgBean> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<MsgBean>>() {
                        }.getType());
                        MsgBean data = apiResult.getData();

                        if (data != null) {
                            int num = 0;
                            for (MsgBean.RowsBean rowsBean : data.getRows()) {
                                if ("0".equals(rowsBean.getIsRead())) {
                                    num++;
                                }
                            }
                            header_menu.setBadge(3, num);
                        }
                    }
                }, Throwable::printStackTrace);*/


    }


    @Override
    public void showLoading() {
//        loading_layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
//        loading_layout.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stopDynamicScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopDynamicScroll();
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
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            stopDynamicScroll();
            startDynamicScroll();
            //获取待办已办数量
            getEvnetNum();
        }
    }




    @Override
    public Context getContext() {
        return getActivity();
    }

    public void startDynamicScroll() {
//        mDynamicScrollDisposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
//                .subscribe(aLong -> mListViewCheck.smoothScrollBy(10, 0), Throwable::printStackTrace);
    }

    public void stopDynamicScroll() {
//        if (mDynamicScrollDisposable != null && !mDynamicScrollDisposable.isDisposed()) {
//            mDynamicScrollDisposable.dispose();
//        }
    }


    class CheckDynamicAdapter extends BaseAdapter {
        private List<CheckIndexBean> mWorkNewsList;

        public CheckDynamicAdapter(List<CheckIndexBean> workNewsList) {
            mWorkNewsList = workNewsList;
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
            ViewHolder mHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_worknews, null);
                mHolder = new ViewHolder();
                mHolder.tv_name = convertView.findViewById(R.id.tv_name);
                mHolder.tv_time = convertView.findViewById(R.id.tv_time);
                mHolder.tv_item = convertView.findViewById(R.id.tv_item);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            mHolder.tv_item.setVisibility(View.VISIBLE);
            mHolder.tv_name.setText(mWorkNewsList.get(position % mWorkNewsList.size()).getProjName());
            mHolder.tv_item.setText(mWorkNewsList.get(position % mWorkNewsList.size()).getItemName());
            mHolder.tv_time.setText(mWorkNewsList.get(position % mWorkNewsList.size()).getIteminstState());

            return convertView;
        }

        public class ViewHolder {
            TextView tv_name;
            TextView tv_item;
            TextView tv_time;
        }
    }

    class AnnounceAdapter extends BaseAdapter {
        private List<Announce.RowsBean> mWorkNewsList;

        public AnnounceAdapter(List<Announce.RowsBean> workNewsList) {
            mWorkNewsList = workNewsList;
        }

        @Override
        public int getCount() {
            return mWorkNewsList == null ? 0 : mWorkNewsList.size();
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
            ViewHolder mHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_worknews, null);
                mHolder = new ViewHolder();
                mHolder.tv_name = convertView.findViewById(R.id.tv_name);
                mHolder.tv_time = convertView.findViewById(R.id.tv_time);
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }

            mHolder.tv_name.setText(mWorkNewsList.get(position).getContentTitle());
            mHolder.tv_time.setText(TimeUtil.getStringTimeYMD(new Date(mWorkNewsList.get(position).getPublishTime())));

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
