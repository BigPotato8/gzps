package com.augurit.agmobile.agwater5.gcjs_public.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.view.imagepicker.ImagePicker;
import com.augurit.agmobile.common.view.imagepicker.loader.GlideImageLoader;
import com.augurit.agmobile.common.view.imagepicker.view.CropImageView;
import com.augurit.agmobile.common.view.widget.WEUICell;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.mine
 * @createTime 创建时间 ：2018/4/25
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/4/25
 * @modifyMemo 修改备注：
 */

public class SelfProfileActivity extends AppCompatActivity {

    private static final int REQ_CHOOSE_PHOTO = 100;

    private ImageView iv_avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_self_profile);
        initView();
    }

    private void initView() {
        View ll_avatar = findViewById(R.id.ll_avatar);
        iv_avatar = findViewById(R.id.iv_avatar);
        WEUICell cell_name = findViewById(R.id.cell_name);
        WEUICell cell_account = findViewById(R.id.cell_account);
        WEUICell cell_org = findViewById(R.id.cell_org);

        User user = LoginManager.getInstance().getCurrentUser();
        cell_name.setContent(user.getUserName());
        cell_account.setContent(user.getLoginName());

        try {
            cell_org.setContent(user.getOrganizations().get(user.getOrgSelected()).getOrgName());
        } catch (Exception e) {
        }

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(GlideImageLoader.getInstance());
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusHeight(800);
        imagePicker.setFocusWidth(800);
        imagePicker.setOutPutX(1000);
        imagePicker.setOutPutY(1000);

//        ll_avatar.setOnClickListener(v -> {
//            PermissionUtil.instance()
//                    .with(SelfProfileActivity.this)
//                    .rationale("图片选择需要以下权限:\\n\\n1.访问设备上的照片\\n\\n2.拍照")
//                    .requestCode(101)
//                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                    .check(permissions -> {
//                        Intent intent = new Intent(SelfProfileActivity.this, ImageGridActivity.class);
//                        startActivityForResult(intent, REQ_CHOOSE_PHOTO);
//                    });
//        });

    }

}
