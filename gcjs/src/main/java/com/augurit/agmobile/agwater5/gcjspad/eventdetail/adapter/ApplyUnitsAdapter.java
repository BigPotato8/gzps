package com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter;

import android.text.TextUtils;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.dict.GcjsDictRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjspad.widget.PadEditText;
import com.augurit.agmobile.busi.bpm.dict.model.Dict;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail
 * @createTime 创建时间 ：2020/12/4
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ApplyUnitsAdapter extends BaseQuickAdapter<EventInfoBean.UnitInfoVoListBean, BaseViewHolder> {
    private GcjsDictRepository mGcjsDictRepository;


    public ApplyUnitsAdapter(int layoutResId) {
        super(layoutResId);
        mGcjsDictRepository = new GcjsDictRepository();
    }

    @Override
    protected void convert(BaseViewHolder helper, EventInfoBean.UnitInfoVoListBean item) {
        helper.setText(R.id.tv_list_index_tip, helper.getAdapterPosition()+1+"");
        String unitType = "建设单位";
        List<? extends IDictItem> xm_dwlx = getDictItemsOrTreeItems("XM_DWLX");
        if(!ListUtil.isEmpty(xm_dwlx)){
            for (IDictItem dictItem : xm_dwlx){
                if(TextUtils.equals(dictItem.getValue(),item.getUnitType())){
                    unitType = dictItem.getLabel();
                    break;
                }
            }
        }
        ((PadEditText)helper.getView(R.id.pe_project_unitType)).setValue(unitType);
        ((PadEditText)helper.getView(R.id.pe_project_unifiedSocialCreditCode)).setValue(item.getUnifiedSocialCreditCode());
        ((PadEditText)helper.getView(R.id.pe_project_applicant)).setValue(item.getApplicant());
        ((PadEditText)helper.getView(R.id.pe_project_idrepresentative)).setValue(item.getIdrepresentative());
        ((PadEditText)helper.getView(R.id.pe_project_idno)).setValue(item.getIdno());
        ((PadEditText)helper.getView(R.id.pe_project_linkmanName)).setValue(item.getLinkmanName());
        ((PadEditText)helper.getView(R.id.pe_project_linkmanMobilePhone)).setValue(item.getLinkmanMobilePhone());
        ((PadEditText)helper.getView(R.id.pe_project_linkmanCertNo)).setValue(item.getLinkmanCertNo());
    }


    public List<? extends IDictItem> getDictItemsOrTreeItems(String parentTypeCode) {
        List<? extends IDictItem> items = null;
        Dict dict = mGcjsDictRepository.getDictByTypeCode(parentTypeCode);
        if (dict != null) {
            if ("0".equals(dict.getTypeIsTree())) {
                items = mGcjsDictRepository.getDictItemByParentTypeCode(parentTypeCode);
            } else if ("1".equals(dict.getTypeIsTree())) {
                items = mGcjsDictRepository.getDictTreeItemByParentTypeCode(parentTypeCode);
            }
        }
        return items;
    }
}
