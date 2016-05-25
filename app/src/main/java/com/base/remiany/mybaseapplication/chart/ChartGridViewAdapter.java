package com.base.remiany.mybaseapplication.chart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.base.remiany.mybaseapplication.utils.Log;
import com.base.remiany.mybaseapplication.view.MBAdapter;
import com.base.remiany.mybaseapplication.view.MBViewHolder;

import java.util.List;

public abstract class ChartGridViewAdapter<T> extends MBAdapter<T> {
    GridView mGridView;
    private int mNumGolums = 4;
    private String[] mColumnTitles;
    private String[] mRowTitles;

    public ChartGridViewAdapter(Context mContext, List mList, int layoutId) {
        super(mContext, mList, layoutId);
    }

    public ChartGridViewAdapter setColumnTitles(String[] titles) {
        mColumnTitles = titles;
        mNumGolums = titles.length;
        return this;
    }

    public ChartGridViewAdapter setRowTitles(String[] titles) {
        mRowTitles = titles;
        return this;
    }

    public ChartGridViewAdapter setGridView(GridView gv) {
        if(mColumnTitles ==null) {
            throw new NullPointerException("Column Names is null");
        }

        if(mRowTitles ==null) {
            throw new NullPointerException("Row Names is null");
        }
        mGridView = gv;
        mGridView.setNumColumns(mNumGolums);
        mGridView.setAdapter(this);
        return this;
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;

        int total = (mRowTitles.length+1) * mNumGolums;
        return total;
    }

    @Override
    public Object getItem(int position) {
        if (mList == null)
            return null;
        if(position < mNumGolums) {//it's title
            return mColumnTitles[position];
        } else if(position % mNumGolums==0) {
            return mRowTitles[position/mNumGolums - 1];
        }
        int p = position - mNumGolums - ((position-1)/mNumGolums) -1;
        return mList.get(p);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MBViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, null);
            holder = new MBViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MBViewHolder) convertView.getTag();
        }


        if(position < mNumGolums) {//it's title
            Log.i("gridview", "position:" + position+" title"+mColumnTitles[position]);
            converTitle(mColumnTitles[position],holder);
        } else if(position % mNumGolums==0) {
            Log.i("gridview", "position:" + position+" title"+mRowTitles[position/mNumGolums - 1]);
            converColumsName(mRowTitles[position/mNumGolums - 1],holder);
        } else {
            int p = position - mNumGolums - ((position - 1) / mNumGolums);
            Log.i("gridview", "position:" + position +" p:"+p+" mlist:" + mList);
            if(p<mList.size()) {
                convert(mList.get(p), position, holder);
            } else {

            }
        }
        return convertView;
    }


    @Override
    protected void convert(T info, int position, MBViewHolder holder) {
        converValue(info,holder);
    }

    public abstract void converTitle(String title, MBViewHolder holder);
    public abstract void converColumsName(String title,MBViewHolder holder);
    public abstract void converValue(T info,MBViewHolder holder);
    public abstract void converNoValue(T info,MBViewHolder holder);
}
