package com.example.duan1.GhiChu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duan1.DAO.GhiChuDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.GhiChu;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.R;


public class XemGhiChuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xem_ghi_chu, container, false);

        TextView tvTieuDe = view.findViewById(R.id.tvTieuDeGhiChu);
        TextView tvNgayTao = view.findViewById(R.id.tvNgayTao);
        TextView tvNoiDung = view.findViewById(R.id.tvNoiDungChiTiet);
        TextView tvMon = view.findViewById(R.id.tvMon);

        // Lấy mã ghi chú từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maGhiChu = bundle.getInt("maGhiChu");

            // Lấy ghi chú từ DAO
            GhiChuDAO ghiChuDAO = new GhiChuDAO(requireContext());
            GhiChu ghiChu = ghiChuDAO.getGhiChuById(maGhiChu);

            // Hiển thị thông tin ghi chú
            if (ghiChu != null) {
                tvTieuDe.setText(ghiChu.getTen());
                tvNgayTao.setText("Ngày tạo: " + ghiChu.getNgay());
                tvNoiDung.setText("Nội dung ghi chú: \n"+ghiChu.getNoiDung());
                String tenMonHoc = getTenMonHoc(ghiChu.getMon());
                tvMon.setText("Môn: " + tenMonHoc);
            }
        }

        return view;
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