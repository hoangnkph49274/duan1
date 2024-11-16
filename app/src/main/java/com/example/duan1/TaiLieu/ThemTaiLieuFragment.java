package com.example.duan1.TaiLieu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.duan1.Adapter.MonHoc;
import com.example.duan1.Adapter.MonHocAdapterSpinner;
import com.example.duan1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemTaiLieuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemTaiLieuFragment extends Fragment {

    Spinner spinner;
    ArrayList<MonHoc> monHocArrayList;



    public ThemTaiLieuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_tai_lieu, container, false);
        spinner = view.findViewById(R.id.spinnerMonHoc);
        monHocArrayList = new ArrayList<>();
        monHocArrayList.add(new MonHoc("Lập Trình C"));
        monHocArrayList.add(new MonHoc("Lập Trình C++"));
        monHocArrayList.add(new MonHoc("Lập Trình Java"));
        monHocArrayList.add(new MonHoc("Lập Trình Python"));
        MonHocAdapterSpinner monHocAdapter = new MonHocAdapterSpinner(monHocArrayList);
        spinner.setAdapter( monHocAdapter);
        return view;
    }
}