package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.BaiTap;

import java.util.ArrayList;
import java.util.List;

public class BaiTapDAO {
    private DbHelper dbHelper;

    public BaiTapDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Thêm bài tập
    public long addBaiTap(String tenBaiTap, String hanNop, int trangThai, String noiDungBaiTap, int maMonHoc) {
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
    // Lấy bài tập theo ID
    public Cursor getBaiTapById(int maBaiTap) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM BaiTap WHERE MaBaiTap = ?";
        return db.rawQuery(query, new String[]{String.valueOf(maBaiTap)});
    }


    // Cập nhật bài tập
    public int updateBaiTap(int maBaiTap, int trangThai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TrangThai", trangThai);
        return db.update("BaiTap", values, "MaBaiTap = ?", new String[]{String.valueOf(maBaiTap)});
    }

    // Xóa bài tập
    public int deleteBaiTap(int maBaiTap) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("BaiTap", "MaBaiTap = ?", new String[]{String.valueOf(maBaiTap)});
    }
    // Tìm kiếm bài tập theo từ khóa (tìm trong tên hoặc nội dung)
    // Tìm kiếm bài tập trong tất cả các cột và trả về danh sách
    public List<BaiTap> searchBaiTap(String keyword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<BaiTap> baiTapList = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM BaiTap WHERE TenBaiTap LIKE ? OR NoiDungBaiTap LIKE ? OR HanNop LIKE ? OR TrangThai LIKE ? OR MaMonHoc LIKE ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maBaiTap = cursor.getInt(cursor.getColumnIndexOrThrow("MaBaiTap"));
                String tenBaiTap = cursor.getString(cursor.getColumnIndexOrThrow("TenBaiTap"));
                String hanNop = cursor.getString(cursor.getColumnIndexOrThrow("HanNop"));
                int trangThai = cursor.getInt(cursor.getColumnIndexOrThrow("TrangThai"));
                int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));

                BaiTap baiTap = new BaiTap(maBaiTap, tenBaiTap, hanNop, trangThai, maMonHoc);
                baiTapList.add(baiTap);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return baiTapList;
    }


}

