package com.base.remiany.mybaseapplication;

import java.util.List;

public interface SelectInterface<T> {
    String getKey();

    List<T> getList();

    int getType();

}
