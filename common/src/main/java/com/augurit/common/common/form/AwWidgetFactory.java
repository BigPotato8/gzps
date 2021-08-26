package com.augurit.common.common.form;

import android.content.Context;

import com.augurit.agmobile.busi.bpm.form.model.Element;
import com.augurit.agmobile.busi.bpm.widget.WidgetFactory;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.common.common.form.AwProblemSelectionKit;
import com.augurit.common.common.form.AwProblemSelectionNw;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.form.view
 * @createTime 创建时间 ：2018/10/22
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwWidgetFactory extends WidgetFactory {

    public static final String AW_PROBLEM_SELECTION_KIT = "AW_PROBLEM_SELECTION_KIT";
    public static final String AW_PROBLEM_SELECTION_NW = "AW_PROBLEM_SELECTION_NW";

    @Override
    public BaseFormWidget create(Context context, Element element) {
        String widgetCode = element.getWidget().getWidgetCode();
        if (widgetCode.equals(AW_PROBLEM_SELECTION_KIT)) {
            return new AwProblemSelectionKit(context, element);
        }else if (widgetCode.equals(AW_PROBLEM_SELECTION_NW)) {
            return new AwProblemSelectionNw(context, element);
        }
        return super.create(context, element);
    }
}
