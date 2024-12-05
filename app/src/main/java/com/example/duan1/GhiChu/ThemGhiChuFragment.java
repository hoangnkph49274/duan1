package com.example.duan1.GhiChu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duan1.Adapter.MonHocAdapterSpinner;
import com.example.duan1.DAO.GhiChuDAO;
import com.example.duan1.DAO.LichHocDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ThemGhiChuFragment extends Fragment {

    private EditText etTieuDe, etNoiDung, etNgayTao;
    private Button btnLuu;
    private GhiChuDAO ghiChuDAO;
    ArrayList<MonHoc> monHocArrayList;
    MonHocDAO monHocDAO;
    Spinner spinnerMonHoc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_them_ghi_chu, container, false);

        // Khởi tạo view
        etTieuDe = view.findViewById(R.id.edtTenGhiChu);
        etNgayTao = view.findViewById(R.id.edtNgayTao);
        etNoiDung = view.findViewById(R.id.edtNoiDungGhiChu);
        btnLuu = view.findViewById(R.id.btnAddGhiChu);

        // Khởi tạo DAO
        ghiChuDAO = new GhiChuDAO(getContext());
        monHocDAO = new MonHocDAO(requireContext());

        // Khởi tạo các thành phần giao diện
        spinnerMonHoc = view.findViewById(R.id.spinnerMonHoc);

        // Lấy danh sách môn học và gán vào Spinner
        monHocArrayList = (ArrayList<MonHoc>) monHocDAO.getAllMonHoc();
        spinnerMonHoc.setAdapter(new MonHocAdapterSpinner(monHocArrayList));
        etNgayTao.setFocusable(false);
        etNgayTao.setClickable(true);
        etNgayTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Xử lý sự kiện nút Lưu
        btnLuu.setOnClickListener(v -> {
            String tieuDe = etTieuDe.getText().toString().trim();
            String ngayTao = etNgayTao.getText().toString().trim();
            String noiDung = etNoiDung.getText().toString().trim();
            MonHoc monHoc = (MonHoc) spinnerMonHoc.getSelectedItem();

            // Kiểm tra dữ liệu nhập
            if (TextUtils.isEmpty(tieuDe) || TextUtils.isEmpty(ngayTao) || TextUtils.isEmpty(noiDung)) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Thêm ghi chú vào cơ sở dữ liệu
            long result = ghiChuDAO.addGhiChu(tieuDe, ngayTao, noiDung, monHoc.getMa()); // Thay "1" bằng mã môn học thực tế nếu có

            if (result > 0) {
                Toast.makeText(getContext(), "Thêm ghi chú thành công", Toast.LENGTH_SHORT).show();
                // Reset các trường nhập
                etTieuDe.setText("");
                etNoiDung.setText("");
                etNgayTao.setText("");
            } else {
                Toast.makeText(getContext(), "Thêm ghi chú thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Chọn ngày và cập nhật vào EditText
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etNgayTao.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
