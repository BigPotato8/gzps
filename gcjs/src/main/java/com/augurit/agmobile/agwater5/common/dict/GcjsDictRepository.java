package com.augurit.agmobile.agwater5.common.dict;

import android.text.TextUtils;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.busi.bpm.dict.DictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.Dict;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.model.DictTreeItem;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.Role;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.database.CommonSynDao;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.common.dict.AwDictLocalDataSource;
import com.augurit.common.dict.remote.AwDictRemoteDataSource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

/**
 * 水务数据字典仓库
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.dict
 * @createTime 创建时间 ：2018/9/7
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class GcjsDictRepository extends DictRepository {
    public List<String> typeCodeList;
    /**
     * rest/index/common/code/multi/items/list?dicCodeTypeCodes=PROJECT_PROPERTY,XM_NATURE
     * 根据typecode获取数据字典,一次查多个
     */
    public final static String GET_DICT_BY_TYPECODE_MULTI = GcjsUrlConstant.HEADER_URL + "rest/index/common/code/multi/items/list";
    /**
     * rest/index/common/code/multi/items/list?dicCodeTypeCode=PROJECT_PROPERTY
     * 根据typecode获取数据字典,一次查单个
     */
    public final static String GET_DICT_BY_TYPECODE = GcjsUrlConstant.HEADER_URL + "rest/index/common/code/items/list";


    public GcjsDictRepository() {
        mRemoteDataSource = new AwDictRemoteDataSource();
        mLocalDataSource = new AwDictLocalDataSource();
        typeCodeList = new ArrayList<>();
        typeCodeList.add("PROJECT_PROPERTY");//待办一般详情-工程性质
        typeCodeList.add("XM_DWLX");//项目单位类型
        typeCodeList.add("ITEMINST_STATE");//事项实例状态
        typeCodeList.add("ITEM_PROPERTY");//办件类型
        typeCodeList.add("SPECIAL_TYPE");//特殊程序类型
        typeCodeList.add("XM_PROJECT_STEP");//立项类型
        typeCodeList.add("XM_ZJLY");//资金来源
        typeCodeList.add("XM_TZLX");//投资类型
        typeCodeList.add("XM_TDLY");//土地来源
        typeCodeList.add("XM_PROJECT_LEVEL");//重点项目
        typeCodeList.add("XM_NATURE");//建设性质
        typeCodeList.add("XM_GCFL");//工程分类
        typeCodeList.add("XM_DWLX");//单位类型
        typeCodeList.add("IDCARD_TYPE");//收件人证件类型、联系人证件类型
        typeCodeList.add("APPLYINST_STATE");//申报状态
    }

    //获取所有的数据字典
    @Override
    public Observable<List<Boolean>> updateDicts() {
        List<Observable<List<DictItem>>> observables = new ArrayList<>();
        for (String s : typeCodeList) {
            Observable<List<DictItem>> dictItemsOb = getDictItemsOb(s);
            observables.add(dictItemsOb);
        }
        mLocalDataSource.deleteAllDicts();
        return Observable.zip(observables, new Function<Object[], List<Boolean>>() {
            @Override
            public List<Boolean> apply(Object[] lists) throws Exception {
                List<Boolean> booleanList = new ArrayList<>();
                for (Object o : lists) {
                    List<DictItem> list = (List<DictItem>) o;
                    if (!ListUtil.isEmpty(list)) {
                        mLocalDataSource.saveDictItems(list);//保存数据字典
                        booleanList.add(true);
                    }
                }
                return booleanList;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 根据code获取数据字典，同时保存本地
     *
     * @param dictCode
     * @return
     */
    protected Observable<List<DictItem>> getDictItemsOb(String dictCode) {
        Map<String, String> map = new HashMap<>();
        map.put("dicCodeTypeCode", dictCode);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GET_DICT_BY_TYPECODE, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(string -> {
                    Gson gson = new Gson();
                    ApiResult<List<DictBean>> apiResult = gson.fromJson(string, new TypeToken<ApiResult<List<DictBean>>>() {
                    }.getType());
                    List<DictItem> list = new ArrayList<>();
                    if (apiResult.isSuccess()) {
                        if (!ListUtil.isEmpty(apiResult.getData())) {
                            List<Dict> dicts = new ArrayList<>();
                            Dict dict = new Dict();
                            dict.setId(UUID.randomUUID().toString());
                            dict.setTypeCode(dictCode);//dict索引，数组字典类型主健
                            dict.setTypeIsTree("0");//非树
                            dicts.add(dict);
                            mLocalDataSource.saveDicts(dicts);
                            for (DictBean dictBean : apiResult.getData()) {
                                DictItem dictItem = new DictItem();
                                dictItem.setParentTypeCode(dictCode);
                                dictItem.setLabel(dictBean.getItemName());
                                dictItem.setValue(dictBean.getItemCode());
                                dictItem.setId(dictBean.getItemId());
                                list.add(dictItem);
                            }
                        }
                    }
                    return list;
                });
    }

    protected Observable<List<? extends IDictItem>> getDictItemsAndSave(Dict dict) {
        return mRemoteDataSource.getDictItemByParentTypeCode(dict.getTypeCode())
                .map(dictItems -> {
                    for (DictItem dictItem : dictItems) {
                        dictItem.setParentTypeCode(dict.getTypeCode());
                    }
                    return dictItems;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(dictItems -> {
                    mLocalDataSource.saveDictItems(dictItems);
                    return dictItems;
                });
    }

    protected Observable<List<? extends IDictItem>> getDictTreeItemsAndSave(Dict dict) {
        return mRemoteDataSource.getDictTreeItemByParentTypeCode(dict.getTypeCode())
                .map(dictItems -> {
                    for (DictTreeItem dictItem : dictItems) {
                        dictItem.setParentTypeCode(dict.getTypeCode());
                    }
                    return dictItems;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(dictTreeItems -> {
                    mLocalDataSource.saveDictTreeItems(dictTreeItems);
                    return dictTreeItems;
                });
    }


    @Override
    public String getDictLabelByTypeCode(String typeCode) {
        // 大多数情况获取子项对应标签即可, 效率较高
        List<DictItem> allDictItems = getAllDictItems();
        for (DictItem dictItem : allDictItems) {
            if (dictItem.getValue().equals(typeCode)) {
                return dictItem.getLabel();
            }
        }
        // 其他情况调用父类方法，效率较低（树形存在递归获取）
        return super.getDictLabelByTypeCode(typeCode);
    }


    /**
     * 从本地获取数据字典
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends IDictItem> List getDicItemFromLocal(Class<T> clazz) {

        return mLocalDataSource.getListDataByClass(clazz);
    }

    public String getDictLabelByTypeCode(Class<? extends IDictItem> clazz, String typeCode) {
        if (TextUtils.isEmpty(typeCode)) return "";
        // 大多数情况获取子项对应标签即可, 效率较高
        List<IDictItem> allDictItems = getDicItemFromLocal(clazz);
        for (IDictItem dictItem : allDictItems) {
            if (dictItem.getValue().equals(typeCode)) {
                return dictItem.getLabel();
            }
        }
        // 其他情况调用父类方法，效率较低（树形存在递归获取）
        return super.getDictLabelByTypeCode(typeCode);
    }


    /**
     * 根据标签检索value
     *
     * @param clazz
     * @param typeCode
     * @return
     */
    public String getTypeCodeByDictLabel(Class<? extends IDictItem> clazz, String typeCode) {
        // 大多数情况获取子项对应标签即可, 效率较高
        List<IDictItem> allDictItems = getDicItemFromLocal(clazz);
        for (IDictItem dictItem : allDictItems) {
            if (dictItem.getLabel().equals(typeCode)) {
                return dictItem.getValue();
            }
        }
        return "";
    }

    /**
     * 判断用户角色
     */
    public static Boolean isSomebody(String roleName) {
        RealmList<Role> roles = LoginManager.getInstance().getCurrentUser().getRole();
        for (Role role : roles) {
            if (role.getOrgRoleCode().contains(roleName)) {
                return true;
            }
        }
        return false;
    }

    public static void changeRoleType(int type) {
        User currentUser = LoginManager.getInstance().getCurrentUser();
        RealmList<Role> roles = currentUser.getRole();
        String roleName;
        switch (type) {
            case 0:
                // 管理员
                roleName = "corp_admin";
                for (Role role : roles) {
                    if (role.getOrgRoleCode().contains("distributors") || role.getOrgRoleCode().contains("groupadmins")) {
                        role.setOrgRoleCode(roleName);
                    }
                }
                break;
            case 1:
                // 所长
                roleName = "distributors";
                for (Role role : roles) {
                    if (role.getOrgRoleCode().contains("corp_admin") || role.getOrgRoleCode().contains("groupadmins")) {
                        role.setOrgRoleCode(roleName);
                    }
                }
                break;
            case 2:
                // 组长
                roleName = "groupadmins";

                for (Role role : roles) {
                    if (role.getOrgRoleCode().contains("corp_admin") || role.getOrgRoleCode().contains("distributors")) {
                        role.setOrgRoleCode(roleName);
                    }
                }
                break;
            default:
                boolean isInited = false;
                for (Role role : roles) {
                    if (role.getOrgRoleCode().contains("corp_admin") || role.getOrgRoleCode().contains("distributors") || role.getOrgRoleCode().contains("groupadmins")) {
                        isInited = true;
                    }
                }
                if (!isInited) {
                    Role newRole = new Role();
                    newRole.setOrgRoleCode("corp_admin");
                    roles.add(newRole);
                }
                break;
        }
        CommonSynDao<User> dao = new CommonSynDao<>();
        dao.update(currentUser);

    }

}
