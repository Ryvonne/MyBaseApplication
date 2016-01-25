package com.base.remiany.mybaseapplication.model.impl;

import java.util.List;

public interface SelectInterface<T> {
    String getKey();

    List<T> getList();

    int getType();

}
