package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.MucTieu;

import java.util.ArrayList;
import java.util.List;

public class MucTieuDAO {
    private DbHelper dbHelper;

    public MucTieuDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm mục tiêu
    public long addMucTieu(String noiDungMucTieu, String ngayBatDau, String ngayKetThuc, int trangThai, int maSinhVien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NoiDungMucTieu", noiDungMucTieu);
        values.put("NgayBatDau", ngayBatDau);
        values.put("NgayKetThuc", ngayKetThuc);
        values.put("TrangThai", trangThai);
        values.put("MaSinhVien", maSinhVien);
        return db.insert("MucTieu", null, values);
    }

    // Lấy tất cả mục tiêu
    // Lấy danh sách tất cả mục tiêu
    public List<MucTieu> getAllMucTieu() {
        List<MucTieu> mucTieuList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MucTieu", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int ma = cursor.getInt(cursor.getColumnIndexOrThrow("MaMucTieu"));
                String noidung = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungMucTieu"));
                String ngay = cursor.getString(cursor.getColumnIndexOrThrow("NgayBatDau"));
                String ngayKt = cursor.getString(cursor.getColumnIndexOrThrow("NgayKetThuc"));
                String trangThai = cursor.getString(cursor.getColumnIndexOrThrow("TrangThai"));

                mucTieuList.add(new MucTieu(ma, ngay,ngayKt,noidung, trangThai));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return mucTieuList;
    }
    // Lấy mục tiêu theo mã sinh viên
    public Cursor getMucTieuBySinhVien(int maSinhVien) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM MucTieu WHERE MaSinhVien = ?", new String[]{String.valueOf(maSinhVien)});
    }

    // Cập nhật mục tiêu
    public int updateMucTieu(int maMucTieu,String noiDung, String ngayBatDau, String ngayKetThuc, int trangThai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NoiDungMucTieu", noiDung);
        values.put("NgayBatDau", ngayBatDau);
        values.put("NgayKetThuc", ngayKetThuc);
        values.put("TrangThai", trangThai);
        return db.update("MucTieu", values, "MaMucTieu = ?", new String[]{String.valueOf(maMucTieu)});
    }
    public MucTieu getMucTieuById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("MucTieu", null, "MaMucTieu=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int mucTieuId = cursor.getInt(cursor.getColumnIndexOrThrow("MaMucTieu"));
            String ngayBatDau = cursor.getString(cursor.getColumnIndexOrThrow("NgayBatDau"));
            String ngayKetThuc = cursor.getString(cursor.getColumnIndexOrThrow("NgayKetThuc"));
            String trangThai = cursor.getString(cursor.getColumnIndexOrThrow("TrangThai"));
            String noiDung = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungMucTieu"));
            cursor.close();
            return new MucTieu(mucTieuId, ngayBatDau, ngayKetThuc,noiDung, trangThai);
        }
        return null;
    }

    // Xóa mục tiêu
    public int deleteMucTieu(int maMucTieu) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("MucTieu", "MaMucTieu = ?", new String[]{String.valueOf(maMucTieu)});
    }
}

