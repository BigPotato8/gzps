package com.augurit.common.common.form;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.augurit.common.common.manager.AwWidgetLayerRepository;
import com.augurit.agmobile.busi.bpm.form.model.Element;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidgetKit;
import com.augurit.agmobile.busi.bpm.widget.view.map.ArcGISMapWidgetView;
import com.augurit.agmobile.busi.bpm.widget.view.map.BaseMapWidget;
import com.augurit.agmobile.busi.bpm.widget.view.map.SelectParam;
import com.augurit.agmobile.common.arcgis.model.AMFindResult;
import com.augurit.agmobile.common.lib.location.DetailAddress;
import com.augurit.agmobile.common.lib.ui.ClickUtil;
import com.augurit.agmobile.common.view.combineview.AGEditText;
import com.augurit.agmobile.common.view.combineview.AGMultiCheck;
import com.augurit.agmobile.common.view.combineview.IDictItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_DICT_TYPE_CODE;

/**
 * 问题地点、设施选取套件
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.busi.bpm.widget.view
 * @createTime 创建时间 ：2018/5/18
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/5/18
 * @modifyMemo 修改备注：
 */

public class AwProblemSelectionKit extends BaseFormWidgetKit {

    //    private BaiduMapWidget mMapWidget;
    private BaseMapWidget mMapWidget;
    private BaseFormWidget<String, String> mEtLocation;
    private BaseFormWidget<String, String> mEtRoad;
    private BaseFormWidget<String, List<? extends IDictItem>> mCbDeviceType;
    private BaseFormWidget<String, List<? extends IDictItem>> mCbProblemType;
    private BaseFormWidget<String, String> mEtX;
    private BaseFormWidget<String, String> mEtY;
    private BaseFormWidget<String, String> mReportX;
    private BaseFormWidget<String, String> mReportY;
    private BaseFormWidget<String, String> mReportAddr;
    private BaseFormWidget<String, String> mReportTime;

    public AwProblemSelectionKit(Context context, Element element) {
        super(context, element);
    }

    @Override
    public void init() {
        List<Element> elements = mElement.getElements();
        mMapWidget = new BaseMapWidget(mContext, mElement, new ArcGISMapWidgetView(mContext));
        //        mMapWidget = new BaiduMapWidget(mContext, mElement);
        // TODO 此处需要根据特定property来生成各控件
        mEtLocation = new BaseFormWidget(mContext, elements.get(0), new AGEditText(mContext));
        mEtRoad = new BaseFormWidget(mContext, elements.get(1), new AGEditText(mContext));
        mCbDeviceType = new BaseFormWidget(mContext, elements.get(2), new AGMultiCheck(mContext, true));
        mCbProblemType = new BaseFormWidget(mContext, elements.get(3), new AGMultiCheck(mContext, true));
        mEtX = new BaseFormWidget(mContext, elements.get(4), new AGEditText(mContext));
        mEtY = new BaseFormWidget(mContext, elements.get(5), new AGEditText(mContext));
        mReportX = new BaseFormWidget(mContext, elements.get(6), new AGEditText(mContext));
        mReportY = new BaseFormWidget(mContext, elements.get(7), new AGEditText(mContext));
        mReportAddr = new BaseFormWidget(mContext, elements.get(8), new AGEditText(mContext));
        mReportTime = new BaseFormWidget(mContext, elements.get(9), new AGEditText(mContext));

        ArcGISMapWidgetView view = (ArcGISMapWidgetView) mMapWidget.getView();
        view.setLayerRepository(new AwWidgetLayerRepository());
        mMapWidget.setLocationChangeListener(selectParam -> {
            // 位置选取回调
            DetailAddress detailAddress = selectParam.getDetailAddress();
            AMFindResult amFindResult = selectParam.getAmFindResult();
            if (detailAddress != null) {

                if (TextUtils.isEmpty(mReportX.getValue())) {
                    mReportX.setValue(String.valueOf(detailAddress.getX()));
                }
                if (TextUtils.isEmpty(mReportY.getValue())) {
                    mReportY.setValue(String.valueOf(detailAddress.getY()));
                }
                if (TextUtils.isEmpty(mReportAddr.getValue())) {
                    mReportAddr.setValue(String.valueOf(detailAddress.getDetailAddress()));
                }

                String addressText = detailAddress.getDetailAddress();
                mEtLocation.setValue(addressText);
                mEtRoad.setValue(detailAddress.getStreet());
                mEtX.setValue(String.valueOf(detailAddress.getX()));
                mEtY.setValue(String.valueOf(detailAddress.getY()));
            }
            if (amFindResult != null) {
                // 获取设备类型对应字典码
                String deviceTypeDictCode = mCbDeviceType.getElement().getWidget().getStringProperty(PROPERTY_DICT_TYPE_CODE);
                List items = formView.getDictItemsOrTreeItems(deviceTypeDictCode);
                String selDeviceTypeCode = null;
                List<? extends IDictItem> dictChildren = null;
                for (Object obj : items) {
                    if (obj instanceof IDictItem) {
                        IDictItem dictItem = (IDictItem) obj;
                        if (dictItem.getLabel().equals(amFindResult.getLayerName())) {
                            selDeviceTypeCode = dictItem.getValue();
                            dictChildren = dictItem.getChildren();
                            break;
                        }
                    }
                }
                // 设置到设施类型与问题类型
                mCbDeviceType.setValue(selDeviceTypeCode);
                mCbDeviceType.validate();
                mCbDeviceType.setEnable(false);
                mCbProblemType.initData(dictChildren);
                if (dictChildren != null) {
                    mCbProblemType.setVisible(true);
                } else {
                    mCbProblemType.setVisible(false);
                }
            } else if (mFormState != FormState.STATE_READ) {
                mCbDeviceType.setEnable(true);
            }
        });
        mMapWidget.setVisible(false);
        AGEditText agEtLocation = (AGEditText) mEtLocation.getView();
        agEtLocation.setMoreButtonIcon(com.augurit.agmobile.busi.bpm.R.drawable.widget_map_ic_select_location);
        agEtLocation.getMoreButton().setVisibility(View.VISIBLE);
        ClickUtil.bind(agEtLocation.getMoreButton(), v -> mMapWidget.jumpToMap());

        // 设施类型联动问题类型
        mCbDeviceType.setWidgetListener((widget, value, item, isEffective) -> {
            if (item != null && item instanceof IDictItem) {
                if (isEffective) {
                    // 选中则设置其子项给问题类型
                    IDictItem dictItem = (IDictItem) item;
                    try {
                        mCbProblemType.initData(dictItem.getChildren());
                        mCbProblemType.setVisible(true);
                    } catch (Exception e) {
                        Log.d("AwProblemSelectionKit","init:"+e.toString());
                    }
                    if (dictItem.getChildren() == null || dictItem.getChildren().isEmpty()) {
                        mCbProblemType.setVisible(false);
                    }
                } else {
                    // 取消选中则隐藏问题类型
                    mCbProblemType.initData(null);
                    mCbProblemType.setVisible(false);
                }
            }
        });

        // 添加到套件
        addChildren(mMapWidget, mEtLocation, mEtRoad, mCbDeviceType,
                mCbProblemType, mEtX, mEtY, mReportX, mReportY, mReportAddr, mReportTime);
    }

    @Override
    protected void initChildrenData() {
        super.initChildrenData();
        //问题地点在没有选取坐标之前不允许编辑，否则会导致无法提交又没校验提示
        mEtLocation.setEnable(false);
        // 初始化字典项
        String deviceTypeDictCode = mCbDeviceType.getElement().getWidget().getStringProperty(PROPERTY_DICT_TYPE_CODE);
        List deviceTypeItems = formView.getDictItemsOrTreeItems(deviceTypeDictCode);
        if (deviceTypeItems != null) {
            mCbDeviceType.initData(deviceTypeItems);
        }
        String problemTypeDictCode = mCbProblemType.getElement().getWidget().getStringProperty(PROPERTY_DICT_TYPE_CODE);
        List problemTypeItems = formView.getDictItemsOrTreeItems(problemTypeDictCode);
        if (problemTypeItems != null) {
            mCbProblemType.initData(problemTypeItems);
        }

        mReportTime.setValue(System.currentTimeMillis() + "");
    }

    @Override
    public Map<String, String> getValue() {
        HashMap<String, String> valueMap = new HashMap<>();
        valueMap.put(mEtLocation.getElement().getElementCode(), mEtLocation.getValue());
        valueMap.put(mEtRoad.getElement().getElementCode(), mEtRoad.getValue());
        valueMap.put(mCbDeviceType.getElement().getElementCode(), mCbDeviceType.getValue());
        valueMap.put(mCbProblemType.getElement().getElementCode(), mCbProblemType.getValue());
        valueMap.put(mEtX.getElement().getElementCode(), mEtX.getValue());
        valueMap.put(mEtY.getElement().getElementCode(), mEtY.getValue());
        valueMap.put(mReportX.getElement().getElementCode(), mReportX.getValue());
        valueMap.put(mReportY.getElement().getElementCode(), mReportY.getValue());
        valueMap.put(mReportAddr.getElement().getElementCode(), mReportAddr.getValue());
        valueMap.put(mReportTime.getElement().getElementCode(), mReportTime.getValue());
        return valueMap;
    }

    @Override
    public void setValue(Map<String, String> value) {
        String location = value.get(mEtLocation.getElement().getElementCode());
        String road = value.get(mEtRoad.getElement().getElementCode());
        String deviceType = value.get(mCbDeviceType.getElement().getElementCode());
        String problemType = value.get(mCbProblemType.getElement().getElementCode());
        String x = value.get(mEtX.getElement().getElementCode());
        String y = value.get(mEtY.getElement().getElementCode());
        String reportX = value.get(mReportX.getElement().getElementCode());
        String reportY = value.get(mReportY.getElement().getElementCode());
        String reportAddr = value.get(mReportAddr.getElement().getElementCode());
        String reportTime = value.get(mReportTime.getElement().getElementCode());

        if (location != null) {
            mEtLocation.setValue(location);
        }
        if (road != null) {
            mEtRoad.setValue(road);
        }
        if (deviceType != null) {
            mCbDeviceType.setValue(deviceType);
        }
        if (problemType != null) {
            mCbProblemType.setValue(problemType);
        }
        if (x != null) {
            mEtX.setValue(x);
        }
        if (y != null) {
            mEtY.setValue(y);
        }

        if (x != null && y != null) {
            try {
                //                DetailAddress detailAddress = new DetailAddress();
                //                detailAddress.setX(Double.parseDouble(x));
                //                detailAddress.setY(Double.parseDouble(y));
                SelectParam selectParam = new SelectParam();
                selectParam.setX(Double.parseDouble(x));
                selectParam.setY(Double.parseDouble(y));
                mMapWidget.setValue(selectParam);
            } catch (Exception e) {
                Log.d("AwProblemSelectionKit","setValue:"+e.toString());
            }
        }

        if (reportX != null) {
            mReportX.setValue(reportX);
        }
        if (reportY != null) {
            mReportY.setValue(reportY);
        }
        if (reportAddr != null) {
            mReportAddr.setValue(reportAddr);
        }
        if (reportTime != null) {
            mReportTime.setValue(reportTime);
        }
    }

    @Override
    public void setRequestCode(int requestCode) {
        super.setRequestCode(requestCode);
        mMapWidget.setRequestCode(requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {//已经选取坐标，问题地点恢复可编辑
            mEtLocation.setEnable(true);
        }
        mMapWidget.onActivityResult(requestCode, resultCode, data);
    }
}
