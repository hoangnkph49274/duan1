package com.example.duan1.Adapter;

import java.io.Serializable;

public class LichHoc implements Serializable {
    String ma;
    String ngay;
    String gio;
    String Gvien;
    String mon;
    String TrangThai;

    public LichHoc(String ma, String ngay, String gio, String gvien, String mon, String trangThai) {
        this.ma = ma;
        this.ngay = ngay;
        this.gio = gio;
        Gvien = gvien;
        this.mon = mon;
        TrangThai = trangThai;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
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

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
}
