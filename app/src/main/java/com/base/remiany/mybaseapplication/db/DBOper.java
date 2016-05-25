package com.base.remiany.mybaseapplication.db;

import android.content.Context;

public class DBOper {
    private DBConnect mDBConnnect;

    public DBOper(Context context) {
        mDBConnnect = DBConnect.getInstance(context);
    }


}
