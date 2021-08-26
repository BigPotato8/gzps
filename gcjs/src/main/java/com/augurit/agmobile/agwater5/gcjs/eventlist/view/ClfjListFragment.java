package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClfjBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.ClfjFileAndFoldListAdapter;
import com.augurit.agmobile.agwater5.gcjs.util.FilePreviewUtil;
import com.augurit.agmobile.common.lib.common.Callback2;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity;
import com.augurit.agmobile.common.view.loadinglayout.LoadingMaskLayout;
import com.augurit.agmobile.common.view.navigation.menu.MenuPopup;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;
import static com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity.RESULT_FILE_PATH;

/**
 * @description 部门材料附件文件夹和列表的Fragment
 * @date: 20190417
 * @author: xieruibin
 */
public class ClfjListFragment extends Fragment implements OnRefreshListener {
    public static final int CLFJ_REQUEST = 2090;

    private EventRepository mEventRepository;
    private String isSeriesApproval;
    private EventBean mEventBean;
    private FilePreviewUtil mFilePreviewUtil;

    ArrayList<ClfjBean> mArrayList;//文件集合
    ArrayList<String> dirIdList;//路径集合
    String dirCurrent;//当前路径名称
    protected View mView;
    protected ViewGroup head_container;     // 头部控件容器
    protected RecyclerView rv_datas;
    protected RefreshLayout refresh_layout;
    protected LoadingMaskLayout mask_layout;
    protected ViewGroup loading_layout;
    protected ViewGroup btn_container;      // 右下角按钮容器
    protected ViewGroup btn_add_floating;   // 右下角添加按钮
    protected ImageView iv_back;
    protected TextView tv_dir;

    private ClfjFileAndFoldListAdapter mAdapter;
    private String mType;

    private CompositeDisposable compositeDisposable;

    public static ClfjListFragment getInstance(String isSeriesApproval) {
        ClfjListFragment baseInfoFragment = new ClfjListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("isSeriesApproval", isSeriesApproval);
        baseInfoFragment.setArguments(bundle);
        return baseInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe
    public void receive(EventBean eventBean) {//获取审批id
        mEventBean = eventBean;
        loadDatas(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.viewlist_fragment, container, false);
        initView();
        initArguments();
        return mView;
    }

    private void initView() {
        rv_datas = mView.findViewById(R.id.rv_datas);
        head_container = mView.findViewById(R.id.head_container);
        rv_datas = mView.findViewById(R.id.rv_datas);
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        mask_layout = mView.findViewById(R.id.mask_layout);
        loading_layout = mView.findViewById(R.id.loading_layout);
        btn_container = mView.findViewById(R.id.btn_container);
        btn_add_floating = mView.findViewById(R.id.btn_add_floating);
        View headerView = LayoutInflater.from(getActivity()).inflate(R.layout.gcjs_clfj_list_header, head_container, false);
        iv_back = headerView.findViewById(R.id.iv_back);
        tv_dir = headerView.findViewById(R.id.tv_dir);
        head_container.addView(headerView);//添加头部

        refresh_layout.setOnRefreshListener(this);
        refresh_layout.setEnableFooterFollowWhenLoadFinished(true);
        btn_add_floating.setVisibility(View.VISIBLE);
        mask_layout.setAction(getString(R.string.bpm_btn_reload), null);
        btn_add_floating.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WEUIFilePickerActivity.class);
            intent.putExtra(WEUIFilePickerActivity.SELECT_LIMIT, 1);
            getActivity().startActivityForResult(intent, CLFJ_REQUEST);
        });
        iv_back.setOnClickListener(v -> doBackPress());
    }

    private void initArguments() {
        mEventRepository = new EventRepository();
        mArrayList = new ArrayList<>();
        mArrayList.add(new ClfjBean());
        dirIdList = new ArrayList<>();
        dirIdList.add("");
        dirCurrent = "";

        isSeriesApproval = getArguments().getString("isSeriesApproval");
        mFilePreviewUtil = new FilePreviewUtil();
        compositeDisposable = new CompositeDisposable();

        refresh_layout.setEnableLoadMore(false);//显示全部，不需要加载更多
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        loadDatas(dirIdList.size() - 1);
    }

    /**
     * 显示加载失败
     */
    protected void showError() {
        String msg = getString(R.string.bpm_view_load_failed);
        mask_layout.showError(msg, v -> {
            loadDatas(dirIdList.size() - 1);
        });
    }

    /**
     * 显示无结果
     */
    public void showEmpty(boolean isEmpty) {
        if (isEmpty) {
            String msg;
            msg = getString(R.string.bpm_view_no_search_result);
            mask_layout.showEmpty(msg, null);
        } else {
            mask_layout.hide();
        }
    }

    /**
     * 加载特定路径数据
     */
    private void loadDatas(int position) {
        mType = "并联".equals(isSeriesApproval) ? "0" : "1";
        Disposable disposable = mEventRepository.getClfjAllList(dirIdList.get(position), mType, mEventBean.getApplyinstId())
                .subscribe(clfjBeanApiResult -> {
                    if (getActivity() == null) {
                        return;
                    }
                    if (refresh_layout!=null) {
                        refresh_layout.finishRefresh();
                    }
                    ProgressDialogUtil.dismissAll();
                    if (clfjBeanApiResult.isSuccess()) {
                        if (mAdapter == null) {
                            mAdapter = new ClfjFileAndFoldListAdapter(getActivity(), clfjBeanApiResult.getData());
                            rv_datas.setAdapter(mAdapter);
                            rv_datas.setLayoutManager(new LinearLayoutManager(getActivity()));
                            mAdapter.setItemClickListener(new ClfjFileAndFoldListAdapter.ItemClickListener() {
                                @Override
                                public void itemClick(int position, ClfjBean mClfjBean) {
                                    ClfjListFragment.this.itemClick(position, mClfjBean);
                                }

                                @Override
                                public void itemLongClick(int position, ClfjBean mClfjBean) {
                                    ClfjListFragment.this.itemLongClick(position, mClfjBean);
                                }
                            });
                        }

                        mArrayList.remove(position);
                        mArrayList.add(position, clfjBeanApiResult.getData());
                        if (position == dirIdList.size() - 1) {//当前页则刷新
                            mAdapter.setClfjBean(clfjBeanApiResult.getData(), ClfjListFragment.this);
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
        compositeDisposable.add(disposable);
    }


    private void itemClick(int position, ClfjBean mClfjBean) {
        if (mAdapter.getItemViewType(position) == 0) {//文件夹
            ProgressDialogUtil.showProgressDialog(getActivity(), false);
            dirIdList.add(mClfjBean.getDirs().get(position).getDirId());//添加新路径
            mArrayList.add(new ClfjBean());//先加载一个空数据
            String s = tv_dir.getText().toString();
            tv_dir.setText(s + "/" + mClfjBean.getDirs().get(position).getDirName());
            loadDatas(dirIdList.size() - 1);//加载
        } else {//文件,打开
            int a = ListUtil.isEmpty(mClfjBean.getDirs()) ? 0 : mClfjBean.getDirs().size();
            FileBean fb = new FileBean();
            fb.setName(mClfjBean.getFiles().get(position - a).getAttName());
            fb.setId(mClfjBean.getFiles().get(position - a).getDetailId());
            fb.setUploaded(true);
            mFilePreviewUtil.downFile(fb, getActivity());
        }
    }

    private void itemLongClick(int position, ClfjBean mClfjBean) {
        if (mAdapter.getItemViewType(position) == 0) {//文件夹

        } else {//文件,删除
            int a = ListUtil.isEmpty(mClfjBean.getDirs()) ? 0 : mClfjBean.getDirs().size();
            mFilePreviewUtil.deleteFile(getActivity(), mAdapter, position, mClfjBean.getFiles().get(position - a), new Callback2<ApiResult>() {
                @Override
                public void onSuccess(ApiResult apiResult) {
                    mClfjBean.getFiles().remove(position - a);
                    mArrayList.remove(mArrayList.size() - 1);
                    mArrayList.add(mClfjBean);
                    mAdapter.setClfjBean(mClfjBean, ClfjListFragment.this);
                }

                @Override
                public void onFail(Exception e) {
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CLFJ_REQUEST) {
            if (resultCode == RESULT_OK) {//获取文件上传
                ArrayList<FileBean> fileBeans = data.getParcelableArrayListExtra(RESULT_FILE_PATH);
                mFilePreviewUtil.uploadFileByCl(getActivity(), fileBeans.get(0), mEventBean.getApplyinstId(), dirIdList.get(dirIdList.size() - 1), new Callback2<ApiResult>() {
                    @Override
                    public void onSuccess(ApiResult apiResult) {
                        loadDatas(0);//根目录刷新
                    }

                    @Override
                    public void onFail(Exception e) {
                    }
                });
            }
        }
    }

    public boolean doBackPress() {
        if (dirIdList.size() == 1) {//最顶层
            return false;
        } else {//返回上一层
            compositeDisposable.clear();
            compositeDisposable = new CompositeDisposable();//网络请求清空重置

            String dir = tv_dir.getText().toString();
            if (dir.contains("/"))
                tv_dir.setText(dir.substring(0,dir.lastIndexOf("/")));//显示路径名称

            dirIdList.remove(dirIdList.size() - 1);
            mArrayList.remove(mArrayList.size() - 1);
            mAdapter.setClfjBean(mArrayList.get(mArrayList.size() - 1), ClfjListFragment.this);
            return true;
        }
    }
}
