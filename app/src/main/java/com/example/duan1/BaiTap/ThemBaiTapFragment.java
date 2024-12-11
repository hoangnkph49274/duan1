package com.example.duan1.BaiTap;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.duan1.Adapter.MonHocAdapterSpinner;
import com.example.duan1.DAO.BaiTapDAO;
import com.example.duan1.DAO.MonHocDAO;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.NotificationReceiver;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ThemBaiTapFragment extends Fragment {

    private EditText edtTenBaiTap, edtHanNop, edtTrangThai, edtNoiDungBaiTap;
    private Spinner spinnerMonHoc;
    private Button btnThemBaiTap;
    private BaiTapDAO baiTapDAO;
    private ArrayList<MonHoc> monHocArrayList;
    MonHocDAO monHocDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_bai_tap, container, false);

        // Ánh xạ các view
        edtTenBaiTap = view.findViewById(R.id.edtTenBaiTap);
        edtHanNop = view.findViewById(R.id.edtHanNop);
        edtTrangThai = view.findViewById(R.id.edtTrangThai);
        edtNoiDungBaiTap = view.findViewById(R.id.edtNoiDungBaiTap);
        spinnerMonHoc = view.findViewById(R.id.spinnerMonHoc);
        btnThemBaiTap = view.findViewById(R.id.btnAddBaiTap);
        monHocDAO = new MonHocDAO(requireContext());

        // Khởi tạo DAO
        monHocDAO = new MonHocDAO(getContext());

        // Khởi tạo DAO
        baiTapDAO = new BaiTapDAO(getContext());

        edtHanNop.setFocusable(false);
        edtHanNop.setClickable(true);
        // Thiết lập sự kiện cho EditText chọn ngày
        edtHanNop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Lấy danh sách môn học và gán vào Spinner
        monHocArrayList = (ArrayList<MonHoc>) monHocDAO.getAllMonHoc();
        spinnerMonHoc.setAdapter(new MonHocAdapterSpinner(monHocArrayList));
        // Xử lý sự kiện click nút thêm bài tập
        btnThemBaiTap.setOnClickListener(v -> {
            String tenBaiTap = edtTenBaiTap.getText().toString().trim();
            String hanNop = edtHanNop.getText().toString().trim();
            String trangThai = edtTrangThai.getText().toString().trim();
            String noiDungBaiTap = edtNoiDungBaiTap.getText().toString().trim();
            MonHoc monHoc = (MonHoc) spinnerMonHoc.getSelectedItem();

            if (tenBaiTap.isEmpty() || hanNop.isEmpty() || trangThai.isEmpty() ||
                    noiDungBaiTap.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int TrangThai;
                if(trangThai.equals("Chưa hoàn thành")){
                    TrangThai=0;
                }else{
                    TrangThai=1;
                }

                // Thêm bài tập vào database
                long result = baiTapDAO.addBaiTap(tenBaiTap, hanNop, TrangThai, noiDungBaiTap, monHoc.getMa());
                if (result > 0) {
                    Toast.makeText(getContext(), "Thêm bài tập thành công!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(getContext(), "Thêm bài tập thất bại!", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Mã môn học phải là số!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Xóa dữ liệu trong các trường nhập
    private void clearFields() {
        edtTenBaiTap.setText("");
        edtHanNop.setText("");
        edtTrangThai.setText("");
        edtNoiDungBaiTap.setText("");
        spinnerMonHoc.setSelection(0);
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
                    edtHanNop.setText(selectedDate);

                    scheduleNotification(edtHanNop.getText().toString(),"8:04");
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    private void scheduleNotification(String ngay, String gio) {

        // Phân tích ngày và giờ từ chuỗi
        String[] dateParts = ngay.split("/");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1; // Tháng trong Calendar bắt đầu từ 0
        int year = Integer.parseInt(dateParts[2]);

        String[] timeParts = gio.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Thiết lập thời gian để thông báo
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 0);

        // Tạo `PendingIntent` với `FLAG_IMMUTABLE`
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(),
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE // Chỉ định FLAG_IMMUTABLE
        );

        // Cài đặt AlarmManager để gửi thông báo
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        }
    }
}