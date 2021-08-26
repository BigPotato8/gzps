package com.augurit.agmobile.agwater5.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.common.view.combineview.ICombineView;
import com.augurit.common.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 包名：com.augurit.common.common.view
 * 文件描述：
 * 创建人：limeijuan
 * 创建时间：2021/6/25 10:13
 * 修改人：limeijuan
 * 修改时间：2021/6/25 10:13
 * 修改备注：
 */
public class EditAndTextView extends LinearLayout implements ICombineView<String> {
    private Context context;
    private FlexboxLayout mLlEditText;
    private List<EditText> mEditTextList = new ArrayList<>();

    public EditAndTextView(Context context) {
        super(context, null);
        this.context = context;
        initView(context);
    }

    public EditAndTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public EditAndTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_edit_and_text_view, this);

        mLlEditText = (FlexboxLayout) view.findViewById(R.id.ff_edit_text);


    }

    public TextView addTextView(String text){
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextSize(16);
        mLlEditText.addView(textView);
        return textView;
    }

    public EditText addEditView(String text,String hintText,int width){
        EditText editText = new EditText(context);
        editText.setTextSize(16);
        editText.setWidth(width);
        editText.setHint(hintText);
        editText.setText(text);
        mEditTextList.add(editText);
        mLlEditText.addView(editText);
        return editText;
    }


    /**
     * 获取textview一行最大能显示几个字(需要在TextView测量完成之后)
     *
     * @param text     文本内容
     * @param paint    textview.getPaint()
     * @param maxWidth textview.getMaxWidth()/或者是指定的数值,如200dp
     */
    private int getLineMaxNumber(String text, TextPaint paint, int maxWidth) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        StaticLayout staticLayout = new StaticLayout(text, paint, maxWidth, Layout.Alignment.ALIGN_NORMAL
                , 1.0f, 0, false);
        //获取第一行最后显示的字符下标
        return staticLayout.getLineEnd(0);
    }


    public List<EditText> getCustomValue(){
        return mEditTextList;
    }


    public void setEditMinWidth(EditText editText,int width){
        editText.setMinWidth(width);
    }


    @Override
    public void setTextViewName(String textViewName) {

    }

    @Override
    public void setHint(String hint) {

    }

    @Override
    public void showRequireTag(boolean isShow) {

    }

    @Override
    public void setErrorText(String errorText) {

    }

    @Override
    public void showErrorText(boolean isShow) {

    }

    @Override
    public void setEnable(boolean isEnable) {

    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public String getValidateName() {
        return null;
    }

    @Override
    public String getValidateUnit() {
        return null;
    }

    @Override
    public void helpValidate(IHelpValidate iHelpValidate) {

    }
}