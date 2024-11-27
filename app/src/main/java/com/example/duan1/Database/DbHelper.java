package com.example.duan1.Database;

import static com.example.duan1.Login_Activity.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, user, null, 1);
    }

    // Lệnh tạo bảng "SinhVien"
    private static final String CREATE_TABLE_SINHVIEN = "CREATE TABLE SinhVien (" +
            "SinhVienID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TenSinhVien TEXT NOT NULL)";

    // Lệnh tạo bảng "MonHoc"
    private static final String CREATE_TABLE_MONHOC = "CREATE TABLE MonHoc (" +
            "MaMonHoc INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TenMonHoc TEXT NOT NULL, " +
            "SoTinChi INTEGER, " +
            "GiangVien TEXT NOT NULL)";

    // Lệnh tạo bảng "BaiTap"
    private static final String CREATE_TABLE_BAITAP = "CREATE TABLE BaiTap (" +
            "MaBaiTap INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TenBaiTap TEXT NOT NULL, " +
            "HanNop TEXT, " +
            "TrangThai TEXT, " +
            "NoiDungBaiTap TEXT, " +
            "MaMonHoc INTEGER, " +
            "FOREIGN KEY (MaMonHoc) REFERENCES MonHoc(MaMonHoc) ON DELETE CASCADE)";

    // Lệnh tạo bảng "TaiLieu"
    private static final String CREATE_TABLE_TAILIEU = "CREATE TABLE TaiLieu (" +
            "MaTaiLieu INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TenTaiLieu TEXT NOT NULL, " +
            "LoaiTaiLieu TEXT, " +
            "DuongDan TEXT, " +
            "MaMonHoc INTEGER, " +
            "FOREIGN KEY (MaMonHoc) REFERENCES MonHoc(MaMonHoc ) ON DELETE CASCADE)";

    // Lệnh tạo bảng "MucTieu"
    private static final String CREATE_TABLE_MUCTIEU = "CREATE TABLE MucTieu (" +
            "MaMucTieu INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NoiDungMucTieu TEXT NOT NULL, " +
            "NgayBatDau TEXT, " +
            "NgayKetThuc TEXT, " +
            "TrangThai INTEGER CHECK(TrangThai IN (0, 1)), " +
            "MaSinhVien INTEGER, " +
            "FOREIGN KEY (MaSinhVien) REFERENCES SinhVien(SinhVienID) ON DELETE CASCADE)";

    // Lệnh tạo bảng "GhiChu"
    private static final String CREATE_TABLE_GHICHU = "CREATE TABLE GhiChu (" +
            "MaGhiChu INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "TenGhiChu TEXT NOT NULL, " +
            "NgayTao TEXT, " +
            "NoiDungGhiChu TEXT, " +
            "MaMonHoc INTEGER, " +
            "FOREIGN KEY (MaMonHoc) REFERENCES MonHoc(MaMonHoc) ON DELETE CASCADE)";

    // Lệnh tạo bảng "LichHoc"
    private static final String CREATE_TABLE_LICHHOC = "CREATE TABLE LichHoc (" +
            "MaLichHoc INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NgayHoc TEXT NOT NULL, " +
            "GioHoc TEXT, " +
            "GiangVien TEXT, " +
            "TrangThai INTEGER CHECK(TrangThai IN (0, 1))," +
            "MaMonHoc INTEGER, " +
            "FOREIGN KEY (MaMonHoc) REFERENCES MonHoc(MaMonHoc) ON DELETE CASCADE)";

    // Lệnh tạo bảng trung gian "SinhVien_MonHoc"
    private static final String CREATE_TABLE_SINHVIEN_MONHOC = "CREATE TABLE SinhVien_MonHoc (" +
            "SinhVienID INTEGER, " +
            "MaMonHoc INTEGER, " +
            "PRIMARY KEY (SinhVienID, MaMonHoc), " +
            "FOREIGN KEY (SinhVienID) REFERENCES SinhVien(SinhVienID) ON DELETE CASCADE, " +
            "FOREIGN KEY (MaMonHoc) REFERENCES MonHoc(MaMonHoc) ON DELETE CASCADE)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SINHVIEN);
        db.execSQL(CREATE_TABLE_MONHOC);
        db.execSQL(CREATE_TABLE_BAITAP);
        db.execSQL(CREATE_TABLE_TAILIEU);
        db.execSQL(CREATE_TABLE_MUCTIEU);
        db.execSQL(CREATE_TABLE_GHICHU);
        db.execSQL(CREATE_TABLE_LICHHOC);

        db.execSQL(CREATE_TABLE_SINHVIEN_MONHOC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SinhVien_MonHoc");
        db.execSQL("DROP TABLE IF EXISTS LichHoc");
        db.execSQL("DROP TABLE IF EXISTS GhiChu");
        db.execSQL("DROP TABLE IF EXISTS MucTieu");
        db.execSQL("DROP TABLE IF EXISTS TaiLieu");
        db.execSQL("DROP TABLE IF EXISTS BaiTap");
        db.execSQL("DROP TABLE IF EXISTS MonHoc");
        db.execSQL("DROP TABLE IF EXISTS SinhVien");
        String sql1 = "INSERT INTO LICHHOC (TrangThai) VALUES (1);";
        String sql2 = "INSERT INTO LICHHOC (TrangThai) VALUES (0);";
        db.execSQL(sql1);
        db.execSQL(sql2);
        String sql3 = "INSERT INTO  MUCTIEU (TrangThai) VALUES (1);";
        String sql4 = "INSERT INTO MUCTIEU (TrangThai) VALUES (0);";
        db.execSQL(sql3);
        db.execSQL(sql4);
        String sql5 = "INSERT INTO SinhVien (MaSinhVien, TenSinhVien) VALUES (1, ''"+user+"');";
        db.execSQL(sql5);

        onCreate(db);
    }
    public long addSinhVien(String tenSinhVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSinhVien", tenSinhVien);
        return db.insert("SinhVien", null, values);
    }
    public long addMonHoc(String tenMonHoc, int soTinChi, String giangVien) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenMonHoc", tenMonHoc);
        values.put("SoTinChi", soTinChi);
        values.put("GiangVien", giangVien);
        return db.insert("MonHoc", null, values);
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.setForeignKeyConstraintsEnabled(true); // Bật ràng buộc khóa ngoại
    }


}
