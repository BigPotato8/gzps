package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.view.DoMorePopupAdapter;
import com.augurit.agmobile.agwater5.common.view.DoMorePopupWindow;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClbzDetailBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventClbzItemBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.TscxBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.ApproveStateBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.augurit.agmobile.common.view.videopicker.utils.ScreenUtils;
import com.augurit.common.common.util.StringUtil;
import com.google.gson.Gson;

import org.codehaus.jackson.node.BooleanNode;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;


/**
 * @description ????????????????????????
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class EventApprovalActivity extends AppCompatActivity {
    private int mFormState = EventState.HANDLING;
    public final static int FINISH_CODE = 1688;
    private ViewPager view_pager;
    private ViewGroup btn_container;
    private Button btn_save;
    private Button btn_submit;
    private Button btn_delete;
    private Button btn_edit;
    private ImageView iv_more;
    private ImageView iv_tab_index;//???????????????
    private TitleBar mTitleBar;
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;
    private EventListItem.DataBean mEventListItem;
    private String correctId;
    //private ClfjFragment mClfjFragment;
    //private ClfjListFragment mClfjListFragment;
//    private PwpfListFragment mPwpfListFragment;
//    private ZjzzListFragment mZjzzListFragment;

    //    private ResultGoodsFragment mResultGoodsFragment;
    private List<ResultGoodsBean> resultGoodsBeanAll;//?????????
    private List<ResultGoodsBean> resultGoodsBeans;//?????????

    private ResultGoodsMaterialFragment mResultGoodsFragment;//?????????????????????

    //????????????
    private MaterialListFragment materialListFragment;

    private TabLayout tab_layout;
    private EventInfoBean eventInfoBean;
    private BaseInfoFragment baseInfoFragment;//????????????
    private ClbzListFragment mClbzListFragment;//????????????
    private TscxListFragment mTscxListFragment;//????????????
    private int currentIndex;
    private int mScrollX = 0;
    private int mOldScrollX = 0;

    private EventRepository mRepository;
    private MyPageAdapter adapter;

    public static Intent newIntent(@EventState int state, EventListItem.DataBean data) {
        Intent intent = new Intent();
        intent.putExtra("state", state);
        intent.putExtra("data", data);
        return intent;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_gcjs_table);
        initArguments();
        initView();
        if(mEventListItem != null){
            if ("1".equals(mEventListItem.getIsInformPromise())) {
                mTitleBar.getView().findViewById(R.id.tv_promise).setVisibility(View.VISIBLE);
            }
            mTitleBar.setTitleText( mEventListItem.getProjName());
            if ("9".equals(mEventListItem.getIteminstState()) || "10".equals(mEventListItem.getIteminstState())) {
                btn_save.setEnabled(false);
                btn_save.setText("??????????????????????????????????????????");
                btn_edit.setEnabled(false);
                btn_edit.setText("??????????????????????????????????????????");
            } else if ("6".equals(mEventListItem.getIteminstState()) || "7".equals(mEventListItem.getIteminstState())) {
                btn_edit.setEnabled(false);
                btn_edit.setText("?????????????????????????????????????????????");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
//            TabLayout.Tab tab = tab_layout.getTabAt(0);
//            initIndicatorByTab(0, 0);
        }
    }

    //????????????????????????
    private void initIndicatorByTab(int position, float positionOffset) {
        TabLayout.Tab tab = tab_layout.getTabAt(position);
        if (tab != null && tab.getCustomView() != null) {
            int[] locations = new int[2];
            tab.getCustomView().getLocationOnScreen(locations);
            int x = locations[0];
            int[] locationNext = new int[2];
            TabLayout.Tab tab2 = tab_layout.getTabAt(position + 1);
            int width = tab.getCustomView().getWidth();
            int indicatorStart = x + width / 2;//??????????????????
            int indicatorEnd = x + width + width / 2;//??????????????????
            float x1 = 0;
            if (tab2 != null && tab2.getCustomView() != null) {//????????????
                tab2.getCustomView().getLocationOnScreen(locationNext);
                indicatorEnd = locationNext[0] + tab2.getCustomView().getWidth() / 2;
            }
            x1 = indicatorStart + (indicatorEnd - indicatorStart) * positionOffset;
            Log.e("initIndicatorByTab", x1 + "");
            initIndicator((int) x1);
        }
    }

    private void initArguments() {
        Intent intent = getIntent();
        if (intent != null) {
            mFormState = intent.getIntExtra("state", EventState.HANDLING);
            mEventListItem = (EventListItem.DataBean) intent.getSerializableExtra("data");
            if (mEventListItem == null) {
                String dataJson = intent.getStringExtra("dataJson");
                if (!StringUtil.isEmpty(dataJson)) {
                    mEventListItem = new Gson().fromJson(dataJson, EventListItem.DataBean.class);
                }
            }
        }
        mRepository = new EventRepository();
//        resultGoodsBeanAll = new ArrayList<>();
//        resultGoodsBeans = new ArrayList<>();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);

        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        btn_container = findViewById(R.id.btn_container);
        btn_delete = findViewById(R.id.btn_delete);
        btn_save = findViewById(R.id.btn_save);
        btn_submit = findViewById(R.id.btn_submit);
        btn_edit = findViewById(R.id.btn_edit);
        iv_more = findViewById(R.id.iv_more);

        //???????????????
        if (EventState.HANDLING == mFormState) {
            btn_container.setVisibility(View.VISIBLE);
        } else {
            btn_container.setVisibility(View.GONE);
        }
        //????????????
        mTitles = new ArrayList<>();
        mTitles.add(getString(R.string.gcjs_title_baseInfo));
//        mTitles.add(getString(R.string.gcjs_title_jgw));
//        mTitles.add(getString(R.string.gcjs_title_pwpf));
//        mTitles.add(getString(R.string.gcjs_title_zjzz));
//        mTitles.add(getString(R.string.gcjs_title_clbz));

        mFragments = new ArrayList<>();
        baseInfoFragment = new BaseInfoFragment();
        baseInfoFragment.setEventListItem(mEventListItem);
        mFragments.add(baseInfoFragment);
//        mResultGoodsFragment = ResultGoodsFragment.getInstance(mEventListItem);
//        mFragments.add(mResultGoodsFragment);
//        mPwpfListFragment = PwpfListFragment.getInstance(mEventListItem);
//        mFragments.add(mPwpfListFragment);
//        mZjzzListFragment = ZjzzListFragment.getInstance(mEventListItem);
//        mFragments.add(mZjzzListFragment);
//        mClbzListFragment = ClbzListFragment.getInstance(mEventListItem.getTaskId());
//        mFragments.add(mClbzListFragment);

        //????????????
        mTitles.add(getString(R.string.gcjs_title_ytcl));
        materialListFragment = MaterialListFragment.getInstance(mEventListItem != null
                ? mEventListItem.getApplyType() : "");//????????????
        mFragments.add(materialListFragment);

        mTitles.add(getString(R.string.gcjs_title_jgwbc));//?????????????????????
        mResultGoodsFragment = new ResultGoodsMaterialFragment();
        mResultGoodsFragment = ResultGoodsMaterialFragment.getInstance(mEventListItem != null
                ?mEventListItem.getTaskId():"");
        mFragments.add(mResultGoodsFragment);

        mTitles.add(getString(R.string.gcjs_title_spxq));
        AdviceListFragment adviceListFragment = new AdviceListFragment();//????????????(????????????)
        adviceListFragment.setEventListItem(mEventListItem, eventInfoBean);
        mFragments.add(adviceListFragment);

        adapter = new MyPageAdapter(getSupportFragmentManager());
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(mFragments.size() + 2);

        for (String title : mTitles) {
            TabLayout.Tab tab = tab_layout.newTab();
            View inflate = View.inflate(this, R.layout.item_gcjs_tab, null);
            TextView textView = inflate.findViewById(R.id.tv_tab_name);
            textView.setText(title);
            tab.setCustomView(inflate);
            tab_layout.addTab(tab);
        }
        //tab_layout.setupWithViewPager(view_pager);
        tab_layout.setTabTextColors(0xff666666, 0xff0191F2);
        tab_layout.setSelectedTabIndicatorColor(0xff0187F2);//???????????????
        //View tabStripView = tab_layout.getChildAt(0);//?????????view
        tab_layout.setSelectedTabIndicatorHeight(0);//???????????????0
        iv_tab_index = findViewById(R.id.iv_tab_index);

        initViewPagerChangeListen();//tab????????????
//        TitleBar tb = findViewById(R.id.title_bar);
//        tb.getChildAt(0).setBackgroundResource(R.drawable.gcjs_approval_titlebar_gradient);//??????
//        btn_delete.setOnClickListener(v -> delete());

        btn_save.setOnClickListener(v -> {
            if (eventInfoBean.getIteminstList() != null && eventInfoBean.getIteminstList().size() > 0
                    && "6".equals(eventInfoBean.getIteminstList().get(0).getIteminstState())
            ) {//"6",???????????????????????????
                //???????????????????????????????????????
                ToastUtil.longToast(this, "???????????????????????????????????????");
                return;
            }
            if (eventInfoBean.getIteminstList() != null && eventInfoBean.getIteminstList().size() > 0
                    && "7".equals(eventInfoBean.getIteminstList().get(0).getIteminstState())
            ) {//7??????????????????????????????
                //???????????????????????????????????????
                ToastUtil.longToast(this, "???????????????????????????????????????????????????");
                return;
            }
            if (eventInfoBean != null && eventInfoBean.getIteminstList() != null) {
                Intent intent = new Intent(EventApprovalActivity.this, MaterialCorrectionActivity.class);
                intent.putExtra("taskInstId", mEventListItem != null
                        ?mEventListItem.getTaskId():"");
                intent.putExtra("projInfoId", eventInfoBean.getProjInfoList().get(0).getProjInfoId());
                intent.putExtra("applyinstId", mEventListItem.getApplyinstId());
                intent.putExtra("iteminstId", eventInfoBean.getIteminstList().get(0).getIteminstId());
                intent.putExtra("mEventListItem",mEventListItem);
//                intent.putExtra("correctId",correctId);
                startActivityForResult(intent, FINISH_CODE);
            } else {
                ToastUtil.longToast(EventApprovalActivity.this, "???????????????????????????????????????");
            }

        });
//        btn_submit.setOnClickListener(v -> submit());
        btn_edit.setOnClickListener(v -> {
            setButtonState(mFormState);
            if (mEventListItem != null) {
                //???????????????6??????????????????7?????????????????????????????????8????????????????????????????????????????????????5??????????????????
                if (eventInfoBean.getIteminstList() != null && eventInfoBean.getIteminstList().size() > 0
                        && "6".equals(eventInfoBean.getIteminstList().get(0).getIteminstState())) {//"6",???????????????????????????
                    //???????????????????????????????????????
                    ToastUtil.longToast(this, "???????????????????????????????????????");
                    return;
                }
                if (eventInfoBean.getIteminstList() != null && eventInfoBean.getIteminstList().size() > 0
                        && "7".equals(eventInfoBean.getIteminstList().get(0).getIteminstState())
                ) {//7??????????????????????????????
                    //???????????????????????????????????????
                    ToastUtil.longToast(this, "???????????????????????????????????????????????????");
                    return;
                }
                Intent intent = new Intent(EventApprovalActivity.this, SendNextLinkFormActivity.class);
                intent.putExtra("mEventListItem", mEventListItem);
                intent.putExtra("parallelSingleItem", eventInfoBean.getParallelSingleItem());
                intent.putExtra("taskInstId", mEventListItem != null
                        ?mEventListItem.getTaskId():"");
                intent.putExtra("taskName", mEventListItem.getTaskName());
                intent.putExtra("viewId", mEventListItem.getViewId());
                startActivityForResult(intent, FINISH_CODE);
            } else {
                ToastUtil.shortToast(EventApprovalActivity.this, "??????????????????");
            }
        });
        setButtonState(mFormState);

        //?????????????????????????????????isApprover???????????????????????????
//        mRepository.getApproveStateBean(mEventListItem.getApplyinstId(), mEventListItem.getTaskId())
//                .subscribe(new Consumer<ApiResult<ApproveStateBean>>() {
//                    @Override
//                    public void accept(ApiResult<ApproveStateBean> apiResult) throws Exception {
//                        if (apiResult.isSuccess() && apiResult.getData() != null) {
//                            if ("0".equals(apiResult.getData().getIsApprover())) {//??????
//                                iv_more.setVisibility(View.GONE);
//                            } else if ("1".equals(apiResult.getData().getIsApprover())) {//??????
//                                iv_more.setVisibility(View.GONE);
//                                iv_more.setOnClickListener(v -> {
//                                    //todo ???????????????????????????????????????
//                                    DoMorePopupWindow doMorePopupWindow = new DoMorePopupWindow(EventApprovalActivity.this, iv_more);
//                                    List<String> list = new ArrayList<>();
//                                    list.add("??????");
//                                    doMorePopupWindow.setButtons(list, (button, position) -> {
//                                        switch (button.getText().toString()) {
//                                            case "??????":
//                                                Log.d("popup click", "??????");
//                                                //todo ??????????????????????????????????????????????????????
//                                                Intent intent = new Intent(EventApprovalActivity.this, TransferActivity.class);
//                                                intent.putExtra(TransferActivity.TASK_ID, mEventListItem.getTaskId());
//                                                startActivityForResult(intent, FINISH_CODE);
//                                                doMorePopupWindow.dismiss();
//                                                break;
//                                        }
//                                    });
//                                    doMorePopupWindow.show();
//                                });
//                            } else {
//                                iv_more.setVisibility(View.GONE);
//                            }
//
//                        }
//                    }
//                }, throwable -> throwable.printStackTrace());


    }

    //????????????????????????
    private void initIndicator(int currentIndex) {
        this.currentIndex = currentIndex;
        int size = mTitles.size();
        int widthIV = getWindowManager().getDefaultDisplay().getWidth();
        int heightIV = ScreenUtils.dp2px(this, 3);//iv_tab_index?????????
        int widthTriangle = ScreenUtils.dp2px(this, 8);//????????????????????????
        int viewWidth = widthIV / size * (size * 2 - 1);

        Bitmap newb = Bitmap.createBitmap(widthIV, heightIV, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newb);
        LinearGradient lg = new LinearGradient(0, 0, widthIV, heightIV, new int[]{0xff36a3f7, 0xff36a3f7}, null, Shader.TileMode.REPEAT);//????????????
        Paint paint = new Paint();
        paint.setShader(lg);//???????????????
        canvas.drawRect(new Rect(0, 0, widthIV, heightIV), paint);//????????????
        //?????????????????????
        Paint p2 = new Paint();
        p2.setColor(0x00ffffff);
        p2.setAlpha(0);
        p2.setAntiAlias(true);
        p2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        Path path = new Path();
        path.moveTo(currentIndex - widthTriangle / 2, heightIV);
        path.lineTo(currentIndex, 0);
        path.lineTo(currentIndex + widthTriangle / 2, heightIV);
        path.lineTo(currentIndex - widthTriangle / 2, heightIV);
        canvas.drawPath(path, p2);//??????????????????????????????

        iv_tab_index.setImageBitmap(newb);
    }

    //?????????viewpager???tablayout
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViewPagerChangeListen() {
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageScrolled", "position: " + position + "   positionOffset: " + positionOffset + "   positionOffsetPixels: " + positionOffsetPixels);
                if (position == -1) return;
                TabLayout.Tab tab = tab_layout.getTabAt(position);
                initIndicatorByTab(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == -1) return;
                tab_layout.setScrollPosition(position, 0f, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
                initIndicatorByTab(tab.getPosition(), 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        ??????tab?????????????????????
        tab_layout.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            Log.e("OnScroll", scrollX + "   " + scrollY + "   " + oldScrollX + "   " + oldScrollY);
            if (mScrollX != scrollX && mOldScrollX != oldScrollX
                    || (mScrollX == 0 && mOldScrollX == 0)
            ) {
                mScrollX = scrollX;
                mOldScrollX = oldScrollX;
                currentIndex = currentIndex - scrollX + oldScrollX;
                initIndicator(currentIndex);
            }
        });

    }

    /**
     * ??????????????????
     *
     * @param formState ????????????
     */
    private void setButtonState(int formState) {
        switch (mFormState) {
            case EventState.HANDLING:
                btn_delete.setVisibility(View.GONE);
                btn_save.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.GONE);
                btn_edit.setVisibility(View.VISIBLE);
                iv_more.setVisibility(View.GONE);
                btn_edit.setText("??????");
                btn_save.setText("????????????");
                break;
        }
    }


    private class MyPageAdapter extends FragmentPagerAdapter {

        MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FINISH_CODE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
        //????????????????????????
        if (requestCode == FINISH_CODE && resultCode == MaterialCorrectionActivity.POST_SUCCESS) {
            setResult(RESULT_OK);
            finish();
        }
        //????????????FINISH_CODE???????????????????????????
        if (requestCode == FINISH_CODE && resultCode == FINISH_CODE) {
            setResult(RESULT_OK);
            finish();
        }

//        else if ((requestCode == FjsbclFragment.FJ_SBCL_REQUEST || requestCode == FjpwpfFragment.FJ_PWPF_REQUEST) && resultCode == RESULT_OK) {
//            if (mClfjFragment != null) {
//                mClfjFragment.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//        else if (requestCode == ZjzzListFragment.FLASH_CODE && resultCode == RESULT_OK) {
//            if (mZjzzListFragment != null) {
//                mZjzzListFragment.onActivityResult(requestCode, resultCode, data);
//            }
//        } else if (requestCode == 456 && resultCode == RESULT_OK) {
//            if (mPwpfListFragment != null) {
//                mPwpfListFragment.onActivityResult(requestCode, resultCode, data);
//            }
//        }
        if (baseInfoFragment != null) {
            baseInfoFragment.onActivityResult(requestCode, resultCode, data);
        }
        if (mResultGoodsFragment != null) {
            mResultGoodsFragment.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Subscribe
    public void onEventDetailInfo(EventInfoBean eventInfoBean) {
        this.eventInfoBean = eventInfoBean;
        //????????????id???????????????????????????????????????????????????????????????
        initClbzAndTscx(eventInfoBean);

        //?????????????????????
        //initResultGoods();

        //????????????????????????
//        initResultDetails(eventInfoBean);

    }

    private void initResultDetails(EventInfoBean eventInfoBean) {
//        AdviceListFragment adviceListFragment = AdviceListFragment.getInstance(mEventListItem, eventInfoBean);
        AdviceListFragment adviceListFragment = new AdviceListFragment();//????????????(????????????)
        adviceListFragment.setEventListItem(mEventListItem, eventInfoBean);
        mFragments.add(adviceListFragment);
        //??????tab
        TabLayout.Tab tab = tab_layout.newTab();
        View inflate = View.inflate(this, R.layout.item_gcjs_tab, null);
        TextView textView = inflate.findViewById(R.id.tv_tab_name);
        //????????????
        textView.setText(R.string.gcjs_title_spxq);
        tab.setCustomView(inflate);
        tab_layout.addTab(tab);
        //??????fragment??????
        adapter.notifyDataSetChanged();
    }

    //????????????id???????????????????????????????????????????????????????????????
    private void initClbzAndTscx(EventInfoBean eventInfoBean) {
        if (eventInfoBean.getIteminstList() != null) {
            String iteminstId = eventInfoBean.getIteminstList().get(0).getIteminstId();
            //????????????????????????????????????????????????tab
            Observable<ApiResult<List<ClbzDetailBean>>> clbzDetail = mRepository.getClbzDetail(mEventListItem.getApplyinstId(), iteminstId);
            Observable<ApiResult<List<TscxBean>>> tscxDetail = mRepository.getTscxDetail(mEventListItem.getApplyinstId(), iteminstId);

            Observable.zip(clbzDetail, tscxDetail, (clbzApiResult, tscxApiResult) -> {
                //????????????????????????
                if (clbzApiResult.isSuccess() && clbzApiResult.getData() != null && clbzApiResult.getData().size() > 0) {
                    mClbzListFragment = ClbzListFragment.getInstance(clbzApiResult.getData());
//                    correctId=clbzApiResult.getData().get(0).getCorrectId();
                    mFragments.add(mClbzListFragment);
                    //??????tab
                    TabLayout.Tab tab = tab_layout.newTab();
                    View inflate = View.inflate(this, R.layout.item_gcjs_tab, null);
                    TextView textView = inflate.findViewById(R.id.tv_tab_name);
                    textView.setText(R.string.gcjs_title_clbz);
                    tab.setCustomView(inflate);
                    tab_layout.addTab(tab);
                    //??????fragment??????
                    adapter.notifyDataSetChanged();
                }
                //????????????????????????
                /*if (tscxApiResult.isSuccess() && tscxApiResult.getData() != null && tscxApiResult.getData().size() > 0) {
                    mTscxListFragment = mTscxListFragment.getInstance(tscxApiResult.getData().get(0));
                    mFragments.add(mTscxListFragment);
                    //??????tab
                    TabLayout.Tab tab = tab_layout.newTab();
                    View inflate = View.inflate(this, R.layout.item_gcjs_tab, null);
                    TextView textView = inflate.findViewById(R.id.tv_tab_name);
                    textView.setText(R.string.gcjs_title_tscx);
                    tab.setCustomView(inflate);
                    tab_layout.addTab(tab);
                    //??????fragment??????
                    adapter.notifyDataSetChanged();
                }*/
                return true;
            }).subscribe(isSuccess -> {
            }, throwable -> throwable.printStackTrace());
        }
    }

//    public List<ResultGoodsBean> getResultGoodsBeanAll() {
//        return resultGoodsBeanAll;
//    }
//
//    public void setResultGoodsBeanAll(List<ResultGoodsBean> resultGoodsBeanAll) {
//        this.resultGoodsBeanAll = resultGoodsBeanAll;
//    }
//
//    public List<ResultGoodsBean> getResultGoodsBeans() {
//        return resultGoodsBeans;
//    }
//
//    public void setResultGoodsBeans(List<ResultGoodsBean> resultGoodsBeans) {
//        this.resultGoodsBeans = resultGoodsBeans;
//    }
//
//    public void reflashGoodsList() {
//        resultGoodsBeans = mResultGoodsFragment.reflashGoodsList(resultGoodsBeanAll);
//    }
//
//    public void reflashGoodsList(List<PwpfBean.ItemMatinstBean> list) {
//        resultGoodsBeans = mResultGoodsFragment.reflashGoodsList(resultGoodsBeanAll, list);
//    }
//
//    public void reflashGoodsList(List<ZjzzBean> list, int aaa) {
//        resultGoodsBeans = mResultGoodsFragment.reflashGoodsList(resultGoodsBeanAll, list, aaa);
//    }
}