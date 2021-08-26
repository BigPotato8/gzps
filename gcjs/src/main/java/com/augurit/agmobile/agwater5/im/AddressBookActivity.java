package com.augurit.agmobile.agwater5.im;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.augurit.agmobile.common.im.R;
import com.augurit.agmobile.common.im.common.base.SlidrActivity;
import com.augurit.agmobile.common.im.timchat.ui.AddressBookFragment;

import java.util.ArrayList;

import static com.augurit.agmobile.common.im.timchat.ui.AddressBookFragment.MODE_ADDRESS_BOOK;

/**
 * 单独跳转的通讯录Activity
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.common.im.timchat.ui
 * @createTime 创建时间 ：2019-04-25
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
@Deprecated
public class AddressBookActivity extends SlidrActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book);
        ArrayList<String> testUserIds = IMConstant.getTestUserIds();
        String[] strings = testUserIds.toArray(new String[0]);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment, new AddressBookFragment.Builder()
                        .mode(MODE_ADDRESS_BOOK)
                        .testUsers(strings)
                        .title("")
                        .inActivity(true)
                        .build())
                .commit();
    }
}
