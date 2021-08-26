package com.augurit.common.dict;

import android.support.constraint.ConstraintLayout;

import com.augurit.agmobile.busi.bpm.dict.model.Dict;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.model.DictTreeItem;
import com.augurit.agmobile.busi.bpm.dict.source.local.DictLocalDataSource;
import com.augurit.agmobile.busi.bpm.dict.source.remote.DictRemoteDataSource;
import com.augurit.agmobile.common.lib.database.CommonSynDao;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.dict.remote.AwDictRemoteDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 数据库list为0，且数据字典有code时，增加网络重新请求
 * todo 需要表单增加回调接口，将网络获取的数据字典重新加载到表单
 */
public class AwDictLocalDataSource extends DictLocalDataSource {
    protected CommonSynDao mSynDao;
    protected DictRemoteDataSource mRemoteDataSource;

    public AwDictLocalDataSource() {
        mSynDao = new CommonSynDao();
        mRemoteDataSource = new AwDictRemoteDataSource();
    }

    public List<DictItem> getDictItemByParentTypeCode(String parentTypeCode) {
        List<DictItem> dictItemByParentTypeCode = super.getDictItemByParentTypeCode(parentTypeCode);
        if (ListUtil.isEmpty(dictItemByParentTypeCode)) {//查询结果为0
            List<Dict> allDicts = getAllDicts();
            for (Dict dict : allDicts) {
                if (dict.getId().equals(parentTypeCode)){//数据字典列表有该code
                    mRemoteDataSource.getDictItemByParentTypeCode(parentTypeCode)
                            .map(dictItems -> {
                                for (DictItem dictItem : dictItems) {
                                    dictItem.setParentTypeCode(parentTypeCode);
                                }
                                return dictItems;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(dictItems -> {
                                saveDictItems(dictItems);
                                return dictItems;
                            })
                            .subscribe(dictItems -> {
                                //todo 需要回调

                            }, throwable -> throwable.printStackTrace());
                }
            }
            return super.getDictItemByParentTypeCode(parentTypeCode);
        } else {
            return dictItemByParentTypeCode;
        }
    }

    public List<DictTreeItem> getDictTreeItemByParentTypeCode(String parentTypeCode) {
        List<DictTreeItem> items = super.getDictTreeItemByParentTypeCode(parentTypeCode);
        if (ListUtil.isEmpty(items)) {//查询结果为0
            List<Dict> allDicts = getAllDicts();
            for (Dict dict : allDicts) {
                if (dict.getId().equals(parentTypeCode)) {//数据字典列表有该code
                    mRemoteDataSource.getDictTreeItemByParentTypeCode(parentTypeCode)
                            .map(dictItems -> {
                                for (DictTreeItem dictItem : dictItems) {
                                    dictItem.setParentTypeCode(parentTypeCode);
                                }
                                return dictItems;
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(dictTreeItems -> {
                                saveDictTreeItems(dictTreeItems);
                                return dictTreeItems;
                            })
                            .subscribe(dictItems -> {
                                //todo 需要回调

                            }, throwable -> throwable.printStackTrace());
                }
            }
            return super.getDictTreeItemByParentTypeCode(parentTypeCode);
        } else {
            return items;
        }
    }
}
