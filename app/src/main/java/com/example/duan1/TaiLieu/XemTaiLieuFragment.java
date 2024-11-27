package com.example.duan1.TaiLieu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.R;

public class XemTaiLieuFragment extends Fragment {

    private TextView  tvXemTenTaiLieu, tvXemLoaiTaiLieu, tvXemDuongDan, tvXemMaMonHoc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_xem_tai_lieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ các TextView

        tvXemTenTaiLieu = view.findViewById(R.id.tvTieuDe);
        tvXemLoaiTaiLieu = view.findViewById(R.id.tvLoai);
        tvXemDuongDan = view.findViewById(R.id.tvDuongDan);
        tvXemMaMonHoc = view.findViewById(R.id.tvMon);

        // Nhận dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maTaiLieu = bundle.getInt("maTaiLieu", -1);
            String tenTaiLieu = bundle.getString("tenTaiLieu", "N/A");
            String loaiTaiLieu = bundle.getString("loaiTaiLieu", "N/A");
            String duongDan = bundle.getString("duongDan", "N/A");
            int maMonHoc = bundle.getInt("maMonHoc", -1);
            Log.d("XemTaiLieuFragment", "tvXemTenTaiLieu: " + tvXemTenTaiLieu);
            Log.d("XemTaiLieuFragment", "tvXemLoaiTaiLieu: " + tvXemLoaiTaiLieu);
            Log.d("XemTaiLieuFragment", "tvXemDuongDan: " + tvXemDuongDan);


            String tenMonHoc = getTenMonHoc(maMonHoc);

            tvXemTenTaiLieu.setText("Tên tài liệu: " + tenTaiLieu);
            tvXemLoaiTaiLieu.setText("Loại tài liệu: " + loaiTaiLieu);
            tvXemDuongDan.setText("Đường dẫn: " + duongDan);
            tvXemMaMonHoc.setText("Môn: " + tenMonHoc);
        }
    }
    private String getTenMonHoc(int maMonHoc) {
        if (maMonHoc == -1) {
            return "N/A";
        }
        MonHocDAO monHocDAO = new MonHocDAO(requireContext());
        String tenMonHoc = monHocDAO.getTenMonHocByMa(maMonHoc);
        return tenMonHoc != null ? tenMonHoc : "Không xác định";
    }
}
