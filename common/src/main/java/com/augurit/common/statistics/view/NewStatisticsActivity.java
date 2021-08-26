package com.augurit.common.statistics.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.augurit.common.R;

/**
 * com.augurit.common.statistics.view
 * Created by sdb on 2019/9/24  13:37.
 * Desc：
 */
public class NewStatisticsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new NewStatisticsFragment()).commit();
    }

}
