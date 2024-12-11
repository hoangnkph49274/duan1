package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {
    private DbHelper dbHelper;
    private int tien = 2000000;

    public MonHocDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm môn học
    public long addMonHoc(String tenMonHoc, int soTinChi, String giangVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenMonHoc", tenMonHoc);
        values.put("SoTinChi", soTinChi);
        values.put("GiangVien", giangVien);

        // Thêm môn học và lấy ID
        long maMonHoc = db.insert("MonHoc", null, values);
        if (maMonHoc == -1) {
            return -1; // Thêm thất bại
        }

        // Thêm dữ liệu mặc định vào bảng BangDiem
        ContentValues diemValues = new ContentValues();
        diemValues.put("MaMonHoc", maMonHoc);
        diemValues.put("DiemGiuaKy", 0.0); // Điểm mặc định
        diemValues.put("DiemCuoiKy", 0.0); // Điểm mặc định
        diemValues.put("DiemKhac", 0.0);   // Điểm mặc định
        diemValues.put("DiemTongKet", 0.0); // Điểm mặc định
        diemValues.put("TrangThai", "Chưa đạt");  // Trạng thái mặc định
        db.insert("BangDiem", null, diemValues);

        // Thêm dữ liệu mặc định vào bảng HocPhi
        ContentValues hocPhiValues = new ContentValues();
        hocPhiValues.put("MaMonHoc", maMonHoc);
        hocPhiValues.put("SoTien", (tien*soTinChi));      // Học phí mặc định
        hocPhiValues.put("SoLanDaHoc", 1); // Số lần học mặc định
        hocPhiValues.put("SoTienDaDong",((tien*soTinChi)*1)); // Số tiền đã đóng mặc định
        db.insert("HocPhi", null, hocPhiValues);

        return maMonHoc; // Trả về ID của môn học vừa thêm
    }


    // Lấy danh sách môn học
    public List<MonHoc> getAllMonHoc() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<MonHoc> monHocList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM MonHoc", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));
                    String tenMonHoc = cursor.getString(cursor.getColumnIndexOrThrow("TenMonHoc"));
                    int soTinChi = cursor.getInt(cursor.getColumnIndexOrThrow("SoTinChi"));
                    String giangVien = cursor.getString(cursor.getColumnIndexOrThrow("GiangVien"));

                    MonHoc monHoc = new MonHoc(maMonHoc,tenMonHoc, soTinChi, giangVien);
                    monHocList.add(monHoc);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        return monHocList;
    }
    public boolean isMonHocExists(String tenMonHoc) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM MonHoc WHERE TenMonHoc = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tenMonHoc});

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            db.close();
            return count > 0;
        }

        return false;
    }



    // Cập nhật môn học
    public int updateMonHoc(int maMonHoc, String tenMonHoc, int soTinChi, String giangVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenMonHoc", tenMonHoc);
        values.put("SoTinChi", soTinChi);
        values.put("GiangVien", giangVien);
        return db.update("MonHoc", values, "MaMonHoc = ?", new String[]{String.valueOf(maMonHoc)});
    }

    // Xóa môn học
    public int deleteMonHoc(int maMonHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Kiểm tra xem cột "MaMonHoc" có khớp với database không
        return db.delete("MonHoc", "MaMonHoc = ?", new String[]{String.valueOf(maMonHoc)});
    }

    public String getTenMonHocByMa(int maMonHoc) {
        String tenMonHoc = "";
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenMonHoc FROM MonHoc WHERE maMonHoc = ?", new String[]{String.valueOf(maMonHoc)});
        if (cursor.moveToFirst()) {
            tenMonHoc = cursor.getString(0);
        }
        cursor.close();
        return tenMonHoc;
    }
    public List<MonHoc> searchMonHoc(String keyword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<MonHoc> monHocList = new ArrayList<>();

        // Truy vấn SQL kết hợp tìm kiếm theo tên môn học hoặc giảng viên
        Cursor cursor = db.rawQuery(
                "SELECT * FROM MonHoc WHERE TenMonHoc LIKE ? OR GiangVien LIKE ? OR MaMonHoc LIKE ? OR SoTinChi LIKE ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));
                    String tenMonHoc = cursor.getString(cursor.getColumnIndexOrThrow("TenMonHoc"));
                    int soTinChi = cursor.getInt(cursor.getColumnIndexOrThrow("SoTinChi"));
                    String giangVien = cursor.getString(cursor.getColumnIndexOrThrow("GiangVien"));

                    // Tạo đối tượng MonHoc
                    MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, soTinChi, giangVien);
                    monHocList.add(monHoc);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }

        return monHocList;
    }


}
