package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;

import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single.model.Situation;

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

public class SingleUploadEventThirdFragment extends BaseViewListFragment<Situation.AeaItemMatsBean> {
    public static final int CLFJ_REQUEST = 2090;
    private List<Situation.AeaItemMatsBean> mList = new ArrayList<>();
    private String index = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {
        super.initView();
        View view = View.inflate(getActivity(), R.layout.view_title_indicator, null);
        TextView textView = view.findViewById(R.id.tv_indicatetor_name);
        textView.setText("材料清单");

        head_container.addView(view);

        loadDatas(true);

    }

    @Override
    public void onItemClick(View itemView, int position, Situation.AeaItemMatsBean data) {

    }

    @Override
    public boolean onItemLongClick(View itemView, int position, Situation.AeaItemMatsBean data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter<Situation.AeaItemMatsBean> initAdapter() {
        SingleUploadEventThirdAdapter singleUploadEventThirdAdapter = new SingleUploadEventThirdAdapter(getActivity());
        singleUploadEventThirdAdapter.setAttachmentClickListener(s -> {
            index = s;
            Intent intent = new Intent(getActivity(), WEUIFilePickerActivity.class);
            intent.putExtra(WEUIFilePickerActivity.SELECT_LIMIT, 1);
            getActivity().startActivityForResult(intent, CLFJ_REQUEST);
        });

        return singleUploadEventThirdAdapter;
    }

    @Override
    protected Observable<ApiResult<List<Situation.AeaItemMatsBean>>> loadDatas(int page, int rows, Map filterParams) {

        refresh_layout.setNoMoreData(true);
        return Observable.create(emitter -> {
            ApiResult<List<Situation.AeaItemMatsBean>> listApiResult = new ApiResult<>();
            listApiResult.setData(mList);
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

    public void showDatas(List<Situation.AeaItemMatsBean> list) {
        for (Object obj : list) {
            if (!(obj instanceof Situation.AeaItemMatsBean)) {
                return;
            }
        }
        this.mList = list;

        if (this.isVisible()) {
            loadDatas(true);
        }
    }

}
