package com.augurit.agmobile.agwater5.gcjspad;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.lib.file.FileUtils;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.DisplayUtils;
import com.augurit.agmobile.common.view.common.fileview.FileOpenViewActivity;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad
 * @createTime 创建时间 ：2020/12/18
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class CustomFileOpenViewActivity extends AppCompatActivity implements TbsReaderView.ReaderCallback {
    public static final String EXTRA_FILE_PATH = "extra_file_path";
    public static final String EXTRA_TEMP_PATH = "extra_temp_path";
    public static final String EXTRA_FILE_NAME = "extra_file_name";
    private TitleBar titleBar;
    private RelativeLayout rlRoot;
    private TbsReaderView tbsReaderView;
    private String tempPath;
    private String filePath;
    private String mTitle;

    /**
     *
     * @param context
     * @param filePath 文件路径
     * @param tempPath 临时文件路径，确保路径文件夹或文件没有空格 例如：Environment.getExternalStorageDirectory().getAbsolutePath()
     * @param fileName 文件名
     * @return
     */
    public static Intent getIntent(Context context, String filePath, String tempPath,String fileName) {
        Intent intent = new Intent(context, CustomFileOpenViewActivity.class);
        intent.putExtra(EXTRA_FILE_PATH, filePath);
        intent.putExtra(EXTRA_TEMP_PATH, tempPath);
        intent.putExtra(EXTRA_FILE_NAME, fileName);
        context.startActivity(intent);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_open_view);
        if(!DisplayUtils.isTabletDevice(this)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        initView();
    }

    private void initView() {
        titleBar = findViewById(R.id.title_bar);
        rlRoot = findViewById(R.id.rl_root);
        tbsReaderView = new TbsReaderView(this,this);
        tempPath = getIntent().getStringExtra(EXTRA_TEMP_PATH);
        filePath = getIntent().getStringExtra(EXTRA_FILE_PATH);
        mTitle = getIntent().getStringExtra(EXTRA_FILE_NAME);
        titleBar.setTitleText(mTitle);
        rlRoot.removeAllViews();
        rlRoot.addView(tbsReaderView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        displayFile();
    }

    private void displayFile() {
        File tempDir = new File(tempPath);
        if(!tempDir.exists()){
            boolean mkdirs = tempDir.mkdirs();
            if(mkdirs){
                openFile();
            }
        }else {
            openFile();
        }
    }

    private void openFile() {
        Bundle bundle = new Bundle();
        bundle.putString("filePath",filePath);
        bundle.putString("tempPath",tempPath);
        File file = new File(filePath);
        boolean result = tbsReaderView.preOpen(FileUtils.getMimeTypeByFileName(file.getName()), false);
        if(result){
            tbsReaderView.openFile(bundle);
        }else {
            try {
                String mineType = FileUtils.getMimeType(filePath);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                FileUtils.setFlags(intent);
                Uri uri = FileUtils.getUri(filePath,this);
                intent.setDataAndType(uri,mineType);
                startActivity(intent);
                finish();
            }catch (Exception e){
                ToastUtil.shortToast(this, getResources().getString(R.string.validate_check_file_open_fail));
                //文件格式无法打开
                getLayoutInflater().inflate(R.layout.layout_file_cannot_pen,rlRoot,true);
            }
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tbsReaderView.onStop();
    }

}
