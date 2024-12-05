package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.HocPhi;

import java.util.ArrayList;
import java.util.List;

public class HocPhiDAO {
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    public HocPhiDAO(Context context) {
        dbHelper = new DbHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Thêm thông tin học phí
     */
    public long addHocPhi(int maMonHoc, int soTien, int soLanDaHoc, int soTienDaDong) {
        ContentValues values = new ContentValues();
        values.put("MaMonHoc", maMonHoc);
        values.put("SoTien", soTien);
        values.put("SoLanDaHoc", soLanDaHoc);
        values.put("SoTienDaDong", soTienDaDong);

        return db.insert("HocPhi", null, values);
    }

    /**
     * Sửa thông tin học phí
     */
    public int updateHocPhi(int maHocPhi,  int soLanDaHoc, double soTienDaDong) {
        ContentValues values = new ContentValues();
        values.put("SoLanDaHoc", soLanDaHoc);
        values.put("SoTienDaDong", soTienDaDong);
        return db.update("HocPhi", values, "MaHocPhi = ?", new String[]{String.valueOf(maHocPhi)});
    }

    /**
     * Xóa thông tin học phí
     */
    public int deleteHocPhi(int maHocPhi) {
        return db.delete("HocPhi", "MaHocPhi = ?", new String[]{String.valueOf(maHocPhi)});
    }

    /**
     * Lấy danh sách học phí
     */
    public List<HocPhi> getAllHocPhi() {
        List<HocPhi> hocPhiList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM HocPhi", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                HocPhi hocPhi = new HocPhi();
                hocPhi.setMaHocPhi(cursor.getInt(cursor.getColumnIndexOrThrow("MaHocPhi")));
                hocPhi.setMaMonHoc(cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc")));
                hocPhi.setSoTien(cursor.getInt(cursor.getColumnIndexOrThrow("SoTien")));
                hocPhi.setSoLanDaHoc(cursor.getInt(cursor.getColumnIndexOrThrow("SoLanDaHoc")));
                hocPhi.setSoTienDaDong(cursor.getInt(cursor.getColumnIndexOrThrow("SoTienDaDong")));
                hocPhiList.add(hocPhi);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return hocPhiList;
    }

}
