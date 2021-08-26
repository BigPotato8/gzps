package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PersonSelectBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.validate.ListUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * 上报事件列表Adapter
 *
 */
public class PersonSelectAdapter extends RecyclerView.Adapter<PersonSelectAdapter.AdviceViewHolder> {
    EventRepository eventRepository;
    Context context;
    List<PersonSelectBean> list;
    int max = 1;
    WeakReference<PersonSelectAdapter> parent;
    List<PersonSelectBean> selectList;
    public void setList(List<PersonSelectBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public PersonSelectAdapter(Context context, List<PersonSelectBean> list,PersonSelectAdapter parent) {
        this.context = context;
        this.list = list;
        eventRepository = new EventRepository();
        this.parent = new WeakReference<PersonSelectAdapter>(parent);
    }

    public PersonSelectAdapter(Context context, List<PersonSelectBean> list,int max) {
        this.context = context;
        this.list = list;
        this.max = max;
        selectList = new ArrayList<>();
        eventRepository = new EventRepository();
    }

    @Override
    public AdviceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person_select, parent, false);
        return new AdviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdviceViewHolder holder, int position) {
        if (list.get(position).getOpen()==null) {
            list.get(position).setOpen(false);
        }
        if (list.get(position).getNocheck()==null) {
            list.get(position).setNocheck(false);
        }
        if (PersonSelectBean.ORG.equals(list.get(position).getDataType())){
            holder.iv_arrow.setVisibility(View.VISIBLE);

            holder.iv_arrow.setImageResource(list.get(position).getOpen()?R.mipmap.ic_arrow_down:R.mipmap.ic_arrow_right_gc);
            holder.iv_check.setClickable(false);
            holder.iv_check.setImageResource(R.mipmap.ic_select_none);
            holder.tv_title.setText(list.get(position).getName());
            holder.iv_arrow.setOnClickListener(v -> {
                //todo 子组织展开
                Boolean open = list.get(position).getOpen();
                if (open){//展开中，收起
                    list.get(position).setOpen(!open);
                    holder.iv_arrow.setImageResource(R.mipmap.ic_arrow_right_gc);
                    holder.rv_child.setVisibility(View.GONE);
                }else{//收起中，展开
                    list.get(position).setOpen(!open);
                    holder.iv_arrow.setImageResource(R.mipmap.ic_arrow_down);
                    holder.rv_child.setVisibility(View.VISIBLE);
                    if (list.get(position).getChildren()==null){
                        Map<String,String> map = new HashMap<>();
                        map.put("assigneePartInMode","2");
                        map.put("dataType",list.get(position).getDataType());
                        map.put("id",list.get(position).getId());
                        eventRepository.getOrgTree(map)
                            .subscribe(listApiResult -> {
                                if (listApiResult.isSuccess() && listApiResult.getData()!=null) {
                                    list.get(position).setChildren(listApiResult.getData());
                                    if(!list.get(position).getOpen()){//获取组织期间收起了
                                        notifyItemChanged(position);
                                        return;
                                    }
                                    if (holder.rv_child.getAdapter() == null) {
                                        PersonSelectAdapter adapter = new PersonSelectAdapter(context, list.get(position).getChildren(),this);
                                        holder.rv_child.setAdapter(adapter);
                                        holder.rv_child.setLayoutManager(new LinearLayoutManager(context));
                                    } else {
                                        ((PersonSelectAdapter) holder.rv_child.getAdapter()).setList(list.get(position).getChildren());
                                    }
                                }
                            },throwable -> throwable.printStackTrace());
                    }else{
                        if (holder.rv_child.getAdapter() == null) {
                            PersonSelectAdapter adapter = new PersonSelectAdapter(context, list.get(position).getChildren(),this);
                            holder.rv_child.setAdapter(adapter);
                            holder.rv_child.setLayoutManager(new LinearLayoutManager(context));
                        } else {
                            ((PersonSelectAdapter) holder.rv_child.getAdapter()).setList(list.get(position).getChildren());
                        }
                    }
                }

            });
        }else if(PersonSelectBean.USER.equals(list.get(position).getDataType())){
            holder.iv_arrow.setVisibility(View.GONE);
            holder.iv_check.setClickable(true);
            holder.iv_check.setImageResource(list.get(position).getNocheck()?R.mipmap.ic_select_all:R.mipmap.ic_select_none);
            holder.iv_check.setOnClickListener(v -> {
                //todo 选中判断，是否大于最大数，不可选
                int max = getMax();
                List<PersonSelectBean> selectList = getSelectList();
                Boolean nocheck = list.get(position).getNocheck();
                if (!nocheck){//目前没被选中
                    if (max>selectList.size()){//超过最大选中数，不可在选中
                        list.get(position).setNocheck(!nocheck);
                        notifyItemChanged(position);
                        selectList.add(list.get(position));
                        setSelectList(selectList);
                    }
                }else {//选中取消
                    list.get(position).setNocheck(!nocheck);
                    notifyItemChanged(position);
                    selectList.remove(list.get(position));
                    setSelectList(selectList);
                }
            });
            holder.tv_title.setText(list.get(position).getName()+"("+list.get(position).getTextValue()+")");
        }

        if (ListUtil.isEmpty(list.get(position).getChildren()) || !list.get(position).getOpen()){//收起或无子组织
            if (holder.rv_child.getAdapter()!=null)
                ((PersonSelectAdapter)holder.rv_child.getAdapter()).setList(null);
            holder.rv_child.setVisibility(View.GONE);
        }else{
            if (list.get(position).getOpen()) {//展开
                holder.rv_child.setVisibility(View.VISIBLE);
                if (holder.rv_child.getAdapter() == null) {
                    PersonSelectAdapter adapter = new PersonSelectAdapter(context, list.get(position).getChildren(),this);
                    holder.rv_child.setAdapter(adapter);
                    holder.rv_child.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    ((PersonSelectAdapter) holder.rv_child.getAdapter()).setList(list.get(position).getChildren());
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class AdviceViewHolder extends RecyclerView.ViewHolder  {
        ImageView iv_arrow;
        ImageView iv_check;
        TextView tv_title;
        RecyclerView rv_child;

        public AdviceViewHolder(View itemView) {
            super(itemView);
            iv_arrow = itemView.findViewById(R.id.iv_arrow);
            iv_check = itemView.findViewById(R.id.iv_check);
            tv_title = itemView.findViewById(R.id.tv_title);
            rv_child = itemView.findViewById(R.id.rv_child);

        }
    }
    //递归，获取或设置最顶层adapter的list
    public List<PersonSelectBean> getSelectList() {
        if (parent==null) {
            return selectList;
        }else{
            PersonSelectAdapter parentAdapter = parent.get();
            return parentAdapter.getSelectList();
        }
    }
    public void setSelectList(List<PersonSelectBean> selectList) {
        if (parent==null) {
            this.selectList = selectList;
        }else{
            PersonSelectAdapter parentAdapter = parent.get();
            parentAdapter.setSelectList(selectList);
        }
    }

    public int getMax() {
        if (parent==null) {
            return max;
        }else{
            PersonSelectAdapter parentAdapter = parent.get();
            return parentAdapter.getMax();
        }
    }


}
