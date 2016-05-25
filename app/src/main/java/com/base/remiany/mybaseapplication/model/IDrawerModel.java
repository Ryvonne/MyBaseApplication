package com.base.remiany.mybaseapplication.model;

import java.util.List;

public interface IDrawerModel {
    List<ActivityInfo> getActivitys();
    List<ActivityInfo> getActivitys(Class current);
}
