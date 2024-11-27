package com.example.duan1.MonHoc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.R;

public class UpdateMonHocFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_monhoc, container, false);

        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            int maMonHoc = bundle.getInt("maMonHoc"); // Lấy đúng giá trị maMonHoc
            String tenMonHoc = bundle.getString("tenMonHoc");
            int soTinChi = bundle.getInt("soTinChi");
            String giangVien = bundle.getString("giangVien");

            // Gán dữ liệu vào các trường nhập
            EditText edtMaMon = view.findViewById(R.id.edtMaMon);
            EditText edtTenMon = view.findViewById(R.id.edtTenMon);
            EditText edtSoTinChi = view.findViewById(R.id.edtSoTinChi);
            EditText edtGiangVien = view.findViewById(R.id.edtGiangVien);

            edtMaMon.setText(String.valueOf(maMonHoc));
            edtTenMon.setText(tenMonHoc);
            edtSoTinChi.setText(String.valueOf(soTinChi));
            edtGiangVien.setText(giangVien);

            view.findViewById(R.id.btnUpdateMonHoc).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy thông tin từ các trường nhập
                    String tenMonHocMoi = edtTenMon.getText().toString().trim();
                    String soTinChiMoiStr = edtSoTinChi.getText().toString().trim();
                    String giangVienMoi = edtGiangVien.getText().toString().trim();

                    // Kiểm tra dữ liệu hợp lệ
                    if (tenMonHocMoi.isEmpty() || soTinChiMoiStr.isEmpty() || giangVienMoi.isEmpty()) {
                        Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int soTinChiMoi;
                    try {
                        soTinChiMoi = Integer.parseInt(soTinChiMoiStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Số tín chỉ phải là số nguyên!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Cập nhật thông tin
                    MonHocDAO monHocDAO = new MonHocDAO(getContext());
                    int result = monHocDAO.updateMonHoc(maMonHoc, tenMonHocMoi, soTinChiMoi, giangVienMoi);

                    if (result > 0) {
                        // Nếu result > 0, có nghĩa là đã cập nhật thành công ít nhất 1 dòng
                        Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        // Quay lại màn hình trước
                        requireActivity().onBackPressed();
                    } else {
                        // Nếu result <= 0, có nghĩa là không có dòng nào bị thay đổi
                        Toast.makeText(getContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return view;
    }
}