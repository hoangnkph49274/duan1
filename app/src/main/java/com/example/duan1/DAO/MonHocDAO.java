package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.MonHoc;

import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {
    private DbHelper dbHelper;

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
        return db.insert("MonHoc", null, values);
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

}
