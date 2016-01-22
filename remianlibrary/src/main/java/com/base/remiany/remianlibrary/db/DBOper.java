package com.base.remiany.remianlibrary.db;

import android.content.Context;

public class DBOper {
    private DBConnect mDBConnnect;

    public DBOper(Context context) {
        mDBConnnect = DBConnect.getInstance(context);
    }


}
