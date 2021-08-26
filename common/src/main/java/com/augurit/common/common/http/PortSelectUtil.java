package com.augurit.common.common.http;

import com.augurit.agmobile.busi.bpm.dict.model.DictTreeItem;
import com.augurit.agmobile.busi.bpm.dict.source.local.DictLocalDataSource;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.common.util.StringUtil;
import com.augurit.common.common.util.ValidateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzpssb.common
 * @createTime 创建时间 ：2018-09-11
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-09-11
 * @modifyMemo 修改备注：
 */

public class PortSelectUtil {

   private static  String defaultPort = "8080";
   private static DictLocalDataSource mDataSource;

    public static String getDefaultPort() {
        return defaultPort;
    }

    public static void setDefaultPort(String defaultPort) {
        PortSelectUtil.defaultPort = defaultPort;
    }

    /**
     * UPLOAD_AGSUPPORT
     * 端口选择
     */
    public static String getSupportPort() {
        String port = defaultPort;//默认的端口
        List<String> ports = getRandomPort("requset_port" );//TODO 数据字典的键值对，需要在平台配置
        if (!ListUtil.isEmpty(ports)) {
            port = ports.get(new Random().nextInt(ports.size()));
        }
        return port;
    }

    private static List<String> getRandomPort( String dicCode) {
        if(mDataSource == null){
            mDataSource = new  DictLocalDataSource();
        }
        DictTreeItem treeItem = mDataSource.getDictTreeItemByParentTypeCode(dicCode).get(0);//数据字典端口
        ArrayList<String> ports = new ArrayList<>();
        if (treeItem!=null && !ListUtil.isEmpty(treeItem.getChildren())) {
            for (DictTreeItem dict :treeItem.getChildren()) {
                if (!StringUtil.isEmpty(dict.getChildren())
                        && ValidateUtils.isInteger(dict.getItemName())) {
                    ports.add(":" + dict.getItemName());
                }
            }
        }
     return ports;
    }
}