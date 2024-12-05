package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.BangDiem;

import java.util.ArrayList;
import java.util.List;

public class DiemDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public DiemDAO(Context context) {
        dbHelper = new DbHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm điểm cho môn học
     */
    public long addDiem(int maMonHoc, double diemGiuaKy, double diemCuoiKy, double diemKhac, double diemTongKet, String trangThai) {
        ContentValues values = new ContentValues();
        values.put("MaMonHoc", maMonHoc);
        values.put("DiemGiuaKy", diemGiuaKy);
        values.put("DiemCuoiKy", diemCuoiKy);
        values.put("DiemKhac", diemKhac);
        values.put("DiemTongKet", diemTongKet);
        values.put("TrangThai", trangThai);

        return db.insert("BangDiem", null, values);
    }

    /**
     * Sửa điểm
     */
    public int updateDiem(int maDiem, double diemGiuaKy, double diemCuoiKy, double diemKhac, double diemTongKet, String trangThai) {
        ContentValues values = new ContentValues();
        values.put("DiemGiuaKy", diemGiuaKy);
        values.put("DiemCuoiKy", diemCuoiKy);
        values.put("DiemKhac", diemKhac);
        values.put("DiemTongKet", diemTongKet);
        values.put("TrangThai", trangThai);

        return db.update("BangDiem", values, "MaDiem = ?", new String[]{String.valueOf(maDiem)});
    }

    /**
     * Xóa điểm
     */
    public int deleteDiem(int maDiem) {
        return db.delete("BangDiem", "MaDiem = ?", new String[]{String.valueOf(maDiem)});
    }

    /**
     * Lấy danh sách điểm
     */
    public List<BangDiem> getAllDiem() {
        List<BangDiem> diemList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM BangDiem", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                BangDiem diem = new BangDiem();
                diem.setMaDiem(cursor.getInt(cursor.getColumnIndexOrThrow("MaDiem")));
                diem.setMaMonHoc(cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc")));
                diem.setDiemGiuaKy(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemGiuaKy")));
                diem.setDiemCuoiKy(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemCuoiKy")));
                diem.setDiemKhac(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemKhac")));
                diem.setDiemTongKet(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemTongKet")));
                diem.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
                diemList.add(diem);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return diemList;
    }

    public List<BangDiem> getDiemByTrangThai(String trangThai) {
        List<BangDiem> diemList = new ArrayList<>();
        // Truy vấn điểm theo trạng thái
        Cursor cursor = db.rawQuery("SELECT * FROM BangDiem WHERE TrangThai = ?", new String[]{trangThai});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                BangDiem diem = new BangDiem();
                diem.setMaDiem(cursor.getInt(cursor.getColumnIndexOrThrow("MaDiem")));
                diem.setMaMonHoc(cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc")));
                diem.setDiemGiuaKy(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemGiuaKy")));
                diem.setDiemCuoiKy(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemCuoiKy")));
                diem.setDiemKhac(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemKhac")));
                diem.setDiemTongKet(cursor.getDouble(cursor.getColumnIndexOrThrow("DiemTongKet")));
                diem.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow("TrangThai")));
                diemList.add(diem);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return diemList;
    }

}

