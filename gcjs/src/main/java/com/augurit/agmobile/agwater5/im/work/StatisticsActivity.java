package com.augurit.agmobile.agwater5.im.work;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.announce.AnnounceRepository;
import com.augurit.agmobile.common.lib.toast.ToastUtil;

import org.json.JSONObject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StatisticsActivity extends AppCompatActivity {
    private TextView mValue0, mValue1, mValue2, mValue3;
    private AnnounceRepository mRepository;
    private LinearLayout loading_layout;
    private CompositeDisposable mCompositeDisposable;
    private int mPage = 1;
    private int mRows = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handling_statistic_layout);
        mValue0 = findViewById(R.id.tv_value_0);
        mValue1 = findViewById(R.id.tv_value_1);
        mValue2 = findViewById(R.id.tv_value_2);
        mValue3 = findViewById(R.id.tv_value_3);
        loading_layout = findViewById(R.id.loading_layout);
        mRepository = new AnnounceRepository();
        mCompositeDisposable = new CompositeDisposable();
        initData();
    }

    private void initData() {
        loading_layout.setVisibility(View.VISIBLE);
        Disposable disposable = mRepository.getHandlingStatistic().subscribe(new Consumer<String>() {
            @Override
            public void accept(String res) throws Exception {
                loading_layout.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(res);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String message = jsonObject.getString("message");
                        String replaceJson = message.replace("\\\"", "");
                        JSONObject resultObject = new JSONObject(replaceJson);
                        JSONObject result = resultObject.getJSONObject("result");
                        String nowMonthComplete = result.getString("nowMonthComplete");
                        String nowMonthCount = result.getString("nowMonthCount");
                        String allItemInstComplete = result.getString("allItemInstComplete");
                        String allItemInstCount = result.getString("allItemInstCount");
                        mValue0.setText(nowMonthComplete);
                        mValue1.setText(nowMonthCount);
                        mValue2.setText(allItemInstComplete);
                        mValue3.setText(allItemInstCount);
                    }
                } catch (Exception e) {
                    ToastUtil.shortToast(StatisticsActivity.this, "获取办件统计数据失败");
                }
            }
        });
    }

}
