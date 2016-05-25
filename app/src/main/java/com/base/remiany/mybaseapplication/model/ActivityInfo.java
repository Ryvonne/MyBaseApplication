package com.base.remiany.mybaseapplication.model;

public class ActivityInfo {
    int mTitleRid;
    Class mActicity;

    public ActivityInfo(int mTitleRid, Class mActicity) {
        this.mTitleRid = mTitleRid;
        this.mActicity = mActicity;
    }

    public int getSID() {
        return mTitleRid;
    }

    public void setSID(int ID) {
        this.mTitleRid = ID;
    }

    public Class getmActicity() {
        return mActicity;
    }

    public void setmActicity(Class mActicity) {
        this.mActicity = mActicity;
    }
}
