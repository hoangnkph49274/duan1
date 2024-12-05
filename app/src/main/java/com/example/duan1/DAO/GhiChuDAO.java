package com.example.duan1.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.Database.DbHelper;
import com.example.duan1.Model.GhiChu;

import java.util.ArrayList;

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
    public ArrayList<GhiChu> getAllGhiChu() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<GhiChu> ghiChuList = new ArrayList<>();

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery("SELECT * FROM GhiChu", null);

        // Kiểm tra nếu Cursor có dữ liệu
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Lấy dữ liệu từ mỗi cột trong bảng
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("MaGhiChu"));
                String tenGhiChu = cursor.getString(cursor.getColumnIndexOrThrow("TenGhiChu"));
                String noiDungGhiChu = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungGhiChu"));
                String ngayTao = cursor.getString(cursor.getColumnIndexOrThrow("NgayTao"));

                // Tạo đối tượng GhiChu
                GhiChu ghiChu = new GhiChu(id, tenGhiChu, ngayTao, noiDungGhiChu );

                // Thêm đối tượng vào danh sách
                ghiChuList.add(ghiChu);
            } while (cursor.moveToNext()); // Lặp qua các dòng tiếp theo
        }

        // Đóng Cursor
        if (cursor != null) {
            cursor.close();
        }

        return ghiChuList; // Trả về danh sách
    }

    // Lấy ghi chú theo mã
    public GhiChu getGhiChuById(int maGhiChu) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        GhiChu ghiChu = null;

        // Câu lệnh truy vấn
        String query = "SELECT * FROM GhiChu WHERE MaGhiChu = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maGhiChu)});

        // Kiểm tra nếu Cursor có dữ liệu
        if (cursor != null && cursor.moveToFirst()) {
            String tenGhiChu = cursor.getString(cursor.getColumnIndexOrThrow("TenGhiChu"));
            String noiDungGhiChu = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungGhiChu"));
            String ngayTao = cursor.getString(cursor.getColumnIndexOrThrow("NgayTao"));
            int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));

            // Tạo đối tượng GhiChu
            ghiChu = new GhiChu(maGhiChu, tenGhiChu, ngayTao, noiDungGhiChu, maMonHoc);
        }

        // Đóng Cursor
        if (cursor != null) {
            cursor.close();
        }

        return ghiChu; // Trả về ghi chú
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

    // Tìm kiếm ghi chú trong tất cả các cột và trả về danh sách
    public ArrayList<GhiChu> searchGhiChu(String keyword) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<GhiChu> ghiChuList = new ArrayList<>();

        // Câu truy vấn SQL
        Cursor cursor = db.rawQuery(
                "SELECT * FROM GhiChu WHERE TenGhiChu LIKE ? OR NoiDungGhiChu LIKE ? OR NgayTao LIKE ? OR MaMonHoc LIKE ? OR MaGhiChu LIKE ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"}
        );
        // Kiểm tra và thêm dữ liệu vào danh sách
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("MaGhiChu"));
                String tenGhiChu = cursor.getString(cursor.getColumnIndexOrThrow("TenGhiChu"));
                String noiDungGhiChu = cursor.getString(cursor.getColumnIndexOrThrow("NoiDungGhiChu"));
                String ngayTao = cursor.getString(cursor.getColumnIndexOrThrow("NgayTao"));
                int maMonHoc = cursor.getInt(cursor.getColumnIndexOrThrow("MaMonHoc"));

                // Tạo đối tượng GhiChu
                GhiChu ghiChu = new GhiChu(id, tenGhiChu, ngayTao, noiDungGhiChu, maMonHoc);

                // Thêm vào danh sách
                ghiChuList.add(ghiChu);
            } while (cursor.moveToNext()); // Lặp qua các dòng tiếp theo
        }

        // Đóng Cursor
        if (cursor != null) {
            cursor.close();
        }

        return ghiChuList; // Trả về danh sách
    }

}

