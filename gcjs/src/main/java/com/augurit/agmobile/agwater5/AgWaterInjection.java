package com.augurit.agmobile.agwater5;


import android.content.Context;

import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.common.dict.AwDictRepository;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5
 * @createTime 创建时间 ：2018/8/23
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/23
 * @modifyMemo 修改备注：
 */
public class AgWaterInjection {

    public static IDictRepository provideDictRepository(Context context){
        return new AwDictRepository();
    }

}
