package com.example.duan1.BaiTap;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1.DAO.BaiTapDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.BaiTap;
import com.example.duan1.Model.TrangThai;
import com.example.duan1.Adapter.TrangThaiAdapter;
import com.example.duan1.R;

import java.util.ArrayList;


public class XemBaiTapFragment extends Fragment {

    Spinner spinner;
    TextView tvTieuDe, tvMon, tvHanNop, tvNoiDungChiTiet;
    BaiTap baiTap;
    java.util.ArrayList<TrangThai> ArrayList;
    public XemBaiTapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xem_bai_tap, container, false);
        spinner = view.findViewById(R.id.spinnerTrangThai);
        ArrayList = new ArrayList<>();
        ArrayList.add(new TrangThai("Chưa hoàn thành"));
        ArrayList.add(new TrangThai("Đã hoàn thành"));
        TrangThaiAdapter trangThaiAdapter = new TrangThaiAdapter(ArrayList);
        spinner.setAdapter(trangThaiAdapter);
        // Ánh xạ các TextView
        tvTieuDe = view.findViewById(R.id.tvTieuDe);
        tvHanNop = view.findViewById(R.id.tvHanNop);
        tvMon = view.findViewById(R.id.tvMon);
        tvNoiDungChiTiet = view.findViewById(R.id.tvNoiDungChiTiet);

        // Nhận dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maBaiTap = bundle.getInt("maBaiTap");

            // Lấy dữ liệu từ DB
            BaiTapDAO baiTapDAO = new BaiTapDAO(requireContext());
            Cursor cursor = baiTapDAO.getBaiTapById(maBaiTap);

            if (cursor != null && cursor.moveToFirst()) {

                // Đọc dữ liệu từ Cursor
                String tenBaiTap = cursor.getString(cursor.getColumnIndexOrThrow("TenBaiTap"));
                String hanNop = cursor.getString(cursor.getColumnIndexOrThrow("HanNop"));
                int trangThai = cursor.getInt(cursor.getColumnIndexOrThrow("TrangThai"));
                String noiDungBaiTap = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungBaiTap"));
                int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));

                // Hiển thị dữ liệu
                tvTieuDe.setText("Tên bài tập: " + tenBaiTap);
                tvHanNop.setText("Hạn nộp: " + hanNop);
                MonHocDAO monHocDAO = new MonHocDAO(getContext());
                String tenMonHoc = monHocDAO.getTenMonHocByMa(maMonHoc);
                tvMon.setText("Môn học: " + tenMonHoc);
                tvNoiDungChiTiet.setText("Nội dung ghi chú: \n" + noiDungBaiTap);
                baiTap = new BaiTap(maBaiTap,tenBaiTap,hanNop,trangThai,maMonHoc);
            }

            // Đóng Cursor để giải phóng tài nguyên
            if (cursor != null) {
                cursor.close();
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    TrangThai trangThai = ArrayList.get(position);

                    if (baiTap != null) {
                        if(trangThai.getName().equals("Chưa hoàn thành")){
                            baiTap.setTrangThai(0);
                        }else{
                            baiTap.setTrangThai(1);
                        }
                        // Cập nhật Lịch học vào cơ sở dữ liệu
                        int result = baiTapDAO.updateBaiTap(maBaiTap, baiTap.getTrangThai());
                        Log.d("Error", ""+result);
                        if (result > 0) {
                            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        return view;
    }
}