package com.augurit.agmobile.agwater5.gcjspad.eventdetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter.ApproveMaterialAdapter;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter.ApproveOtherResultAdapter;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter.ApproveReplyAdapter;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail
 * @createTime 创建时间 ：2020/12/16
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ApproveResultFragment extends BasePadFragment {
    private EventRepository mEventRepository;
    private RecyclerView recycler_view_approval;//批文批复列表
    private RecyclerView recycler_view_material;//证件材料列表
    private RecyclerView recycler_view_other;//其它结果物列表
    private ApproveReplyAdapter mApproveReplyAdapter;
    private ApproveMaterialAdapter mApproveMaterialAdapter;
    private ApproveOtherResultAdapter mApproveOtherResultAdapter;

    private TextView tv_approve_no_data;
    private TextView tv_material_no_data;
    private TextView tv_other_no_data;

    private String applyinstId;
    private String iteminstId;

    public static ApproveResultFragment newInstance(String applyinstId, String iteminstId) {
        Bundle args = new Bundle();
        ApproveResultFragment fragment = new ApproveResultFragment();
        args.putString("applyinstId", applyinstId);
        args.putString("iteminstId", iteminstId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approve_result, container, false);
        mEventRepository = new EventRepository();
        mApproveReplyAdapter = new ApproveReplyAdapter(R.layout.layout_approve_reply_item);
        mApproveMaterialAdapter = new ApproveMaterialAdapter(R.layout.layout_approve_material_item);
        mApproveOtherResultAdapter = new ApproveOtherResultAdapter(R.layout.layout_approve_other_item);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initArgument();
        initData();
    }

    private void initView(View view) {
        tv_approve_no_data = view.findViewById(R.id.tv_approve_no_data);
        tv_material_no_data = view.findViewById(R.id.tv_material_no_data);
        tv_other_no_data = view.findViewById(R.id.tv_other_no_data);
        recycler_view_approval = view.findViewById(R.id.recycler_view_approval);
        recycler_view_material = view.findViewById(R.id.recycler_view_material);
        recycler_view_other = view.findViewById(R.id.recycler_view_other);
        recycler_view_approval.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view_material.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view_other.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view_approval.setAdapter(mApproveReplyAdapter);
        recycler_view_material.setAdapter(mApproveMaterialAdapter);
        recycler_view_other.setAdapter(mApproveOtherResultAdapter);
        recycler_view_approval.setHasFixedSize(true);
        recycler_view_approval.setNestedScrollingEnabled(false);
        recycler_view_material.setHasFixedSize(true);
        recycler_view_material.setNestedScrollingEnabled(false);
        recycler_view_other.setHasFixedSize(true);
        recycler_view_other.setNestedScrollingEnabled(false);
        mApproveReplyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示:")
                            .setMessage("是否删除")
                            .setNegativeButton("取消", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .setPositiveButton("确定", (dialog12, which) -> {
                                PwpfBean.ItemMatinstBean item = (PwpfBean.ItemMatinstBean) adapter.getItem(position);
                                deletePwpfItem(item);
                            })
                            .create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.agmobile_red));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.black));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                }
            }
        });
        mApproveReplyAdapter.setDeleteListener(new FileDeleteListener() {
            @Override
            public void onFileDelete(BaseQuickAdapter adapter, View view, int position, Object item) {
                if (view.getId() == R.id.iv_delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示:")
                            .setMessage("是否删除")
                            .setNegativeButton("取消", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .setPositiveButton("确定", (dialog12, which) -> {
                                PwpfBean.ItemMatinstBean.AttFilesBean attFilesBean = (PwpfBean.ItemMatinstBean.AttFilesBean) adapter.getItem(position);
                                deletePwpfFile(attFilesBean, (PwpfBean.ItemMatinstBean) item);
                            })
                            .create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.agmobile_red));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.black));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                }

            }
        });
        mApproveMaterialAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示:")
                            .setMessage("是否删除")
                            .setNegativeButton("取消", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .setPositiveButton("确定", (dialog12, which) -> {
                                ZjzzBean item = (ZjzzBean) adapter.getItem(position);
                                deleteZjzzItem(item);
                            })
                            .create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.agmobile_red));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.black));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                }
            }
        });
        mApproveMaterialAdapter.setDeleteListener(new FileDeleteListener() {
            @Override
            public void onFileDelete(BaseQuickAdapter adapter, View view, int position, Object item) {
                if (view.getId() == R.id.iv_delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示:")
                            .setMessage("是否删除")
                            .setNegativeButton("取消", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .setPositiveButton("确定", (dialog12, which) -> {
                                ZjzzBean.AttFilesBean attFilesBean = (ZjzzBean.AttFilesBean) adapter.getItem(position);
                                deleteZjzzFile(attFilesBean, (ZjzzBean) item);
                            })
                            .create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.agmobile_red));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.black));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                }

            }
        });
        mApproveOtherResultAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tv_delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示:")
                            .setMessage("是否删除")
                            .setNegativeButton("取消", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .setPositiveButton("确定", (dialog12, which) -> {
                                PwpfBean.ItemMatinstBean item = (PwpfBean.ItemMatinstBean) adapter.getItem(position);
                                deleteOtherItem(item);
                            })
                            .create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.agmobile_red));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.black));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                }

            }
        });
        mApproveOtherResultAdapter.setDeleteListener(new FileDeleteListener() {
            @Override
            public void onFileDelete(BaseQuickAdapter adapter, View view, int position, Object item) {
                if (view.getId() == R.id.iv_delete) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("提示:")
                            .setMessage("是否删除")
                            .setNegativeButton("取消", (dialog1, which) -> {
                                dialog1.dismiss();
                            })
                            .setPositiveButton("确定", (dialog12, which) -> {
                                PwpfBean.ItemMatinstBean.AttFilesBean attFilesBean = (PwpfBean.ItemMatinstBean.AttFilesBean) adapter.getItem(position);
                                deleteOtherFile(attFilesBean, (PwpfBean.ItemMatinstBean) item);
                            })
                            .create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getActivity().getResources().getColor(R.color.agmobile_red));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getActivity().getResources().getColor(R.color.black));
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20);
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(20);
                }

            }
        });
    }

    private void deletePwpfFile(PwpfBean.ItemMatinstBean.AttFilesBean attFilesBean, PwpfBean.ItemMatinstBean item) {
        Map<String, String> map = new HashMap<>();
        map.put("detailIds", attFilesBean.getFileId());//文件id
        map.put("matinstId", item.getMatinstId());//事项id
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).delete(GcjsUrlConstant.DELETE_PWPF_MATERIAL_ATTACHMENT, map)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                        ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                        }.getType());
                        if (apiResult != null && apiResult.isSuccess()) {
                            ToastUtil.shortToast(getContext(), "删除成功");
                            loadApproveReplyData();
                        } else {
                            ToastUtil.shortToast(getContext(), "删除失败");
                        }
                    }

                });
        compositeDisposable.add(subscribe);

    }

    private void deleteZjzzFile(ZjzzBean.AttFilesBean attFilesBean, ZjzzBean item) {
        Map<String, String> map = new HashMap<>();
        map.put("detailIds", attFilesBean.getFileId());//文件id
        map.put("matinstId", item.getMatinstId());//事项id
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).delete(GcjsUrlConstant.DELETE_PWPF_MATERIAL_ATTACHMENT, map)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                        ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                        }.getType());
                        if (apiResult != null && apiResult.isSuccess()) {
                            ToastUtil.shortToast(getContext(), "删除成功");
                            loadMaterialData();
                        } else {
                            ToastUtil.shortToast(getContext(), "删除失败");
                        }
                    }

                });
        compositeDisposable.add(subscribe);
    }

    private void deleteOtherFile(PwpfBean.ItemMatinstBean.AttFilesBean attFilesBean, PwpfBean.ItemMatinstBean item) {
        Map<String, String> map = new HashMap<>();
        map.put("detailIds", attFilesBean.getFileId());//文件id
        map.put("matinstId", item.getMatinstId());//事项id
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).delete(GcjsUrlConstant.DELETE_PWPF_MATERIAL_ATTACHMENT, map)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                        ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                        }.getType());
                        if (apiResult != null && apiResult.isSuccess()) {
                            ToastUtil.shortToast(getContext(), "删除成功");
                            loadOtherResultData();
                        } else {
                            ToastUtil.shortToast(getContext(), "删除失败");
                        }
                    }

                });
        compositeDisposable.add(subscribe);
    }


    private void deletePwpfItem(PwpfBean.ItemMatinstBean item) {
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).delete(GcjsUrlConstant.DELETE_PWPF + "/" + item.getMatinstId(), null)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                        ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                        }.getType());
                        if (apiResult != null && apiResult.isSuccess()) {
                            ToastUtil.shortToast(getActivity(), "删除成功");
                            loadApproveReplyData();
                        } else {
                            ToastUtil.shortToast(getActivity(), "删除失败");
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.shortToast(getActivity(), "删除失败");
                });
        compositeDisposable.add(subscribe);
    }

    private void deleteZjzzItem(ZjzzBean item) {
        Map<String, String> map = new HashMap<>();
        map.put("matinstIds", item.getMatinstId() == null ? "" : item.getMatinstId());
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.DELETE_ZJZZ, map)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                        ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                        }.getType());
                        if (apiResult != null && apiResult.isSuccess()) {
                            ToastUtil.shortToast(getActivity(), "删除成功");
                            loadMaterialData();
                        } else {
                            ToastUtil.shortToast(getActivity(), "删除失败");
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.shortToast(getActivity(), "删除失败");
                });
        compositeDisposable.add(subscribe);
    }

    private void deleteOtherItem(PwpfBean.ItemMatinstBean item) {
        Map<String, String> map = new HashMap<>();
        map.put("matinstIds", item.getMatinstId() == null ? "" : item.getMatinstId());
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.DELETE_ZJZZ, map)
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s) && StringUtils.isJson(s)) {
                        ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                        }.getType());
                        if (apiResult != null && apiResult.isSuccess()) {
                            ToastUtil.shortToast(getActivity(), "删除成功");
                            loadOtherResultData();
                        } else {
                            ToastUtil.shortToast(getActivity(), "删除失败");
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.shortToast(getActivity(), "删除失败");
                });
        compositeDisposable.add(subscribe);
    }

    private void initArgument() {
        applyinstId = getArguments().getString("applyinstId", "");
        iteminstId = getArguments().getString("iteminstId", "");
    }

    private void initData() {
        loadApproveReplyData();
        loadMaterialData();
        loadOtherResultData();
    }

    private void loadApproveReplyData() {
        Disposable subscribe = mEventRepository.getPwpfList(applyinstId, iteminstId)
                .subscribe(listApiResult -> {
                    List<PwpfBean> data = listApiResult.getData();
                    if (listApiResult.isSuccess() && !ListUtil.isEmpty(data) && !ListUtil.isEmpty(data.get(0))) {
                        PwpfBean pwpfBean = data.get(0);
                        if (pwpfBean != null && !ListUtil.isEmpty(pwpfBean.getItemMatinst())) {
                            List<PwpfBean.ItemMatinstBean> itemMatinst = pwpfBean.getItemMatinst();
                            mApproveReplyAdapter.setNewData(itemMatinst);
                            recycler_view_approval.setVisibility(View.VISIBLE);
                            tv_approve_no_data.setVisibility(View.GONE);
                        } else {
                            recycler_view_approval.setVisibility(View.GONE);
                            tv_approve_no_data.setVisibility(View.VISIBLE);
                        }
                    } else {
                        recycler_view_approval.setVisibility(View.GONE);
                        tv_approve_no_data.setVisibility(View.VISIBLE);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    recycler_view_approval.setVisibility(View.GONE);
                    tv_approve_no_data.setVisibility(View.VISIBLE);
                });
        compositeDisposable.add(subscribe);
    }

    private void loadMaterialData() {
        Disposable subscribe = mEventRepository.getZjzzList(applyinstId, iteminstId)
                .subscribe(listApiResult -> {
                    List<ZjzzBean> data = listApiResult.getData();
                    if (listApiResult.isSuccess() && !ListUtil.isEmpty(data)) {
                        mApproveMaterialAdapter.setNewData(data);
                        recycler_view_material.setVisibility(View.VISIBLE);
                        tv_material_no_data.setVisibility(View.GONE);
                    } else {
                        recycler_view_material.setVisibility(View.GONE);
                        tv_material_no_data.setVisibility(View.VISIBLE);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    recycler_view_material.setVisibility(View.GONE);
                    tv_material_no_data.setVisibility(View.VISIBLE);
                });
        compositeDisposable.add(subscribe);
    }


    private void loadOtherResultData() {
        Disposable subscribe = mEventRepository.getOtherGoodsList(applyinstId, iteminstId)
                .subscribe(listApiResult -> {
                    List<PwpfBean> data = listApiResult.getData();
                    if (listApiResult.isSuccess() && !ListUtil.isEmpty(data) && !ListUtil.isEmpty(data.get(0))) {
                        PwpfBean pwpfBean = data.get(0);
                        if (pwpfBean != null && !ListUtil.isEmpty(pwpfBean.getItemMatinst())) {
                            List<PwpfBean.ItemMatinstBean> itemMatinst = pwpfBean.getItemMatinst();
                            mApproveOtherResultAdapter.setNewData(itemMatinst);
                            recycler_view_other.setVisibility(View.VISIBLE);
                            tv_other_no_data.setVisibility(View.GONE);
                        } else {
                            recycler_view_other.setVisibility(View.GONE);
                            tv_other_no_data.setVisibility(View.VISIBLE);
                        }
                    } else {
                        recycler_view_other.setVisibility(View.GONE);
                        tv_other_no_data.setVisibility(View.VISIBLE);
                    }

                }, throwable -> {
                    throwable.printStackTrace();
                    recycler_view_other.setVisibility(View.GONE);
                    tv_other_no_data.setVisibility(View.VISIBLE);
                });
        compositeDisposable.add(subscribe);
    }


}
