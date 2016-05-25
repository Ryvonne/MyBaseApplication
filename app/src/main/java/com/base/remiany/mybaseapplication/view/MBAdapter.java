package com.base.remiany.mybaseapplication.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class MBAdapter<T> extends BaseAdapter {
    private final static String TAG = "MBAdapter";
    String mKey;
    FlagsManger mFlagManger;
    protected Context mContext;
    protected List<T> mList;
    protected int layoutId;

    public List<T> getmList() {
        return mList;
    }

    public void setmList(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public MBAdapter(Context mContext, List<T> mList, int layoutId) {
        this.mContext = mContext;
        this.mList = mList;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList == null)
            return null;
        return mList.get(position);
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
        convert(mList.get(position), position, holder);
        return convertView;
    }

    /**
     * 更新子控件的界面
     *
     * @param holder
     */
    protected abstract void convert(T info, int position, MBViewHolder holder);

    public void initFlagPoolManger(int max) {
        mFlagManger = new FlagsManger(max);
    }

    /**
     * 在点击监听OnItemClickListener()中调用此方法，记录下点击事件
     *
     * @param position
     */
    public void selectByPosition(int position) {
        if (mFlagManger == null) {
            Log.e(TAG, "FlagManger hasn't initialization.");
            return;
        }

        mFlagManger.add(position);
        notifyDataSetChanged();
    }

    public boolean hasSelectByPosition(int position) {
        if (mFlagManger == null) {
            Log.e(TAG, "FlagManger hasn't initialization.");
            return false;
        }
        return mFlagManger.contains(position);
    }

    /**
     * 获得选择的Item
     *
     * @return
     */
    public T getSelectItem() {
        List<Integer> list = mFlagManger.numList;
        if (list == null || list.size() == 0) {
            return null;
        }
        return mList.get(mFlagManger.numList.get(0));
    }

    /**
     * 设置key
     *
     * @param key
     */
    public void setSelectKey(String key) {
        mKey = key;
    }

    public String getSelectKey() {
        return mKey;
    }

    /**
     * 设置已选项
     * 注意！！！！！！！！！！！！！！！！！！！
     * ①FlagManger记得要初始化
     * ②如果不能以==判断是否相等的话，isEquals()方法要重写
     *
     * @param info
     */
    public void setSelectItem(T info) {
        if (info == null) {
            return;
        }
        for (int i = 0; i < mList.size(); i++) {
            if (isEquals(mList.get(i), info)) {
                mFlagManger.add(i);
                return;
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 设置已选项
     * 注意！！！！！！！！！！！！！！！！！！！
     * ①FlagManger记得要初始化
     * ②如果不能以==判断是否相等的话，isEquals()方法要重写
     */
    public void setSelectItems(List<T> infos) {
        if (infos == null) {
            return;
        }

        for (int i = 0; i < mList.size(); i++) {
            for (int j = 0; j < infos.size(); j++) {
                if (isEquals(infos.get(j), mList.get(i))) {
                    mFlagManger.add(i);
                }
            }
        }
        notifyDataSetChanged();
    }

    public boolean isEquals(T info1, T info2) {
        return info1 == info2;
    }

    /**
     * 获得选择的Item列表
     *
     * @return
     */
    public List<T> getSelectItems() {
        List<T> list = new ArrayList<>();
        for (int i : mFlagManger.numList) {
            list.add(mList.get(i));
        }
        return list;
    }

    public void clearFlagManger() {
        mFlagManger.clear();
    }

    class FlagsManger {
        int MAX_NUM = 1;//最多可选数量
        LinkedList<Integer> numList = new LinkedList<>();
        Map<Integer, Boolean> flagMap = new HashMap<>();

        public FlagsManger() {
        }

        public FlagsManger(int MAX_NUM) {
            this.MAX_NUM = MAX_NUM;
        }

        public int getMAX_NUM() {
            return MAX_NUM;
        }

        public void setMAX_NUM(int MAX_NUM) {
            this.MAX_NUM = MAX_NUM;
        }

        boolean contains(int position) {
            return flagMap.containsKey(position);
        }

        /**
         * 增加一个position
         *
         * @param position
         */
        void add(int position) {
            //如果Map内已存在，则移除，相当于点击后再度点击意味着取消
            if (flagMap.containsKey(position)) {
                flagMap.remove(position);
                for (int i = 0; i < numList.size(); i++) {
                    if (numList.get(i) == position) {
                        numList.remove(i);
                        break;
                    }
                }
            } else {
                if (flagMap.size() >= MAX_NUM) {
                    int num = numList.removeFirst();
                    flagMap.remove(num);
                }
                numList.addLast(position);
                flagMap.put(position, true);
            }
        }

        void clear() {
            numList.clear();
            flagMap.clear();
        }
    }
}
