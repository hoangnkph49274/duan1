package com.example.duan1.MucTieu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.duan1.Adapter.TrangThai;
import com.example.duan1.Adapter.TrangThaiAdapter;
import com.example.duan1.R;

import java.util.ArrayList;


public class UpdateMucTieuFragment extends Fragment {

    Spinner spinner;
    java.util.ArrayList<TrangThai> ArrayList;
    public UpdateMucTieuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_muc_tieu, container, false);
        spinner = view.findViewById(R.id.spinnerTrangThai);
        ArrayList = new ArrayList<>();
        ArrayList.add(new TrangThai("Chưa hoàn thành"));
        ArrayList.add(new TrangThai("Đã hoàn thành"));
        TrangThaiAdapter trangThaiAdapter = new TrangThaiAdapter(ArrayList);
        spinner.setAdapter( trangThaiAdapter);
        return view;
    }
}