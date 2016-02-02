package com.base.remiany.mybaseapplication.model;

import com.base.remiany.mybaseapplication.model.impl.SelectInterface;

import java.util.List;

/**
 * Created by lxl on 2016/1/21.
 */
public class SelectItem implements SelectInterface {
    String title;
    String key;
    String value;
    int type;
    int baccolor;//存储颜色，不为0时表示背景有特殊颜色
    List<SelectItem> mlist;

    public SelectItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public SelectItem(String title, int type) {
        this(title, title, null, type);
    }

    public SelectItem(String title, String key, int type) {
        this(title, key, null, type);
    }

    public SelectItem(String title, List<SelectItem> mlist, int type) {
        this(title, title, mlist, type);
    }

    public SelectItem(String title, String key, List<SelectItem> mlist, int type) {
        this.title = title;
        this.mlist = mlist;
        this.type = type;
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public List<SelectItem> getList() {
        return mlist;
    }

    @Override
    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<SelectItem> getMlist() {
        return mlist;
    }

    public void setMlist(List<SelectItem> mlist) {
        this.mlist = mlist;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getBaccolor() {
        return baccolor;
    }

    public void setBaccolor(int baccolor) {
        this.baccolor = baccolor;
    }
}
