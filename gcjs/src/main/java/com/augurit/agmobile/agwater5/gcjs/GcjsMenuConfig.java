package com.augurit.agmobile.agwater5.gcjs;

import android.content.Context;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.model.MenuItem;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewActivity;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.EventListActivity;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.agwater5.gcjs.zcfg.ZczyActivity;
import com.augurit.common.common.form.AwFormActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 排水巡检 本地菜单配置
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage
 * @createTime 创建时间 ：2018/8/21
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/21
 * @modifyMemo 修改备注：
 */
public class GcjsMenuConfig {

    public static List<MenuItem> getHeaderMenus(Context context) {
        ArrayList<MenuItem> menus = new ArrayList<>();
        menus.add(new MenuItem(context.getString(R.string.menu_todo1), R.mipmap.gcjs_dbsx, EventListActivity.class)
                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.HANDLING)
                .addParam(EventListActivity.EXTRA_TITLE, context.getString(R.string.menu_todo1)));
//        menus.add(new MenuItem(context.getString(R.string.menu_done), R.mipmap.gcjs_bjsx, EventListActivity.class)
//                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.HANDLED)
//                .addParam(EventListActivity.EXTRA_TITLE, context.getString(R.string.menu_done)));//已办
//        menus.add(new MenuItem(context.getString(R.string.menu_all), R.mipmap.home_tijiao, EventListActivity.class)
//                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.DEPARTMENT_ALL)
//                .addParam(EventListActivity.EXTRA_TITLE, context.getString(R.string.menu_all)));//督办
//        menus.add(new MenuItem(context.getString(R.string.menu_msg), R.mipmap.gcjs_grxx, MsgListActivity.class)
//                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.UPLOADED)
//                .addParam(EventListActivity.EXTRA_TITLE, context.getString(R.string.menu_msg)));//个人消息
        menus.add(new MenuItem(context.getString(R.string.menu_overtime), R.mipmap.home_yuqi, EventListActivity.class)
                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.HANDLED)
                .addParam(EventListActivity.EXTRA_TITLE, context.getString(R.string.menu_overtime)));
        menus.add(new MenuItem(context.getString(R.string.menu_warning), R.mipmap.home_yujing, EventListActivity.class)
                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.DEPARTMENT_ALL)
                .addParam(EventListActivity.EXTRA_TITLE, context.getString(R.string.menu_warning)));
        return menus;
    }

    public static List<MenuItem> getShortcutMenus(Context context) {
        ArrayList<MenuItem> menus = new ArrayList<>();
        ArrayList<String> rawFiles = new ArrayList<>();
        rawFiles.add(AwUrlManager.serverUrl() + "/" + GcjsUrlConstant.HEADER_URL + "/docs/policy_file.pdf");

        ArrayList<String> reformtreeFiles = new ArrayList<>();
        reformtreeFiles.add("http://shenbao.dg.gov.cn/dgsyth/serviceimplement/indexFile/20180828_img.png");
        menus.add(new MenuItem(context.getString(R.string.menu_rawFile), R.mipmap.rawfile, WatchImageOrPdfActivity.class)
                .addParam(WatchImageOrPdfActivity.FILES_PATH, rawFiles));
        menus.add(new MenuItem(context.getString(R.string.menu_fileIndicate), R.mipmap.fileindicate, WebViewActivity.class)
                .addParam(WebViewConstant.WEBVIEW_TITLE, context.getString(R.string.menu_fileIndicate))
                .addParam(WebViewConstant.WEBVIEW_URL_PATH, AwUrlManager.serverUrl()  + "/" + GcjsUrlConstant.HEADER_URL +  "/rest/index/policy/handingGuides"));
//        menus.add(new MenuItem(context.getString(R.string.menu_fileIndicate), R.mipmap.fileindicate, BsznActivity.class));
        menus.add(new MenuItem(context.getString(R.string.menu_reformTree), R.mipmap.reformtree, WatchImageOrPdfActivity.class)
                .addParam(WatchImageOrPdfActivity.FILES_PATH, reformtreeFiles));
//        menus.add(new MenuItem(context.getString(R.string.menu_informPromise), R.mipmap.informpromise, GzcnActivity.class));

//        menus.add(new MenuItem(context.getString(R.string.menu_informPromise), R.mipmap.informpromise, WebViewActivity.class)
//                .addParam(WebViewConstant.WEBVIEW_TITLE, context.getString(R.string.menu_informPromise))
//                .addParam(WebViewConstant.WEBVIEW_URL_PATH, AwUrlManager.serverUrl() + "/" + GcjsUrlConstant.HEADER_URL + "/rest/index/policy/pdf"));
        menus.add(new MenuItem(context.getString(R.string.menu_informPromise), R.mipmap.informpromise, ZczyActivity.class)
                .addParam(AwFormActivity.EXTRA_TITLE, context.getString(R.string.menu_informPromise)));

        return menus;
    }

    public static List<MenuItem> getSpecialColumnMenus(Context context) {
        ArrayList<MenuItem> menus = new ArrayList<>();
//        menus.add(new MenuItem(context.getString(R.string.menu_news), R.mipmap.menu_news_global, null));
        menus.add(new MenuItem(context.getString(R.string.menu_announcement), R.mipmap.home_meage, null));
        menus.add(new MenuItem(context.getString(R.string.menu_communication), R.mipmap.home_talk, null));
//        menus.add(new MenuItem(context.getString(R.string.menu_laws), R.mipmap.home_law, null));

//        menus.add(new MenuItem(context.getString(R.string.menu_board), R.mipmap.home_bang, null));
        menus.add(new MenuItem(context.getString(R.string.menu_help), R.mipmap.home_goverment, null));
        //menus.add(new MenuItem(context.getString(R.string.menu_transaction), R.mipmap.home_shiwu, NWPublicAffairActivity.class));
//        menus.add(new MenuItem(context.getString(R.string.menu_examination_public), R.mipmap.menu_news_global, CheckPublicActivity.class
//        ));

//        menus.add(new MenuItem(context.getString(R.string.menu_patrol_public), R.mipmap.home_meage, InspectPublicActivity.class));
//        menus.add(new MenuItem(context.getString(R.string.menu_device_state), R.mipmap.home_talk, FacilityStateActivity.class));

        return menus;
    }

}
