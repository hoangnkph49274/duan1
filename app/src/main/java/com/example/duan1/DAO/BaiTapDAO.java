package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;

public class BaiTapDAO {
    private DbHelper dbHelper;

    public BaiTapDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm bài tập
    public long addBaiTap(String tenBaiTap, String hanNop, String trangThai, String noiDungBaiTap, int maMonHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenBaiTap", tenBaiTap);
        values.put("HanNop", hanNop);
        values.put("TrangThai", trangThai);
        values.put("NoiDungBaiTap", noiDungBaiTap);
        values.put("MaMonHoc", maMonHoc);
        return db.insert("BaiTap", null, values);
    }

    // Lấy danh sách bài tập
    public Cursor getAllBaiTap() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM BaiTap", null);
    }

    // Cập nhật bài tập
    public int updateBaiTap(int maBaiTap, String tenBaiTap, String hanNop, String trangThai, String noiDungBaiTap, int maMonHoc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenBaiTap", tenBaiTap);
        values.put("HanNop", hanNop);
        values.put("TrangThai", trangThai);
        values.put("NoiDungBaiTap", noiDungBaiTap);
        values.put("MaMonHoc", maMonHoc);
        return db.update("BaiTap", values, "MaBaiTap = ?", new String[]{String.valueOf(maBaiTap)});
    }

    // Xóa bài tập
    public int deleteBaiTap(int maBaiTap) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("BaiTap", "MaBaiTap = ?", new String[]{String.valueOf(maBaiTap)});
    }
}

