package com.augurit.common.dict;

import android.text.TextUtils;

import com.augurit.agmobile.busi.bpm.dict.DictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.Dict;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.model.DictTreeItem;
import com.augurit.agmobile.busi.bpm.dict.source.local.DictLocalDataSource;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.Role;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.database.CommonSynDao;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.dict.remote.AwDictRemoteDataSource;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

/**
 * 水务数据字典仓库
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.dict
 * @createTime 创建时间 ：2018/9/7
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwDictRepository extends DictRepository {

    public AwDictRepository() {
        mRemoteDataSource = new AwDictRemoteDataSource();
        mLocalDataSource = new AwDictLocalDataSource();
    }

    @Override
    public Observable<List<Boolean>> updateDicts() {
        return mRemoteDataSource.getAllDict()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(dicts -> {
                    if (ListUtil.isEmpty(dicts)) {
                        mLocalDataSource.deleteAllDicts();
                        return dicts;
                    }
                    mLocalDataSource.deleteAllDicts();
                    mLocalDataSource.saveDicts(dicts);
                    return dicts;
                })
                .flatMap(Observable::fromIterable)
                .observeOn(Schedulers.io())
                // 获取列表字典项并保存，树形结构也获取一次列表子项，用于提高检索效率
                .flatMap(dict -> {
                    return Observable.zip(getDictItemsAndSave(dict),
                            getDictTreeItemsAndSave(dict), (iDictItems, iDictItems2) -> {
                                return true;
                            });
                })
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .toObservable();
    }

    protected Observable<List<? extends IDictItem>> getDictItemsAndSave(Dict dict) {
        return mRemoteDataSource.getDictItemByParentTypeCode(dict.getTypeCode())
                .map(dictItems -> {
                    for(DictItem dictItem : dictItems){
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
                    for(DictTreeItem dictItem : dictItems){
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
     * @param clazz
     * @param <T>
     * @return
     */
    public   <T extends IDictItem>List getDicItemFromLocal( Class<T> clazz){

        return mLocalDataSource.getListDataByClass(clazz);
    }

    public String getDictLabelByTypeCode(Class<? extends IDictItem> clazz,String typeCode) {
        if(TextUtils.isEmpty(typeCode)) return "";
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
     * @param clazz
     * @param typeCode
     * @return
     */
    public String getTypeCodeByDictLabel(Class<? extends IDictItem> clazz,String typeCode) {
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
    public static  Boolean isSomebody(String roleName){
        RealmList<Role> roles = LoginManager.getInstance().getCurrentUser().getRole();
        for (Role role:roles) {
            if (role.getOrgRoleCode().contains(roleName)) {
                return  true;
            }
        }
        return  false;
    }

    public static void changeRoleType(int type){
        User currentUser = LoginManager.getInstance().getCurrentUser();
        RealmList<Role> roles = currentUser.getRole();
        String roleName;
        switch (type){
            case 0:
                // 管理员
                roleName = "corp_admin";
                for (Role role:roles) {
                    if (role.getOrgRoleCode().contains("distributors")||role.getOrgRoleCode().contains("groupadmins")) {
                        role.setOrgRoleCode(roleName);
                    }
                }
                break;
            case 1:
                // 所长
                roleName ="distributors";
                for (Role role:roles) {
                    if (role.getOrgRoleCode().contains("corp_admin")||role.getOrgRoleCode().contains("groupadmins")) {
                        role.setOrgRoleCode(roleName);
                    }
                }
                break;
            case 2:
                // 组长
                roleName ="groupadmins";

                for (Role role:roles) {
                    if (role.getOrgRoleCode().contains("corp_admin")||role.getOrgRoleCode().contains("distributors")) {
                        role.setOrgRoleCode(roleName);
                    }
                }
                break;
            default:
                boolean isInited = false;
                for (Role role:roles) {
                    if (role.getOrgRoleCode().contains("corp_admin")||role.getOrgRoleCode().contains("distributors")||role.getOrgRoleCode().contains("groupadmins")) {
                        isInited = true;
                    }
                }
                if(!isInited){
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
