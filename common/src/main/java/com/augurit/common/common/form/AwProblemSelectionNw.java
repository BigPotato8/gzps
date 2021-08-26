package com.augurit.common.common.form;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.augurit.common.common.manager.AwWidgetLayerRepository;
import com.augurit.agmobile.busi.bpm.form.model.Element;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidgetKit;
import com.augurit.agmobile.busi.bpm.widget.view.map.ArcGISMapWidgetView;
import com.augurit.agmobile.busi.bpm.widget.view.map.BaseMapWidget;
import com.augurit.agmobile.busi.bpm.widget.view.map.SelectParam;
import com.augurit.agmobile.common.lib.location.DetailAddress;
import com.augurit.agmobile.common.lib.ui.ClickUtil;
import com.augurit.agmobile.common.view.combineview.AGEditText;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class AwProblemSelectionNw extends BaseFormWidgetKit {

    //    private BaiduMapWidget mMapWidget;
    private BaseMapWidget mMapWidget;
    private BaseFormWidget<String, String> mEtSZWZ;
    private BaseFormWidget<String, String> mEtJDMC;
    private BaseFormWidget<String, String> mEtSZQ;
    private BaseFormWidget<String, String> mEtSZZ;
    private BaseFormWidget<String, String> mEtSZC;
    private BaseFormWidget<String, String> mEtX;
    private BaseFormWidget<String, String> mEtY;
    private BaseFormWidget<String, String> mReportTime;

    public AwProblemSelectionNw(Context context, Element element) {
        super(context, element);
    }

//    .addChildElement(new ElementBuilder("SZWZ")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("问题地点")
//                                        .hint("选择设施或井")
//                                        .isRequired(true)
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("JDMC")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("所在道路")
//                                        .hint("请输入")
//                                        .isRequired(true)
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("szq")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("所在区")
//                                        .hint("请输入")
//                                        .isRequired(true)
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("szz")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("所在镇(街)")
//                                        .hint("请输入")
//                                        .isRequired(true)
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("X")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .addProperty(PROPERTY_TITLE, "Y坐标")
//                                        .addProperty(PROPERTY_IS_REQUIRED, "1")
//                                        .addProperty(PROPERTY_IS_VISIBLE, "0")
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("Y")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .addProperty(PROPERTY_TITLE, "Y坐标")
//                                        .addProperty(PROPERTY_IS_REQUIRED, "1")
//                                        .addProperty(PROPERTY_IS_VISIBLE, "0")
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("szc")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("所在行政村")
//                                        .hint("请输入")
//                                        .isRequired(true)
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("SSLX")
//                                .widget(new WidgetBuilder(SPINNER)
//                                        .title("设施类型")
//                                        .hint("请选择")
//                                        .defaultSelection(0)
//                                        .maxLimit(1)
//                                        .initData(nwdicmodel)
//                                        .relativeElements("WTLX")
//                                        .allowCancelCheck(false)
//                                        .allowTextInput(true)
//                                        .isRequired(true)
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("WTLX")
//                                .widget(new WidgetBuilder(CHECKBOX_EXPAND)
//                                        .title("问题类型")
//                                        .hint("其它类型")
//                                        .defaultSelection(0)
//    //                                .initData(alldicmodel)
//                                        .maxLimit(1)
//                                        .allowTextInput(true)
//                                        .isRequired(true)
//                                        .build())
//            .build())
//            .addChildElement(new ElementBuilder("reporttime")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .addProperty(PROPERTY_TITLE, mContext.getString(R.string.form_report_time))
//            .addProperty(PROPERTY_IS_VISIBLE, "0")
//                                        .build())
//            .build())
//            .build())

    @Override
    public void init() {
        List<Element> elements = mElement.getElements();
        mMapWidget = new BaseMapWidget(mContext, mElement, new ArcGISMapWidgetView(mContext));
        // TODO 此处需要根据特定property来生成各控件
        mEtSZWZ = new BaseFormWidget(mContext, elements.get(0), new AGEditText(mContext));
        mEtJDMC = new BaseFormWidget(mContext, elements.get(1), new AGEditText(mContext));
        mEtSZQ = new BaseFormWidget(mContext, elements.get(2), new AGEditText(mContext));
        mEtSZZ = new BaseFormWidget(mContext, elements.get(3), new AGEditText(mContext));
        mEtSZC = new BaseFormWidget(mContext, elements.get(4), new AGEditText(mContext));
        mEtX = new BaseFormWidget(mContext, elements.get(5), new AGEditText(mContext));
        mEtY = new BaseFormWidget(mContext, elements.get(6), new AGEditText(mContext));
        mReportTime = new BaseFormWidget(mContext, elements.get(7), new AGEditText(mContext));

        ArcGISMapWidgetView view = (ArcGISMapWidgetView) mMapWidget.getView();
        view.setLayerRepository(new AwWidgetLayerRepository());
        mMapWidget.setLocationChangeListener(selectParam -> {
            // 位置选取回调
            DetailAddress detailAddress = selectParam.getDetailAddress();
//            AMFindResult amFindResult = selectParam.getAmFindResult();
            if (detailAddress != null) {

                String addressText = detailAddress.getDetailAddress();
                mEtSZWZ.setValue(addressText);
                mEtJDMC.setValue(detailAddress.getStreet());
                mEtSZQ.setValue(detailAddress.getDistrict());
                mEtSZZ.setValue(detailAddress.getStreet());
                mEtSZC.setValue(detailAddress.getDistrict());
                mEtX.setValue(String.valueOf(detailAddress.getX()));
                mEtY.setValue(String.valueOf(detailAddress.getY()));
            }
        });
        mMapWidget.setVisible(false);
        AGEditText agEtLocation = (AGEditText) mEtSZWZ.getView();
        agEtLocation.setMoreButtonIcon(com.augurit.agmobile.busi.bpm.R.drawable.widget_map_ic_select_location);
        agEtLocation.getMoreButton().setVisibility(View.VISIBLE);
        ClickUtil.bind(agEtLocation.getMoreButton(), v -> mMapWidget.jumpToMap());

        // 添加到套件
        addChildren(mMapWidget, mEtSZWZ, mEtJDMC, mEtSZQ,
                mEtSZZ, mEtSZC,mEtX, mEtY, mReportTime);
    }

    @Override
    protected void initChildrenData() {
        super.initChildrenData();
        //问题地点在没有选取坐标之前不允许编辑，否则会导致无法提交又没校验提示
        mEtSZWZ.setEnable(false);
//        // 初始化字典项
//        String deviceTypeDictCode = mCbDeviceType.getElement().getWidget().getStringProperty(PROPERTY_DICT_TYPE_CODE);
//        List deviceTypeItems = formView.getDictItemsOrTreeItems(deviceTypeDictCode);
//        if (deviceTypeItems != null) {
//            mCbDeviceType.initData(deviceTypeItems);
//        }
//        String problemTypeDictCode = mCbProblemType.getElement().getWidget().getStringProperty(PROPERTY_DICT_TYPE_CODE);
//        List problemTypeItems = formView.getDictItemsOrTreeItems(problemTypeDictCode);
//        if (problemTypeItems != null) {
//            mCbProblemType.initData(problemTypeItems);
//        }

        mReportTime.setValue(System.currentTimeMillis() + "");
    }

    @Override
    public Map<String, String> getValue() {
        HashMap<String, String> valueMap = new HashMap<>();
        valueMap.put(mEtSZWZ.getElement().getElementCode(), mEtSZWZ.getValue());
        valueMap.put(mEtJDMC.getElement().getElementCode(), mEtJDMC.getValue());
        valueMap.put(mEtSZQ.getElement().getElementCode(), mEtSZQ.getValue());
        valueMap.put(mEtSZZ.getElement().getElementCode(), mEtSZZ.getValue());
        valueMap.put(mEtSZC.getElement().getElementCode(), mEtSZC.getValue());
        valueMap.put(mEtX.getElement().getElementCode(), mEtX.getValue());
        valueMap.put(mEtY.getElement().getElementCode(), mEtY.getValue());
        valueMap.put(mReportTime.getElement().getElementCode(), mReportTime.getValue());
        return valueMap;
    }

    @Override
    public void setValue(Map<String, String> value) {
        String SZWZ = value.get(mEtSZWZ.getElement().getElementCode());
        String JDMC = value.get(mEtJDMC.getElement().getElementCode());
        String SZQ = value.get(mEtSZQ.getElement().getElementCode());
        String SZZ = value.get(mEtSZZ.getElement().getElementCode());
        String SZC = value.get(mEtSZC.getElement().getElementCode());
        String x = value.get(mEtX.getElement().getElementCode());
        String y = value.get(mEtY.getElement().getElementCode());
        String reportTime = value.get(mReportTime.getElement().getElementCode());


        if (SZWZ != null) {
            mEtSZWZ.setValue(SZWZ);
        }
        if (JDMC != null) {
            mEtJDMC.setValue(JDMC);
        }
        if (SZQ != null) {
            mEtSZQ.setValue(SZQ);
        }
        if (SZZ != null) {
            mEtSZZ.setValue(SZZ);
        }
        if (SZC != null) {
            mEtSZC.setValue(SZC);
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
                Log.d("AwProblemSelectionNw","setValue:"+e.toString());
            }
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
            mEtSZWZ.setEnable(true);
        }
        mMapWidget.onActivityResult(requestCode, resultCode, data);
    }
}
