package com.example.duan1.MucTieu;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1.DAO.MucTieuDAO;
import com.example.duan1.Model.MucTieu;
import com.example.duan1.NotificationReceiver;
import com.example.duan1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThemMucTieuFragment extends Fragment {

    private EditText edtNgayBatDau, edtNgayKetThuc, edtTrangThai, edtNoiDung;
    private Button btnAddMucTieu;
    private MucTieuDAO mucTieuDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_them_muc_tieu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtNgayBatDau = view.findViewById(R.id.edtNgayBatDau);
        edtNgayKetThuc = view.findViewById(R.id.edtNgayKetThuc);
        edtTrangThai = view.findViewById(R.id.edtTrangThai);
        edtNoiDung = view.findViewById(R.id.edtNoiDung);
        btnAddMucTieu = view.findViewById(R.id.btnAddMucTieu);

        edtNgayBatDau.setFocusable(false);
        edtNgayBatDau.setClickable(true);
        // Thiết lập sự kiện cho EditText chọn ngày
        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        edtNgayKetThuc.setFocusable(false);
        edtNgayKetThuc.setClickable(true);
        // Thiết lập sự kiện cho EditText chọn ngày
        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }
        });
        // Khởi tạo DAO
        mucTieuDAO = new MucTieuDAO(getContext());

        // Sự kiện khi nhấn nút Thêm
        btnAddMucTieu.setOnClickListener(v -> {
            String ngayBatDau = edtNgayBatDau.getText().toString().trim();
            String ngayKetThuc = edtNgayKetThuc.getText().toString().trim();
            String trangThai = edtTrangThai.getText().toString().trim();
            String noiDung = edtNoiDung.getText().toString().trim();

            // Kiểm tra dữ liệu đầu vào
            if (TextUtils.isEmpty(ngayBatDau) ||
                    TextUtils.isEmpty(ngayKetThuc) || TextUtils.isEmpty(trangThai) || TextUtils.isEmpty(noiDung)) {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int TrangThai;
            if(trangThai.equals("Chưa hoàn thành")){
                TrangThai=0;
            }else{
                TrangThai=1;
            }

            // Thêm vào cơ sở dữ liệu
            long result = mucTieuDAO.addMucTieu(noiDung, ngayBatDau, ngayKetThuc, TrangThai);
            if (result > 0) {
                Toast.makeText(getContext(), "Thêm mục tiêu thành công!", Toast.LENGTH_SHORT).show();
                scheduleNotification(ngayKetThuc, noiDung);
                getParentFragmentManager().popBackStack(); // Quay lại màn hình trước
            } else {
                Toast.makeText(getContext(), "Thêm mục tiêu thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
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
                    edtNgayBatDau.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    private void showDatePickerDialog2() {
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
                    edtNgayKetThuc.setText(selectedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
    private void scheduleNotification(String ngayKetThuc, String mucTieuName) {
        try {
            // Chuyển đổi ngày kết thúc thành đối tượng Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(ngayKetThuc);

            // Đặt thời gian cho thông báo
            Calendar calendar = Calendar.getInstance();
            if (date != null) {
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, 9); // Ví dụ: Thông báo vào 9h sáng
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
            }

            // Tạo Intent cho NotificationReceiver
            Intent intent = new Intent(getContext(), NotificationReceiver.class);
            intent.putExtra("MucTieuName", mucTieuName);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    getContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            // Đặt AlarmManager
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                Toast.makeText(getContext(), "Thông báo sẽ hiển thị vào ngày kết thúc!", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ngày kết thúc không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }
}
