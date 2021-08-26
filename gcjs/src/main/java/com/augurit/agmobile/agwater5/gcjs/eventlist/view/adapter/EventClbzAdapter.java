package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventClbzItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 材料附件列表Adapter
 */
public class EventClbzAdapter extends RecyclerView.Adapter<EventClbzAdapter.ClbzViewHolder> {
    private List<EventClbzItemBean.SubmittedMatsBean> beanList;
    private Context mContext;
    private  String s1;

    public List<EventClbzItemBean.SubmittedMatsBean> getSelectList() {
        List<EventClbzItemBean.SubmittedMatsBean> newList = new ArrayList<>();
        for (EventClbzItemBean.SubmittedMatsBean bean : beanList) {
            if (bean.isSelected()) {
                newList.add(bean);
            }
        }
        return newList;
    }

    public EventClbzAdapter(Context ctx, List<EventClbzItemBean.SubmittedMatsBean> list) {
        mContext = ctx;
        beanList = list;
    }


    @Override
    public ClbzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_clbz_listitem, parent, false);
        return new ClbzViewHolder(view);
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    @Override
    public void onBindViewHolder(ClbzViewHolder holder, int position) {
        if (holder instanceof ClbzViewHolder) {
            EventClbzItemBean.SubmittedMatsBean bean = beanList.get(position);
            holder.tv_file_name.setText(beanList.get(position).getMatName());
            if (bean.isSelected()) {
                holder.ll_content.setBackgroundColor(0x2f1488fc);
                holder.view_selected.setVisibility(View.VISIBLE);
                holder.ll_bz_advice.setVisibility(View.VISIBLE);
            } else {
                holder.ll_content.setBackgroundColor(0xffffffff);
                holder.view_selected.setVisibility(View.GONE);
                holder.ll_bz_advice.setVisibility(View.GONE);
            }
            if ("1".equals(bean.getAttIsRequire())) {
                holder.tv_type.setText("必交");
            } else if ("0".equals(bean.getAttIsRequire())) {
                holder.tv_type.setText("可选");
            }
            holder.ll_content.setOnClickListener(v -> {
                beanList.get(position).setSelected(!bean.isSelected());
                String s = holder.tv_num.getText().toString();
                String s1=holder.et_advice.getText().toString();
//                setS1(holder.et_advice.getText().toString());
//                notifyItemChanged(position);
                notifyDataSetChanged();
            });
            if (holder.et_advice.getTag() instanceof TextWatcher) {
                holder.et_advice.removeTextChangedListener((TextWatcher) holder.et_advice.getTag());
            }
            holder.et_advice.setText(beanList.get(position).getAttDueIninstOpinion());
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    if (s.length() > 0){
//                        bean.setCorrectMemo(s.toString());
                        bean.setAttDueIninstOpinion(s.toString());
                    }else {
                        bean.setAttDueIninstOpinion("");
                    }
                }
                @Override
                public void afterTextChanged(Editable editable) {}
            };
            holder.et_advice.addTextChangedListener(textWatcher);
            holder.et_advice.setTag(textWatcher);
//            setS1(holder.et_advice.getText().toString());
        }
//        if (holder instanceof ClbzViewHolder) {
//            //移除监听
//            if (holder.et_advice.getTag() != null) {
//                holder.et_advice.removeTextChangedListener((TextWatcher) holder.et_advice.getTag());
//            }
//            if (holder.et_advice_second.getTag() != null) {
//                holder.et_advice_second.removeTextChangedListener((TextWatcher) holder.et_advice_second.getTag());
//            }
//            if (holder.et_num.getTag() != null) {
//                holder.et_num.removeTextChangedListener((TextWatcher) holder.et_num.getTag());
//            }
//            if (holder.et_num_second.getTag() != null) {
//                holder.et_num_second.removeTextChangedListener((TextWatcher) holder.et_num_second.getTag());
//            }
//
//            holder.tv_file_name.setText(beanList.get(position).getMatName());
//            //有原件和复印件
//            if (beanList.get(position).getRealCopyCount() != null && beanList.get(position).getRealCopyCount() != 0
//                    && beanList.get(position).getRealPaperCount() != null && beanList.get(position).getRealPaperCount() != 0
//            ) {
//                holder.tv_type_second.setVisibility(View.VISIBLE);
//                holder.tv_num_second.setVisibility(View.VISIBLE);
//                holder.et_num_second.setVisibility(View.VISIBLE);
//                holder.et_advice_second.setVisibility(View.VISIBLE);
//
//                holder.tv_type.setText("原件");
//                holder.tv_num.setText(beanList.get(position).getPaperIsCollected() + "/" + beanList.get(position).getRealPaperCount());
//                holder.et_num.setText(beanList.get(position).getPaperCount() + "");
//                holder.et_advice.setText(beanList.get(position).getPaperDueIninstOpinion());
//
//                holder.tv_type_second.setText("复印件");
//                holder.tv_num_second.setText(beanList.get(position).getCopyIsCollected() + "/" + beanList.get(position).getRealCopyCount());
//                holder.et_num_second.setText(beanList.get(position).getCopyCount() + "");//补全份数
//                holder.et_advice_second.setText(beanList.get(position).getCopyDueIninstOpinion());//补全意见
//
//            } else {//只有其中之一
//                holder.tv_type_second.setVisibility(View.GONE);
//                holder.tv_num_second.setVisibility(View.GONE);
//                holder.et_num_second.setVisibility(View.GONE);
//                holder.et_advice_second.setVisibility(View.GONE);
//
//                if (beanList.get(position).getRealCopyCount() != null) {
//                    holder.tv_type.setText("复印件");
//                    if (beanList.get(position).getRealCopyCount() != 0) {
//                        holder.tv_num.setText(beanList.get(position).getCopyIsCollected() + "/" + beanList.get(position).getRealCopyCount());
//                    }
//                    holder.et_num.setText(beanList.get(position).getCopyCount() + "");//补全份数
//                    holder.et_advice.setText(beanList.get(position).getCopyDueIninstOpinion());//补全意见
//                } else if (beanList.get(position).getRealPaperCount() != null) {
//                    holder.tv_type.setText("原件");
//                    if (beanList.get(position).getRealPaperCount() != 0) {
//                        holder.tv_num.setText(beanList.get(position).getPaperIsCollected() + "/" + beanList.get(position).getRealPaperCount());
//                    }
//                    holder.et_num.setText(beanList.get(position).getPaperCount() + "");
//                    holder.et_advice.setText(beanList.get(position).getPaperDueIninstOpinion());
//                } else{//未交材料
//                    if(!TextUtils.isEmpty(beanList.get(position).getCopyIsCollected())){
//                        holder.tv_type.setText("复印件");
//                        holder.tv_num.setText(beanList.get(position).getCopyIsCollected());
//                        holder.et_num.setText(beanList.get(position).getCopyCount()==null?"":beanList.get(position).getCopyCount()+"");//补全份数
//                        holder.et_advice.setText(beanList.get(position).getCopyDueIninstOpinion());//补全意见
//                    }else{
//                        holder.tv_type.setText("原件");
//                        holder.tv_num.setText(beanList.get(position).getPaperIsCollected());
//                        holder.et_num.setText(beanList.get(position).getPaperCount()==null?"":beanList.get(position).getPaperCount()+"");
//                        holder.et_advice.setText(beanList.get(position).getPaperDueIninstOpinion());
//                    }
//
//                }
//            }
//
//            if (beanList.get(position).isSelected()) {
//
//                holder.ll_content.setBackgroundColor(0x2f1488fc);
//                holder.view_selected.setVisibility(View.VISIBLE);
//                holder.ll_bz_num.setVisibility(View.VISIBLE);
//                holder.ll_bz_advice.setVisibility(View.VISIBLE);
//            } else {
//                holder.ll_content.setBackgroundColor(0xffffffff);
//                holder.view_selected.setVisibility(View.GONE);
//                holder.ll_bz_num.setVisibility(View.GONE);
//                holder.ll_bz_advice.setVisibility(View.GONE);
//            }
//            holder.ll_content.setOnClickListener(v -> {
//                beanList.get(position).setSelected(!beanList.get(position).isSelected());
//                String s = holder.tv_num.getText().toString();
//                String s1 = holder.et_advice.getText().toString();
//                //EventClbzAdapter.this.notifyItemChanged(position);
//                EventClbzAdapter.this.notifyDataSetChanged();
//            });
//
//            TextWatcher watcherAdvice = new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (beanList.get(position).getRealPaperCount() != null && beanList.get(position).getRealPaperCount() != 0) {
//                        beanList.get(position).setPaperDueIninstOpinion(s.toString());//原件
//                    } else if (beanList.get(position).getRealCopyCount() != null && beanList.get(position).getRealCopyCount() != 0) {
//                        beanList.get(position).setCopyDueIninstOpinion(s.toString());//复印件
//                    }
//                }
//            };
//            TextWatcher watcherAdviceSecond = new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if (beanList.get(position).getRealCopyCount() != null && beanList.get(position).getRealCopyCount() != 0) {
//                        beanList.get(position).setCopyDueIninstOpinion(s.toString());//复印件
//                    }
//                }
//            };
//            holder.et_advice.addTextChangedListener(watcherAdvice);
//            holder.et_advice.setTag(watcherAdvice);
//            holder.et_advice_second.addTextChangedListener(watcherAdviceSecond);//
//            holder.et_advice_second.setTag(watcherAdviceSecond);
//
//            TextWatcher watcherNum = new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    try {
//                        if (beanList.get(position).getRealPaperCount() != null && beanList.get(position).getRealPaperCount() != 0) {
//                            beanList.get(position).setPaperCount(Integer.parseInt(s.toString()));
//                        } else if (beanList.get(position).getRealCopyCount() != null && beanList.get(position).getRealCopyCount() != 0) {
//                            beanList.get(position).setCopyCount(Integer.parseInt(s.toString()));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            TextWatcher watcherNumSecond = new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    try {
//                       if (beanList.get(position).getRealCopyCount() != null && beanList.get(position).getRealCopyCount() != 0) {
//                            beanList.get(position).setCopyCount(Integer.parseInt(s.toString()));
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//            holder.et_num.addTextChangedListener(watcherNum);
//            holder.et_num.setTag(watcherNum);
//            holder.et_num_second.addTextChangedListener(watcherNumSecond);
//            holder.et_num_second.setTag(watcherNumSecond);
//        }

    }

    public static class ClbzViewHolder extends RecyclerView.ViewHolder {
        TextView tv_file_name;
        TextView tv_type;
        TextView tv_type_second;
        TextView tv_num;
        TextView tv_num_second;
        LinearLayout ll_content;
        LinearLayout view_selected;
        LinearLayout ll_bz_num;
        LinearLayout ll_bz_advice;
        EditText et_num;
        EditText et_num_second;
        EditText et_advice;
        EditText et_advice_second;


        public ClbzViewHolder(View itemView) {
            super(itemView);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_type_second = itemView.findViewById(R.id.tv_type_second);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_num_second = itemView.findViewById(R.id.tv_num_second);
            ll_content = itemView.findViewById(R.id.ll_content);
            ll_bz_num = itemView.findViewById(R.id.ll_bz_num);
            ll_bz_advice = itemView.findViewById(R.id.ll_bz_advice);
            view_selected = itemView.findViewById(R.id.view_selected);
            et_num = itemView.findViewById(R.id.et_num);
            et_num_second = itemView.findViewById(R.id.et_num_second);
            et_advice = itemView.findViewById(R.id.et_advice);
            et_advice_second = itemView.findViewById(R.id.et_advice_second);
        }
    }


    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }


}
