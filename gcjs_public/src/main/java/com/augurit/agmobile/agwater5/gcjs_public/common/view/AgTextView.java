package com.augurit.agmobile.agwater5.gcjs_public.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.lib.validate.MaxLengthInputFilter;
import com.augurit.agmobile.common.view.combineview.ICombineView;
import com.augurit.agmobile.common.view.skin.SkinManager;

import skin.support.content.res.SkinCompatResources;

/**
 * com.augurit.agmobile.agwater5.common.view
 * Created by sdb on 2019/5/30  16:23.
 * Desc：
 */

public class AgTextView extends RelativeLayout implements ICombineView<String>, TextWatcher {

    protected TextView tv_left;
    protected TextView et_right;
    protected View tv_requiredTag;
    protected TextView tv_error;
    protected View btn_more;
    protected ImageView iv_more;
    protected TextView tv_more;
    protected IHelpValidate mIHelpValidate;
    protected int maxLimit;
    protected boolean mIsFilterMode = false;

    protected OnFocusChangeListener mOnFocusChangedListener;

    public AgTextView(Context context) {
        this(context, false);
    }

    public AgTextView(Context context, boolean isFilterMode) {
        super(context);
        mIsFilterMode = isFilterMode;
        initView(context, null);
        mIHelpValidate = new IHelpValidate() {
            @Override
            public void validate() {
                if (et_right!=null && !TextUtils.isEmpty(et_right.getText().toString())){
                    showErrorText(false);
                }else{
                    showErrorText(true);
                }
            }
        };
    }

    public AgTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {
//        View view = LayoutInflater.from(context).inflate(R.layout.view_edit_text, this);
        View view = SkinManager.getInstance().inflate(context, mIsFilterMode?
                R.layout.custom_ag_textview : R.layout.custom_ag_textview, this);
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

    protected void setListener() {
        //焦点发生变化时校验控件（更新ErrorTex的是否可见）
        setFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && mIHelpValidate != null && et_right.getText().length()!=0) {
                mIHelpValidate.validate();
            }
        });
        //输入完成后校验控件（更新ErrorTex的是否可见）
        addTextChangedListener(this);
    }

//    public void setSelection(int start, int stop) {
//        et_right.setSelection(start, stop);
//    }


    @Override
    public void setTextViewName(String textViewName) {
        tv_left.setText(textViewName);
    }

    @Override
    public void setMaxLimit(int maxLimit) {
        this.maxLimit = maxLimit;
        et_right.setFilters(new InputFilter[]{new MaxLengthInputFilter(maxLimit * 2)});
    }

    @Override
    public void setEnable(boolean isEnable) {
        et_right.setEnabled(isEnable);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        setEnable(!readOnly);
        showRequireTag(!readOnly);
    }

    public void setOnEditTextClickListener(OnClickListener onClickListener) {
        et_right.setOnClickListener(onClickListener);
    }

    public void setEditTextColor(int color) {
        et_right.setTextColor(color);
    }

    @Override
    public void showRequireTag(boolean isShow) {
        if (isShow) {
            tv_requiredTag.setVisibility(VISIBLE);
        } else {
            tv_requiredTag.setVisibility(GONE);
        }
    }

    @Override
    public void setErrorText(String errorText) {
        tv_error.setText(errorText);
    }

    @Override
    public void showErrorText(boolean isShow) {
        if (isShow) {
            setErrorText("不能为空");
            tv_error.setVisibility(VISIBLE);
        } else {
            setErrorText("");
            tv_error.setVisibility(GONE);
        }
    }

    public void setMoreButtonIcon(@DrawableRes int resId) {
        if (resId == 0) {
            iv_more.setVisibility(GONE);
        } else {
            iv_more.setVisibility(VISIBLE);
            iv_more.setImageResource(resId);
        }
    }

    public void setMoreButtonText(@Nullable CharSequence text) {
        if (text == null || text.length() == 0) {
            tv_more.setVisibility(GONE);
        } else {
            tv_more.setVisibility(VISIBLE);
            tv_more.setText(text);
        }
    }

    public View getMoreButton() {
        return btn_more;
    }

    @Override
    public void setHint(String hint) {
        et_right.setHint(hint);
    }

    @Override
    public void showHint(boolean isShow) {
        if (!isShow) {
            et_right.setHintTextColor(SkinCompatResources.getColor(getContext(), android.R.color.transparent));
        } else {
            et_right.setHintTextColor(SkinCompatResources.getColor(getContext(), R.color.agmobile_text_caption));
        }
    }

    @Override
    public void setInputType(String inputType) {
        // 暂时去除TextWatcher 避免不必要的触发
        et_right.removeTextChangedListener(this);
        if("number".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_NUMBER);//只能输入数字
        }else if("numberSigned".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);//输入带正负号的数字
        }else if("numberDecimal".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);//输入带小数点的数字，和输入数字
        }else if("numberDecimalSigned".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);//输入带正负号的数字，带小数点的数字
        }else if("phone".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_PHONE);//输入电话号码所需的相关字符和数字
        }else if("datetime".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_DATETIME);//输入日期时间所需的相关字符和数字
        }else if("date".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);//输入日期所需的相关字符和数字
        }else if("time".equalsIgnoreCase(inputType)){
            et_right.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);//输入时间所需的相关字符和数字
        }else if("textPassword".equalsIgnoreCase(inputType)) {
            et_right.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }else if("textWebPassword".equalsIgnoreCase(inputType)) {
            et_right.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
        }else if("textEmailAddress".equalsIgnoreCase(inputType)) {
            et_right.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }else if ("textVisiblePassword".equalsIgnoreCase(inputType)) {
            et_right.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else {
            et_right.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);//输入字符串
        }
        // 恢复TextWatcher
        et_right.addTextChangedListener(this);
    }

    @Override
    public void setDigits(String digits) {
        et_right.setKeyListener(DigitsKeyListener.getInstance(digits));
    }

    public void setFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangedListener = onFocusChangeListener;
        et_right.setOnFocusChangeListener(mOnFocusChangedListener);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        et_right.addTextChangedListener(textWatcher);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public String getValue() {
        return et_right.getText().toString();
    }

    @Override
    public void setValue(String value) {
        et_right.setText(value);
    }

    @Override
    public String getValidateName() {
        return getContext().getString(R.string.validate_edit_text_name);
    }

    @Override
    public String getValidateUnit() {
        return getContext().getString(R.string.validate_edit_text_unit);
    }

    @Override
    public void helpValidate(IHelpValidate iHelpValidate) {
        this.mIHelpValidate = iHelpValidate;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mIHelpValidate != null && et_right.getText().length()!=0) {
            mIHelpValidate.validate();
        }
    }

    public void showOnlyEditText(boolean isShow) {
        findViewById(R.id.ll_left_container).setVisibility(isShow? GONE : VISIBLE);
//        tv_error.setVisibility(isShow? GONE : VISIBLE);
        btn_more.setVisibility(isShow? GONE : VISIBLE);
    }

}
