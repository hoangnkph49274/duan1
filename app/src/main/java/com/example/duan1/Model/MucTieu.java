package com.example.duan1.Model;

import java.io.Serializable;

public class MucTieu implements Serializable {
    int ma;
    String ten;
    String ngay;
    String ngaykt;
    String noidung;
    String trangthai;

    public MucTieu(int ma, String ten, String ngay, String trangthai) {
        this.ma = ma;
        this.ten = ten;
        this.ngay = ngay;
        this.trangthai = trangthai;
    }
    public MucTieu(int ma, String ngay, String ngaykt ,String noidung, String trangthai) {
        this.ma = ma;
        this.ngay = ngay;
        this.ngaykt = ngaykt;
        this.noidung = noidung;
        this.trangthai = trangthai;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getNgaykt() {
        return ngaykt;
    }

    public void setNgaykt(String ngaykt) {
        this.ngaykt = ngaykt;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
