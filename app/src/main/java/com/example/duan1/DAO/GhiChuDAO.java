package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;

public class GhiChuDAO {
    private DbHelper dbHelper;

    public GhiChuDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm ghi chú
    public long addGhiChu(String tenGhiChu, String ngayTao, String noiDungGhiChu, int maMonHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenGhiChu", tenGhiChu);
        values.put("NgayTao", ngayTao);
        values.put("NoiDungGhiChu", noiDungGhiChu);
        values.put("MaMonHoc", maMonHoc);
        return db.insert("GhiChu", null, values);
    }

    // Lấy danh sách ghi chú
    public Cursor getAllGhiChu() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM GhiChu", null);
    }

    // Cập nhật ghi chú
    public int updateGhiChu(int maGhiChu, String tenGhiChu, String ngayTao, String noiDungGhiChu, int maMonHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenGhiChu", tenGhiChu);
        values.put("NgayTao", ngayTao);
        values.put("NoiDungGhiChu", noiDungGhiChu);
        values.put("MaMonHoc", maMonHoc);
        return db.update("GhiChu", values, "MaGhiChu = ?", new String[]{String.valueOf(maGhiChu)});
    }

    // Xóa ghi chú
    public int deleteGhiChu(int maGhiChu) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("GhiChu", "MaGhiChu = ?", new String[]{String.valueOf(maGhiChu)});
    }
}

