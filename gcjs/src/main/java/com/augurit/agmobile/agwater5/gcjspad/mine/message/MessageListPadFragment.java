package com.augurit.agmobile.agwater5.gcjspad.mine.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.agwater5.gcjs.model.Message;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.mine.notice.AnnounceListAdapter;
import com.augurit.agmobile.agwater5.gcjspad.mine.notice.NoticeDetailFragment;
import com.augurit.agmobile.agwater5.gcjspad.widget.PageControlView;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.view.widget.WEUIButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * 消息列表
 */
public class MessageListPadFragment extends BasePadFragment {
    RecyclerView rv_notice_list;
    View loading_layout;
    private int pageSize=10;
    private int curPage=0;
    private int maxPage=0;
    private TextView tv_list_index_tip;//页码提示
    private PageControlView pc_page_control;//分页控件
    private EditText et_jump_index;//跳转指定页面填写框
    private WEUIButton btn_jump_confirm;//跳转确定

    MessageListAdapter messageListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list_pad,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        view.findViewById(R.id.iv_back).setOnClickListener(v ->{
            removeFragment(this);
        });
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText("我的消息");
        rv_notice_list = view.findViewById(R.id.rv_notice_list);
        loading_layout = view.findViewById(R.id.loading_layout);

        messageListAdapter = new MessageListAdapter(R.layout.item_message_list_pad);
        rv_notice_list.setAdapter(messageListAdapter);
        rv_notice_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                addFragmentOnActivity(MessageDetailFragment.getInstance((Message.RowsBean) adapter.getData().get(position)));
            }
        });
        //底部分页导航
        tv_list_index_tip = view.findViewById(R.id.tv_list_index_tip);
        pc_page_control = view.findViewById(R.id.pc_page_control);
        et_jump_index = view.findViewById(R.id.et_jump_index);
        btn_jump_confirm = view.findViewById(R.id.btn_jump_confirm);
        pc_page_control.setPageChangeListener(new PageControlView.OnPageChangeListener() {
            @Override
            public void pageChanged(PageControlView pageControl, int numPerPage) {
                getAnnounce(numPerPage,false);
            }
        });
        btn_jump_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = et_jump_index.getText().toString();
                if(TextUtils.isEmpty(number) || Integer.parseInt(number) > maxPage){
                    ToastUtil.shortToast(getActivity(),"请输入合法的页码");
                    return;
                }
                getAnnounce(Integer.parseInt(number),false);
            }
        });
        getAnnounce(1,true);
    }

    private void getAnnounce(int pageNum,boolean isFirst) {
        if(curPage==pageNum && !isFirst){
            ToastUtil.shortToast(getActivity(),"当前页面已加载");
            return;
        }
        loading_layout.setVisibility(View.VISIBLE);
        curPage = pageNum;

        Map<String, String> map = new HashMap<>();
        map.put("pageSize", pageSize + "");
        map.put("pageNum", pageNum + "");
        map.put("businessFlag", "busiTypeDefault");
        //获取政策文件
//        Disposable subscribe = HttpUtil.getInstance("http://192.168.30.125:8087/").get(GcjsUrlConstant.ANNOUNCE_LIST, map)
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.MESSAGE_LIST, map)
                .subscribe(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult<Message> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<Message>>() {
                        }.getType());
                        Message data = apiResult.getData();
                        if (data != null) {
                            messageListAdapter.setNewData(data.getRows());
                            maxPage = data.getTotal()/pageSize+1;
                            pc_page_control.setTotalPage(maxPage);
                            tv_list_index_tip.setText("第"+((curPage-1)*pageSize +1 )+"-"+(curPage * pageSize )+"条/总共"+maxPage+"页");
                        }

                    } else {

                    }
                    loading_layout.setVisibility(View.GONE);
                }, throwable -> {
                    throwable.printStackTrace();
                    loading_layout.setVisibility(View.GONE);
                });
        compositeDisposable.add(subscribe);
    }
}
