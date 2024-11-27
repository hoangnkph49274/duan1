package com.example.duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duan1.Model.MonHoc;
import com.example.duan1.R;

import java.util.ArrayList;

public class MonHocAdapterSpinner extends BaseAdapter {

    ArrayList<MonHoc> monHocArrayList;

    public MonHocAdapterSpinner(ArrayList<MonHoc> monHocArrayList) {
        this.monHocArrayList = monHocArrayList;
    }

    @Override
    public int getCount() {
        return monHocArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return monHocArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spn_monhoc, parent, false);
        TextView txtMonHoc = v.findViewById(R.id.tvName);
        MonHoc monHoc = monHocArrayList.get(position);
        txtMonHoc.setText(monHoc.getTen());

        return v;
    }
}
