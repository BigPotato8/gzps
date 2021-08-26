package com.augurit.agmobile.agwater5.gcjspad.listener;

import android.support.annotation.Nullable;

import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.common.Function5;
import com.augurit.agmobile.common.view.combineview.IDictItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.listener
 * @createTime 创建时间 ：2020/12/3
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class PadFilterWidgetListener implements WidgetListener {

    String _pageKey;
    LinkedHashMap<String, BaseFormWidget> _widgetsMap;
    Function5<String, BaseFormWidget, Object, Object, Boolean, Void> _padFilterWidgetValueChangeListener;

    public PadFilterWidgetListener(String pageKey, LinkedHashMap<String, BaseFormWidget> widgetsMap) {
        this._pageKey = pageKey;
        this._widgetsMap = widgetsMap;
    }


    @Override
    public void onValueChange(BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {
        // 设置关联控件值
        String[] relativeElementCodes = widget.getRelativeElementCodes();
        if (relativeElementCodes != null && relativeElementCodes.length != 0) {
            // 获取关联控件
            ArrayList<BaseFormWidget> relativeWidgets = new ArrayList<>();
            for (String relativeElementCode : relativeElementCodes) {
                for (BaseFormWidget widget1 : _widgetsMap.values()) {
                    if (widget1.getElement().getElementCode().equals(relativeElementCode)) {
                        relativeWidgets.add(widget1);
                        break;
                    }
                }
            }

            // 并设值
            if (item != null
                    && item instanceof IDictItem) {
                if (isEffective) {
                    IDictItem dictItem = (IDictItem) item;
                    try {
                        for (BaseFormWidget relativeWidget : relativeWidgets) {
                            relativeWidget.initData(dictItem.getChildren());
                            relativeWidget.setVisible(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (dictItem.getChildren() == null || dictItem.getChildren().isEmpty()) {
                        for (BaseFormWidget relativeWidget : relativeWidgets) {
                            relativeWidget.setVisible(false);
                        }
                    }
                } else {
                    for (BaseFormWidget relativeWidget : relativeWidgets) {
                        relativeWidget.initData(null);
                        relativeWidget.setVisible(false);
                    }
                }
            }
        }

        // 通知监听
        if (_padFilterWidgetValueChangeListener != null) {
            _padFilterWidgetValueChangeListener.apply(_pageKey, widget, value, item, isEffective);
        }

    }


    public void setPadFilterWidgetValueChangeListener(Function5<String, BaseFormWidget, Object, Object, Boolean, Void> padFilterWidgetValueChangeListener) {
        this._padFilterWidgetValueChangeListener = padFilterWidgetValueChangeListener;
    }
}
