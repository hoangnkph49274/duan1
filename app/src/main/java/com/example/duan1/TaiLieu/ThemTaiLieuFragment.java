package com.example.duan1.TaiLieu;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.MotionEvent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.Adapter.MonHocAdapterSpinner;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.DAO.TaiLieuDAO;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.Model.TaiLieu;
import com.example.duan1.R;

import java.util.ArrayList;

public class ThemTaiLieuFragment extends Fragment {

    private EditText etTenTaiLieu, etDuongDan;
    private RadioGroup rgLoaiTaiLieu;
    private Spinner spinner;
    private Button btnAddTaiLieu;

    private ArrayList<MonHoc> monHocArrayList;
    private MonHocDAO monHocDAO;
    private TaiLieuDAO taiLieuDAO;

    public ThemTaiLieuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_them_tai_lieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ view
        etTenTaiLieu = view.findViewById(R.id.etTenTaiLieu);
        rgLoaiTaiLieu = view.findViewById(R.id.rgLoaiTaiLieu);
        etDuongDan = view.findViewById(R.id.etDuongDan);
        spinner = view.findViewById(R.id.spinnerMonHoc);
        btnAddTaiLieu = view.findViewById(R.id.btnAddTaiLieu);

        // Khởi tạo DAO
        monHocDAO = new MonHocDAO(requireContext());
        taiLieuDAO = new TaiLieuDAO(requireContext());

        // Lấy danh sách môn học
        monHocArrayList = (ArrayList<MonHoc>) monHocDAO.getAllMonHoc();
        MonHocAdapterSpinner monHocAdapter = new MonHocAdapterSpinner(monHocArrayList);
        spinner.setAdapter(monHocAdapter);
        etDuongDan = view.findViewById(R.id.etDuongDan);

        // Xử lý sự kiện thêm tài liệu
        btnAddTaiLieu.setOnClickListener(v -> {
            String tenTaiLieu = etTenTaiLieu.getText().toString().trim();
            String duongDan = etDuongDan.getText().toString().trim();
            MonHoc selectedMonHoc = (MonHoc) spinner.getSelectedItem();

            if (tenTaiLieu.isEmpty() || duongDan.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Lấy loại tài liệu được chọn
            int selectedLoaiId = rgLoaiTaiLieu.getCheckedRadioButtonId();
            String loaiTaiLieu = null;
            if (selectedLoaiId == R.id.rbBaiGiang) {
                loaiTaiLieu = "Bài giảng";
            } else if (selectedLoaiId == R.id.rbThamKhao) {
                loaiTaiLieu = "Tham khảo";
            }

            if (loaiTaiLieu == null) {
                Toast.makeText(requireContext(), "Vui lòng chọn loại tài liệu!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedMonHoc == null) {
                Toast.makeText(requireContext(), "Vui lòng chọn môn học!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo tài liệu mới
            TaiLieu taiLieu = new TaiLieu();
            taiLieu.setTen(tenTaiLieu);
            taiLieu.setLoai(loaiTaiLieu);
            taiLieu.setDdan(duongDan);
            taiLieu.setMon(selectedMonHoc.getMa());

            // Thêm vào cơ sở dữ liệu
            long result = taiLieuDAO.addTaiLieu(tenTaiLieu,loaiTaiLieu,duongDan,selectedMonHoc.getMa());
            if (result > 0) {
                Toast.makeText(requireContext(), "Thêm tài liệu thành công!", Toast.LENGTH_SHORT).show();
                clearForm();
            } else {
                Toast.makeText(requireContext(), "Thêm tài liệu thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        etTenTaiLieu.setText("");
        etDuongDan.setText("");
        rgLoaiTaiLieu.clearCheck();
        spinner.setSelection(0);
    }
}
