package com.base.remiany.mybaseapplication.presenter;

import com.base.remiany.mybaseapplication.model.IMainModel;
import com.base.remiany.mybaseapplication.model.MainModel;
import com.base.remiany.mybaseapplication.view.IMainView;

public class MainPresenter {
    IMainView mIMainView;
    IMainModel mIMainModel;
    public MainPresenter(IMainView view) {
        mIMainView = view;
        mIMainModel = new MainModel();
    }

    public void add() {
        mIMainView.add(mIMainModel.getMsg());
    }
    
}
