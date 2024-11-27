package com.example.duan1.LichHoc;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1.DAO.LichHocDAO;
import com.example.duan1.DAO.MonHocDAO;

import com.example.duan1.Model.MonHoc;
import com.example.duan1.Adapter.MonHocAdapterSpinner;
import com.example.duan1.NotificationReceiver;
import com.example.duan1.R;

import java.util.ArrayList;
import java.util.Calendar;

public class ThemLichHocFragment extends Fragment {
    Spinner spinnerMonHoc;
    EditText edtNgay, edtGio, edtGiangVien, edtTrangThai;
    Button btnSave;
    ArrayList<MonHoc> monHocArrayList;
    MonHocDAO monHocDAO;
    LichHocDAO lichHocDAO;

    public ThemLichHocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_lich_hoc, container, false);

        // Khởi tạo DAO
        monHocDAO = new MonHocDAO(requireContext());
        lichHocDAO = new LichHocDAO(requireContext());

        // Khởi tạo các thành phần giao diện
        spinnerMonHoc = view.findViewById(R.id.spinnerMonHoc);

        edtNgay = view.findViewById(R.id.edtNgay);
        edtGio = view.findViewById(R.id.edtGio);
        edtGiangVien = view.findViewById(R.id.edtGiangVien);
        edtTrangThai = view.findViewById(R.id.edtTrangThai);
        btnSave = view.findViewById(R.id.btnAddLichHoc);

        // Lấy danh sách môn học và gán vào Spinner
        monHocArrayList = (ArrayList<MonHoc>) monHocDAO.getAllMonHoc();
        spinnerMonHoc.setAdapter(new MonHocAdapterSpinner(monHocArrayList));
        edtNgay.setFocusable(false);
        edtNgay.setClickable(true);
        // Thiết lập sự kiện cho EditText chọn ngày
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        edtGio.setFocusable(false);
        edtGio.setClickable(true);
        // Thiết lập sự kiện cho EditText chọn giờ
        edtGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // Lắng nghe sự kiện nút Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin nhập vào
                String ngay = edtNgay.getText().toString();
                String gio = edtGio.getText().toString();
                String giangVien = edtGiangVien.getText().toString();
                String trangThai = edtTrangThai.getText().toString();
                MonHoc monHoc = (MonHoc) spinnerMonHoc.getSelectedItem();

                // Kiểm tra dữ liệu nhập vào
                if (ngay.isEmpty() || gio.isEmpty() || giangVien.isEmpty() ) {
                    Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    int TrangThai;
                    if(trangThai.equals("Chưa tham gia")){
                        TrangThai=0;
                    }else{
                        TrangThai=1;
                    }
                    // Lưu lịch học vào cơ sở dữ liệu
                    long result = lichHocDAO.addLichHoc(ngay, gio, giangVien, TrangThai, monHoc.getMa());
                    if (result > 0) {
                        Toast.makeText(getContext(), "Thêm lịch học thành công", Toast.LENGTH_SHORT).show();
                        clearForm();
                    } else {
                        Toast.makeText(getContext(), "Thêm lịch học thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }

    // Hàm để hiển thị DatePickerDialog
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
                    edtNgay.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    // Hàm để hiển thị TimePickerDialog
    private void showTimePickerDialog() {
        // Lấy giờ hiện tại
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Tạo TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                (view, selectedHour, selectedMinute) -> {
                    // Chọn giờ và cập nhật vào EditText
                    String selectedTime = selectedHour + ":" + (selectedMinute < 10 ? "0" + selectedMinute : selectedMinute);
                    edtGio.setText(selectedTime);
                    scheduleNotification(edtNgay.getText().toString(),edtGio.getText().toString());
                },
                hour, minute, true // true = 24-hour format
        );
        timePickerDialog.show();
    }
    private void clearForm() {
        edtNgay.setText("");
        edtGio.setText("");
        edtGiangVien.setText("");
        spinnerMonHoc.setSelection(0);
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
