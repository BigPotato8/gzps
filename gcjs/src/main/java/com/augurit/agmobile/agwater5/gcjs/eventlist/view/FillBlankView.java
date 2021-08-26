package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AnswerRange;

import java.util.ArrayList;
import java.util.List;


public class FillBlankView extends RelativeLayout {

    private int status = -1;
    private TextView tvContent;
    private Context context;
    // 答案集合
    private List<String> answerList;
    // 答案范围集合
    private List<AnswerRange> rangeList;
    // 填空题内容
    private SpannableStringBuilder content;
    private View mDividerTop;

    public FillBlankView(Context context, int i) {
        this(context, null);
        this.status = i;//0同意
    }

    public FillBlankView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FillBlankView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_fill_blank, this);
        mDividerTop = view.findViewById(R.id.view_widget_divider_top);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    public void setVisible(int isVisible) {
        mDividerTop.setVisibility(isVisible);
    }

    public void setTextSize(int size) {
        tvContent.setTextSize(size);
        tvContent.setPadding(0, 10, 0, 10);
    }

    /**
     * 设置数据
     *
     * @param originContent   源数据
     * @param answerRangeList 答案范围集合
     */
    public void setData(String originContent, List<AnswerRange> answerRangeList) {
        if (TextUtils.isEmpty(originContent) || answerRangeList == null
                || answerRangeList.isEmpty()) {
            return;
        }

        // 获取课文内容
        content = new SpannableStringBuilder(originContent);
        // 答案范围集合
        rangeList = answerRangeList;

        // 设置下划线颜色
        for (int i = 0; i < rangeList.size(); i++) {
            AnswerRange range = rangeList.get(i);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
            content.setSpan(colorSpan, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // 答案集合
        answerList = new ArrayList<>();
        for (int i = 0; i < rangeList.size(); i++) {
            answerList.add("");
        }

        // 设置填空处点击事件
        for (int i = 0; i < rangeList.size(); i++) {
            if (status == 0) {
                if (i < 4) {
                    continue;
                } else {
                    AnswerRange range = rangeList.get(i);
                    BlankClickableSpan blankClickableSpan = new BlankClickableSpan(i);
                    content.setSpan(blankClickableSpan, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                AnswerRange range = rangeList.get(i);
                BlankClickableSpan blankClickableSpan = new BlankClickableSpan(i);
                content.setSpan(blankClickableSpan, range.start, range.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }

        // 设置此方法后，点击事件才能生效
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvContent.setText(content);
    }

    /**
     * 点击事件
     */
    class BlankClickableSpan extends ClickableSpan {

        private int position;

        public BlankClickableSpan(int position) {
            this.position = position;
        }

        @Override
        public void onClick(final View widget) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_input, null);
            final EditText etInput = (EditText) view.findViewById(R.id.et_answer);
            Button btnFillBlank = (Button) view.findViewById(R.id.btn_fill_blank);

            // 显示原有答案
            String oldAnswer = answerList.get(position);
            if (!TextUtils.isEmpty(oldAnswer)) {
                etInput.setText(oldAnswer);
                etInput.setSelection(oldAnswer.length());
                if (status == 2) {
                    etInput.setTextSize(14);
                } else {
                    etInput.setTextSize(16);
                }
            }

            final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, dp2px(40));
            // 获取焦点
            popupWindow.setFocusable(true);
            // 为了防止弹出菜单获取焦点之后，点击Activity的其他组件没有响应
            popupWindow.setBackgroundDrawable(new PaintDrawable());
            // 设置PopupWindow在软键盘的上方
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            // 弹出PopupWindow
            popupWindow.showAtLocation(tvContent, Gravity.BOTTOM, 0, 0);

            btnFillBlank.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 填写答案
                    String answer = etInput.getText().toString();
                    fillAnswer(answer, position);
                    popupWindow.dismiss();
                }
            });

            // 显示软键盘
            InputMethodManager inputMethodManager =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            // 不显示下划线
            ds.setUnderlineText(true);

        }
    }

    /**
     * 填写答案
     *
     * @param answer   当前填空处答案
     * @param position 填空位置
     */
    public void fillAnswer(String answer, int position) {
        answer = " " + answer + " ";

        // 替换答案
        AnswerRange range = rangeList.get(position);
        content.replace(range.start, range.end, answer);

        // 更新当前的答案范围
        AnswerRange currentRange = new AnswerRange(range.start, range.start + answer.length());
        rangeList.set(position, currentRange);

        // 答案设置下划线
        content.setSpan(new UnderlineSpan(),
                currentRange.start, currentRange.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 将答案添加到集合中
        answerList.set(position, answer.replace(" ", ""));

        // 更新内容
        tvContent.setText(content);
        if (status == 2) {
            tvContent.setTextSize(14);
        } else {
            tvContent.setTextSize(16);
        }

        for (int i = 0; i < rangeList.size(); i++) {
            if (i > position) {
                // 获取下一个答案原来的范围
                AnswerRange oldNextRange = rangeList.get(i);
                int oldNextAmount = oldNextRange.end - oldNextRange.start;
                // 计算新旧答案字数的差值
                int difference = currentRange.end - range.end;

                // 更新下一个答案的范围
                AnswerRange nextRange = new AnswerRange(oldNextRange.start + difference,
                        oldNextRange.start + difference + oldNextAmount);
                rangeList.set(i, nextRange);
            }
        }
    }

    /**
     * 获取答案列表
     *
     * @return 答案列表
     */
    public List<String> getAnswerList() {
        return answerList;
    }

    public void setEnable(boolean isEnable) {
        tvContent.setEnabled(isEnable);
    }

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
