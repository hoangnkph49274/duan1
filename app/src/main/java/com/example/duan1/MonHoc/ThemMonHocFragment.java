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
        // Get the input values
        String tenMonHoc = edtTenMon.getText().toString().trim();
        String soTinChiStr = edtSoTinChi.getText().toString().trim();
        String giangVien = edtGiangVien.getText().toString().trim();

        // Validate the input
        if (tenMonHoc.isEmpty() || soTinChiStr.isEmpty() || giangVien.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the number of credits to integer
        int soTinChi;
        try {
            soTinChi = Integer.parseInt(soTinChiStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Số tín chỉ phải là số nguyên!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert the MonHoc into the database
        MonHocDAO monHocDAO = new MonHocDAO(getContext());
        long result = monHocDAO.addMonHoc(tenMonHoc, soTinChi, giangVien); // Assuming addMonHoc method returns the row ID if successful

        if (result > 0) {
            // Successfully added, navigate back to the list
            Toast.makeText(getContext(), "Môn học đã được thêm!", Toast.LENGTH_SHORT).show();
            navController = Navigation.findNavController(getView());
            navController.popBackStack(); // Go back to the previous fragment (MonHocFragment)
        } else {
            // Failed to add
            Toast.makeText(getContext(), "Thêm môn học thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
