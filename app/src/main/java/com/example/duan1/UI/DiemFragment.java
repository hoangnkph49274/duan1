package com.example.duan1.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1.Adapter.DiemAdapter;
import com.example.duan1.Adapter.TrangThaiAdapter;
import com.example.duan1.DAO.DiemDAO;
import com.example.duan1.Model.BangDiem;
import com.example.duan1.Model.TrangThai;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.List;

public class DiemFragment extends Fragment {

    private RecyclerView recyclerViewDiem;
    private DiemAdapter diemAdapter;
    private DiemDAO diemDAO;
    private Spinner spinnerTrangThai;
    private ArrayList<TrangThai> trangThaiList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_diem, container, false);

        // Initialize views
        recyclerViewDiem = rootView.findViewById(R.id.recyclerViewDiem);
        spinnerTrangThai = rootView.findViewById(R.id.spnTTHoc);

        // Initialize DAO
        diemDAO = new DiemDAO(getContext());
        trangThaiList = new ArrayList<>();
        trangThaiList.add(new TrangThai("Tất cả"));
        trangThaiList.add(new TrangThai("Chưa đạt"));
        trangThaiList.add(new TrangThai("Đạt"));

        TrangThaiAdapter trangThaiAdapter = new TrangThaiAdapter(trangThaiList);
        spinnerTrangThai.setAdapter(trangThaiAdapter);

        // Get list of Diem from DAO
        List<BangDiem> diemList = diemDAO.getAllDiem();

        // Set up RecyclerView
        diemAdapter = new DiemAdapter(getContext(), diemList);
        recyclerViewDiem.setAdapter(diemAdapter);
        recyclerViewDiem.setLayoutManager(new LinearLayoutManager(getContext()));

        spinnerTrangThai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTrangThai = trangThaiList.get(position).getName();
                List<BangDiem> diemList = new ArrayList<>();
                if (selectedTrangThai.equals("Tất cả")) {
                    diemList = diemDAO.getAllDiem();
                } else {
                    diemList = diemDAO.getDiemByTrangThai(selectedTrangThai);
                }

                // Cập nhật RecyclerView với danh sách điểm đã lọc
                diemAdapter.updateDiemList(diemList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }
}
