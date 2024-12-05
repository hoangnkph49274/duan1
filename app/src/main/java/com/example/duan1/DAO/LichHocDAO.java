package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.LichHoc;
import com.example.duan1.Model.MonHoc;
import com.example.duan1.Model.TaiLieu;

import java.util.ArrayList;

public class LichHocDAO {
    private DbHelper dbHelper;

    public LichHocDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm lịch học
    public long addLichHoc(String ngayHoc, String gioHoc, String giangVien, int trangThai, int maMonHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NgayHoc", ngayHoc);
        values.put("GioHoc", gioHoc);
        values.put("GiangVien", giangVien);
        values.put("TrangThai", trangThai);
        values.put("MaMonHoc", maMonHoc);
        return db.insert("LichHoc", null, values);
    }

    // Lấy danh sách lịch học
    public ArrayList<LichHoc> getAllLichHoc() {
        ArrayList<LichHoc> lichHocList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LichHoc", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    // Truy xuất dữ liệu từ các cột trong cơ sở dữ liệu
                    int maLichHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaLichHoc"));
                    String ngayHoc = cursor.getString(cursor.getColumnIndexOrThrow("NgayHoc"));
                    String gioHoc = cursor.getString(cursor.getColumnIndexOrThrow("GioHoc"));
                    String giangVien = cursor.getString(cursor.getColumnIndexOrThrow("GiangVien"));
                    String trangThai = cursor.getString(cursor.getColumnIndexOrThrow("TrangThai"));
                    int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));

                    // Tạo đối tượng LichHoc và thiết lập các trường thông qua phương thức set
                    LichHoc lichHoc = new LichHoc();  // Tạo đối tượng mặc định
                    lichHoc.setMa(maLichHoc);
                    lichHoc.setNgay(ngayHoc);
                    lichHoc.setGio(gioHoc);
                    lichHoc.setGvien(giangVien);
                    lichHoc.setTrangThai(trangThai);
                    lichHoc.setMon(maMonHoc);

                    lichHocList.add(lichHoc);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return lichHocList;
    }




    // Cập nhật lịch học
    public int updateLichHoc(int maLichHoc, int trangThai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TrangThai", trangThai);
        return db.update("LichHoc", values, "MaLichHoc = ?", new String[]{String.valueOf(maLichHoc)});
    }

    // Xóa lịch học
    public int deleteLichHoc(int maLichHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("LichHoc", "MaLichHoc = ?", new String[]{String.valueOf(maLichHoc)});
    }
    // Tìm kiếm lịch học trong tất cả các cột và trả về danh sách
    public ArrayList<LichHoc> searchLichHoc(String keyword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<LichHoc> lichHocList = new ArrayList<>();

        // Câu truy vấn SQL tìm kiếm trong các trường: NgayHoc, GioHoc, GiangVien, TrangThai, MaMonHoc
        Cursor cursor = db.rawQuery(
                "SELECT * FROM LichHoc WHERE NgayHoc LIKE ? OR GioHoc LIKE ? OR GiangVien LIKE ? OR TrangThai LIKE ? OR MaMonHoc LIKE ? OR MaLichHoc LIKE ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}
        );


        // Kiểm tra và thêm dữ liệu vào danh sách
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maLichHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaLichHoc"));
                String ngayHoc = cursor.getString(cursor.getColumnIndexOrThrow("NgayHoc"));
                String gioHoc = cursor.getString(cursor.getColumnIndexOrThrow("GioHoc"));
                String giangVien = cursor.getString(cursor.getColumnIndexOrThrow("GiangVien"));
                String trangThai = cursor.getString(cursor.getColumnIndexOrThrow("TrangThai"));
                int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));

                // Tạo đối tượng LichHoc
                LichHoc lichHoc = new LichHoc();
                lichHoc.setMa(maLichHoc);
                lichHoc.setNgay(ngayHoc);
                lichHoc.setGio(gioHoc);
                lichHoc.setGvien(giangVien);
                lichHoc.setTrangThai(trangThai);
                lichHoc.setMon(maMonHoc);

                // Thêm vào danh sách
                lichHocList.add(lichHoc);
            } while (cursor.moveToNext());
        }

        // Đóng Cursor
        if (cursor != null) {
            cursor.close();
        }

        return lichHocList; // Trả về danh sách lịch học
    }

}
