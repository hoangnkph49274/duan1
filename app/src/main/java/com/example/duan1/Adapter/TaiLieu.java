package com.example.duan1.Adapter;

import java.io.Serializable;

public class TaiLieu implements Serializable {
    String ma;
    String ten;
    String loai;
    String Ddan;
    String mon;

    public TaiLieu(String ma, String ten, String loai, String ddan, String mon) {
        this.ma = ma;
        this.ten = ten;
        this.loai = loai;
        Ddan = ddan;
        this.mon = mon;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getDdan() {
        return Ddan;
    }

    public void setDdan(String ddan) {
        Ddan = ddan;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }
}
