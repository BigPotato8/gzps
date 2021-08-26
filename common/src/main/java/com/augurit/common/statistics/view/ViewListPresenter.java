package com.augurit.common.statistics.view;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.augurit.agmobile.busi.bpm.dict.DictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.Dict;
import com.augurit.agmobile.busi.bpm.view.model.ViewInfo;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.bpm.viewlist.view.IViewListContract;
import com.augurit.agmobile.common.lib.net.NetworkStateManager;
import com.augurit.agmobile.common.view.combineview.IDictItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列表视图Presenter
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.busi.bpm.viewlist.view
 * @createTime 创建时间 ：2018/9/3
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ViewListPresenter implements IViewListContract.Presenter, NetworkStateManager.OnNetworkChangedListener {

    protected IViewListContract.View mView;
    protected NetworkStateManager mNetworkManager;
    protected ArrayList<String> mPageTitles;
    protected DictRepository mDictRepository;
    protected HashMap<String, Map<String, String>> mFiltersMap;

    public ViewListPresenter(IViewListContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mDictRepository = new DictRepository();
    }

    @Override
    public void init(ArrayList<String> pageTitles, ArrayList<? extends BaseViewListFragment> fragments, @Nullable Map<String, ViewInfo> viewInfoMap,boolean a) {
        mPageTitles = pageTitles;
        mView.init(pageTitles, fragments, viewInfoMap,a);
        mNetworkManager = new NetworkStateManager();
        mNetworkManager.registerNetworkChangeListener((Activity)mView.getContext(), this);
    }

    @Override
    public List<? extends IDictItem> getDictItemsOrTreeItems(String parentTypeCode) {
        List<? extends IDictItem> items = null;
        Dict dict = mDictRepository.getDictByTypeCode(parentTypeCode);
        if (dict != null) {
            if ("0".equals(dict.getTypeIsTree())) {
                items = mDictRepository.getDictItemByParentTypeCode(parentTypeCode);
            } else if ("1".equals(dict.getTypeIsTree())) {
                items = mDictRepository.getDictTreeItemByParentTypeCode(parentTypeCode);
            }
        }
        return items;
    }

    @Override
    public void onDestroy() {
        if (mNetworkManager != null) {
            mNetworkManager.unregisterNetworkChangeListener();
        }
    }

    @Override
    public void onNetworkChange(boolean isConnectedBefore, boolean isConnectedNow) {
        if (!isConnectedBefore && isConnectedNow) {
            mView.showNetError(false);
        } else if (isConnectedBefore && !isConnectedNow) {
            mView.showNetError(true);
        }
    }
}
