package com.base.remiany.mybaseapplication;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.base.remiany.remianlibrary.adapter.MBAdapter;
import com.base.remiany.remianlibrary.adapter.MBViewHolder;
import com.base.remiany.remianlibrary.assist.AnimationAssist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchHelper implements View.OnClickListener {
    Activity mContext;
    HashMap<String, SelectItem> mMap = new HashMap<String, SelectItem>();
    ListView lstTitle, lstSelect;
    List<SelectItem> mTitles;
    RelativeLayout rlLayout, rlEditView;
    EditText etSelect;
    Button btnCancel, btnConfirm;
    MBAdapter<SelectItem> mSelectAdapter;
    MBAdapter<SelectItem> mTitleAdapter;
    private int mType;

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
                holder.setText(R.id.tv_title, info.getKey());
                SelectItem item = mMap.get(info.getKey());
                if (item == null) {
                    holder.setText(R.id.tv_valuve, "");
                } else {
                    holder.setText(R.id.tv_valuve, item.getTitle());
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
    }

    void initTitles() {
        mTitles = new ArrayList<>();

        createType();
        createAMPM();
        createName();
    }

    void createType() {
        List<SelectItem> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new SelectItem("业务" + i, 1));
        }
        SelectItem item = new SelectItem("业务类型", list, 1);
        mTitles.add(item);
    }

    void createAMPM() {
        List<SelectItem> list = new ArrayList<>();
        list.add(new SelectItem("AM", 1));
        list.add(new SelectItem("PM", 1));
        SelectItem item = new SelectItem("时间", list, 1);
        mTitles.add(item);
    }

    void createName() {
        List<SelectItem> list = new ArrayList<>();
        SelectItem item = new SelectItem("名字", list, 2);
        mTitles.add(item);
    }


    void animationOpen(int type) {
        mType = type;
        switch (type) {
            case 1:
                lstSelect.setVisibility(View.VISIBLE);
                rlEditView.setVisibility(View.GONE);
                break;
            case 2:
                String key = mSelectAdapter.getSelectKey();
                SelectItem item = mMap.get(key);
                if (item != null) {
                    etSelect.setText(item.getKey());
                }
                lstSelect.setVisibility(View.GONE);
                rlEditView.setVisibility(View.VISIBLE);
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
                    case 1:
                        SelectItem item = mSelectAdapter.getSelectItem();
                        if (item != null) {
                            String key = mSelectAdapter.getSelectKey();
                            mMap.put(key, item);
                        }
                        break;
                    case 2:
                        String key = mSelectAdapter.getSelectKey();
                        SelectItem item2 = new SelectItem(etSelect.getText().toString(), 1);
                        mMap.put(key, item2);
                        break;
                }
            case R.id.btn_cancel:
                etSelect.setText("");
                mTitleAdapter.notifyDataSetChanged();
                animationClose();
                break;
        }
    }
}
