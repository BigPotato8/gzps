package com.augurit.agmobile.agwater5.gcjs.bszn;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.view.navigation.TitleBar;

import java.util.HashMap;

/**
 * com.augurit.agmobile.agwater5.gcjs.bszn
 * Created by sdb on 2019/4/3  15:31.
 * Desc：
 */

public class BsznDetailActivity extends AppCompatActivity {

    private String[] indicateArr = new String[]{"基本信息", "范围与条件", "办理流程", "办事情形",
            "所需材料", "咨询监督", "窗口监督", "许可收费", "中介服务", "设定依据", "权利与义务", "法律救济"};
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcjs_bszn_detail_activity);
        TitleBar titleBar = findViewById(R.id.title_bar);
        titleBar.setBackListener(v -> finish());
        mTextView = findViewById(R.id.tv_title);
        RecyclerView rv_indicate = findViewById(R.id.rv_indicate);
        RecyclerView rv_content = findViewById(R.id.rv_content);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rv_indicate.setLayoutManager(layoutManager);
        rv_content.setLayoutManager(layoutManager1);
        rv_indicate.setAdapter(new RvIndicateAdapter(this));
    }

    class RvIndicateAdapter extends RecyclerView.Adapter {
        Context contex;
        HashMap<Integer, TextView> mHashMap;
        int selectPosition = 0;

        public RvIndicateAdapter(Context context) {
            this.contex = context;
            mHashMap = new HashMap<>();
        }


        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(View.inflate(contex, R.layout.gcjs_bszn_detail_indicate_item, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.mTextView.setText(indicateArr[position]);

            if (!mHashMap.containsKey(position)) {
                mHashMap.put(position, myViewHolder.mTextView);
            }
            myViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int key : mHashMap.keySet()) {
                        TextView textView = mHashMap.get(key);
                        textView.setTextColor(contex.getResources().getColor(R.color.text_grey));
                        textView.setBackgroundColor(Color.parseColor("#2fB2DFEE"));
                    }

                    mTextView.setText(indicateArr[position]);
                    myViewHolder.mTextView.setTextColor(contex.getResources().getColor(R.color.agmobile_blue));
                    myViewHolder.mTextView.setBackgroundColor(Color.WHITE);
                }
            });
            if (position == selectPosition) {
                mTextView.setText(indicateArr[position]);
                myViewHolder.mTextView.setTextColor(contex.getResources().getColor(R.color.agmobile_blue));
                myViewHolder.mTextView.setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public int getItemCount() {
            return indicateArr.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView mTextView;

            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                mTextView = (TextView) itemView.findViewById(R.id.tv_item);
            }

        }
    }

}
