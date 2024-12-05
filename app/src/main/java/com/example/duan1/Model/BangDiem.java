package com.example.duan1.Model;

public class BangDiem {
    private int maDiem;
    private int maMonHoc;
    private double diemGiuaKy;
    private double diemCuoiKy;
    private double diemKhac;
    private double diemTongKet;
    private String trangThai;

    // Constructor không tham số
    public BangDiem() {
    }

    // Constructor đầy đủ
    public BangDiem(int maDiem, int maMonHoc, double diemGiuaKy, double diemCuoiKy, double diemKhac, double diemTongKet, String trangThai) {
        this.maDiem = maDiem;
        this.maMonHoc = maMonHoc;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemKhac = diemKhac;
        this.diemTongKet = diemTongKet;
        this.trangThai = trangThai;
    }

    public int getMaDiem() {
        return maDiem;
    }

    public void setMaDiem(int maDiem) {
        this.maDiem = maDiem;
    }

    public int getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(int maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public double getDiemGiuaKy() {
        return diemGiuaKy;
    }

    public void setDiemGiuaKy(double diemGiuaKy) {
        this.diemGiuaKy = diemGiuaKy;
    }

    public double getDiemCuoiKy() {
        return diemCuoiKy;
    }

    public void setDiemCuoiKy(double diemCuoiKy) {
        this.diemCuoiKy = diemCuoiKy;
    }

    public double getDiemKhac() {
        return diemKhac;
    }

    public void setDiemKhac(double diemKhac) {
        this.diemKhac = diemKhac;
    }

    public double getDiemTongKet() {
        return diemTongKet;
    }

    public void setDiemTongKet(double diemTongKet) {
        this.diemTongKet = diemTongKet;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
