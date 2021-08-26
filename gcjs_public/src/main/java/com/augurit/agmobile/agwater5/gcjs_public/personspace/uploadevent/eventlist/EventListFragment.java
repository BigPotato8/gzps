package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.eventlist;

import android.content.Intent;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.UploadProjectListAdapter;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single.SingleUploadEventActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.ReportRepository;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  9:55.
 * Desc：
 */

public class EventListFragment extends BaseViewListFragment<EventItemBean> {
    public static final String EVENT_ITEM = "event_item";
    private String keyword;
    private ReportRepository mRepository;

    @Override
    protected BaseViewListAdapter initAdapter() {
        return new EventListAdapter(getActivity());
    }


    @Override
    protected void initView() {

        super.initView();
//        View searchView = View.inflate(getActivity(), R.layout.gcjs_public_event_list_header, null);
//        head_container.addView(searchView);//不要头部，直接放筛选
        mRepository = new ReportRepository();
    }

    @Override
    protected Observable<ApiResult<List<EventItemBean>>> loadDatas(int i, int i1, Map map) {
        refresh_layout.setEnableLoadMore(false);
        return mRepository.getEventListByDepartment((String) map.get("itemName"), (String) map.get("orgId"), null, null)
                ;
    }

    @Override
    public void onItemClick(View view, int i, EventItemBean o) {
        Intent intent = new Intent(getActivity(), SingleUploadEventActivity.class);
        intent.putExtra(UploadProjectListAdapter.PROJECT, getActivity().getIntent().getSerializableExtra(UploadProjectListAdapter.PROJECT));
        intent.putExtra(EVENT_ITEM, o);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View view, int i, EventItemBean o) {
        return false;
    }
}
