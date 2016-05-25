package com.base.remiany.mybaseapplication.presenter;

import com.base.remiany.mybaseapplication.model.ActivityInfo;
import com.base.remiany.mybaseapplication.model.IDrawerModel;
import com.base.remiany.mybaseapplication.model.IMainModel;
import com.base.remiany.mybaseapplication.model.MainDrawerModel;
import com.base.remiany.mybaseapplication.model.MainModel;
import com.base.remiany.mybaseapplication.activity.IMainView;

import java.util.List;

public class MainPresenter {
    IMainView mIMainView;
    IMainModel mIMainModel;
    IDrawerModel mIDrawerModel;
    public MainPresenter(IMainView view) {
        mIMainView = view;
        mIMainModel = new MainModel();
        mIDrawerModel = new MainDrawerModel();
    }

    public void createDrawer() {
        mIMainView.createDrawer(mIDrawerModel.getActivitys());
    }



}
