package com.example.duan1.Model;

import java.io.Serializable;

public class LichHoc implements Serializable {
    int ma;
    String ngay;
    String gio;
    String Gvien;
    int mon;
    String TrangThai;

    public LichHoc() {
    }

    public LichHoc(int ma, String ngay, String gio, String gvien, int mon, String trangThai) {
        this.ma = ma;
        this.ngay = ngay;
        this.gio = gio;
        Gvien = gvien;
        this.mon = mon;
        TrangThai = trangThai;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getGvien() {
        return Gvien;
    }

    public void setGvien(String gvien) {
        Gvien = gvien;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
