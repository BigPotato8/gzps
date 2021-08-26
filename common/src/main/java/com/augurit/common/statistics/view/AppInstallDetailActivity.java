package com.augurit.common.statistics.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.common.R;
import com.augurit.common.common.manager.AwUrlManager;
import com.augurit.common.statistics.model.InstallDetailInfo;

import java.util.List;

public class AppInstallDetailActivity extends AppCompatActivity implements IStatisticsContract.View {

    private RecyclerView mRecyclerView;
    private TextView tvNoData;
    private ProgressDialog progressDialog;
    private StatisticsPresenter mPresenter;
    private boolean roleType;
    private String org_name;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_install_detail);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.install_rv);
        findViewById(R.id.ll_back).setOnClickListener(v -> finish());
        tvTitle = findViewById(R.id.tv_title);
        tvNoData = findViewById(R.id.tv_no_data);
        mPresenter = new StatisticsPresenter(this);

    }

    private void initData() {
        Intent intent = getIntent();
        org_name = intent.getStringExtra("org_name");
        roleType = intent.getBooleanExtra("roleType", false);
        if (org_name.equals("净水公司")) {
            org_name = "净水";
        }
        String isInstalled = intent.getStringExtra("inInstall");
        if (TextUtils.isEmpty(isInstalled)) {
            //柱状图点击
            String url = AwUrlManager.serverUrl() + "rest/app/installRecord/StatisticalAppInOrg?org_name=" + org_name + "&roleType=" + roleType;
            mPresenter.loadInstallDetailInfo(url);
        } else {
            //安装数字点就点击
            String url = AwUrlManager.serverUrl() + "rest/app/installRecord/StatisticalAppGetUsers?org_name=" + org_name + "&isInstalled=" + isInstalled + "&roleType=" + roleType;
            mPresenter.loadInstallDetailInfo(url);
        }
    }

    @Override
    public void showAreaInfo(List<String> areaList) {

    }

    @Override
    public void showLoading() {
        progressDialog = new ProgressDialog(AppInstallDetailActivity.this);
        progressDialog.setMessage(getString(R.string.message_loading));
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void showLoadError(Exception e) {

    }

    @Override
    public void showInstallDetailData(List<InstallDetailInfo.InstallUser> installUser) {
        if (installUser == null || installUser.size() == 0) {
            tvNoData.setVisibility(View.VISIBLE);
            return;
        }
        MyAdapter myAdapter = new MyAdapter(installUser, AppInstallDetailActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AppInstallDetailActivity.this));
        mRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public Context getContext() {
        return null;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        List<InstallDetailInfo.InstallUser> datas;
        Context context;

        public MyAdapter(List<InstallDetailInfo.InstallUser> installUsers, Context context) {
            this.datas = installUsers;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(context, R.layout.install_user_item2, null);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            if (!TextUtils.isEmpty(datas.get(position).getUser_name())) {
                holder.userName.setText(datas.get(position).getUser_name());
            }
            if (!TextUtils.isEmpty(datas.get(position).getDirec_org())) {
                holder.org.setText(datas.get(position).getDirec_org());
            }
            if (!TextUtils.isEmpty(datas.get(position).getJob())) {
                holder.job.setText(datas.get(position).getJob());
            } else {
                holder.job.setText("");
            }
            if (!TextUtils.isEmpty(datas.get(position).getPhone())) {
                holder.userPhone.setText(datas.get(position).getPhone());
            } else {
                holder.userPhone.setText("");
            }
            if (datas.get(position).isInstalled()) {
                holder.isInstall.setText(getString(R.string.statistic_installed));
                holder.isInstall.setTextColor(getResources().getColor(R.color.agmobile_primary));
            } else {
                holder.isInstall.setText(getString(R.string.statistic_unInstalled));
                holder.isInstall.setTextColor(Color.RED);
            }
            holder.userPhone.setOnClickListener(v ->
                    startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + datas.get(position).getPhone()))));
        }

        @Override
        public int getItemCount() {
            if (roleType) {
                tvTitle.setText(org_name + "管理层(" + datas.size() + "人)");
            } else {
                tvTitle.setText(org_name + "一线人员(" + datas.size() + "人)");
            }

            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView userName;
            TextView userPhone;
            TextView isInstall;
            TextView job;
            TextView org;

            public MyViewHolder(View itemView) {
                super(itemView);
                userName = itemView.findViewById(R.id.username);
                userPhone = itemView.findViewById(R.id.userphone);
                isInstall = itemView.findViewById(R.id.isinstall);
                job = itemView.findViewById(R.id.job);
                org = itemView.findViewById(R.id.org);
            }
        }
    }
}
