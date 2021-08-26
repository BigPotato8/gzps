package com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.adapter.ProcessDetailTopAdapter;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.adapter.ProjectProcessDetailAdapter;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.ProjectProcessDetail;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class ProjectProcessDetailFragment extends BaseViewListFragment<ProjectProcessDetail.ProcessItem> {

    private ProjectProcessDetail mProcessDetail;
    private String keyword = "";
    private RecyclerView topRecyclerView;
    private String stageId = "";
    public static ProjectProcessDetailFragment newInstance() {
        ProjectProcessDetailFragment fragment = new ProjectProcessDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mProcessDetail = (ProjectProcessDetail) getActivity().getIntent().getSerializableExtra("processDetail");
        stageId = mProcessDetail.getAeaParStages().get(mProcessDetail.getAeaParStages().size()-1).getStageId();
    }
View laseView;
    @Override
    protected void initView() {

        super.initView();
        View searchView = View.inflate(getActivity(), R.layout.process_top_card, null);
        topRecyclerView = searchView.findViewById(R.id.id_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());  //LinearLayoutManager中定制了可扩展的布局排列接口，子类按照接口中的规范来实现就可以定制出不同排雷方式的布局了
        //配置布局，默认为vertical（垂直布局），下边这句将布局改为水平布局
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topRecyclerView.setLayoutManager(layoutManager);
        ProcessDetailTopAdapter adapter=new ProcessDetailTopAdapter(getActivity());
        adapter.setDatas(mProcessDetail.getAeaParStages());
        topRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new BaseViewListAdapter.OnItemClickListener<ProjectProcessDetail.ProcessBean>() {
            @Override
            public void onItemClick(View itemView, int position, ProjectProcessDetail.ProcessBean data) {
                itemView.setBackgroundColor(Color.parseColor("#AEDEF4"));
                stageId = data.getStageId();
                loadDatas(true);
                if(laseView!=null){
                    laseView.setBackgroundColor(Color.parseColor("#00ffffff"));
                   }
                laseView = itemView;
            }
        });

        head_container.addView(searchView);
    }

    @Override
    public void onItemClick(View itemView, int position, ProjectProcessDetail.ProcessItem data) {


    }

    @Override
    public boolean onItemLongClick(View itemView, int position,ProjectProcessDetail.ProcessItem data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter<ProjectProcessDetail.ProcessItem> initAdapter() {
        return new ProjectProcessDetailAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<ProjectProcessDetail.ProcessItem>>> loadDatas(int page, int rows, Map filterParams) {
//        Map<String, String> params = new HashMap<>();
//        params.put("keyword", keyword);
//        params.put("pageNum", page + "");
//        params.put("pageSize", rows + "");
//
//        HashMap <String,String>header =   new HashMap<>();
//        header.put("Authorization", LoginManager.getInstance().getCurrentUser().getUserType());
//        return HttpUtil.getInstance(
////                AwUrlManager.serverUrl()
//        "https://augur.tinyxiong.net:8490"
//        ).getWithHeader(GcjsPuUrlConstant.GET_MY_PROJECT_PROGRESS, params,header).map(
//                s -> {
//                    MyProjectBean listApiResult = new Gson().fromJson(s, new TypeToken<MyProjectBean>() {
//                    }.getType());
//                    ApiResult<List<ProjectDetailBean.ProjectInfoBean>> apiResult= new ApiResult<>();
//                    apiResult.setData(listApiResult.getContent().getList());
//                    apiResult.setMessage(listApiResult.getMessage());
//                    apiResult.setSuccess(listApiResult.isSuccess());
//                    return apiResult;
//                }
//        );
        ApiResult<List<ProjectProcessDetail.ProcessItem>> apiResult= new ApiResult<>();
        if (page !=1){
            apiResult.setData(null);
            return Observable.fromArray(apiResult);
        }
        ProjectProcessDetail.ProcessBean   processBean = null;
        ArrayList<ProjectProcessDetail.ProcessItem> items = new ArrayList<>();
        List<ProjectProcessDetail.ProcessItem> tempList = new ArrayList<>();
        for(int i=0; i<mProcessDetail.getAeaParStages().size();i++){
            processBean = mProcessDetail.getAeaParStages().get(i);
//            for (int j=0;j<processBean.getCoreItemList().size();j++){
////                items.add(processBean.get)
////            }
            if (stageId.equals(processBean.getStageId())){
                for(ProjectProcessDetail.ProcessItem  item:processBean.getCoreItemList()){
                    item.setIteminstState("1");
                    tempList.add(item);
                }
                for(ProjectProcessDetail.ProcessItem  item:processBean.getParallelItemList()){
                    item.setIteminstState("2");
                    tempList.add(item);
                }
//                items.addAll(processBean.getCoreItemList());
                items.addAll(tempList);
                items.addAll(processBean.getParallelItemList());
            }
        }
        apiResult.setData(items);
          return Observable.fromArray(apiResult);
    }


}
