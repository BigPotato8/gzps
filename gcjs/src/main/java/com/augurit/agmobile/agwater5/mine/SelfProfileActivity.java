package com.augurit.agmobile.agwater5.mine;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.im.timchat.event.UserProfileChangedEvent;
import com.augurit.agmobile.common.im.timchat.manager.UserManager;
import com.augurit.agmobile.common.im.timchat.model.SelfInfo;
import com.augurit.agmobile.common.im.timchat.ui.AvatarPreviewActivity;
import com.augurit.agmobile.common.im.timchat.utils.AvatarGenerator;
import com.augurit.agmobile.common.im.timchat.utils.CosManager;
import com.augurit.agmobile.agwater5.im.IMConstant;
import com.augurit.agmobile.common.lib.log.LogUtil;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.imagepicker.ImagePicker;
import com.augurit.agmobile.common.view.imagepicker.bean.ImageItem;
import com.augurit.agmobile.common.view.imagepicker.loader.GlideImageLoader;
import com.augurit.agmobile.common.view.imagepicker.ui.ImageGridActivity;
import com.augurit.agmobile.common.view.imagepicker.ui.ImageViewActivity;
import com.augurit.agmobile.common.view.imagepicker.view.CropImageView;
import com.augurit.agmobile.common.view.widget.WEUICell;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMUserProfile;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


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

    private static final String TAG = SelfProfileActivity.class.getSimpleName();
    private static final int REQ_CHOOSE_PHOTO = 100;

    private ImageView iv_avatar;
    private Disposable disposable;

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
//        imagePicker.setImageLoader(GlideImageLoader.getInstance());
        imagePicker.setCrop(true);
        imagePicker.setSaveRectangle(true);
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusHeight(800);
        imagePicker.setFocusWidth(800);
        imagePicker.setOutPutX(1000);
        imagePicker.setOutPutY(1000);

        if (!IMConstant.IS_ENABLED) return;
        TIMUserProfile profile = SelfInfo.getInstance().getProfile();
        if (profile == null) return;

        String nickName = profile.getNickName();
        if (!TextUtils.isEmpty(nickName)) {
            cell_name.setContent(nickName);
        }

        String url = SelfInfo.getInstance().getFaceUrl();
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.avatar_person_default)
                    .into(iv_avatar);
        }

        // 查看头像
        iv_avatar.setOnClickListener(v -> {
            String faceUrl = SelfInfo.getInstance().getFaceUrl();

//            Intent intent = new Intent(SelfProfileActivity.this, ImageViewActivity.class);
//            ImageItem imageItem = new ImageItem();
//            imageItem.path = faceUrl;
//            ArrayList<ImageItem> list = new ArrayList<>();
//            list.add(imageItem);
//            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, list);
//            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
//            intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
//            startActivity(intent);

            Intent intent = AvatarPreviewActivity.getIntent(this, faceUrl);
            startActivity(intent);
        });

        // 变更头像
        ll_avatar.setOnClickListener(v -> {
            PermissionUtil.instance()
                    .with(SelfProfileActivity.this)
                    .rationale("图片选择需要以下权限:\\n\\n1.访问设备上的照片\\n\\n2.拍照")
                    .requestCode(101)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .check(permissions -> {
                        Intent intent = new Intent(SelfProfileActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent, REQ_CHOOSE_PHOTO);
                    });
        });
    }

    // TODO 有待封装至底层
    private void modifyAvatar(ImageItem imageItem) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("上传头像中...");
        dialog.show();

        String path = imageItem.path;

        disposable = Observable.just(path)
                .map(s -> {
                    String faceUrl = "";
                    // 从文件中获取bitmap
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    bitmap = AvatarGenerator.cropBitmap(bitmap);    // 居中切成正方形
                    if (bitmap.getHeight() > 512) {
                        bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
                    }
                    // 上传
                    String cosPath = CosManager.getInstance().getPersonAvatarPath() + "/"
                            + SelfInfo.getInstance().getId() + "_" + System.currentTimeMillis() + ".png";
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    try {
                        PutObjectResult result = CosManager.getInstance().putSync(cosPath, baos.toByteArray());
                        if (result.getHttpCode() == 200) {
                            faceUrl = result.accessUrl;
                        }
                    } catch (CosXmlServiceException | CosXmlClientException e) {
                        e.printStackTrace();
                    }
                    return faceUrl;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (s != null && !s.isEmpty()) {
                        Glide.with(SelfProfileActivity.this).load(s).into(iv_avatar);
                        String oldFaceUrl = SelfInfo.getInstance().getFaceUrl();
                        // 更新用户资料
                        TIMFriendshipManager.ModifyUserProfileParam param = new TIMFriendshipManager.ModifyUserProfileParam();
                        param.setFaceUrl(s);
                        UserManager.getInstance().modifyUserProfile(param, result -> {
                            dialog.dismiss();
                            SelfInfo.getInstance().setProfile(result);
                            EventBus.getDefault().post(new UserProfileChangedEvent());
                        }, error -> {
                            dialog.dismiss();
                            ToastUtil.shortToast(SelfProfileActivity.this, "上传头像失败");
                        });
                        // 把旧的头像文件删除
                        CosManager.getInstance().deleteAsync(CosManager.getInstance().getCosPath(oldFaceUrl),
                                result -> LogUtil.i(TAG, "用户旧头像已删除"), null);
                    } else {
                        dialog.dismiss();
                        ToastUtil.shortToast(SelfProfileActivity.this, "上传头像失败");
                    }
                }, e -> {
                    dialog.dismiss();
                    ToastUtil.shortToast(SelfProfileActivity.this, "上传头像失败");
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CHOOSE_PHOTO) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS && data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (!ListUtil.isEmpty(images)) {
                    modifyAvatar(images.get(0));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
