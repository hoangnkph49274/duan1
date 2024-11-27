package com.example.duan1.MucTieu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duan1.DAO.MucTieuDAO;
import com.example.duan1.Model.MucTieu;
import com.example.duan1.Model.TrangThai;
import com.example.duan1.Adapter.TrangThaiAdapter;
import com.example.duan1.R;

import java.util.ArrayList;

public class UpdateMucTieuFragment extends Fragment {

    private Spinner spinner;
    private EditText edtMaMucTieu, edtNoiDungMucTieu, edtNgayBatDau, edtNgayKetThuc;
    private Button btnCapNhat;
    private MucTieuDAO mucTieuDAO;
    private ArrayList<TrangThai> trangThaiList;

    public UpdateMucTieuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_muc_tieu, container, false);

        // Ánh xạ view
        spinner = view.findViewById(R.id.spinnerTrangThai);
        edtMaMucTieu = view.findViewById(R.id.edtMaMucTieu);
        edtNoiDungMucTieu = view.findViewById(R.id.edtNoidungMucTieu);
        edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
        btnCapNhat = view.findViewById(R.id.btnUpateMucTieu);

        // Khởi tạo DAO
        mucTieuDAO = new MucTieuDAO(getContext());
        trangThaiList = new ArrayList<>();
        trangThaiList.add(new TrangThai("Chưa hoàn thành"));
        trangThaiList.add(new TrangThai("Đã hoàn thành"));

        TrangThaiAdapter trangThaiAdapter = new TrangThaiAdapter(trangThaiList);
        spinner.setAdapter(trangThaiAdapter);

        // Lấy thông tin mục tiêu hiện tại
        int mucTieuId = getArguments().getInt("MucTieuId", -1);

        if (mucTieuId != -1) {
            // Sử dụng DAO để lấy thông tin mục tiêu
            MucTieuDAO mucTieuDAO = new MucTieuDAO(getContext());
            MucTieu mucTieu = mucTieuDAO.getMucTieuById(mucTieuId);

            if (mucTieu != null) {
                // Hiển thị thông tin mục tiêu trên UI
                edtMaMucTieu.setText(String.valueOf(mucTieu.getMa()));
                edtNoiDungMucTieu.setText(mucTieu.getNoidung());
                Log.d("gggg", ""+mucTieu.getNoidung());
                edtNgayBatDau.setText(mucTieu.getNgay());
                edtNgayKetThuc.setText(mucTieu.getNgaykt());
                String trangThai = mucTieu.getTrangthai();
                if (trangThai.equals("Chưa hoàn thành")) {
                    spinner.setSelection(0);
                } else {
                    spinner.setSelection(1);
                }

            }
        }

        // Cập nhật mục tiêu
        btnCapNhat.setOnClickListener(v -> {
            updateMucTieu();
        });

        return view;
    }



    private void updateMucTieu() {
        // Lấy dữ liệu từ UI
        int maMucTieu = Integer.parseInt(edtMaMucTieu.getText().toString().trim());
        String tenMucTieu = edtNoiDungMucTieu.getText().toString().trim();
        String ngayBatDau = edtNgayBatDau.getText().toString().trim();
        String ngayKetThuc = edtNgayKetThuc.getText().toString().trim();
        String trangThai = ((TrangThai) spinner.getSelectedItem()).getName();

        int trangThaiInt;
        if (trangThai.equals("Chưa hoàn thành")) {
            trangThaiInt = 0;
        } else {
            trangThaiInt = 1;
        }


        // Gọi DAO để cập nhật vào database
        int result = mucTieuDAO.updateMucTieu(maMucTieu, tenMucTieu, ngayBatDau, ngayKetThuc, trangThaiInt);
        if (result>0) {
            // Thông báo thành công
            Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        } else {
            // Thông báo thất bại
            Toast.makeText(getContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
