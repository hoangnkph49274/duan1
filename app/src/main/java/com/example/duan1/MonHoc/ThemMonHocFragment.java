package com.example.duan1.MonHoc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.R;

public class ThemMonHocFragment extends Fragment {

    private EditText edtTenMon, edtSoTinChi, edtGiangVien;
    private NavController navController;

    public ThemMonHocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_them_mon_hoc, container, false);

        // Initialize views
        edtTenMon = view.findViewById(R.id.edtTenMon);
        edtSoTinChi = view.findViewById(R.id.edtSoTinChi);
        edtGiangVien = view.findViewById(R.id.edtGiangVien);
        view.findViewById(R.id.btnAddMonHoc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMonHoc(); // Call method to add the new MonHoc
            }
        });

        return view;
    }

    private void addMonHoc() {
        // Lấy dữ liệu từ các EditText
        String tenMonHoc = edtTenMon.getText().toString().trim();
        String soTinChiStr = edtSoTinChi.getText().toString().trim();
        String giangVien = edtGiangVien.getText().toString().trim();

        // Kiểm tra thông tin nhập liệu
        if (tenMonHoc.isEmpty() || soTinChiStr.isEmpty() || giangVien.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chuyển đổi số tín chỉ
        int soTinChi;
        try {
            soTinChi = Integer.parseInt(soTinChiStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Số tín chỉ phải là số nguyên!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem tên môn học đã tồn tại chưa
        MonHocDAO monHocDAO = new MonHocDAO(getContext());
        if (monHocDAO.isMonHocExists(tenMonHoc)) {
            Toast.makeText(getContext(), "Tên môn học đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Nếu tên môn học chưa tồn tại, thêm mới
        long result = monHocDAO.addMonHoc(tenMonHoc, soTinChi, giangVien);

        if (result > 0) {
            // Thêm thành công
            Toast.makeText(getContext(), "Môn học đã được thêm kèm điểm và học phí!", Toast.LENGTH_SHORT).show();
            navController = Navigation.findNavController(getView());
            navController.popBackStack(); // Quay lại màn hình trước
        } else {
            // Thêm thất bại
            Toast.makeText(getContext(), "Thêm môn học thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

}
