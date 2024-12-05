package com.example.duan1.Model;

public class HocPhi {
    private int maHocPhi;
    private int maMonHoc;
    private double soTien;
    private int soLanDaHoc;
    private double soTienDaDong;

    // Constructor không tham số
    public HocPhi() {}

    // Constructor đầy đủ
    public HocPhi(int maHocPhi, int maMonHoc, double soTien, int soLanDaHoc, double soTienDaDong) {
        this.maHocPhi = maHocPhi;
        this.maMonHoc = maMonHoc;
        this.soTien = soTien;
        this.soLanDaHoc = soLanDaHoc;
        this.soTienDaDong = soTienDaDong;
    }

    // Getter và Setter
    public int getMaHocPhi() {
        return maHocPhi;
    }

    public void setMaHocPhi(int maHocPhi) {
        this.maHocPhi = maHocPhi;
    }

    public int getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(int maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public int getSoLanDaHoc() {
        return soLanDaHoc;
    }

    public void setSoLanDaHoc(int soLanDaHoc) {
        this.soLanDaHoc = soLanDaHoc;
    }

    public double getSoTienDaDong() {
        return soTienDaDong;
    }

    public void setSoTienDaDong(double soTienDaDong) {
        this.soTienDaDong = soTienDaDong;
    }
}
