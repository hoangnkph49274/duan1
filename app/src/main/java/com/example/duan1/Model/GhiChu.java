package com.example.duan1.Model;

import java.io.Serializable;

public class GhiChu implements Serializable {
    int ma;
    String ten;
    String ngay;
    String noiDung;

    int mon;



    public GhiChu() {
    }

    public GhiChu(int ma, String ten, String ngay, String noiDung) {
        this.ma = ma;
        this.ten = ten;
        this.ngay = ngay;
        this.noiDung = noiDung;
    }


    public GhiChu(int ma, String ten, String ngay, String noiDung, int mon) {
        this.ma = ma;
        this.ten = ten;
        this.ngay = ngay;
        this.noiDung = noiDung;
        this.mon = mon;
    }

    public GhiChu(int ma, String ten, String ngay) {
        this.ma = ma;
        this.ten = ten;
        this.ngay = ngay;
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

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getMon() {
        return mon;
    }

    public void setMon(int mon) {
        this.mon = mon;
    }
}
