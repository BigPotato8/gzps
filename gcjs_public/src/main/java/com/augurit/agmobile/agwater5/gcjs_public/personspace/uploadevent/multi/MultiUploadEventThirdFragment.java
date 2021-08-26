package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.multi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;

import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MultiMaterialBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.ReportRepository;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity;
import com.augurit.common.common.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import static android.app.Activity.RESULT_OK;
import static com.augurit.agmobile.common.view.filepicker.ui.WEUIFilePickerActivity.RESULT_FILE_PATH;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class MultiUploadEventThirdFragment extends BaseViewListFragment<MultiMaterialBean> {
    public static final int CLFJ_REQUEST = 2090;
    public static final String TO_REFLASH = "TO_REFLASH";
    private String index = "";
    private ReportRepository mRepository;

    public ArrayList<MultiMaterialBean> getDatas(){
        return mDatas;
    }

    @Override
    protected void initView() {
        super.initView();
        View view = View.inflate(getActivity(), R.layout.view_title_indicator, null);
        TextView textView = view.findViewById(R.id.tv_indicatetor_name);
        textView.setText("材料清单");
        head_container.addView(view);
        mRepository = new ReportRepository();
        loadDatas(true);
    }



    @Override
    public void onItemClick(View itemView, int position, MultiMaterialBean data) {

    }

    @Override
    public boolean onItemLongClick(View itemView, int position, MultiMaterialBean data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter<MultiMaterialBean> initAdapter() {
        MultiUploadEventThirdAdapter multiUploadEventThirdAdapter = new MultiUploadEventThirdAdapter(getActivity());
        multiUploadEventThirdAdapter.setAttachmentClickListener(s -> {
            index = s;
            Intent intent = new Intent(getActivity(), WEUIFilePickerActivity.class);
            intent.putExtra(WEUIFilePickerActivity.SELECT_LIMIT, 1);
            getActivity().startActivityForResult(intent, CLFJ_REQUEST);
        });

        return multiUploadEventThirdAdapter;
    }

    @Override
    protected Observable<ApiResult<List<MultiMaterialBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setEnableLoadMore(false);
        if (getActivity()!=null && getActivity() instanceof MultiUploadEventActivity) {
            MultiUploadEventActivity activity = (MultiUploadEventActivity) getActivity();
            String stageId = activity.stageId;//阶段id
            StringBuilder array = new StringBuilder();
            for (String s : activity.stateIds){
                array.append(s+",");
            }
            if (array.lastIndexOf(",")!=-1) {
                array.deleteCharAt(array.lastIndexOf(","));
            }
            return mRepository.getMultiMats(stageId,array.toString());//加载材料列表

        }

        return Observable.create(emitter -> {
            List<MultiMaterialBean> objects = new ArrayList<>();
            ApiResult<List<MultiMaterialBean>> listApiResult = new ApiResult<>();
            listApiResult.setData(objects);
            emitter.onNext(listApiResult);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CLFJ_REQUEST) {
            if (resultCode == RESULT_OK) {//获取文件上传
                ArrayList<FileBean> fileBeans = data.getParcelableArrayListExtra(RESULT_FILE_PATH);
                try {

                    if (!ListUtil.isEmpty(fileBeans)) {
                        uploadFiles(fileBeans.get(0));
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadFiles(FileBean fileBean) {

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.show();
        Map<String, String> params = new HashMap<>();
//        params.put("tableName", "AEA_HI_ITEM_MATINST");
//        params.put("pkName", "MATINST_ID");
//        params.put("matId", mDatas.get(Integer.parseInt(index)).getMatId());
//        params.put("matinstId", "");
//        params.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
//        Map<String, FileBean> paramFiles = new HashMap<>();
//        paramFiles.put("files", fileBean);
        params.put("matinstCode", mDatas.get(Integer.parseInt(index)).getMatCode());
        params.put("matinstName", mDatas.get(Integer.parseInt(index)).getMatName());
        params.put("matId", mDatas.get(Integer.parseInt(index)).getMatId());
        params.put("tableName", "AEA_HI_ITEM_MATINST");
        params.put("pkName", "MATINST_ID");
        params.put("matId", mDatas.get(Integer.parseInt(index)).getMatId());
        params.put("matinstId", "");
        params.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
        Map<String, ArrayList<FileBean>> paramFiles = new HashMap<>();
        ArrayList<FileBean> fileBeans = new ArrayList<>();
        fileBeans.add(fileBean);
        paramFiles.put("files", fileBeans);
        HttpUtil.getInstance(AwUrlManager.serverUrl()).postWithFile(GcjsPuUrlConstant.UPLOAD_FILES, params, paramFiles).subscribe(new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                }.getType());
                if (apiResult.isSuccess()) {
                    ToastUtil.shortToast(getActivity(), "上传成功");

                    List<FileBean> fileBeans1 = mDatas.get(Integer.parseInt(index)).getFileBeans();
                    if (fileBeans1 == null) {
                        fileBeans1 = new ArrayList<>();
                    }
                    fileBeans1.add(fileBean);
                    mDatas.get(Integer.parseInt(index)).setFileBeans(fileBeans1);//关联材料
                    mAdapter.notifyItemChanged(Integer.parseInt(index));
                } else {
                    ToastUtil.shortToast(getActivity(), "上传失败");
                }
                pd.dismiss();
            }
        }, throwable -> {
            pd.dismiss();
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void toReflash(String flag){
        if (TO_REFLASH.equals(flag)){
            loadDatas(true);
        }
    }

}
