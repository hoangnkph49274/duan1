package com.example.duan1.LichHoc;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duan1.Adapter.TrangThaiAdapter;
import com.example.duan1.DAO.LichHocDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.LichHoc;
import com.example.duan1.Model.TrangThai;
import com.example.duan1.R;

import java.util.ArrayList;

public class UpdateLichHocFragment extends Fragment {

    EditText edtMaLich, edtNgayHoc, edtGioHoc, edtGiangVien, edtMonHoc;
    Spinner spinner;
    ArrayList<TrangThai> trangThaiList;
    LichHocDAO lichHocDAO;
    LichHoc lichHoc;

    public UpdateLichHocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_lich_hoc, container, false);

        // Nhận đối tượng LichHoc từ Bundle
        if (getArguments() != null) {
            lichHoc = (LichHoc) getArguments().getSerializable("lichHoc");
        }
        Log.d("Error", ""+lichHoc);

        // Khởi tạo các trường input (EditText)
        edtMaLich = view.findViewById(R.id.edtMaLichHoc);
        edtNgayHoc = view.findViewById(R.id.edtNgay);
        edtGioHoc = view.findViewById(R.id.edtGio);
        edtGiangVien = view.findViewById(R.id.edtGVien);
        edtMonHoc = view.findViewById(R.id.edtMon);
        spinner = view.findViewById(R.id.spinnerTrangThai);

        // Hiển thị dữ liệu của LichHoc lên các EditText
        if (lichHoc != null) {
            edtMaLich.setText(String.valueOf(lichHoc.getMa()));
            edtNgayHoc.setText(lichHoc.getNgay());
            edtGioHoc.setText(lichHoc.getGio());
            edtGiangVien.setText(lichHoc.getGvien());
            // Lấy tên môn học từ database
            MonHocDAO monHocDAO = new MonHocDAO(getContext());
            String tenMonHoc = monHocDAO.getTenMonHocByMa(lichHoc.getMon());
            edtMonHoc.setText(tenMonHoc);
        }

        // Khởi tạo trang thái cho Spinner
        trangThaiList = new ArrayList<>();
        trangThaiList.add(new TrangThai("Chưa hoàn thành"));
        trangThaiList.add(new TrangThai("Đã hoàn thành"));

        TrangThaiAdapter trangThaiAdapter = new TrangThaiAdapter(trangThaiList);
        spinner.setAdapter(trangThaiAdapter);

        // Khởi tạo DAO
        lichHocDAO = new LichHocDAO(getContext());

        // Sự kiện chọn trạng thái
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TrangThai trangThai = trangThaiList.get(position);
                if (lichHoc != null) {
                    lichHoc.setTrangThai(trangThai.getName());

                    // Kiểm tra trạng thái và thực hiện cập nhật
                    int trangThaiInt;
                    if (lichHoc.getTrangThai().equals("Chưa hoàn thành")) {
                        trangThaiInt = 0;
                    } else {
                        trangThaiInt = 1;
                    }

                    // Cập nhật Lịch học vào cơ sở dữ liệu
                    int result = lichHocDAO.updateLichHoc(lichHoc.getMa(), trangThaiInt);
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
                // Không làm gì khi không có mục nào được chọn
            }
        });

        return view;
    }
}
