package com.augurit.common.common.manager;

import com.augurit.agmobile.busi.bpm.form.model.Element;
import com.augurit.agmobile.busi.bpm.widget.view.map.SelectParam;
import com.augurit.agmobile.busi.bpm.widget.view.map.arcgis.IWidgetLayerRepository;
import com.augurit.agmobile.busi.map.common.FeatureQueryService;
import com.augurit.agmobile.common.arcgis.model.AMFindResult;
import com.augurit.agmobile.common.arcgis.model.LayerInfo;
import com.augurit.agmobile.common.arcgis.model.LayerType;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 水务地图控件图层仓库
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.form.source
 * @createTime 创建时间 ：2018/12/12
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwWidgetLayerRepository implements IWidgetLayerRepository {

    public ArrayList<LayerInfo> getLoadLayerInfoList(Element element){
        ArrayList<LayerInfo> layerInfos = new ArrayList<>();
        LayerInfo layerInfo = new LayerInfo.Builder("123", "设施图层")
                .url("http://139.159.243.185:6080/arcgis/rest/services/GZPS/GZSWPSGXOwnDept/MapServer")
                .type(LayerType.DynamicLayer)
                .dirTypeName("设施图层")
                .centerY(23.26028940885048)
                .centerX(113.49891996096478)
                .build();
        LayerInfo layerInfo1 = new LayerInfo.Builder("125", "广州地图")
                .url("http://139.159.243.230:6080/arcgis/rest/services/vec/vec1/MapServer")
                .type(LayerType.TileLayer)
                .dirTypeName("广州地图")
                .baseMap(true)//全图按钮以底图的初始分辨率、地图中心为准
                .initResolution(0.00118973050291514)//必须设置初始分辨率才有全图按钮
                .centerY(23.26028940885048)//必须设置初始地图中心点才有全图按钮
                .centerX(113.49891996096478)//必须设置初始地图中心点才有全图按钮
                .build();
        layerInfos.add(layerInfo);
        layerInfos.add(layerInfo1);
        return layerInfos;
    }

    public ArrayList<String> getQueryLayerList(Element element){
        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://139.159.243.185:6080/arcgis/rest/services/GZPS/GZSWPSGXOwnDept/MapServer/5");
        urls.add("http://139.159.243.185:6080/arcgis/rest/services/GZPS/GZSWPSGXOwnDept/MapServer/4");
        return urls;
    }

    @Override
    public Observable<SelectParam> getSelectParam(SelectParam selectParam) {

        Observable<SelectParam> observable = new FeatureQueryService().queryFeatures(selectParam.getLayerUrl(),
                selectParam.getQueryFieldName(), selectParam.getQueryValue())
                .flatMap(new Function<List<AMFindResult>, ObservableSource<SelectParam>>() {
                    @Override
                    public ObservableSource<SelectParam> apply(List<AMFindResult> amFindResults) throws Exception {
                        selectParam.setAmFindResult(amFindResults.get(0));
                        return Observable.just(selectParam);
                    }
                })
                .subscribeOn(Schedulers.io());
        return observable;
    }
}
