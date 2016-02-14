package com.base.remiany.mybaseapplication.helper;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.base.remiany.mybaseapplication.R;
import com.base.remiany.mybaseapplication.model.SelectItem;
import com.base.remiany.remianlibrary.view.adapter.MBAdapter;
import com.base.remiany.remianlibrary.view.adapter.MBViewHolder;
import com.base.remiany.remianlibrary.app.assist.AnimationAssist;
import com.base.remiany.remianlibrary.utils.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchHelper implements View.OnClickListener {
    Activity mContext;
    HashMap<String, SelectItem> mMap = new HashMap<String, SelectItem>();
    List<String> mDateList = new ArrayList<>();
    ListView lstTitle, lstSelect;
    List<SelectItem> mTitles = new ArrayList<>();
    RelativeLayout rlLayout, rlEditView, rlDate;
    EditText etSelect;
    Button btnCancel, btnConfirm;
    MBAdapter<SelectItem> mSelectAdapter;
    MBAdapter<SelectItem> mTitleAdapter;
    private int mType;

    DatePicker mDatePicker;
    TimePicker mTimePicker;

    public final static int TEXT_TYPE = 0;
    public final static int SPINNER_TYPE = 1;
    public final static int EDIT_TYPE = 2;
    public final static int DATE_TYPE = 3;
    public final static int TIME_TYPE = 4;

    private final String DATE_START_TAG = "_start";
    private final String DATE_END_TAG = "_end";
    private final String DATE_TITLE_TAG = "_title";

    public SearchHelper(Activity context) {
        mContext = context;
        initTitles();
        lstTitle = (ListView) context.findViewById(R.id.list_title);
        lstSelect = (ListView) context.findViewById(R.id.list_select);
        rlLayout = (RelativeLayout) context.findViewById(R.id.rl_view);
        btnCancel = (Button) context.findViewById(R.id.btn_cancel);
        btnConfirm = (Button) context.findViewById(R.id.btn_confirm);
        etSelect = (EditText) context.findViewById(R.id.et_select);
        rlEditView = (RelativeLayout) context.findViewById(R.id.rl_et);
        rlDate = (RelativeLayout) context.findViewById(R.id.rl_date);
        mDatePicker = (DatePicker) context.findViewById(R.id.datePicker);
        mTimePicker = (TimePicker) context.findViewById(R.id.timePicker);
        Button btnSearch = (Button) context.findViewById(R.id.btn_search);

        //设置为24小时制，否则会显示AM/PM的选择
        mTimePicker.setIs24HourView(true);

        rlEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);

        mTitleAdapter = new MBAdapter<SelectItem>(mContext, mTitles, R.layout.item_main) {
            @Override
            protected void convert(SelectItem info, int position, MBViewHolder holder) {
                holder.setText(R.id.tv_title, info.getTitle());
                SelectItem item = mMap.get(info.getKey());
                if (item == null) {
                    holder.setText(R.id.tv_valuve, "");
                } else {
                    holder.setText(R.id.tv_valuve, item.getTitle());
                }

                int bacolor = info.getBaccolor();
                if (bacolor == 1) {
                    holder.setBackgroundResource(R.id.rl_main, R.color.gray_deep_backgroud);
                } else if (bacolor == 2) {
                    holder.setBackgroundResource(R.id.rl_main, R.color.gray_backgroud);
                } else {
                    holder.setBackgroundResource(R.id.rl_main, R.color.gray_light_backgroud);
                }
            }
        };

        mSelectAdapter = new MBAdapter<SelectItem>(mContext, mTitles, R.layout.item_value) {
            @Override
            protected void convert(SelectItem info, int position, MBViewHolder holder) {
                holder.setText(R.id.tv, info.getTitle());
                holder.setImageResource(R.id.image, R.mipmap.ic_launcher);
                if (this.hasSelectByPosition(position)) {
                    holder.setVisibility(R.id.image, View.VISIBLE);
                } else {
                    holder.setVisibility(R.id.image, View.GONE);
                }
            }

            @Override
            public boolean isEquals(SelectItem info1, SelectItem info2) {
                boolean is = info1.getKey().equals(info2.getKey());
                return is;
            }
        };

        lstTitle.setAdapter(mTitleAdapter);
        lstTitle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                SelectItem item = mTitles.get(position);
                String key = item.getKey();
                mSelectAdapter.clearFlagManger();
                mSelectAdapter.setSelectKey(key);
                mSelectAdapter.setmList(mTitles.get(position).getList());
                SelectItem val = mMap.get(key);
                mSelectAdapter.setSelectItem(val);
                animationOpen(item.getType());
            }
        });

        lstSelect.setAdapter(mSelectAdapter);
        mSelectAdapter.initFlagPoolManger(1);
        lstSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectAdapter.selectByPosition(position);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmMap();
            }
        });
    }

    public Map<String, String> confirmMap() {
        HashMap<String, String> map = new HashMap<>();
        for (String key : mMap.keySet()) {
            String val = mMap.get(key).getKey();
            map.put(key, val);
        }

        for (String date : mDateList) {
            String key = date;

            if (mMap.get(date + DATE_TITLE_TAG) == null) {
                Log.i("Map hasn't " + date + ",don't search this param");
                continue;
            }
            key = mMap.get(date + DATE_TITLE_TAG).getKey();

            String start = map.get(date + DATE_START_TAG) == null ? null : mMap.get(date + DATE_START_TAG).getKey();
            String end = map.get(date + DATE_END_TAG) == null ? null : mMap.get(date + DATE_END_TAG).getKey();
            String searchdate = "";
            if (!TextUtils.isEmpty(start)) {
                searchdate += start;
                if (!TextUtils.isEmpty(end)) {
                    searchdate += "|" + end;
                }

                map.remove(date + DATE_TITLE_TAG);
                map.remove(date + DATE_START_TAG);
                map.remove(date + DATE_END_TAG);
                map.put(key, searchdate);
                continue;
            }

            if (!TextUtils.isEmpty(end)) {
                searchdate = end;
                map.put(date, searchdate);
            }

            map.remove(date + DATE_TITLE_TAG);
            map.remove(date + DATE_START_TAG);
            map.remove(date + DATE_END_TAG);
        }

        return map;
    }

    void initTitles() {
        createName();
        createDate("日期", "test");
        createTime();
        createType();
    }

    void createType() {
        List<SelectItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new SelectItem("业务" + i, 1));
        }
        SelectItem item = new SelectItem("业务类型", "type", list, SPINNER_TYPE);
        mTitles.add(item);
    }

    void createDate(String title, String key) {
        mDateList.add(key);
        List<SelectItem> titleList = new ArrayList<>();
//        titleList.add(new SelectItem("预约时间", "fum_date", 1));
//        titleList.add(new SelectItem("执行时间", "book_date", 1));

        SelectItem item = null;
        if (titleList.size() == 0) {
            item = new SelectItem(title, key + DATE_TITLE_TAG, titleList, TEXT_TYPE);
        } else {
            item = new SelectItem(title, key + DATE_TITLE_TAG, titleList, SPINNER_TYPE);
            mMap.put(key + DATE_TITLE_TAG, new SelectItem("预约时间", "fum_date", 1));
        }
        item.setBaccolor(1);
        mTitles.add(item);
        SelectItem startitem = new SelectItem("开始日期", key + DATE_START_TAG, new ArrayList<SelectItem>(), DATE_TYPE);
        startitem.setBaccolor(2);
        SelectItem enditem = new SelectItem("结束日期", key + DATE_END_TAG, new ArrayList<SelectItem>(), DATE_TYPE);
        enditem.setBaccolor(2);
        mTitles.add(startitem);
        mTitles.add(enditem);
    }

    void createTime() {
        List<SelectItem> list = new ArrayList<>();
        list.add(new SelectItem("AM", "AM", 1));
        list.add(new SelectItem("PM", "PM", 1));
        SelectItem item = new SelectItem("时间", "AM OR PM", list, TIME_TYPE);
        mTitles.add(item);
    }

    void createName() {
        List<SelectItem> list = new ArrayList<>();
        SelectItem item = new SelectItem("名字", "NAME", list, TEXT_TYPE);
        mTitles.add(item);
    }


    void animationOpen(int type) {
        mType = type;
        switch (type) {
            case TEXT_TYPE:
                return;
            case SPINNER_TYPE:
                lstSelect.setVisibility(View.VISIBLE);
                rlEditView.setVisibility(View.GONE);
                rlDate.setVisibility(View.GONE);
                break;
            case EDIT_TYPE:
                String key = mSelectAdapter.getSelectKey();
                SelectItem item = mMap.get(key);
                if (item != null) {
                    etSelect.setText(item.getKey());
                }
                lstSelect.setVisibility(View.GONE);
                rlEditView.setVisibility(View.VISIBLE);
                rlDate.setVisibility(View.GONE);
                break;
            case TIME_TYPE:
                String timekey = mSelectAdapter.getSelectKey();
                setTimeValue(mMap.get(timekey) == null ? null : mMap.get(timekey).getKey());
                mTimePicker.setVisibility(View.VISIBLE);
            case DATE_TYPE:
                String datekey = mSelectAdapter.getSelectKey();
                setDateValue(mMap.get(datekey) == null ? null : mMap.get(datekey).getKey());
                lstSelect.setVisibility(View.GONE);
                rlEditView.setVisibility(View.GONE);
                rlDate.setVisibility(View.VISIBLE);
                break;
        }
        rlLayout.setVisibility(View.VISIBLE);
        AnimationAssist.slideview(rlLayout, 1, 0);
    }

    void animationClose() {
        AnimationAssist.slideview(rlLayout, 0, 1);
        rlLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                switch (mType) {
                    case SPINNER_TYPE:
                        SelectItem item = mSelectAdapter.getSelectItem();
                        if (item != null) {
                            String key = mSelectAdapter.getSelectKey();
                            mMap.put(key, item);
                        }
                        break;
                    case EDIT_TYPE:
                        String key = mSelectAdapter.getSelectKey();
                        SelectItem item2 = new SelectItem(etSelect.getText().toString(), 1);
                        mMap.put(key, item2);
                        break;
                    case DATE_TYPE:
                        String datekey = mSelectAdapter.getSelectKey();
                        String date = getdata(mDatePicker);
                        SelectItem item3 = new SelectItem(date, 1);
                        mMap.put(datekey, item3);
                        break;
                    case TIME_TYPE:
                        String datekey2 = mSelectAdapter.getSelectKey();
                        String date2 = getdata(mDatePicker) + " " + getTime(mTimePicker);
                        SelectItem item4 = new SelectItem(date2, 1);
                        mMap.put(datekey2, item4);
                        break;
                }
            case R.id.btn_cancel:
                etSelect.setText("");
                mTitleAdapter.notifyDataSetChanged();
                animationClose();
                break;
        }
    }

    private String getdata(DatePicker date) {
        String sdate = "";
        sdate = date.getYear() + "-";
        if (date.getMonth() + 1 < 10) {
            sdate += "0" + (date.getMonth() + 1) + "-";
        } else {
            sdate += (date.getMonth() + 1) + "-";
        }
        if (date.getDayOfMonth() < 10) {
            sdate += "0" + (date.getDayOfMonth());
        } else {
            sdate += date.getDayOfMonth();
        }
        return sdate;
    }

    private String getTime(TimePicker date) {
        String sdate = "";

        int hour = date.getCurrentHour();
        if (hour < 10) {
            sdate += "0" + hour + ":";
        } else {
            sdate += hour + ":";
        }

        int minute = date.getCurrentMinute();
        if (minute < 10) {
            sdate += "0" + minute + ":";
        } else {
            sdate += minute + ":";
        }
        sdate += "01";
        return sdate;
    }

    void setDateValue(String date) {
        if (TextUtils.isEmpty(date) || "null".equals(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.format(new Date());
        }
        String[] date2s = date.split(" ");
        String[] dates = date2s[0].split("-");
        if (3 == dates.length) {
            mDatePicker.updateDate(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]) - 1, Integer.parseInt(dates[2]));
        }


    }

    void setTimeValue(String date) {
        if (TextUtils.isEmpty(date) || "null".equals(date)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = format.format(new Date());
        }
        String[] date2s = date.split(" ");
        String[] times = date2s[1].split(":");

        mTimePicker.setCurrentHour(Integer.valueOf(times[0]));
        mTimePicker.setCurrentMinute(Integer.valueOf(times[1]));
    }

}
