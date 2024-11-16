package com.example.duan1.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duan1.Adapter.TrangThai;
import com.example.duan1.R;

import java.util.ArrayList;

public class TrangThaiAdapter extends BaseAdapter {

    ArrayList<TrangThai> arrayList;

    public TrangThaiAdapter(ArrayList<TrangThai> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spn_monhoc, parent, false);
        TextView txtMonHoc = v.findViewById(R.id.tvName);
        TrangThai trangThai = arrayList.get(position);
        txtMonHoc.setText(trangThai.getName());

        return v;
    }
}
