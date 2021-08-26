package com.augurit.agmobile.agwater5.gcjspad.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.view.combineview.AGEditText;
import com.augurit.agmobile.common.view.skin.SkinManager;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.widget
 * @createTime 创建时间 ：2020/12/14
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class PadEditText2 extends AGEditText {

    public PadEditText2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void initView(Context context, AttributeSet attrs) {
        //        View view = LayoutInflater.from(context).inflate(R.layout.view_edit_text, this);
        View view = SkinManager.getInstance().inflate(context, R.layout.view_edit_text_pad2, this);
        tv_left = view.findViewById(R.id.tv_name);
        tv_requiredTag = view.findViewById(R.id.tv_requiredTag);
        et_right = view.findViewById(R.id.et_content);
        tv_error = view.findViewById(R.id.tv_error);
        btn_more = view.findViewById(R.id.btn_more);
        iv_more = view.findViewById(R.id.iv_more);
        tv_more = view.findViewById(R.id.tv_more);
        setListener();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AGEditText);
        String textName = a.getString(R.styleable.AGEditText_textViewName);
        String hint = a.getString(R.styleable.AGEditText_editTextHint);
        int maxLength = a.getInt(R.styleable.AGEditText_editTextMaxLength, 50);

        tv_left.setText(textName);
        et_right.setHint(hint);
        if (maxLength != -1) {
            setMaxLimit(maxLength);
        }
        a.recycle();
    }
}
