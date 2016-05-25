package com.base.remiany.mybaseapplication.model;

import com.base.remiany.mybaseapplication.R;
import com.base.remiany.mybaseapplication.activity.ChartActivity;
import com.base.remiany.mybaseapplication.activity.ImageActivity;

import java.util.ArrayList;
import java.util.List;

public class MainDrawerModel implements IDrawerModel {

    @Override
    public List<ActivityInfo> getActivitys() {
        List<ActivityInfo> list = new ArrayList<>();
        ActivityInfo imageActInfo = new ActivityInfo(R.string.title_activity_image, ImageActivity.class);
        list.add(imageActInfo);

        ActivityInfo chartActInfo = new ActivityInfo(R.string.title_activity_chart, ChartActivity.class);
        list.add(chartActInfo);
        return list;
    }

    @Override
    public List<ActivityInfo> getActivitys(Class current) {
        return null;
    }
}
