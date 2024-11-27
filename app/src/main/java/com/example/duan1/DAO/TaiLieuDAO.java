package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.TaiLieu;

import java.util.ArrayList;
import java.util.List;

public class TaiLieuDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public TaiLieuDAO(Context context) {
        dbHelper = new DbHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm tài liệu mới
     */
    public long addTaiLieu(String tenTaiLieu, String loaiTaiLieu, String duongDan, int maMonHoc) {
        ContentValues values = new ContentValues();
        values.put("TenTaiLieu", tenTaiLieu);
        values.put("LoaiTaiLieu", loaiTaiLieu);
        values.put("DuongDan", duongDan);
        values.put("MaMonHoc", maMonHoc);

        return db.insert("TaiLieu", null, values);
    }

    /**
     * Sửa thông tin tài liệu
     */
    public int updateTaiLieu(int maTaiLieu, String tenTaiLieu, String loaiTaiLieu, String duongDan, int maMonHoc) {
        ContentValues values = new ContentValues();
        values.put("TenTaiLieu", tenTaiLieu);
        values.put("LoaiTaiLieu", loaiTaiLieu);
        values.put("DuongDan", duongDan);
        values.put("MaMonHoc", maMonHoc);

        return db.update("TaiLieu", values, "MaTaiLieu = ?", new String[]{String.valueOf(maTaiLieu)});
    }

    /**
     * Xóa tài liệu
     */
    public int deleteTaiLieu(int maTaiLieu) {
        return db.delete("TaiLieu", "MaTaiLieu = ?", new String[]{String.valueOf(maTaiLieu)});
    }

    /**
     * Lấy danh sách tất cả tài liệu
     */
    public List<TaiLieu> getAllTaiLieu() {
        List<TaiLieu> taiLieuList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM TaiLieu", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setMa(cursor.getInt(cursor.getColumnIndexOrThrow("MaTaiLieu")));
                taiLieu.setTen(cursor.getString(cursor.getColumnIndexOrThrow("TenTaiLieu")));
                taiLieu.setLoai(cursor.getString(cursor.getColumnIndexOrThrow("LoaiTaiLieu")));
                taiLieu.setDdan(cursor.getString(cursor.getColumnIndexOrThrow("DuongDan")));
                taiLieu.setMon(cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc")));
                taiLieuList.add(taiLieu);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return taiLieuList;
    }

    /**
     * Lấy danh sách tài liệu theo mã môn học
     */
    public List<TaiLieu> getTaiLieuByMonHoc(int maMonHoc) {
        List<TaiLieu> taiLieuList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM TaiLieu WHERE MaMonHoc = ?", new String[]{String.valueOf(maMonHoc)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TaiLieu taiLieu = new TaiLieu();
                taiLieu.setMa(cursor.getInt(cursor.getColumnIndexOrThrow("MaTaiLieu")));
                taiLieu.setTen(cursor.getString(cursor.getColumnIndexOrThrow("TenTaiLieu")));
                taiLieu.setLoai(cursor.getString(cursor.getColumnIndexOrThrow("LoaiTaiLieu")));
                taiLieu.setDdan(cursor.getString(cursor.getColumnIndexOrThrow("DuongDan")));
                taiLieu.setMon(cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc")));
                taiLieuList.add(taiLieu);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return taiLieuList;
    }
}
