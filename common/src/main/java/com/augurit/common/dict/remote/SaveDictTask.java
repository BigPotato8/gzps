package com.augurit.common.dict.remote;

import com.augurit.agmobile.busi.bpm.dict.source.local.DictLocalDataSource;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 描述
 *      保存数据字典信息到本地。
 * @author 创建人 ：zhouxin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.dict.remote
 * @createTime 创建时间 ：18/11/8
 * @modifyBy 修改人 ：zhouxin
 * @modifyTime 修改时间 ：18/11/8
 * @modifyMemo 修改备注：
 */

public class SaveDictTask<T extends IDictItem> {

         public void saveDic1Local(DictLocalDataSource dataSource, String json, Class<T> clazz){
            Observable.just("")
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext->{
                        Gson gson = new Gson();
                        Type type = new ParameterizedTypeImpl(List.class,new Class[]{clazz});
                        List<T> beans = gson.fromJson(json, type);
                        if (!ListUtil.isEmpty(beans)) {
                            dataSource.deleteAllListDatas(beans.get(0).getClass());
                            dataSource.savaListData(beans);
//                            System.out.println("---------saveDic1Local--------"+clazz);
                        }
                    });

        }
        public List getDicFromLocal(DictLocalDataSource dataSource, Class<T> clazz){
             return dataSource.getListDataByClass(clazz);
        }

    public class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
    }