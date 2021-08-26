package com.augurit.common.statistics.model;

import java.util.List;

/**
 * Created by taoerxiang on 2017/11/20.
 */
public class EchartsPieBean {

    public String type;
    public String title;
    public List<ValueData> values;

    public static class ValueData {
        public float value;
    }

}
