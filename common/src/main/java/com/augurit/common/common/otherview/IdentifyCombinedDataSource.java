package com.augurit.common.common.otherview;

import com.augurit.agmobile.busi.map.common.FeatureQueryService;
import com.augurit.agmobile.busi.map.identify.IdentifyUtils;
import com.augurit.agmobile.busi.map.identify.source.IIdentifyDataSource;
import com.augurit.agmobile.common.arcgis.model.AMFindResult;
import com.augurit.agmobile.common.arcgis.model.LayerInfo;
import com.esri.android.map.Layer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.tasks.identify.IdentifyResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 结合Identify与FeatureQueryService
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.commonly
 * @createTime 创建时间 ：2019-06-14
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class IdentifyCombinedDataSource implements IIdentifyDataSource {

    protected MapView mMapView;

    public IdentifyCombinedDataSource(MapView mapView) {
        mMapView = mapView;
    }

    @Override
    public Observable<AMFindResult[]> selectedFeature(Geometry geometry,
                                                      SpatialReference spatialReference,
                                                      List<LayerInfo> queryLayerInfos,
                                                      List<Layer> queryLayers,
                                                      int tolerance,
                                                      Envelope mapExtent,
                                                      int mapWidth,
                                                      int mapHeight,
                                                      boolean ifOnlyQueryPolygon) {

        List<Layer> identifyLayers = new ArrayList<>();
        List<String> featureQueryLayerUrls = new ArrayList<>();
        for (int i = 0; i < queryLayers.size(); i++) {
            Layer layer = queryLayers.get(i);
            if (layer == null) {
                featureQueryLayerUrls.add(queryLayerInfos.get(i).getUrl());
            } else {
                identifyLayers.add(layer);
            }
        }
        // Identify
        Observable<AMFindResult[]> identify = Observable.just(identifyLayers)
                .map(layers -> {
                    if (layers.isEmpty()) return new AMFindResult[0];
                    IdentifyResult[] identifyResults = IdentifyUtils.selectedFeature(geometry, spatialReference, identifyLayers, tolerance, mapExtent, mapWidth, mapHeight);
                    AMFindResult[] amFindResults = IdentifyUtils.convertIdentifyResultToAMFindResult(queryLayers, identifyResults);
                    return amFindResults;
                })
                .subscribeOn(Schedulers.io())
                .timeout(50000, TimeUnit.MILLISECONDS, Observable.error(new TimeoutException()));

        // FeatureQuery
        Observable<AMFindResult[]> featureQuery;
        if (featureQueryLayerUrls.isEmpty()) {
            featureQuery = Observable.just(new AMFindResult[0]);
        } else {
            Polygon buffer = GeometryEngine.buffer(geometry, spatialReference, 40 * mMapView.getResolution(), null);
            featureQuery = new FeatureQueryService()
                    .queryFeatures(buffer, featureQueryLayerUrls)
                    .map(list -> list.toArray(new AMFindResult[0]));
        }
        return Observable.zip(identify, featureQuery, (amFindResults, amFindResults2) -> {
            AMFindResult[] results = Arrays.copyOf(amFindResults, amFindResults.length + amFindResults2.length);
            System.arraycopy(amFindResults2, 0, results, amFindResults.length, amFindResults2.length);
            return results;
        });
    }

}
